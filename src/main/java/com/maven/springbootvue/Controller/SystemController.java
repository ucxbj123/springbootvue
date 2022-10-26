package com.maven.springbootvue.Controller;

import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Pojo.Admin;
import com.maven.springbootvue.Service.Impl.AdminServiceImpl;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/25--17:15
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    TeacherServiceImpl teacherService;

    /**
    *@description：根据账号类型、userID查询用户信息封装统一格式进行返回
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/26--16:47
    */
    @RequestMapping(value = "getUser",method = RequestMethod.POST)
    public BaseResponse<List> getUser(@RequestBody(required = false) JSONObject message){
        System.out.println("message:"+message);
        //用户类型与账号
        String usertype = message.getString("usertype");
        String userID = message.getString("userID");
        //返回前端的data
        List<UserInfo> userInfos = new ArrayList<>();

        switch (usertype){
            case "admin":
                userInfos = adminService.getAdmins(userID);
                if (userInfos.size() <= 0){
                    return  new BaseResponse<List>(false,"账号不存在",userInfos,20000);
                }
                break;

            case "student":
                userInfos = studentService.getStudents(userID);
                if (userInfos.size() <= 0){
                    return  new BaseResponse<List>(false,"账号不存在",userInfos,20000);
                }
                break;

            case "teacher":
                userInfos = teacherService.getTeachers(userID);
                if (userInfos.size() <= 0){
                    return  new BaseResponse<List>(false,"账号不存在",userInfos,20000);
                }
                break;
        }

        return new BaseResponse<List>(true,"用户信息",userInfos,20000);

    }
}
