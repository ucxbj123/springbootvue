package com.maven.springbootvue.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
public class Testproject implements Serializable {
    private Integer id; //自增ID

    private String project; //项目

    private String standardcode;   //检验标准编码

    private String standardname;    //检验标准名称

    private Integer checklength;    //校验长度

    private Boolean isenabled;      //是否启用

    private LocalDateTime createtime;        //创建时间

    private String createuser;      //创建者

    public Testproject() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    public String getStandardcode() {
        return standardcode;
    }

    public void setStandardcode(String standardcode) {
        this.standardcode = standardcode == null ? null : standardcode.trim();
    }

    public String getStandardname() {
        return standardname;
    }

    public void setStandardname(String standardname) {
        this.standardname = standardname == null ? null : standardname.trim();
    }

    public Integer getChecklength() {
        return checklength;
    }

    public void setChecklength(Integer checklength) {
        this.checklength = checklength;
    }

    public Boolean getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Boolean isenabled) {
        this.isenabled = isenabled;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }
}