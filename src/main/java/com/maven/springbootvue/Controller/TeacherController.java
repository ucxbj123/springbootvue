package com.maven.springbootvue.Controller;

import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.TeacherDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Pojo.Teacher;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/11/7--13:53
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    //日志记录器
    private static final Logger logger= LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private TeacherServiceImpl teacherService;

    /**
     * 获取教师的信息
     * @return
     */
    @RequestMapping(value = "getTeachers",method = RequestMethod.POST)
    public BaseResponse<List> getTeachers(@RequestBody(required = false) String tno){
        //参数为空则取全部记录
        List<UserInfo> list = teacherService.getTeachers(tno);
        return new BaseResponse<List>(true,"获取教师记录",list,20000);
    }

    /**
    *@description：获取教师的分页数据
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/30--15:04
    */
    @RequestMapping(value = "/getTeacherPage",method = RequestMethod.POST)
    public BaseResponse<Map> getTeacherPage(@RequestBody TeacherDto dto){
        Map<String, Object> map = teacherService.getPageTeacher(dto,dto.getCurrentPage(),dto.getPagesize());
        return new BaseResponse<Map>(true,"查询成功",map,20000);
    }

    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    public BaseResponse<String> updateTeacher(@RequestBody UserInfo userInfo){
        Map<String,Object> map = teacherService.updateTeacherOne(userInfo);
        return new BaseResponse<String>((Boolean) map.get("success"), (String) map.get("msg"),(String) map.get("msg"), 20000);
    }

}
