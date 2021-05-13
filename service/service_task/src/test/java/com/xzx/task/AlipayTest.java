package com.xzx.task;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserInfoAuthRequest;
import com.alipay.api.response.AlipayUserInfoAuthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.xzx.common.constant.AlipayConstant.*;

/**
 * 作者: xzx
 * 创建时间: 2021-05-12-23-29
 **/
@SpringBootTest
public class AlipayTest {

    @Test
    public void test1() {
        AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayUserInfoAuthRequest request = new AlipayUserInfoAuthRequest();
        request.setBizContent("{" +
                "      \"scopes\":[" +
                "        \"auth_user\"" +
                "      ]," +
                "\"state\":\"init\"" +
                "  }");
        try {
            AlipayUserInfoAuthResponse response = alipayClient.pageExecute(request);
            System.out.println("response = " + response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

}
