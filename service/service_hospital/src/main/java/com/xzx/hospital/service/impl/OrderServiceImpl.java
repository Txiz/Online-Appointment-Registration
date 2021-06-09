package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.constant.OrderStatusEnum;
import com.xzx.common.result.R;
import com.xzx.hospital.mapper.OrderMapper;
import com.xzx.hospital.mapper.PatientInfoMapper;
import com.xzx.hospital.mapper.UserInfoMapper;
import com.xzx.hospital.repository.HospitalInfoRepository;
import com.xzx.hospital.repository.ScheduleRepository;
import com.xzx.hospital.service.OrderService;
import com.xzx.model.entity.*;
import com.xzx.model.vo.OrderCountQueryVo;
import com.xzx.model.vo.OrderCountVo;
import com.xzx.model.vo.OrderQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private HospitalInfoRepository hospitalInfoRepository;

    @Resource
    private ScheduleRepository scheduleRepository;

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

        // 创建订单
        // 保存订单
        Order order = new Order();

        return R.ok().message("测试成功！");
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
