package com.maven.springbootvue.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 谢秉均
 * @description 教师任课数据表
 * @date 2022/12/7--13:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Class implements Serializable {

    private Integer id;

    private String tno;//教师编号

    private String name;//教师姓名

    private String cno; //任课班级编号

    private String clazz_name;  //任课班级名称

    private String content; //任课内容.例如语文

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

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getClazz_name() {
        return clazz_name;
    }

    public void setClazz_name(String clazz_name) {
        this.clazz_name = clazz_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
