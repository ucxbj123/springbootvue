package com.maven.springbootvue.Config;

import com.maven.springbootvue.Shiro.JWTFilter;
import com.maven.springbootvue.Shiro.LoginRealm;
import com.maven.springbootvue.Shiro.ShiroExceptionResolver;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/21--15:29
 */
@Configuration
//设置优先加载
@Order(1)
public class ShiroConfig {


    /**
     * 注入异常类 现在暂时未生效，未找到原因
     */
    @Bean
    public ShiroExceptionResolver myExceptionResolver(){
        return new ShiroExceptionResolver();
    }

    /**
     * 身份认证的realm，自己写
     * @return
     */
    @Bean
    public LoginRealm loginRealm(){
        return new LoginRealm();
    }


    /**
     * 安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(loginRealm());

        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //设置JWTFilter过滤器对token进行拦截验证
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("jwt",new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();//线程安全的集合

        //配置登录、注销、验证码图片、测试控制器、过滤器处理控制器url跳过拦截验证
        filterChainDefinitionMap.put("/login/login","anon");
        filterChainDefinitionMap.put("/login/logout","anon");
        filterChainDefinitionMap.put("/login/getVerifiCodeImage","anon");
        filterChainDefinitionMap.put("/test/*","anon");
        filterChainDefinitionMap.put("/filterError/*","anon");


        //配置其他url进行JWTFilter进行拦截验证
        filterChainDefinitionMap.put("/**","jwt");

        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error/jwtFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }


    /**
     * 开启shiro aop注解支持. 使用代理方式; 所以需要开启代码支持;
     *
     * @param securityManager 安全管理器
     * @return 授权Advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
