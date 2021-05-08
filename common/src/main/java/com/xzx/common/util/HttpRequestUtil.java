package com.xzx.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class HttpRequestUtil {

    public static void main(String[] args) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("d", "4");
        paramMap.put("b", "2");
        paramMap.put("c", "3");
        paramMap.put("a", "1");
        paramMap.put("timestamp", getTimestamp());
        log.info(getSign(paramMap, "111111111"));
    }

    public static Map<String, Object> switchMap(Map<String, String[]> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            resultMap.put(param.getKey(), param.getValue()[0]);
        }
        return resultMap;
    }

    /**
     * 请求数据获取签名
     */
    public static String getSign(Map<String, Object> paramMap, String signKey) {
        paramMap.remove("sign");
        TreeMap<String, Object> sorted = new TreeMap<>(paramMap);
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, Object> param : sorted.entrySet()) {
            str.append(param.getValue()).append("|");
        }
        str.append(signKey);
        log.info("加密前：" + str);
        String md5Str = MD5Util.encrypt(str.toString());
        log.info("加密后：" + md5Str);
        return md5Str;
    }

    /**
     * 签名校验
     */
    public static boolean isSignEquals(Map<String, Object> paramMap, String signKey) {
        String sign = (String) paramMap.get("sign");
        String md5Str = getSign(paramMap, signKey);
        return sign.equals(md5Str);
    }

    /**
     * 获取时间戳
     */
    public static long getTimestamp() {
        return new Date().getTime();
    }

    /**
     * 封装同步请求
     */
    public static JSONObject sendRequest(Map<String, Object> paramMap, String url) {
        String result = "";
        try {
            //封装post参数
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : paramMap.entrySet()) {
                postData.append(param.getKey()).append("=")
                        .append(param.getValue()).append("&");
            }
            log.info(String.format("--> 发送请求：post data %1s", postData));
            byte[] reqData = postData.toString().getBytes(StandardCharsets.UTF_8);
            byte[] requestData = HttpUtil.doPost(url, reqData);
            result = new String(requestData);
            log.info(String.format("--> 应答结果：result data %1s", result));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return JSONObject.parseObject(result);
    }
}
