package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Dto.StudentDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谢秉均
 * @date 2022/10/17--23:31
 */
@Repository
public interface StudentMapper {

    //登录验证
    Student Login(@Param("sno") String userID, @Param("password") String password);

    //获取单个学生信息
    Student getStudent(@Param("sno") String userID);

    //修改密码
    int updatePassword(@Param("sno") String sno, @Param("password") String newPassword);

    //获取学生信息，根据传入的账号进行查询，若值为null则查询全部
    List<UserInfo> getStudents(@Param("sno") String userID);

    //更改账号的状态（isdelete）
    Integer updateStatus(@Param("isdelete") Integer status, @Param("sno") String userID);

    //添加学生用户
    Integer insertStudentOne(UserInfo userInfo);

    //删除学生用户
    Integer deleteStudentOne(UserInfo userInfo);

    //更新用户信息
    Integer updateStudentOne(UserInfo userInfo);

    //修改学生的归属班级
    Integer updateClazzMore(@Param("students") List<Student> students);

    //根据姓名、账号、性别、账号是否正常动态查询学生信息
    List<Student> selectDynamic(StudentDto studentDto);

    //通过学生归属班级进行查询，根据条件查询已分配班级的学生，未分配班级的学生，全部学生
    List<Student> selectByCno(@Param("cno") String cno);
}
