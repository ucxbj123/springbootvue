package com.maven.springbootvue.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Service.Impl.AdminServiceImpl;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import com.maven.springbootvue.Util.CreateVerifiCodeImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


    Map<String,Object> result  = new HashMap<>();

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private AdminServiceImpl adminService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestBody String msg, HttpServletRequest request){
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
            String token = userID+"-"+usertype+"-"+password;
            //校验正确则移除session的验证码，需要重新获取
            request.getSession().removeAttribute("verifiCode");
            if (usertype.equals("student")){//学生账号验证
                Map<String, Object> re = studentService.loginForm(userID,password);//获取验证结果
                Boolean status = (Boolean) re.get("status");
                if (status){
                    Result.put("token",token);
                    Result.put("name",studentService.getStudent(userID).getName());
                    Result.put("status",true);
                }else {
                    Result.put("status",false);
                    Result.put("msg",re.get("msg"));
                }

            }else if (usertype.equals("teacher")){//教师账号验证

                Map<String, Object> re = teacherService.loginForm(userID,password);//获取验证结果
                System.out.println("教师"+re.get("status"));
                Boolean status = (Boolean) re.get("status");
                if (status){
                    Result.put("token",token);
                    Result.put("name",teacherService.getTeacher(userID).getName());
                    Result.put("status",true);
                }else {
                    Result.put("status",false);
                    Result.put("msg",re.get("msg"));
                }

            }else if (usertype.equals("admin")){ //管理员账号验证

                Map<String, Object> re = adminService.loginForm(userID,password);//获取验证结果
                System.out.println("admin:"+re.get("status"));
                Boolean status = (Boolean) re.get("status");
                if (status){
                    Result.put("token",token);
                    Result.put("name",adminService.getAdmin(userID).getName());
                    Result.put("status",true);
                }else {
                    Result.put("status",false);
                    Result.put("msg",re.get("msg"));
                }

            }else{
                //预防前端返回非法的账号类型
                Result.put("status",false);
                Result.put("msg","非法用户");
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

    @RequestMapping(value = "info",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject logininfo( String token){
        System.out.println("信息："+token);
        List<String> roles = Arrays.asList("student");
        result.put("code",20000);
        result.put("data","info");
        result.put("name","lucy");
        result.put("userID","396012");
        result.put("roles",roles);
        result.put("avatar","image/admin.gif");
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(result));
        return json;
    }

    /**
    *@description：退出登录
    *@Param:
    *@return: 
    *@Author: 谢秉均
    *@date: 2022/10/12--15:24
    */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject logout(@RequestBody String token){
        System.out.println("信息："+token);
        result.put("code",20000);
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(result));
        return json;
    }

    @RequestMapping(value = "updatepassword",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updatepassword(@RequestBody String message){
        System.out.println("修改表单："+message);
        result.put("code",20000);
        result.put("result","修改密码成功");
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(result));
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
