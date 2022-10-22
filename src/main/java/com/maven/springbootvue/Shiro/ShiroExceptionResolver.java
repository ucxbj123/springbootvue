package com.maven.springbootvue.Shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 谢秉均
 * @date 2022/10/22--10:39
 * 自定义Shiro异常处理类，因为shiro的认证在filter进行的，springboot是在controller捕获异常的，需要自定义异常处理类
 */
public class ShiroExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //token认证失败进行服务转发,先判断异常是否属于AuthenticationException或者其子类
        if (ex instanceof AuthenticationException) {
            System.out.println("AuthenticationException==============");
            return new ModelAndView("/error/shiro");
        }
        if (ex instanceof TokenExpiredException){
            System.out.println("ShiroExceptionResolver==============");
            return new ModelAndView("/error/shiro");
        }
        System.out.println("ModelAndView==============");
        return null;
    }
}
