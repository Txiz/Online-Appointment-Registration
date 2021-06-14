package com.xzx.task.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xzx.common.result.R;
import com.xzx.model.entity.Order;
import com.xzx.task.client.HospitalClient;
import com.xzx.task.service.AlipayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.xzx.common.constant.AlipayConstant.*;

/**
 * 支付宝当面付服务实现
 * 作者: Txiz
 * 创建时间: 2021-06-14
 **/
@Service
public class AlipayServiceImpl implements AlipayService {

    @Resource
    private HospitalClient hospitalClient;

    @Override
    public R pay(Integer orderId) {
        Map<String, Object> orderMap = hospitalClient.getOrder(orderId).getData();
        String jsonString = JSONObject.toJSONString(orderMap.get("order"));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String amount = jsonObject.getString("amount");
        String outTradeNumber = jsonObject.getString("outTradeNumber");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(outTradeNumber);
            model.setTotalAmount(amount);
            model.setSubject("挂号");
            model.setTimeoutExpress("90m");
            request.setBizModel(model);
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return R.ok().data("qrCode", response.getQrCode()).data("outTradeNumber", response.getOutTradeNo()).message(response.getMsg());
            } else
                return R.error().message(response.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.error().message("尚未支付");
    }

    @Override
    public R queryPayStatus(Integer orderId) {
        Map<String, Object> orderMap = hospitalClient.getOrder(orderId).getData();
        String jsonString = JSONObject.toJSONString(orderMap.get("order"));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String amount = jsonObject.getString("amount");
        String outTradeNumber = jsonObject.getString("outTradeNumber");
        Integer orderStatus = jsonObject.getInteger("orderStatus");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(outTradeNumber);
            request.setBizModel(model);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.getCode().equals("10000") && orderStatus == 0) {
                Order order = new Order();
                order.setId(orderId);
                order.setOrderStatus(1);
                hospitalClient.updateOrder(order);
            }
            return R.ok().message(response.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.error().message("查询失败！");
    }

    @Override
    public R refund(Integer orderId) {
        Map<String, Object> orderMap = hospitalClient.getOrder(orderId).getData();
        String jsonString = JSONObject.toJSONString(orderMap.get("order"));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String amount = jsonObject.getString("amount");
        String outTradeNumber = jsonObject.getString("outTradeNumber");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(outTradeNumber);
            model.setRefundAmount(amount);
            request.setBizModel(model);
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            return R.ok().message(response.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.error().message("查询失败！");
    }
}
