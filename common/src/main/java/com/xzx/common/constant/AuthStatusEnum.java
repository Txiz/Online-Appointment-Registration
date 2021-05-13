package com.xzx.common.constant;

/**
 * 认证状态枚举
 * 作者: xzx
 * 创建时间: 2021-05-12-14-25
 **/
public enum AuthStatusEnum {

    NO_AUTH(0, "未认证"),
    WAIT_AUTH(1, "认证中"),
    SUCCESS_AUTH(2, "认证成功"),
    FAIL_AUTH(-1, "认证失败"),
    ;

    private Integer status;
    private String name;

    AuthStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String getStatusNameByStatus(Integer status) {
        AuthStatusEnum[] arrObj = AuthStatusEnum.values();
        for (AuthStatusEnum obj : arrObj) {
            if (status.intValue() == obj.getStatus().intValue()) {
                return obj.getName();
            }
        }
        return "";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
