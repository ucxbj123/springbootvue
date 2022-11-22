package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Dto.UserInfo;
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

    /**
    *@description：修改密码
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--23:30
    */
    public int updatePassword(String sno , String newPassword){
        return studentMapper.updatePassword(sno, newPassword);
    }

    /**
    *@description：根据账号进行查询，若账号为空，则查询全部账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/26--15:46
    */
    public List<UserInfo> getStudents(String sno){
        List<UserInfo> students = studentMapper.getStudents(sno);
        return students;
    }

    /**
    *@description：修改账号状态
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/27--13:52
    */
    public Integer updateStatus(String userID , Integer isdelete){
        return studentMapper.updateStatus(isdelete,userID);
    }

    /**
    *@description：添加学生账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:17
    */
    public Map<String,Object> insertStudentOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = studentMapper.insertStudentOne(userInfo);
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
    *@description：删除学生账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:57
    */
    public Map<String,Object> deleteStudentOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = studentMapper.deleteStudentOne(userInfo);
        if(res == 1){//影响的记录数为1则删除单条记录成功
            map.put("success",true);
            map.put("msg","删除成功");
        }else {
            map.put("success",false);
            map.put("msg","删除失败");
        }
        return map;
    }


}
