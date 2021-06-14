package com.xzx.hospital.controller;


import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.UserInfoService;
import com.xzx.model.vo.UserAuthVo;
import com.xzx.model.vo.UserLoginVo;
import com.xzx.model.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.xzx.common.constant.LogConstant.LOGIN_LOG;
import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/hospital/user-info")
@Api(tags = "用户信息控制器")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "手机号登录用户")
    @PostMapping("/loginByPhone")
    @LogAnnotation(description = "手机号登录用户", type = LOGIN_LOG)
    public R loginByPhone(@RequestBody UserLoginVo userLoginVo) {
        return userInfoService.loginByPhone(userLoginVo);
    }

    @ApiOperation(value = "用户信息认证")
    @PostMapping("/auth")
    @LogAnnotation(description = "用户信息认证", type = OPERATE_LOG)
    public R authUserInfo(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        return userInfoService.authUserInfo(userAuthVo, request);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/get")
    public R getUserInfo(HttpServletRequest request) {
        return userInfoService.getUserInfo(request);
    }

    @ApiOperation(value = "根据表id获取用户信息")
    @GetMapping("/{id}")
    public R getUserInfoById(@PathVariable Integer id) {
        return userInfoService.getUserInfoById(id);
    }

    @ApiOperation(value = "根据表id锁定用户信息")
    @GetMapping("/lock/{id}/{isEnable}")
    @LogAnnotation(description = "根据表id锁定用户信息", type = OPERATE_LOG)
    public R lockUserInfo(@PathVariable Integer id, @PathVariable Integer isEnable) {
        return userInfoService.lockUserInfo(id, isEnable);
    }

    @ApiOperation(value = "根据表id认证用户信息")
    @GetMapping("/approval/{id}/{authStatus}")
    @LogAnnotation(description = "根据表id认证用户信息", type = OPERATE_LOG)
    public R approvalUserInfo(@PathVariable Integer id, @PathVariable Integer authStatus) {
        return userInfoService.approvalUserInfo(id, authStatus);
    }

    @ApiOperation(value = "分页查询所有用户信息列表")
    @PostMapping("/page/{current}/{size}")
    public R pageUserInfo(@PathVariable Integer current, @PathVariable Integer size, @RequestBody(required = false) UserQueryVo userQueryVo) {
        return userInfoService.pageUserInfo(current, size, userQueryVo);
    }
}

