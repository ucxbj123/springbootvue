package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Pojo.Teststandard;
import com.maven.springbootvue.Service.Impl.TeststandardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 谢秉均
 * @description
 * @date 2023/2/2--14:55
 */
@RestController
@RequestMapping("/standard")
public class TeststandardController {

    @Autowired
    private TeststandardServiceImpl teststandardService;

    /**
    *@description：保存检验标准的检验项
    *@param
    *@return
    *@Author 谢秉均
    *@date 2023/2/2--14:57
    */
    @RequestMapping(value = "/saveStandard",method = RequestMethod.POST)
    public BaseResponse<String> saveStandard(@RequestBody List<Teststandard> teststandards, String standardcode){
        Boolean res = teststandardService.saveTestStandard(teststandards,standardcode);
        return new BaseResponse<>(res, "保存成功", "保存成功", 20000);
    }

    /**
    *@description：根据检验标准编号查询检验项
    *@param
    *@return
    *@Author 谢秉均
    *@date 2023/2/2--15:52
    */
    @RequestMapping(value = "/getTeststandard",method = RequestMethod.POST)
    public BaseResponse<List> getTeststandard(String standardcode){
        return new BaseResponse<>(true,"查询成功",teststandardService.getStandards(standardcode), 20000);
    }
}
