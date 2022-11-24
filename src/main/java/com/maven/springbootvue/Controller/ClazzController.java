package com.maven.springbootvue.Controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Pojo.Clazz;
import com.maven.springbootvue.Service.Impl.ClazzServiceImpl;
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
 * @description
 * @date 2022/11/17--15:06
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {

    //日志记录器
    private static final Logger logger= LoggerFactory.getLogger(ClazzController.class);

    @Autowired
    ClazzServiceImpl clazzService;


    /**
    *@description：获取班级的分页数据
    *@param  msg
    *@return
    *@Author 谢秉均
    *@date 2022/11/17--15:20
    */
    @RequestMapping(value = "/getPage",method = RequestMethod.POST)
    public BaseResponse<Map> getPage(@RequestBody JSONObject msg){
        System.out.println("-------------------"+msg.toJSONString());
        //每页显示数据
        Integer pagesize = Integer.valueOf(msg.getString("pagesize"));
        //当前页
        Integer currentPage= Integer.valueOf(msg.getString("currentPage"));
        //班级名称、班级编号
        String cno = msg.getString("cno");
        String name = msg.getString("name");
        PageInfo<Clazz> pageInfo = clazzService.getPageClazz(cno,name,pagesize,currentPage);
        logger.info("pageInfo:"+pageInfo);
        //存储返回的结果，线程安全的map集合
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("clazz",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return new BaseResponse<Map>(true,"班级级信息",map,20000);
    }

    /**
    *@description： 添加班级信息
    *@param  clazz 班级信息
    *@return
    *@Author 谢秉均
    *@date 2022/11/18--9:59
    */
    @RequestMapping(value = "insertClazz",method = RequestMethod.POST)
    public BaseResponse<String> insertClazz (@RequestBody Clazz clazz){
        //新添加的班级，id自增、默认未删除、班级人数为0
        clazz.setId(null);
        clazz.setIsdelete(0);
        clazz.setNumber(0);
        Integer res = clazzService.InsertClazz(clazz);
        //结果描述
        String msg = null;
        Boolean result = null;
        if(res > 0){
            msg = "添加成功";
            result = true;
        }else if (res == -1){
            msg = "班级已存在";
            result = false;
        }else if (res == 0){
            msg = "添加失败";
            result = false;
        }
        return new BaseResponse<String>(result,msg,"",20000);
    }

    /**
    *@description：更新班级信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/18--10:44
    */
    @RequestMapping(value = "updateClazz",method = RequestMethod.POST)
    public BaseResponse<String> updateClazz(@RequestBody Clazz clazz){
        Integer res = clazzService.updateClazz(clazz);
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
    *@description：删除班级信息，实际是更改isdelete状态为1
    *@param clazz
    *@return
    *@Author 谢秉均
    *@date 2022/11/18--11:15
    */
    @RequestMapping(value = "/deleteClazz",method = RequestMethod.POST)
    public BaseResponse<String> deleteClazz(@RequestBody Clazz clazz){
        //设置该班级信息为删除状态，进行后续状态修改操作
        clazz.setIsdelete(1);
        Map<String,Object> map = clazzService.deleteClazz(clazz);
        return new BaseResponse<String>((Boolean) map.get("success"),(String) map.get("msg"),"",20000);
    }

    /**
    *@description：将数据导出为excel文件，导出班级信息
    *@param  msg List的JSON数据
    *@return
    *@Author 谢秉均
    *@date 2022/11/18--11:25
    */
    @RequestMapping("/download")
    public void exportExcel(@RequestBody List<Clazz> msg, HttpServletResponse response) throws IOException {
        String nowtime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = URLEncoder.encode("班级信息-"+nowtime, "UTF-8");
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
        EasyExcel.write(response.getOutputStream(), Clazz.class).sheet("班级信息").doWrite(msg);
    }
}
