package com.maven.springbootvue.Pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
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
 * 可以通过IndexedColors.GREEN.getIndex()获取对应颜色代表的数字
 * 9:设为白色
 */
@HeadStyle(fillForegroundColor = 9)
public class Grade implements Serializable {
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private Integer id; //ID

    /**
     * value:列名称
     * index：从小到大排列字段顺序
     */
    @ExcelProperty(value = "年级名称",index = 1)
    private String name; //年级名称

    @ExcelProperty(value = "年级主任",index = 2)
    private String manager; //年级主任

    @ExcelProperty(value = "主任邮箱",index = 3)
    private String email; //主任邮箱

    @ExcelProperty(value = "主任电话",index = 4)
    private String telephone; //主任电话

    @ExcelProperty(value = "年级简介",index = 5)
    private String introducation; //年级简介

    @ExcelIgnore
    private Integer isdelete = 0; //年级信息是否被删除，默认未删除

    @ExcelProperty(value = "年级编号",index = 0)
    private String gno; //年级编号

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
        this.name = name == null ? null : name.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getIntroducation() {
        return introducation;
    }

    public void setIntroducation(String introducation) {
        this.introducation = introducation == null ? null : introducation.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getGno() {
        return gno;
    }

    public void setGno(String gno) {
        this.gno = gno == null ? null : gno.trim();
    }
}