package com.maven.springbootvue.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 谢秉均
 * @description
 * @date 2022/11/29--17:08
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TeacherDto implements Serializable {
    private String tno; //教师账号

    private String name; //姓名

    private Integer currentPage; //当前页

    private Integer pagesize;   //每天显示数据

    private Boolean isdelete;   //账号状态，true代表正常

    private String gender;  //性别


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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
