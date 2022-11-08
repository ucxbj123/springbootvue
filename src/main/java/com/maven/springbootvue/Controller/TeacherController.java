package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
