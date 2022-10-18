package com.maven.springbootvue.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Pojo.Admin;
import com.maven.springbootvue.Pojo.Student;
import com.maven.springbootvue.Pojo.Teacher;
import com.maven.springbootvue.Service.Impl.AdminServiceImpl;
import com.maven.springbootvue.Service.Impl.StudentServiceImpl;
import com.maven.springbootvue.Service.Impl.TeacherServiceImpl;
import com.maven.springbootvue.Util.CreateVerifiCodeImageUtil;
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
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--20:21
    */
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
                    //验证成功则把token存进session
                    request.getSession().setAttribute("token",token);
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
                    //验证成功则把token存进session
                    request.getSession().setAttribute("token",token);
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
                    //验证成功则把token存进session
                    request.getSession().setAttribute("token",token);
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
            Result.put("code",500);
            return JSON.parseObject(JSONObject.toJSONString(Result));
        }
        //获取token的值
        String[] token2 = token.split("-");

        switch (token2[1]){
            case "student":
                //账号密码验证
                Map<String, Object> re = studentService.loginForm(token2[0],token2[2]);
                Boolean status = (Boolean) re.get("status");
                if (status){//账号验证正确则获取用户信息进行返回
                    Result.put("code",20000);
                    Student student = studentService.getStudent(token2[0]);
                    List<String> roles = Arrays.asList("student");
//                    Result.put("data","info");
                    Result.put("name",student.getName());
                    Result.put("userID",student.getSno());
                    Result.put("roles",roles);
                    Result.put("avatar",student.getPortrait_path());
                }else {
                    Result.put("code",500);
                }
                break;

            case "teacher":
                Map<String, Object> teacherResult = teacherService.loginForm(token2[0],token2[2]);
                Boolean teacherStatus = (Boolean) teacherResult.get("status");
                if (teacherStatus){//账号验证正确则获取用户信息进行返回
                    Result.put("code",20000);
                    Teacher teacher = teacherService.getTeacher(token2[0]);
                    List<String> roles = Arrays.asList("teacher","student");
                    Result.put("name",teacher.getName());
                    Result.put("userID",teacher.getTno());
                    Result.put("roles",roles);
                    Result.put("avatar",teacher.getPortrait_path());
                }else {
                    Result.put("code",500);
                }
                break;

            case "admin":
                Map<String, Object> adminResult = adminService.loginForm(token2[0],token2[2]);
                Boolean adminStatus = (Boolean) adminResult.get("status");
                if (adminStatus){//账号验证正确则获取用户信息进行返回
                    Result.put("code",20000);
                    Admin admin = adminService.getAdmin(token2[0]);
                    List<String> roles = Arrays.asList("admin");
                    Result.put("name",admin.getName());
                    Result.put("userID",admin.getAno());
                    Result.put("roles",roles);
                    Result.put("avatar",admin.getPortrait_path());
                }else {
                    Result.put("code",500);
                }
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
    public JSONObject updatepassword(@RequestBody String message, @RequestHeader(value = "X-Token",required = false) String token){
        //储存返回信息
        Map<String,Object> Result  = new HashMap<>();
        System.out.println("修改表单："+message+" headerToken："+token);
        //头部信息
        String[] headerToken = token.split("-");
        //表单信息
        JSONObject jsonmsg = JSON.parseObject(message);
        String oldpassword = jsonmsg.getString("oldpassword");
        String newpassword = jsonmsg.getString("newpassword");
        Result.put("code", 20000);
        if (token == null || !headerToken[2].equals(oldpassword) || oldpassword == null){ //排除token为空、旧密码为空或者旧密码输入错误情况
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
