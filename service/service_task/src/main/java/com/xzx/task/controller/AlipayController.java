package com.xzx.task.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xzx.common.result.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xzx.common.constant.AlipayConstant.*;

/**
 * 支付宝控制器
 * 作者: xzx
 * 创建时间: 2021-05-13-10-55
 **/
@RestController
@RequestMapping("/task/alipay")
@Api(tags = "支付宝控制器")
public class AlipayController {

    @GetMapping("/auth")
    public R auth() {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\"201503280101003002\"," +
                    "\"total_amount\":\"88.88\"," +
                    "\"subject\":\"Iphone6 16G\"," +
                    "\"store_id\":\"NJ_001\"," +
                    "\"timeout_express\":\"90m\"}");
            AlipayTradePrecreateResponse response;
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return R.ok().data("qrCode", response.getQrCode()).data("outTradeNumber", response.getOutTradeNo()).message(response.getMsg());
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    @GetMapping("/queryPayStatus")
    public R queryPayStatus() {
        AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo("201503280101003002");
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            return R.ok().message(response.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
