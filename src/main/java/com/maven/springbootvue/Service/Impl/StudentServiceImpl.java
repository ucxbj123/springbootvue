package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Mapper.StudentMapper;
import com.maven.springbootvue.Pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谢秉均
 * @date 2022/10/17--23:44
 */
@Service
public class StudentServiceImpl {

    @Autowired
    private StudentMapper studentMapper;

    //学生登录验证
    public Boolean loginForm(String sno ,String password){
        Student student = studentMapper.Login(sno,password);
        if (student != null && student.getSno()!=""){
            return true;
        }else {
            return false;
        }
    }

    //获取单个学生信息
    public Student getStudent(String userID){
        return studentMapper.getStudent(userID);
    }

}
