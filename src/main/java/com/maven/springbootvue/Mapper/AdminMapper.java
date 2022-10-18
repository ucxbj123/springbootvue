package com.maven.springbootvue.Mapper;


import com.maven.springbootvue.Pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/18--16:26
 */
@Component
public interface AdminMapper {
    //登录验证
    Admin Login(@Param("username") String userID, @Param("password") String password);

    //获取管理员信息
    Admin getAdmin(@Param("username") String userID);

    //修改密码
    Integer updatePassword(@Param("ano") String ano , @Param("password") String newPassword);
}
