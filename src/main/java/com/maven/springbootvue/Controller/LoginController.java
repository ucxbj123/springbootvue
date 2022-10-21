package com.maven.springbootvue.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Pojo.Admin;
import com.maven.springbootvue.Pojo.Student;
import com.maven.springbootvue.Pojo.Teacher;
import com.maven.springbootvue.Service.Impl.AdminServiceImpl;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import com.maven.springbootvue.Shiro.JWTToken;
import com.maven.springbootvue.Util.CreateVerifiCodeImageUtil;
import com.maven.springbootvue.Util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * @author 谢秉均
 * @description
 * @date 2022/9/22--14:01
 */
@Controller
@RequestMapping("login")
public class LoginController {

    //日志记录器
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);



    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private AdminServiceImpl adminService;

    /**
    *@description：登录验证
    *@param msg 表单登录信息
     *@param request 请求
    *@return
    *@Author: 谢秉均
    *@date: 2022/10/18--20:21
    */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestBody String msg, HttpServletRequest request){
        logger.info("start "+this.getClass());
        //储存返回信息
        Map<String,Object> Result  = new HashMap<>();
        //正常返回结果的状态码
        Result.put("code",20000);
        //输出获取的登录表单信息
        System.out.println("信息："+msg);
        //转成JSON对象方便获取属性信息
        JSONObject jsonmsg = JSON.parseObject(msg);
        //获取登录框验证码
        String verifiCode = String.valueOf(jsonmsg.get("verifiCode"));
        //获取session验证码
        String sessionVerifiCode = String.valueOf(request.getSession().getAttribute("verifiCode"));

        //若验证码对则继续进行密码账号验证，错误则返回登录失败
        if (verifiCode.equals(sessionVerifiCode)){

            //获取用户类型
            String usertype = String.valueOf(jsonmsg.get("usertype"));
            //账号密码
            String userID = String.valueOf(jsonmsg.get("userID"));
            String password = String.valueOf(jsonmsg.get("password"));
//            String token = userID+"-"+usertype+"-"+password;已用shiro与JWT技术进行整合
            //校验正确则移除session的验证码，需要重新获取
            request.getSession().removeAttribute("verifiCode");
            try {

                //获取subject对象
                Subject subject  = SecurityUtils.getSubject();
                //封装请求数据到token
                JWTToken token = new JWTToken(JWTUtil.sign(userID,usertype,password));
                //调用login进行登录验证，若成功则继续往下执行，否则抛出异常
                subject.login(token);
                //验证成功则把token存进session
                request.getSession().setAttribute("token",token.getCredentials());
                Result.put("token",token.getCredentials());

                //根据用户类型获取用户名
                switch (usertype){
                    case "student":
                        Result.put("name",studentService.getStudent(userID).getName());
                        List<String> sturoles = Arrays.asList("student");
                        Result.put("roles",sturoles);
                        break;
                    case "teacher" :
                        Result.put("name",teacherService.getTeacher(userID).getName());
                        List<String> tearoles = Arrays.asList("teacher");
                        Result.put("roles",tearoles);
                        break;
                    case "admin":
                        Result.put("name",adminService.getAdmin(userID).getName());
                        List<String> admroles = Arrays.asList("admin");
                        Result.put("roles",admroles);
                        break;
                }


                Result.put("status",true);
                Result.put("usertype","teacher");
            }catch (Exception e){
                e.printStackTrace();
                logger.warn("用户登录失败");
                Result.put("status",false);
                Result.put("msg","账号密码错误");
            }



        }else {
            //验证码错误
            Result.put("status",false);
            Result.put("msg","验证码错误");

        }
        //统一最后返回结果
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(Result));
        return json;

    }

    /**
    *@description：用户基础信息获取
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--20:21
    */
    @RequestMapping(value = "info",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject logininfo( String token, HttpServletRequest request){
        System.out.println("token："+token);
        //储存返回信息
        Map<String,Object> Result  = new HashMap<>();
        //获取session的token
        String sessionToken = (String) request.getSession().getAttribute("token");
        if (!sessionToken.equals(token) || sessionToken == null){//若前端返回的token不一致则属于非法token进行中断验证
            logger.warn("token与session不一致 非法！！");
            Result.put("code",500);
            return JSON.parseObject(JSONObject.toJSONString(Result));
        }
        //获取token的账号与账号类型
        String[] token2 = new String[]{JWTUtil.getUserID(token),JWTUtil.getUsertype(token)};

        switch (token2[1]){
            case "student":
                //账号密码验证
                Student student = studentService.getStudent(token2[0]);
                Result.put("code",20000);
                List<String> sturoles = Arrays.asList("student");
                Result.put("name",student.getName());
                Result.put("userID",student.getSno());
                Result.put("roles",sturoles);
                Result.put("avatar",student.getPortrait_path());
                Result.put("usertype","student");
                break;      //记得加break否则会继续往下执行

            case "teacher":
                Result.put("code",20000);
                Teacher teacher = teacherService.getTeacher(token2[0]);
                List<String> tearoles = Arrays.asList("teacher","student");
                Result.put("name",teacher.getName());
                Result.put("userID",teacher.getTno());
                Result.put("roles",tearoles);
                Result.put("avatar",teacher.getPortrait_path());
                Result.put("usertype","teacher");
                break;

            case "admin":
                Result.put("code",20000);
                Admin admin = adminService.getAdmin(token2[0]);
                List<String> adminroles = Arrays.asList("admin");
                Result.put("name",admin.getName());
                Result.put("userID",admin.getAno());
                Result.put("roles",adminroles);
                Result.put("avatar",admin.getPortrait_path());
                Result.put("usertype","admin");
                break;

            default:
                Result.put("code",500);

        }


        JSONObject json = JSON.parseObject(JSONObject.toJSONString(Result));
        return json;
    }

    /**
    *@description：注销
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--22:19
    */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject logout(@RequestBody String token, HttpServletRequest request){
        //储存返回信息
        Map<String,Object> Result  = new HashMap<>();
        System.out.println("注销："+token);
        String sessionToken = (String) request.getSession().getAttribute("token");
        if (sessionToken.equals(token) || sessionToken != null){//若token正确或者存在都进行移除token
            request.getSession().removeAttribute("token");
        }
        Result.put("code",20000);
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(Result));
        return json;
    }


    /**
    *@description：修改密码
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--22:19
    */
    @RequestMapping(value = "updatepassword",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updatepassword(@RequestBody String message, @RequestHeader(value = "X-Token",required = false) String token, HttpServletRequest request){
        //储存返回信息
        Map<String,Object> Result  = new HashMap<>();
        logger.info("修改表单："+message+" headerToken："+token);
        //头部信息
        String[] headerToken = new String[]{JWTUtil.getUserID(token),JWTUtil.getUsertype(token)};
        //表单信息
        JSONObject jsonmsg = JSON.parseObject(message);
        String oldpassword = jsonmsg.getString("oldpassword");
        String newpassword = jsonmsg.getString("newpassword");
        Result.put("code", 20000);
        if (token == null || oldpassword == null){ //排除token为空、旧密码为空或者旧密码输入错误情况
            Result.put("result","修改密码失败");
            Result.put("status", "error");
        }else {
            switch (headerToken[1]) {
                case "student":
                    int studentnumber = studentService.updatePassword(headerToken[0],newpassword);
                    if (studentnumber > 0){
                        Result.put("result","修改密码成功");
                        Result.put("status", "success");
                        break;
                    }
                    Result.put("result","修改密码失败");
                    Result.put("status", "error");
                    break;

                case "teacher":
                    int teachernumber = teacherService.updatePassword(headerToken[0],newpassword);
                    if (teachernumber > 0){
                        Result.put("result","修改密码成功");
                        Result.put("status", "success");
                        break;
                    }
                    Result.put("result","修改密码失败");
                    Result.put("status", "error");
                    break;

                case "admin":

                    int adminnumber = adminService.updatePassword(headerToken[0],newpassword);
                    if (adminnumber > 0){
                        Result.put("result","修改密码成功");
                        Result.put("status", "success");
                        break;
                    }
                    Result.put("result","修改密码失败");
                    Result.put("status", "error");
                    break;

                default:
                    Result.put("result","修改密码失败");
                    Result.put("status", "error");
            }

        }
        //当修改密码成功的时候，更新token给到客户端与session
        logger.info("updatepassword  旧token："+token);
        String newtoken = JWTUtil.sign(headerToken[0],headerToken[1],newpassword );
        request.getSession().setAttribute("token",newtoken);
        logger.info("updatepassword  新token："+token);
        Result.put("token",newtoken);
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(Result));
        return json;
    }

    /**
    *@description：  获取验证码
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/9/22--14:10
    */
    @RequestMapping(value = "/getVerifiCodeImage",method = RequestMethod.GET)
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //生成验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImageUtil.getVerifiCodeImage();
        //验证码
        String verifiCode = String.valueOf(CreateVerifiCodeImageUtil.getVerifiCode());
        HttpSession session = request.getSession();
        //将验证码图片输出到登录界面
        try{
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
            logger.info("验证码图片获取成功");
            logger.info("验证码："+verifiCode);
        }catch (IOException e){
            e.printStackTrace();
            logger.error("验证码图片获取失败");
        }
        // 存储验证码Session
        session.setAttribute("verifiCode", verifiCode);
    }


}
