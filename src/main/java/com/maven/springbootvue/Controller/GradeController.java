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
}
