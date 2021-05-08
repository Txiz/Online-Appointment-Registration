package com.xzx.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *
 */
@Slf4j
public final class HttpUtil {

    static final String POST = "POST";
    static final String GET = "GET";
    static final int CONN_TIMEOUT = 30000;// ms
    static final int READ_TIMEOUT = 30000;// ms

    /**
     * post 方式发送http请求.
     */
    public static byte[] doPost(String strUrl, byte[] reqData) {
        return send(strUrl, POST, reqData);
    }

    /**
     * get方式发送http请求.
     */
    public static byte[] doGet(String strUrl) {
        return send(strUrl, GET, null);
    }

    private static byte[] send(String strUrl, String method, byte[] reqData) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setUseCaches(false);
            http.setInstanceFollowRedirects(true);
            http.setConnectTimeout(CONN_TIMEOUT);
            http.setReadTimeout(READ_TIMEOUT);
            http.setRequestMethod(method);
            http.connect();
            if (method.equalsIgnoreCase(POST)) {
                OutputStream os = http.getOutputStream();
                os.write(reqData);
                os.flush();
                os.close();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder bankXmlBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                bankXmlBuffer.append(inputLine);
            }
            in.close();
            http.disconnect();
            return bankXmlBuffer.toString().getBytes();
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            return null;
        }
    }

    /**
     * 从输入流中读取数据
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();// 网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
