package com.maven.springbootvue.Shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/21--15:08
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    //日志记录器
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);


    /** 博主的解释
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        logger.info(this.getName()+"  拦截url进行验证" );
        if (isLoginAttempt(request,response)){//若header未携带token则直接拦截;若携带则进行口令认证
            executeLogin(request,response);
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
    *@description：判断用户是否想要登入，检测header里面是否包含X-Token字段即可
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/21--15:16
    */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String headertoken = request1.getHeader("X-Token");
        return headertoken != null;
    }

    /**
    *@description：若header携带token则进行登录验证
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/21--15:22
    */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("X-Token");
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
}
