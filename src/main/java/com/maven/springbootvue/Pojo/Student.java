package com.maven.springbootvue.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 谢秉均
 * @date 2022/10/17--23:18
 */
@Component
@Data
@AllArgsConstructor
public class Student implements Serializable {

    private Integer id; //ID
    private String sno; //学号
    private String name; //姓名
    private char gender = '男';//default
    private String password; //密码
    private String email; //邮件
    private String telephone; //电话
    private String address; //住址
    private String introducation; //简介
    private String portrait_path = "image/pika.gif";//存储头像的项目路径
    private String clazz_name;//班级名称
    private String cno; //班级编号
    private Integer isdelete = 0;//默认是禁用的

    public Student(Integer id, String sno, String name, char gender, String password, String email, String telephone, String address, String introducation, String portrait_path, String clazz_name, Integer isdelete) {
        this.id = id;
        this.sno = sno;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.introducation = introducation;
        this.portrait_path = portrait_path;
        this.clazz_name = clazz_name;
        this.isdelete = isdelete;
    }

    public Student() {
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

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public String getIntroducation() {
        return introducation;
    }

    public void setIntroducation(String introducation) {
        this.introducation = introducation;
    }

    public String getPortrait_path() {
        return portrait_path;
    }

    public void setPortrait_path(String portrait_path) {
        this.portrait_path = portrait_path;
    }

    public String getClazz_name() {
        return clazz_name;
    }

    public void setClazz_name(String clazz_name) {
        this.clazz_name = clazz_name;
    }
}
