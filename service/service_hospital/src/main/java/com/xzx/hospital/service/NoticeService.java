package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.Notice;
import com.xzx.model.vo.NoticeQueryVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-05-23
 */
public interface NoticeService extends IService<Notice> {

    R saveNotice(Notice notice);

    R pageNotice(Integer current, Integer size, NoticeQueryVo noticeQueryVo);

    R getNotice(Integer noticeId);

    R removeNotice(Integer noticeId);

    R enableNotice(Integer noticeId, Integer isEnable);

    R listNotice();
}
