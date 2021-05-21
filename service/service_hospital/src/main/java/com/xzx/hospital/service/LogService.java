package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.Log;
import com.xzx.model.vo.LogQueryVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-05-14
 */
public interface LogService extends IService<Log> {

    R saveLog(Log log);

    R pageLog(Integer current, Integer size, LogQueryVo logQueryVo);

    R removeLog(Integer logId);
}
