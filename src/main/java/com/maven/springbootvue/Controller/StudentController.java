package com.maven.springbootvue.Controller;

import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.StudentDto;
import com.maven.springbootvue.Dto.TeacherDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
    *@description：根据班级编号进行查询，cno=null则查询未分配班级的，cno=''则查询全部学生
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/5--11:18
    */
    @RequestMapping(value = "/getStudentByCno",method = RequestMethod.POST)
    public BaseResponse<List> getStudentByCno(@RequestParam(required = false) String cno){
        return new BaseResponse<List>(true, "查询成功", studentService.selectByCno(cno), 20000);
    }

    /**
    *@description：修改学生所属班级，或者取消学生所属班级
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/5--15:59
    */
    @RequestMapping(value = "/updateStudentClazz",method = RequestMethod.POST)
    public BaseResponse<String> updateStudentClazz(@RequestBody JSONObject data){
        //获取请求体的数据
        List<String> users = (List<String>) data.get("users");
        String cno = data.getString("cno");
        String clazz_name = data.getString("clazz_name");
        Boolean shift = (Boolean) data.get("shift");
        //执行修改操作
        studentService.updateClazzMore(users, cno, clazz_name, shift);
        return new BaseResponse<String>(true, "学生所属班级修改成功","",20000);
    }
}
