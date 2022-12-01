package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.StudentDto;
import com.maven.springbootvue.Dto.TeacherDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/12/1--16:36
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    //日志记录器
    private static final Logger logger= LoggerFactory.getLogger(StudentController.class);

    //
    @Autowired
    StudentServiceImpl studentService;

    /**
    *@description：获取学生的分页数据
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/1--16:38
    */
    @RequestMapping(value = "/getStudentPage",method = RequestMethod.POST)
    public BaseResponse<Map> getStudentPage(@RequestBody StudentDto dto){
        Map<String, Object> map = studentService.getPageStudent(dto,dto.getCurrentPage(),dto.getPagesize());
        return new BaseResponse<Map>(true,"查询成功",map,20000);
    }

    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public BaseResponse<String> updateStudent(@RequestBody UserInfo userInfo){
        Map<String,Object> map = studentService.updateStudentOne(userInfo);
        return  new BaseResponse<String>((Boolean) map.get("success"), (String) map.get("msg"),(String) map.get("msg"), 20000);
    }
}
