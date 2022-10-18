package com.maven.springbootvue.Service.Impl;


import com.maven.springbootvue.Mapper.TeacherMapper;
import com.maven.springbootvue.Pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/18--15:27
 */
@Service
@Transactional
public class TeacherServiceImpl {
    @Autowired
    private TeacherMapper teacherMapper;

    //教师登录验证
    /**
    *@description：
    *@Param: 
    *@return: 
    *@Author: 谢秉均
    *@date: 2022/10/18--15:43
    */
    public Map<String,Object> loginForm(String tno , String password){
        Map<String,Object> map = new HashMap<>();//存储验证结果
        Teacher teacher = teacherMapper.Login(tno,password);
        System.out.println(teacher); //调试-查看查询的结果
        if (teacher != null && teacher.getIsdelete() != 1 ){
            map.put("status",true);
        }else {
            if(teacher != null && teacher.getIsdelete() == 1){
                map.put("status", false);
                map.put("msg", "账号已删除");
            }else {
                map.put("status", false);
                map.put("msg", "账号密码错误或者不存在");
            }
        }
//        System.out.println("map:"+map);//调试
        return map;
    }

    //获取单个教师信息
    /**
    *@description：
    *@Param:
    *@return: 
    *@Author: 谢秉均
    *@date: 2022/10/18--15:52
    */
    public Teacher getTeacher(String userID){
        return teacherMapper.getTeacher(userID);
    }

}
