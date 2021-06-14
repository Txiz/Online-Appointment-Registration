package com.xzx.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.constant.OrderStatusEnum;
import com.xzx.common.result.R;
import com.xzx.common.util.HttpRequestUtil;
import com.xzx.common.util.MD5Util;
import com.xzx.hospital.mapper.HospitalSetMapper;
import com.xzx.hospital.mapper.OrderMapper;
import com.xzx.hospital.mapper.PatientInfoMapper;
import com.xzx.hospital.mapper.UserInfoMapper;
import com.xzx.hospital.repository.DepartmentRepository;
import com.xzx.hospital.repository.HospitalInfoRepository;
import com.xzx.hospital.repository.ScheduleRepository;
import com.xzx.hospital.service.OrderService;
import com.xzx.model.entity.*;
import com.xzx.model.vo.OrderCountQueryVo;
import com.xzx.model.vo.OrderCountVo;
import com.xzx.model.vo.OrderQueryVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-06-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PatientInfoMapper patientInfoMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Resource
    private HospitalInfoRepository hospitalInfoRepository;

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public R saveOrder(String scheduleId, Integer patientId) {
        // 获取就诊人信息
        PatientInfo patientInfo = patientInfoMapper.selectById(patientId);
        // 获取排班相关信息
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        HospitalInfo hospitalInfo = hospitalInfoRepository.getHospitalInfoByHospitalCode(schedule.getHospitalCode());
        BookingRule bookingRule = hospitalInfo.getBookingRule();
        DateTime startTime = getDateTime(new Date(), bookingRule.getReleaseTime());
        DateTime endTime = getDateTime(new DateTime().plusDays(bookingRule.getCycle()).toDate(), bookingRule.getStopTime());
        // 判断当前时间是否还可以预约
        if (startTime.isAfterNow() || endTime.isBeforeNow())
            return R.error().message("当前时间已不可预约！");
        Integer availableNumber = schedule.getAvailableNumber();
        // 判断当前剩余预约数是否是0
        if (availableNumber <= 0) return R.error().message("剩余预约号为0！");
        // 获取医院请求地址
        String hospitalApiUrl = hospitalSetMapper.getHospitalApiUrl(schedule.getHospitalCode());
        // 获取签名密钥并加密
        String signKey = hospitalSetMapper.getSignKey(schedule.getHospitalCode());
        signKey = MD5Util.encrypt(signKey);
        // 创建订单
        Order order = new Order();
        String outTraderNumber = System.currentTimeMillis() + "" + new Random().nextInt(100);
        order.setUserId(patientInfo.getUserId());
        order.setOutTradeNumber(outTraderNumber);
        order.setHospitalCode(schedule.getHospitalCode());
        order.setHospitalName(hospitalInfo.getHospitalName());
        order.setDepartmentCode(schedule.getDepartmentCode());
        order.setDepartmentName(departmentRepository.getDepartmentByHospitalCodeAndDepartmentCode(schedule.getHospitalCode(), schedule.getDepartmentCode()).getDepartmentName());
        order.setTitle(schedule.getTitle());
        order.setScheduleId(schedule.getHospitalScheduleId());
        order.setReserveDate(schedule.getWorkDate());
        order.setReserveTime(schedule.getWorkTime());
        order.setPatientId(patientId);
        order.setPatientName(patientInfo.getRealName());
        order.setPatientPhone(patientInfo.getPhone());
        order.setAmount(schedule.getAmount());
        order.setOrderStatus(OrderStatusEnum.UNPAID.getStatus());
        order.getParam().put("orderStatusString", OrderStatusEnum.getStatusNameByStatus(order.getOrderStatus()));
        // 保存订单
        if (!save(order)) return R.error().message("订单创建失败！");
        // 构造请求参数
        Map<String, Object> requestParameter = new HashMap<>();
        requestParameter.put("hospitalScheduleId", order.getScheduleId());
        requestParameter.put("signKey", signKey);
        // 调用三方医院接口，实现预约挂号操作
        JSONObject result = HttpRequestUtil.sendRequest(requestParameter, hospitalApiUrl + "/imitate/order/createOrder");
        if (result.getInteger("code") == 20000) {
            // 获得返回结果
            JSONObject data = result.getJSONObject("data");
            if (ObjectUtils.isEmpty(data)) throw new RuntimeException("远程订单返回结果异常！");
            String id = data.getString("id");
            Integer reserved = data.getInteger("reservedNumber");
            Integer available = data.getInteger("availableNumber");
            Integer number = data.getInteger("number");
            String fetchTime = data.getString("fetchTime");
            String fetchAddress = data.getString("fetchAddress");
            Date quitTime = data.getDate("quitTime");
            // 更新订单信息
            order.setHospitalRecordId(id);
            order.setNumber(number);
            order.setFetchTime(fetchTime);
            order.setFetchAddress(fetchAddress);
            order.setQuitTime(quitTime);
            if (!updateById(order)) throw new RuntimeException("本地订单更新失败！");
            // 更新排班
            schedule.setReservedNumber(reserved);
            schedule.setAvailableNumber(available);
            schedule.setUpdateTime(new Date());
            scheduleRepository.save(schedule);
        } else {
            throw new RuntimeException("远程订单请求失败！");
        }
        return R.ok().data("orderId", order.getId()).message("订单保存成功！");
    }

    private DateTime getDateTime(Date date, String timeString) {
        String dateTimeString = new DateTime(date).toString("yyyy-MM-dd") + " " + timeString;
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").parseDateTime(dateTimeString);
    }

    @Override
    public R pageOrder(Integer current, Integer size, OrderQueryVo orderQueryVo) {
        // 设置当前用户
        String username = orderQueryVo.getUsername();
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("phone", username);
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        // 获取条件查询参数
        String hospitalName = orderQueryVo.getKeyword();
        Integer patientId = orderQueryVo.getPatientId();
        String orderStatus = orderQueryVo.getOrderStatus();
        String reserveDate = orderQueryVo.getReserveDate();
        String beginTime = orderQueryVo.getBeginTime();
        String endTime = orderQueryVo.getEndTime();
        // 构造条件查询器
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(hospitalName)) wrapper.like("hospital_name", hospitalName);
        if (patientId != null) wrapper.eq("patient_id", patientId);
        if (!StringUtils.isEmpty(orderStatus)) wrapper.eq("order_status", orderStatus);
        if (!StringUtils.isEmpty(reserveDate)) wrapper.ge("reserve_date", reserveDate);
        if (!StringUtils.isEmpty(beginTime)) wrapper.le("create_time", beginTime);
        if (!StringUtils.isEmpty(endTime)) wrapper.le("create_time", endTime);
        wrapper.eq("user_id", userInfo.getId());
        // 分页条件查询
        Page<Order> orderPage = new Page<>(current, size);
        page(orderPage, wrapper);
        orderPage.getRecords().forEach(item -> item.getParam().put("orderStatusString", OrderStatusEnum.getStatusNameByStatus(item.getOrderStatus())));
        return R.ok().data("orderList", orderPage.getRecords()).data("total", orderPage.getTotal()).message("分页查询所有订单列表成功！");
    }

    @Override
    public R getOrder(Integer orderId) {
        return R.ok().data("order", getById(orderId)).message("根据表id查询订单信息成功！");
    }

    @Override
    public R listOrderStatus() {
        return R.ok().data("orderStatus", OrderStatusEnum.getStatusList()).message("获取订单状态成功！");
    }

    @Override
    public R cancelOrder(Integer orderId) {
        return R.ok().message("测试成功！");
    }

    @Override
    public R getOrderStatistics(OrderCountQueryVo orderCountQueryVo) {
        List<OrderCountVo> orderCountVoList = orderMapper.getOrderStatistics(orderCountQueryVo);
        Map<String, Object> map = new HashMap<>();
        map.put("dateList", orderCountVoList.stream().map(OrderCountVo::getReserveDate).collect(Collectors.toList()));
        map.put("countList", orderCountVoList.stream().map(OrderCountVo::getCount).collect(Collectors.toList()));
        return R.ok().data(map).message("获取订单统计数据成功！");
    }
}
