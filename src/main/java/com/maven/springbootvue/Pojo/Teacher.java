package com.maven.springbootvue.Pojo;

import java.io.Serializable;

/**
*@description：
*@Param: 
*@return: 
*@Author: 谢秉均
*@date: 2022/10/18--15:06
*/
public class Teacher implements Serializable {

    private Integer id; //ID
    private String tno; //工号
    private String name;   //姓名
    private char gender;    //性别
    private String password;    //密码
    private String email;   //邮件
    private String telephone;   //电话
    private String address; //住址
    private String clazz_name;  //任课班级
    private String portrait_path = "image/run.gif";//存储头像的项目路径
    private Integer isdelete = 0; //默认未禁用

    public Teacher() {
    }

    public Teacher(Integer id, String tno, String name, char gender, String password, String email, String telephone, String address, String clazz_name, String portrait_path, Integer isdelete) {
        this.id = id;
        this.tno = tno;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.clazz_name = clazz_name;
        this.portrait_path = portrait_path;
        this.isdelete = isdelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
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

    public String getClazz_name() {
        return clazz_name;
    }

    public void setClazz_name(String clazz_name) {
        this.clazz_name = clazz_name;
    }

    public String getPortrait_path() {
        return portrait_path;
    }

    public void setPortrait_path(String portrait_path) {
        this.portrait_path = portrait_path;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", tno='" + tno + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", clazz_name='" + clazz_name + '\'' +
                ", portrait_path='" + portrait_path + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }
}
