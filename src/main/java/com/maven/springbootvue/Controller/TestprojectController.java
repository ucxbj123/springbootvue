package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Pojo.Testproject;
import com.maven.springbootvue.Service.Impl.TestprojectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 谢秉均
 * @date 2023/2/1--21:43
 */
@RestController
@RequestMapping("/inspect")
public class TestprojectController {

    @Autowired
    private TestprojectServiceImpl testprojectService;


    /**
    *@description：添加新的项目
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2023/2/1--21:46
    */
    @RequestMapping(value = "/insertproject",method = RequestMethod.POST)
    public BaseResponse<String> insertProject(@RequestBody Testproject testproject){
        System.out.println("上传："+testproject);
        int num = testprojectService.InsertTestProject(testproject);
        String msg = "";
        if (num == 1){
            msg = "添加成功";
        }else {
            msg = "添加异常";
        }
        return new BaseResponse<String>(true,msg,msg,20000);
    }

    /**
    *@description：查询项目的数据
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2023/2/1--22:49
    */
    @RequestMapping(value = "/getTestprojects",method = RequestMethod.POST)
    public BaseResponse<List<Testproject>> getTestproject(){
        return new BaseResponse<>(true, "查询成功", testprojectService.getTestproject(), 20000);
    }
}
