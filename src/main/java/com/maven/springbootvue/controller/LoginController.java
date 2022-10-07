package com.maven.springbootvue.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maven.springbootvue.Util.CreateVerifiCodeImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("login")
    @ResponseBody
    public JSONObject login(@RequestBody String msg){
        System.out.println("信息："+msg);
        result.put("code",20000);
        result.put("token","lucy");
        result.put("name","lucy");
        JSONObject json = JSON.parseObject(JSONObject.toJSONString(result));
        return json;
    }

    @RequestMapping(value = "info",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject logininfo( String token){
        System.out.println("信息："+token);
        result.put("code",20000);
        result.put("data","info");
        result.put("avatar","@assets/admin.gif");
        result.put("avatar","image/admin.gif");
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
    @RequestMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //生成验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImageUtil.getVerifiCodeImage();
        //将验证码String存在session
        String verifiCode = String.valueOf(CreateVerifiCodeImageUtil.getVerifiCode());
        HttpSession session=request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片输出到登录界面
        try{
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
            logger.error("验证码图片获取成功");
        }catch (IOException e){
            e.printStackTrace();
            logger.error("验证码图片获取失败");
        }
    }
}
