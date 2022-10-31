package com.maven.springbootvue.Controller;

import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Dto.UserTypeEnum;
import com.maven.springbootvue.Service.Impl.AdminServiceImpl;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
                //设置账号类型
                for (UserInfo userInfo: userInfos){
                    userInfo.setUsertype(UserTypeEnum.ADMIN.getUsertype());
                }
                break;

            case "student":
                userInfos = studentService.getStudents(userID);
                if (userInfos.size() <= 0){
                    return  new BaseResponse<List>(false,"账号不存在",userInfos,20000);
                }
                for (UserInfo userInfo: userInfos){
                    userInfo.setUsertype(UserTypeEnum.STUDENT.getUsertype());
                }
                break;

            case "teacher":
                userInfos = teacherService.getTeachers(userID);
                if (userInfos.size() <= 0){
                    return  new BaseResponse<List>(false,"账号不存在",userInfos,20000);
                }
                for (UserInfo userInfo: userInfos){
                    userInfo.setUsertype(UserTypeEnum.TEACHER.getUsertype());
                }
                break;
        }

        return new BaseResponse<List>(true,"用户信息",userInfos,20000);

    }

    /**
     * 禁用/启用账号功能
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public BaseResponse<String> updateUserStatus(@RequestBody UserInfo userInfo){
        if(userInfo == null){//处理前端参数为空情况
            return new BaseResponse<>(false,"更改状态失败","",20000);
        }
        //根据账号类型进行对应账号的状态变更
        switch (userInfo.getUsertype()){
            case "admin":
                adminService.updateStatus(userInfo.getUserID(),userInfo.getIsdelete());
                break;
            case "student":
                studentService.updateStatus(userInfo.getUserID(),userInfo.getIsdelete());
                break;
            case "teacher":
                teacherService.updateStatus(userInfo.getUserID(),userInfo.getIsdelete());
        }
        return new BaseResponse<String>(true,"更改成功","",20000);
    }

}