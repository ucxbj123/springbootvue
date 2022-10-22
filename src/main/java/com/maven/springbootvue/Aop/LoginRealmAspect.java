package com.maven.springbootvue.Aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 谢秉均
 * @date 2022/10/22--9:54
 */
@Aspect
@Component
public class LoginRealmAspect {

    //切入点
    @Pointcut("execution(* com.maven.springbootvue.Shiro.LoginRealm.*(..))")
    public void pointcut(){}

    @AfterThrowing(pointcut = "pointcut()",throwing = "e")
    public void  afterThrowing(Throwable e){
        System.out.println("LoginRealm 校验异常："+e);
    }
}
