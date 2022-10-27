package com.maven.springbootvue.Mapper;


import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/18--16:26
 */
@Repository
public interface AdminMapper {
    //登录验证
    Admin Login(@Param("username") String userID, @Param("password") String password);

    //获取管理员信息
    Admin getAdmin(@Param("username") String userID);

    //修改密码
    Integer updatePassword(@Param("ano") String ano , @Param("password") String newPassword);

    //获取管理员信息，根据传入的账号进行查询，若值为null则查询全部
    List<UserInfo> getAdmins(@Param("ano") String userID);

    //更改管理员账号的状态（isdelete）
    Integer updateStatus(@Param("isdelete") Integer status, @Param("ano") String userID);
}
