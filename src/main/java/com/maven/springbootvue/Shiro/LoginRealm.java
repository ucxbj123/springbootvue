package com.maven.springbootvue.Shiro;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.maven.springbootvue.Dto.TokenResponseCodeEnum;
import com.maven.springbootvue.Pojo.Admin;
import com.maven.springbootvue.Pojo.Student;
import com.maven.springbootvue.Pojo.Teacher;
import com.maven.springbootvue.Service.Impl.AdminServiceImpl;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import com.maven.springbootvue.Util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author 谢秉均
 * @description 身份登录验证类
 * @date 2022/10/21--14:03
 * 说明：
 * 1、在本项目用到该认证的功能有JWTFilter过滤器、LoginController用户登录功能/login/login（暂时）。
 * 2、JWTFilter已统一通过onAccessDenied方法将认证失败的请求统一转发到对应的控制器进行处理。
 * 3、不需要经过JWTFilter拦截认证的请求，若在controller有使用subject.login(token)登录功能并抛出异常；
 *    springboot通过全局异常捕获AuthenticationException异常，并获取该异常的message进行判断处理，LoginRealm抛出的异常部分会携带message；
 *    继承Throwable该异常类都会有getMessage方法，默认的属性名是：detailMessage;AuthenticationException是Exception子类，Exception是Throwable子类。
 */
public class LoginRealm extends AuthorizingRealm {

    //日志记录器
    private static final Logger logger = LoggerFactory.getLogger(LoginRealm.class);

    @Autowired
    StudentServiceImpl studentService;

    @Autowired
    TeacherServiceImpl teacherService;

    @Autowired
    AdminServiceImpl adminService;

    /**
     * 必须重写此方法，不然Shiro会报错，因为内部用了另外的技术生成token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
    *@description：身份认证 用来验证用户信息的
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/21--14:10
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("LoginRealm.doGetAuthenticationInfo()");
        //获取token，实际在controll（控制器）使用subject.login(token)传入的就是JWTToken类型的,JWTToken重写的getCredentials()返回的是token字符串
        String token = (String) authenticationToken.getCredentials();
        if (StringUtils.isEmpty(token)){//判断token是否为空
            logger.warn("token invalid");
            throw new AuthenticationException("token invalid");
        }
        //解密获取UserID与usertype也就是登录账号，账号类型，用于在数据库获取信息
        String userID = JWTUtil.getUserID(token);
        String usertype = JWTUtil.getUsertype(token);
        logger.info("userID:"+userID);
        logger.info("usertype:"+usertype);
        //判断token是否为空
        if (StringUtils.isEmpty(userID) || StringUtils.isEmpty(usertype)){
            logger.warn("userID or usertype invalid");
            throw new AuthenticationException("userID or usertype invalid");
        }
        //根据账号类型从数据库获取对应的用户密码进行验证
        if (usertype.equals("student")){//学生账号验证
            Student student = studentService.getStudent(userID);//获取student对象
            if (student == null){
                logger.warn("用户不存在!");
                throw new AuthenticationException("用户不存在!");
            }
            try {
                JWTUtil.verify(token, userID, student.getPassword(), usertype);
                logger.info("student token验证成功");
            }catch (Exception e){//捕获token异常，重新抛出AuthenticationException;登录功能调用subject.login(token)是在Controller抛出校验失败，被全局异常捕获进行相应处理
                logger.warn("学生 token 认证失败");
                String tokenerror = this.getTokenError(e);
                throw new AuthenticationException(tokenerror);//重新抛出异常
            }

        }else if (usertype.equals("teacher")){//教师账号验证

            Teacher teacher = teacherService.getTeacher(userID);//获取teacher对象
            if (teacher == null){
                logger.warn("用户不存在!");
                throw new AuthenticationException("用户不存在!");
            }
            try {
                JWTUtil.verify(token, userID, teacher.getPassword(), usertype);
                logger.info("teacher token验证成功");
            }catch (Exception e){
                logger.warn("教师 token 认证失败");
                String tokenerror = this.getTokenError(e);
                throw new AuthenticationException(tokenerror);
            }

        }else if (usertype.equals("admin")){ //管理员账号验证

            Admin admin = adminService.getAdmin(userID);//获取admin对象
            if (admin == null){
                logger.warn("用户不存在!");
                throw new AuthenticationException("用户不存在!");
            }
            try {
                JWTUtil.verify(token, userID, admin.getPassword(), usertype);
                logger.info("admin token验证成功");
            }catch (Exception e){
                logger.warn("管理员 token 认证失败");
                String tokenerror = this.getTokenError(e);//封装好的异常类型判断功能
                throw new AuthenticationException(tokenerror);//重新抛出异常
            }

        }else{
            logger.warn("用户类型无效");
            throw new AuthenticationException("用户类型无效");
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(),authenticationToken.getCredentials(),this.getName());
    }

    /**
     * 判断JWT抛出的异常类型，返回对应的状态
     * @param e
     * @return
     */
    public String getTokenError(Exception e){
        if (e instanceof TokenExpiredException){
            return TokenResponseCodeEnum.TOKEN_EXPIRED.getMessage();
        }else if( e instanceof SignatureVerificationException){
            return TokenResponseCodeEnum.TOKEN_SIGNATURE_INVALID.getMessage();
        }
        return TokenResponseCodeEnum.UNKNOWN_ERROR.getMessage();
    }
}
