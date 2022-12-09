package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.TeacherDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Pojo.Class;
import com.maven.springbootvue.Pojo.Teacher;
import com.maven.springbootvue.Service.Impl.ClassServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    
    //班级业务的逻辑
    @Autowired
    private ClassServiceImpl classService;

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

    /**
    *@description：修改教师信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/1--16:35
    */
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    public BaseResponse<String> updateTeacher(@RequestBody UserInfo userInfo){
        Map<String,Object> map = teacherService.updateTeacherOne(userInfo);
        return new BaseResponse<String>((Boolean) map.get("success"), (String) map.get("msg"),(String) map.get("msg"), 20000);
    }
    
    /**
    *@description：获取班级信息，全部的班级与教师任课的班级信息
    *@param tno 教师编号
    *@return 
    *@Author 谢秉均
    *@date 2022/12/7--15:28
    */
    @RequestMapping(value = "/getClass",method = RequestMethod.GET)
    public BaseResponse<Map> getClass(@RequestParam("tno")String tno){
        logger.info(this.getClass()+"--正在获取教师任课班级信息--getClass");
        Map<String,Object> map = classService.getCLazzInfo(tno, false);
        //存储该教师信息
        Teacher teacher = teacherService.getTeacher(tno);
        map.put("teacher",teacher);
        return new BaseResponse<Map>(true,"查询成功",map, 20000);
    }

    /**
    *@description：批量添加教师任课班级信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/7--17:19
    */
    @RequestMapping(value = "/insertClassBatch",method = RequestMethod.POST)
    public BaseResponse<String> insertClassBatch(@RequestBody List<Class> list){
        classService.insertClassBatch(list);
        return new BaseResponse<String>(true, "添加任课班级成功", "", 20000);
    }

    /**
    *@description：批量删除教师任课班级信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/8--11:21
    */
    @RequestMapping(value = "/deleteClassBatch",method = RequestMethod.POST)
    public BaseResponse<String> deleteClassBatch(@RequestBody List<Class> list){
        classService.deleteClassBatch(list);
        return new BaseResponse<String>(true, "删除任课班级成功", "", 20000);
    }

    /**
    *@description：修改任课内容
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/12/9--9:57
    */
    @RequestMapping(value = "/updateContent",method = RequestMethod.POST)
    public BaseResponse<String> updateContent(@RequestParam("tno") String tno, @RequestParam("cno") String cno, @RequestParam("content") String content){
        classService.updateContent(tno,cno,content);
        return new BaseResponse<String>(true, "修改任课内容成功", "", 20000);
    }

    @RequestMapping(value = "/getContentByTnoAndCno",method = RequestMethod.POST)
    public BaseResponse<String> getContentByTnoAndCno(@RequestParam("tno") String tno, @RequestParam("cno") String cno){
        String content = classService.getContentByTnoAndCno(tno, cno);
        return new BaseResponse<String>(true, "获取任课内容成功", content, 20000);
    }

}
