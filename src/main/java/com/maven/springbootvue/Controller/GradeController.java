package com.maven.springbootvue.Controller;

import com.alibaba.excel.EasyExcel;
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


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return new BaseResponse<Map>(true,"年级信息",map,20000);
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

    /**
    *@description：将数据导出为excel文件，导出年级信息
    *@param  msg List的JSON数据
    *@return
    *@Author 谢秉均
    *@date 2022/11/15--16:10
    */
    @RequestMapping("/download")
    public void exportExcel(@RequestBody List<Grade> msg, HttpServletResponse response) throws IOException {
        String nowtime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = URLEncoder.encode("年级信息-"+nowtime, "UTF-8");
        /**setContentType是用来区分数据类型的
         * {".xls", "application/vnd.ms-excel" },
         * {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
         */
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //下载文件的默认名称，前端会根据res.headers["content-disposition"].split("=")[1]获取文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        //设置不缓存
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        EasyExcel.write(response.getOutputStream(), Grade.class).sheet("年级信息").doWrite(msg);
    }
}
