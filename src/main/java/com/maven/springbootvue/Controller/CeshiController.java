package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 谢秉均
 * @date 2022/10/22--9:20
 * 测试，在过滤器已跳过拦截
 */
@RestController
@RequestMapping("test")
public class CeshiController {

    //测试BaseResponse返回的数据的格式
    @RequestMapping("test1")
    public BaseResponse<Map> getone(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",2000);
        return new BaseResponse<Map>(true,"测试",map,50008);
    }

    //测试获取储存在cookie的token，测试OK
    @RequestMapping("gettoken")
    public BaseResponse<String> getcookieToken(@CookieValue("USERTOKEN") String token){
        return new BaseResponse<String>(true,"测试token",token,20000);
    }
}
