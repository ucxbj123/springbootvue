package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Mapper.StudentMapper;
import com.maven.springbootvue.Pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @date 2022/10/17--23:44
 */
@Service
@Transactional  //开启事务 默认传播行为是：Propagation.REQUIRED；隔离级别：为数据源的默认隔离级别，mysql是REPEATABLE_READ（可重复读），能解决脏读、不可重复读问题
public class StudentServiceImpl {

    @Autowired
    private StudentMapper studentMapper;

    //学生登录验证
    public Map<String,Object> loginForm(String sno , String password){
        Map<String,Object> map = new HashMap<>();//存储验证结果
        Student student = studentMapper.Login(sno,password);
        System.out.println(student); //调试-查看查询的结果
        if (student != null && student.getIsdelete() != 1 ){
            map.put("status",true);
        }else {
            if(student != null && student.getIsdelete() == 1){
                map.put("status", false);
                map.put("msg", "账号已删除");
            }else {
                map.put("status", false);
                map.put("msg", "账号密码错误或者不存在");
            }

        }
        return map;
    }

    //获取单个学生信息
    public Student getStudent(String userID){
        return studentMapper.getStudent(userID);
    }

}
