package com.maven.springbootvue.Controller;

import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.TokenResponseCodeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 谢秉均
 * @date 2022/10/22--20:59
 * 因为是前后端分离，不会在过滤器直接跳转页面，过滤器统一服务转发在ErrorController处理ShiroFilter认证失败的情况
 */
@RestController
@RequestMapping("/filterError")
public class ShiroFilterErrorController {

    @RequestMapping("/JWTFilter")
    public BaseResponse<String> JWTFilterError(){//前端已对code=50008口令失效进行拦截
        return new BaseResponse<String>(false,"token认证失败", TokenResponseCodeEnum.TOKEN_EXPIRED.getMessage(), 50008);
    }
}
