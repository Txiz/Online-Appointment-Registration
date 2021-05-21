package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.hospital.mapper.BannerMapper;
import com.xzx.hospital.service.BannerService;
import com.xzx.model.entity.Banner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-03-29
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public R saveBanner(Banner banner) {
        return save(banner) ? R.ok().message("保存轮播图成功！") : R.error().message("保存轮播图失败！");
    }

    @Override
    public R pageBanner(Integer current, Integer size) {
        Page<Banner> bannerPage = new Page<>(current, size);
        page(bannerPage);
        Map<String, Object> map = new HashMap<>();
        map.put("total", bannerPage.getTotal());
        map.put("bannerList", bannerPage.getRecords());
        return R.ok().data(map).message("分页查询轮播图成功!");
    }

    @Override
    public R removeBanner(Integer bannerId) {
        return removeById(bannerId) ? R.ok().message("轮播图删除成功!") : R.error().message("轮播图删除失败!");
    }

    @Override
    public R enableBanner(Integer bannerId, Integer isEnable) {
        Banner banner = getById(bannerId);
        banner.setIsEnable(isEnable);
        return updateById(banner) ? R.ok().message("启用轮播图成功！") : R.ok().message("启用轮播图失败！");
    }

    @Override
    public R listBanner() {
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("is_enable", 1);
        return R.ok().data("bannerList", list(wrapper)).message("查询轮播图列表成功！");
    }
}
