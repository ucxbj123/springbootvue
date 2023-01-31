package com.maven.springbootvue.Pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
public class Teststandard {
    private Integer id; //自增ID

    private String standardcode;   //检验标准编号

    private String standardname;    //检验标准名称

    private String standardproject; //检验项目

    private String checkstartposition;  //检验起始位置

    private String standardlength;  //长度

    private String checkcondition;  //检验条件1

    private String checkvalue;  //参考值

    private String logicvalue;  //逻辑值

    private String checkcondition1; //检验条件2

    private String checkvalue1; //参考值2

    private Boolean ischeck;    //是否校验

    private Boolean uniquecheck;    //唯一校验

    private String valuetype;   //值类型

    private Boolean isenabled;  //是否启用

    private LocalDateTime createtime;   //创建时间

    private String createuser;  //创建者

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStandardproject() {
        return standardproject;
    }

    public void setStandardproject(String standardproject) {
        this.standardproject = standardproject == null ? null : standardproject.trim();
    }

    public String getCheckstartposition() {
        return checkstartposition;
    }

    public void setCheckstartposition(String checkstartposition) {
        this.checkstartposition = checkstartposition == null ? null : checkstartposition.trim();
    }

    public String getStandardlength() {
        return standardlength;
    }

    public void setStandardlength(String standardlength) {
        this.standardlength = standardlength == null ? null : standardlength.trim();
    }

    public String getCheckcondition() {
        return checkcondition;
    }

    public void setCheckcondition(String checkcondition) {
        this.checkcondition = checkcondition == null ? null : checkcondition.trim();
    }

    public String getCheckvalue() {
        return checkvalue;
    }

    public void setCheckvalue(String checkvalue) {
        this.checkvalue = checkvalue == null ? null : checkvalue.trim();
    }

    public String getLogicvalue() {
        return logicvalue;
    }

    public void setLogicvalue(String logicvalue) {
        this.logicvalue = logicvalue == null ? null : logicvalue.trim();
    }

    public String getCheckcondition1() {
        return checkcondition1;
    }

    public void setCheckcondition1(String checkcondition1) {
        this.checkcondition1 = checkcondition1 == null ? null : checkcondition1.trim();
    }

    public String getCheckvalue1() {
        return checkvalue1;
    }

    public void setCheckvalue1(String checkvalue1) {
        this.checkvalue1 = checkvalue1 == null ? null : checkvalue1.trim();
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Boolean getUniquecheck() {
        return uniquecheck;
    }

    public void setUniquecheck(Boolean uniquecheck) {
        this.uniquecheck = uniquecheck;
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype == null ? null : valuetype.trim();
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