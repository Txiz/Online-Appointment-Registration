package com.xzx.common.util;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * UserAgent解析工具类
 * 作者: xzx
 * 创建时间: 2021-03-14-13-38
 **/
@Component
public class UserAgentUtil {
    private UserAgentAnalyzer uaa;

    public UserAgentUtil() {
        this.uaa = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withField("OperatingSystemNameVersionMajor")
                .withField("AgentNameVersion")
                .build();
    }

    /**
     * 从User-Agent解析客户端操作系统和浏览器版本
     *
     * @param userAgent 客户端信息
     * @return map
     */
    public Map<String, String> parseOsAndBrowser(String userAgent) {
        UserAgent agent = uaa.parse(userAgent);
        String os = agent.getValue("OperatingSystemNameVersionMajor");
        String browser = agent.getValue("AgentNameVersion");
        Map<String, String> map = new HashMap<>();
        map.put("os", os);
        map.put("browser", browser);
        return map;
    }
}
