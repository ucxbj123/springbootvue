package com.maven.springbootvue.Controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Pojo.Grade;
import com.maven.springbootvue.Service.Impl.GradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description 年级功能模块
 * @date 2022/10/31--16:57
 */
@RestController
@RequestMapping("/grade")
public class GradeController {

    //日志记录器
    private static final Logger logger= LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeServiceImpl gradeService;

    /**
    *@description：获取年级的分页数据
    *@param  msg
    *@return
    *@Author 谢秉均
    *@date 2022/11/8--13:51
    */
    @RequestMapping(value = "/getPage",method = RequestMethod.POST)
    public BaseResponse<Map> getPage(@RequestBody JSONObject msg){
        System.out.println("-------------------"+msg.toJSONString());
        //每页显示数据
        Integer pagesize = Integer.valueOf(msg.getString("pagesize"));
        //当前页
        Integer currentPage= Integer.valueOf(msg.getString("currentPage"));
        //年级名称、年级编号
        String gno = msg.getString("gno");
        String name = msg.getString("name");
        PageInfo<Grade> pageInfo = gradeService.getPageGrade(gno,name,pagesize,currentPage);
        logger.info("pageInfo:"+pageInfo);
        //存储返回的结果，线程安全的map集合
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("grades",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return new BaseResponse(true,"年级信息",map,20000);
    }

    /**
    *@description：添加年级信息
    *@param  grade 年级
    *@return
    *@Author 谢秉均
    *@date 2022/11/8--13:50
    */
    @RequestMapping(value = "insertGrade",method = RequestMethod.POST)
    public BaseResponse<String> insertGrade (@RequestBody Grade grade){
        System.out.println(grade);
        Integer res = gradeService.InsertGrade(grade);
        //结果描述
        String msg = null;
        Boolean result = null;
        if(res > 0){
            msg = "添加成功";
            result = true;
        }else if (res == -1){
            msg = "年级已存在";
            result = false;
        }else if (res == 0){
            msg = "添加失败";
            result = false;
        }
        return new BaseResponse<String>(result,msg,"",20000);
    }

    /**
    *@description：根据年级编号与名称修改对应的年级信息
    *@param  grade
    *@return
    *@Author 谢秉均
    *@date 2022/11/8--15:34
    */
    @RequestMapping(value = "updateGrade",method = RequestMethod.POST)
    public BaseResponse<String> updateGrade(@RequestBody Grade grade){
        Integer res = gradeService.updateGrade(grade);
        Boolean result = null;
        String msg = null;
        if (res > 0){
            result = true;
            msg = "信息更新成功";
        }else {
            result =false;
            msg = "信息更新失败";
        }
        return new BaseResponse<String>(result,msg,"",20000);
    }

    /**
    *@description：根据年级编号和名称删除年级信息，实际是更改isdelete = 1
    *@param  grade
    *@return
    *@Author 谢秉均
    *@date 2022/11/8--15:36
    */
    @RequestMapping(value = "/deleteGrade",method = RequestMethod.POST)
    public BaseResponse<String> deleteGrade(@RequestBody Grade grade){
        //设置该年级信息为删除状态，进行后续状态修改操作
        grade.setIsdelete(1);
        Map<String,Object> map = gradeService.deleteGrade(grade);
        return new BaseResponse<String>((Boolean) map.get("success"),(String) map.get("msg"),"",20000);
    }
}
