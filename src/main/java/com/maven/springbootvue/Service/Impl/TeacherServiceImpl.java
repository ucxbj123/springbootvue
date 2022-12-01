package com.maven.springbootvue.Service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Dto.TeacherDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Mapper.TeacherMapper;
import com.maven.springbootvue.Pojo.Teacher;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    /**
    *@description：根据姓名、账号、性别、账号是否正常动态获取分页的教师信息
    *@param currentPage 当前页
     * @param pagesize  每页的记录数
    *@return
    *@Author 谢秉均
    *@date 2022/11/29--16:50
    */
    public Map<String,Object> getPageTeacher(TeacherDto teacher, Integer currentPage, Integer pagesize){
        //分页
        PageHelper.startPage(currentPage,pagesize);
        List<Teacher> list = teacherMapper.selectDynamic(teacher);
        PageInfo<Teacher> pageInfo = new PageInfo<>(list);

        //获取分页后的数据
        List<Teacher> list1 = pageInfo.getList();

        //声明一个列表存储处理后的数据，用于返回前端
        List<Teacher> list2 = new ArrayList<>();

        if(teacher.getIsdelete()){//若为true则过滤已禁用的账号，否则查询全部的教师账号进行返回
            for( int i = 0; i < list1.size(); i++){
                if(list1.get(i).getIsdelete() != 1){
                    list2.add(list1.get(i));
                }
            }
        }else {
            list2 = list1;
        }

        //返回pageinfo与处理好的数据
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("teachers",list2);
        map.put("total",pageInfo.getTotal());
        return map;
    }


}
