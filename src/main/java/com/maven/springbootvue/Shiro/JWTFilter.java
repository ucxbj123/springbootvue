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



    /**
     * 执行用户token认证，成功则继续执行请求，失败则进入shiro的错误处理控制器
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        logger.info(this.getName()+"  拦截url进行验证" );
        if (isLoginAttempt(request,response)){//若header未携带token则直接拦截;若携带则进行口令认证
            try {//手动捕获认证抛出的异常，若成功则继续执行请求，失败则跳到onAccessDenied进行后续处理
                executeLogin(request,response);
                return true;
            }catch (Exception e){
                logger.warn("isAccessAllowed认证失败 跳转到onAccessDenied进行处理");
                return false;
            }
        }else{
            return false;
        }
//        return super.isAccessAllowed(request, response, mappedValue);
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

    /**
     * 认证失败后在此方法执行操作
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.getRequestDispatcher("/filterError/JWTFilter").forward(request,response);
        return false;//因为要做服务转发，如果设置为true会陷入死循环

    }
}
