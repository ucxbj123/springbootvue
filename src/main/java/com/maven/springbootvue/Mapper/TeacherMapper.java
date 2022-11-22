package com.maven.springbootvue.Mapper;


import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //获取教师信息，根据传入的账号进行查询，若值为null则查询全部
    List<UserInfo> getTeachers(@Param("tno") String userID);

    //更改账号的状态（isdelete）
    Integer updateStatus(@Param("isdelete") Integer status, @Param("tno") String userID);

    //添加教师用户
    Integer insertTeacherOne(UserInfo userInfo);

    //删除教师用户
    Integer deleteTeacherOne(UserInfo userInfo);
}
