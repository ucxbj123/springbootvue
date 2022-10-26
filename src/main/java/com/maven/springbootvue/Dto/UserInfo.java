package com.maven.springbootvue.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

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

    public UserInfo(String userID, String name, String gender, String address, String email, String telephone, Integer isdelete) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.isdelete = isdelete;
    }
}
