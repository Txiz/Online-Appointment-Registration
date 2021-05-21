package com.xzx.hospital.aspect;

import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.util.AuthUtil;
import com.xzx.common.util.ErrorUtil;
import com.xzx.common.util.IpUtil;
import com.xzx.common.util.UserAgentUtil;
import com.xzx.hospital.service.LogService;
import com.xzx.model.entity.Log;
import com.xzx.model.vo.UserLoginVo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 日志切面
 * 作者: xzx
 * 创建时间: 2021-05-19-13-52
 **/
@Component
@Aspect
public class LogAspect {

    @Resource
    private LogService logService;

    @Resource
    private UserAgentUtil userAgentUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut(value = "@annotation(logAnnotation)", argNames = "logAnnotation")
    public void pointCut(LogAnnotation logAnnotation) {
    }

    @Pointcut(value = "execution(* com.xzx.hospital.controller..*.*(..))")
    public void exceptionPointCut() {

    }

    @Around(value = "pointCut(logAnnotation)", argNames = "joinPoint,logAnnotation")
    public Object around(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object res = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        // 方法执行时间
        int times = (int) (endTime - startTime);
        Log log = logHandler(joinPoint, logAnnotation, times);
        logService.saveLog(log);
        return res;
    }

    @AfterThrowing(value = "exceptionPointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        Log log = logHandler(joinPoint, exception);
        logService.saveLog(log);
        Integer exceptionNum = (Integer) redisTemplate.opsForValue().get("exception_num");
        if (exceptionNum == null) exceptionNum = 0;
        redisTemplate.opsForValue().set("exception_num", exceptionNum + 1);
    }

    private Log logHandler(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation, int times) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Object[] args = joinPoint.getArgs();
        int type = logAnnotation.type();
        String username;
        if (type == 0)
            username = ((UserLoginVo) args[0]).getPhone();
        else
            username = AuthUtil.getUsername(request);
        String ip = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getCityInfo(ip);
        Map<String, String> userAgent = userAgentUtil.parseOsAndBrowser(request.getHeader("User-Agent"));
        // 构建日志对象并返回
        Log log = new Log();
        log.setLogType(type);
        log.setUsername(username);
        log.setIp(ip);
        log.setIpSource(ipSource);
        log.setOs(userAgent.get("os"));
        log.setBrowser(userAgent.get("browser"));
        log.setTimes(times);
        if (type == 1 || type == 3) {
            log.setUri(request.getRequestURI());
            log.setMethod(request.getMethod());
            log.setDescription(logAnnotation.description());
        }
        return log;
    }

    private Log logHandler(JoinPoint joinPoint, Exception exception) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String username = AuthUtil.getUsername(request);
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String error = ErrorUtil.getStackTrace(exception);
        String ip = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getCityInfo(ip);
        Map<String, String> userAgent = userAgentUtil.parseOsAndBrowser(request.getHeader("User-Agent"));
        // 构建日志对象并返回
        Log log = new Log();
        log.setLogType(2);
        log.setUsername(username);
        log.setUri(uri);
        log.setMethod(method);
        log.setError(error);
        log.setIp(ip);
        log.setIpSource(ipSource);
        log.setOs(userAgent.get("os"));
        log.setBrowser(userAgent.get("browser"));
        return log;
    }
}
