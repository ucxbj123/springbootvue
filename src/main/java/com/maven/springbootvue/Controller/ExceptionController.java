package com.maven.springbootvue.Controller;


import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.maven.springbootvue.Dto.BaseResponse;
import com.maven.springbootvue.Dto.TokenResponseCodeEnum;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 谢秉均
 * @date 2022/10/21--23:53
 * 全局统一捕获异常进行处理：
 * 1、注意不能手动try-catch,否则全局异常捕获不到
 * 2、若是必须使用try-catch，则可以在catch手动抛出异常throw new
 * 3、springboot只能捕获控制层的异常，在过滤器中可以进行请求转发到指定的Error控制器进行异常抛出
 * 4、Realm的抛出的自定义异常无效，统一被改写成AuthenticationException
 */
@RestControllerAdvice
public class ExceptionController {

    //捕获AuthenticationException异常，因为LoginRealm验证token失败统一抛出AuthenticationException异常
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AuthenticationException.class)
    public BaseResponse<String> LoginRealmError(AuthenticationException e){//因为在shiro认证抛出的异常统一被修改为AuthenticationException，需要根据message进行判断
        String data = "账号异常";
        if (e.getMessage().equals(TokenResponseCodeEnum.TOKEN_EXPIRED.getMessage())){
            data = TokenResponseCodeEnum.TOKEN_EXPIRED.getMessage();
        }else if( e.getMessage().equals(TokenResponseCodeEnum.TOKEN_SIGNATURE_INVALID.getMessage())){
            data = TokenResponseCodeEnum.TOKEN_SIGNATURE_INVALID.getMessage();
        }
        System.out.println("LoginRealmError"+e.getMessage()+":data"+data);
        return new BaseResponse<String>(false,"账号异常",data,50008);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TokenExpiredException.class)
    public BaseResponse<String> JWTTokenError(){
        Map<String, Object> map = new HashMap<>();
        return new BaseResponse<String>(false,"令牌无效", TokenResponseCodeEnum.TOKEN_EXPIRED.getMessage(),50008);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SignatureVerificationException.class)
    public BaseResponse<String> JWTTokenverifyError(){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 50008);
        map.put("msg","token失效");
        return new BaseResponse<String>(false,"令牌无效",TokenResponseCodeEnum.TOKEN_SIGNATURE_INVALID.getMessage(),50008);
    }
}
