package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.Banner;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-03-29
 */
public interface BannerService extends IService<Banner> {

    R saveBanner(Banner banner);

    R pageBanner(Integer current, Integer size);

    R removeBanner(Integer bannerId);

    R enableBanner(Integer bannerId, Integer isEnable);

    R listBanner();
}
