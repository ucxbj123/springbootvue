package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author 谢秉均
 * @date 2022/10/17--23:31
 */
@Component
public interface StudentMapper {

    //登录验证
    Student Login(@Param("sno") String userID, @Param("password") String password);

    //获取单个学生信息
    Student getStudent(@Param("sno") String userID);

    //修改密码
    int updatePassword(@Param("sno") String sno, @Param("password") String newPassword);
}
