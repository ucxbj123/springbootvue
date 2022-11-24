package com.maven.springbootvue.Service.Impl;


import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Mapper.TeacherMapper;
import com.maven.springbootvue.Pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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


    /**
    *@description：教师登录验证
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


    /**
    *@description：获取单个教师信息
    *@Param:
    *@return: 
    *@Author: 谢秉均
    *@date: 2022/10/18--15:52
    */
    public Teacher getTeacher(String userID){
        return teacherMapper.getTeacher(userID);
    }

    /**
    *@description：修改密码
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--23:51
    */
    public int updatePassword(String tno , String newPassword){
        return teacherMapper.updatePassword(tno, newPassword);
    }


    /**
    *@description：根据账号进行查询，若账号为空，则查询全部账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/26--15:46
    */
    public List<UserInfo> getTeachers(String tno){
        List<UserInfo> teachers = teacherMapper.getTeachers(tno);
        return teachers;
    }

    /**
    *@description：修改账号状态
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/27--13:52
    */
    public Integer updateStatus(String userID , Integer isdelete){
        return teacherMapper.updateStatus(isdelete,userID);
    }

    /**
    *@description：添加教师账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:06
    */
    public Map<String,Object> insertTeacherOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = teacherMapper.insertTeacherOne(userInfo);
        if(res == 1){//影响的记录数为1则添加单条记录成功
            map.put("success",true);
            map.put("msg","添加成功");
        }else {
            map.put("success",false);
            map.put("msg","添加失败");
        }
        return map;
    }

    /**
    *@description：删除教师账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--18:00
    */
    public Map<String,Object> deleteTeacherOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = teacherMapper.deleteTeacherOne(userInfo);
        if(res == 1){//影响的记录数为1则删除单条记录成功
            map.put("success",true);
            map.put("msg","删除成功");
        }else {
            map.put("success",false);
            map.put("msg","删除失败");
        }
        return map;
    }

    /**
    *@description：修改单个用户信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/24--11:20
    */
    public Map<String,Object> updateTeacherOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = teacherMapper.updateTeacherOne(userInfo);
        if(res == 1){//影响的记录数为1则修改单条记录成功
            map.put("success",true);
            map.put("msg","更新信息成功");
        }else {
            map.put("success",false);
            map.put("msg","更新信息失败");
        }
        return map;
    }

}
