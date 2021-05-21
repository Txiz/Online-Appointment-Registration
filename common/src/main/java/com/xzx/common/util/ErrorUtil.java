package com.xzx.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误处理工具类
 * 作者: xzx
 * 创建时间: 2021-03-28-23-41
 **/
public class ErrorUtil {
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
