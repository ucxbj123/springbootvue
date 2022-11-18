package com.maven.springbootvue.Pojo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * 默认不加ExcelProperty 的注解的都会参与读写，加了不会参与
 */
@ExcelIgnoreUnannotated
@HeadStyle(fillForegroundColor = 9)
public class Clazz implements Serializable {

    private Integer id;

    @ExcelProperty(value = "班级名称",index = 1)
    private String name;

    @ExcelProperty(value = "班级人数",index = 5)
    private Integer number;

    @ExcelProperty(value = "班级简介",index = 6)
    private String introducation;

    @ExcelProperty(value = "班主任",index = 2)
    private String coordinator;

    @ExcelProperty(value = "班主任邮箱",index = 3)
    private String email;

    @ExcelProperty(value = "班主任电话",index = 4)
    private String telephone;

    private String gno;

    @ExcelProperty(value = "所属年级",index = 7)
    private String grade_name;

    @ExcelProperty(value = "班级编码",index = 0)
    private String cno;

    private Integer isdelete = 0;//是否删除，默认未删除

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIntroducation() {
        return introducation;
    }

    public void setIntroducation(String introducation) {
        this.introducation = introducation;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
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

    public String getGno() {
        return gno;
    }

    public void setGno(String gno) {
        this.gno = gno;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}