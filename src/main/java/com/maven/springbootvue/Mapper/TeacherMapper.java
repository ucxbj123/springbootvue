package com.maven.springbootvue.Mapper;


import com.maven.springbootvue.Pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/18--15:00
 */
@Repository
public interface TeacherMapper {

    //登录验证
    Teacher Login(@Param("tno") String userID, @Param("password") String password);

    //获取单个教师信息
    Teacher getTeacher(@Param("tno") String userID);

    //修改密码
    int updatePassword(@Param("tno") String tno , @Param("password") String newPassword);
}
