package com.maven.springbootvue.Dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/26--14:20
 * 封装统一返回前端的用户信息
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserInfo implements Serializable {

    private String usertype;

    private Integer id;

    private String userID;

    private String name;

    private String gender;

    private String address;

    private String email;

    private String telephone;

    private Integer isdelete;

    private String portrait_path;

    private String password;

    public UserInfo(String userID, String name, String gender, String address, String email, String telephone, Integer isdelete) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.isdelete = isdelete;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsertype() {
        return usertype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getPortrait_path() {
        return portrait_path;
    }

    public void setPortrait_path(String portrait_path) {
        this.portrait_path = portrait_path;
    }
}
