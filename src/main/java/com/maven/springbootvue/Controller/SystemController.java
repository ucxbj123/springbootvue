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

    /**
    *@description： 添加用户
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--14:49
    */
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST)
    public BaseResponse<String> insertUser(@RequestBody UserInfo userInfo){
        if(userInfo == null){//处理前端参数为空情况
            return new BaseResponse<String>(false,"添加用户失败","",20000);
        }
        //新添加的账号ID自增、默认是启用状态
        userInfo.setId(null);
        userInfo.setIsdelete(0);

        //根据账号类型进行对应用户账号的添加
        Map<String,Object> map = null;
        switch (userInfo.getUsertype()){
            case "admin":
                map = adminService.insertAdminOne(userInfo);
                break;
            case "student":
                map = studentService.insertStudentOne(userInfo);
                break;
            case "teacher":
                map = teacherService.insertTeacherOne(userInfo);
        }
        return new BaseResponse<String>((Boolean) map.get("success"),(String) map.get("msg"),"",20000);
    }


    /**
    *@description：删除用户
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:51
    */
    @RequestMapping(value = "/deletetUser",method = RequestMethod.POST)
    public BaseResponse<String> deletetUser(@RequestBody UserInfo userInfo){
        if(userInfo == null){//处理前端参数为空情况
            return new BaseResponse<String>(false,"删除用户失败","",20000);
        }
        //根据账号类型进行对应用户账号的删除
        Map<String,Object> map = null;
        switch (userInfo.getUsertype()){
            case "admin":
                map = adminService.deleteAdminOne(userInfo);
                break;
            case "student":
                map = studentService.deleteStudentOne(userInfo);
                break;
            case "teacher":
                map = teacherService.deleteTeacherOne(userInfo);
        }
        return new BaseResponse<String>((Boolean) map.get("success"),(String) map.get("msg"),"",20000);
    }

    @RequestMapping(value = "/updatetUser",method = RequestMethod.POST)
    public BaseResponse<String> updatetUser(@RequestBody UserInfo userInfo){
        if(userInfo == null){//处理前端参数为空情况
            return new BaseResponse<String>(false,"更新用户信息失败","",20000);
        }
        //根据账号类型进行对应用户账号的信息更新
        Map<String,Object> map = null;
        switch (userInfo.getUsertype()){
            case "admin":
                map = adminService.updateAdminOne(userInfo);
                break;
            case "student":
                map = studentService.updateStudentOne(userInfo);
                break;
            case "teacher":
                map = teacherService.updateTeacherOne(userInfo);
        }
        return new BaseResponse<String>((Boolean) map.get("success"),(String) map.get("msg"),"",20000);
    }



}
