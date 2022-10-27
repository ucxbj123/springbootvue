package com.maven.springbootvue.Dto;

/**
 * @author 谢秉均
 * @description 账号类型，统一用枚举表示，方便管理
 * @date 2022/10/27--10:06
 */
public enum UserTypeEnum {
    ADMIN("admin"),STUDENT("student"),TEACHER("teacher");

    private String usertype;

    UserTypeEnum(String usertype) {
        this.usertype = usertype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
