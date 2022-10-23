package com.maven.springbootvue.Shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
    *@description：对跨域提供支持
    *@Author: 谢秉均
    *@date: 2022/10/23--11:20
    */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //表示接受域名的请求：* 表示接受任意域名的请求，还可以指定域名。使用httpServletRequest.getHeader("Origin")其实也是接受任意域名，只是动态将对应的值放入请求头
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        //请求允许的方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        //有效时间，在有效时间内，浏览器无须为同一请求再次发起预检请求,单位为秒;下面设置为3天
        httpServletResponse.setHeader("Access-Control-Max-Age","259200");
        //表明服务器允许请求中携带字段 ，如Cache-Control、Content-Type、Expires等
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        /**
         * 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态，进行拦截
         * 说明：
         * 预检请求（option请求），用来让服务端返回请求允许的方法（如get、post），orgin（来源|域名），以及是否需要Credentials(认证信息)等
         */
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
