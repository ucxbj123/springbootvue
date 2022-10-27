package com.maven.springbootvue.Pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *@description：
 *@Param:
 *@return:
 *@Author: 谢秉均
 *@date: 2022/10/18--16:24
 */
@Component
public class Admin implements Serializable {

    private Integer id; //ID
    private String name; //姓名
    private String ano; //工号
    private char gender; //性别
    private String password; //密码
    private String email; //邮箱
    private String telephone; //电话
    private String address; //地址
    private String portrait_path = "image/admin.gif";//存储头像的项目路径
    private Integer isdelete = 0; //默认未禁用

    public Admin() {
    }

    public Admin(Integer id, String name, String ano, char gender, String password, String email, String telephone, String address, String portrait_path, Integer isdelete) {
        this.id = id;
        this.name = name;
        this.ano = ano;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.portrait_path = portrait_path;
        this.isdelete = isdelete;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ano='" + ano + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", portrait_path='" + portrait_path + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPortrait_path() {
        return portrait_path;
    }

    public void setPortrait_path(String portrait_path) {
        this.portrait_path = portrait_path;
    }
}
