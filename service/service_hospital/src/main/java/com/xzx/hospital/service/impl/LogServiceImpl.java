package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.hospital.mapper.LogMapper;
import com.xzx.hospital.service.LogService;
import com.xzx.model.entity.Log;
import com.xzx.model.vo.LogQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-05-14
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public R saveLog(Log log) {
        return save(log) ? R.ok().message("保存日志成功！") : R.error().message("保存日志失败！");
    }

    @Override
    public R pageLog(Integer current, Integer size, LogQueryVo logQueryVo) {
        Integer logType = logQueryVo.getLogType();
        String username = logQueryVo.getUsername();
        String uri = logQueryVo.getUri();
        String method = logQueryVo.getMethod();
        String ip = logQueryVo.getIp();
        String ipSource = logQueryVo.getIpSource();
        String os = logQueryVo.getOs();
        String browser = logQueryVo.getBrowser();
        // 构造分页对象
        Page<Log> logPage = new Page<>(current, size);
        // 构造查询条件
        QueryWrapper<Log> wrapper = new QueryWrapper<>();
        if (logType != null) wrapper.like("log_type", logType);
        if (!StringUtils.isEmpty(username)) wrapper.eq("username", username);
        if (!StringUtils.isEmpty(uri)) wrapper.eq("uri", uri);
        if (!StringUtils.isEmpty(method)) wrapper.eq("method", method);
        if (!StringUtils.isEmpty(ip)) wrapper.eq("ip", ip);
        if (!StringUtils.isEmpty(ipSource)) wrapper.eq("ip_source", ipSource);
        if (!StringUtils.isEmpty(os)) wrapper.eq("os", os);
        if (!StringUtils.isEmpty(browser)) wrapper.eq("browser", browser);
        // 调用分页方法
        page(logPage, wrapper);
        // 封装结果集
        Map<String, Object> map = new HashMap<>();
        map.put("total", logPage.getTotal());
        map.put("logPageList", logPage.getRecords());
        return R.ok().data(map).message("分页查询所有日志列表成功！");
    }

    @Override
    public R removeLog(Integer logId) {
        return removeById(logId) ? R.ok().message("登录日志删除成功!") : R.error().message("登录日志删除失败!");
    }
}
