package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.hospital.mapper.NoticeMapper;
import com.xzx.hospital.service.NoticeService;
import com.xzx.model.entity.Notice;
import com.xzx.model.vo.NoticeQueryVo;
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
 * @since 2021-05-23
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public R saveNotice(Notice notice) {
        return save(notice) ? R.ok().message("保存公告成功！") : R.error().message("保存公告失败！");
    }

    @Override
    public R pageNotice(Integer current, Integer size, NoticeQueryVo noticeQueryVo) {
        Integer noticeType = noticeQueryVo.getNoticeType();
        String noticeTitle = noticeQueryVo.getNoticeTitle();
        Integer isEnable = noticeQueryVo.getIsEnable();
        String beginTime = noticeQueryVo.getBeginTime();
        String endTime = noticeQueryVo.getEndTime();
        Page<Notice> noticePage = new Page<>(current, size);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (noticeType != null) wrapper.like("notice_type", noticeType);
        if (!StringUtils.isEmpty(noticeTitle)) wrapper.eq("notice_title", noticeTitle);
        if (isEnable != null) wrapper.like("is_enable", isEnable);
        if (!StringUtils.isEmpty(beginTime)) wrapper.ge("create_time", beginTime);
        if (!StringUtils.isEmpty(endTime)) wrapper.le("create_time", endTime);
        page(noticePage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", noticePage.getTotal());
        map.put("noticeList", noticePage.getRecords());
        return R.ok().data(map).message("分页查询公告成功!");
    }

    @Override
    public R getNotice(Integer noticeId) {
        return R.ok().data("notice", getById(noticeId)).message("查询id为：" + noticeId + " 的公告成功！");
    }

    @Override
    public R removeNotice(Integer noticeId) {
        return removeById(noticeId) ? R.ok().message("公告删除成功!") : R.error().message("公告删除失败!");
    }

    @Override
    public R enableNotice(Integer noticeId, Integer isEnable) {
        Notice notice = getById(noticeId);
        notice.setIsEnable(isEnable);
        return updateById(notice) ? R.ok().message("启用公告成功！") : R.ok().message("启用公告失败！");
    }

    @Override
    public R listNotice() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("is_enable", 1);
        return R.ok().data("noticeList", list(wrapper)).message("查询公告列表成功！");
    }
}
