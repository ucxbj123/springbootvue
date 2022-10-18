package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Mapper.AdminMapper;
import com.maven.springbootvue.Pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/18--16:54
 */
@Component
@Transactional
public class AdminServiceImpl {

    @Autowired
    private AdminMapper adminMapper;


    /**
    *@description：登录验证
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--16:55
    */
    public Map<String,Object> loginForm(String userID , String password){
        Map<String,Object> map = new HashMap<>();//存储验证结果
        Admin admin = adminMapper.Login(userID,password);
        System.out.println(admin); //调试-查看查询的结果
        if (admin != null && admin.getIsdelete() != 1 ){
            map.put("status",true);
        }else {
            if(admin != null && admin.getIsdelete() == 1){
                map.put("status", false);
                map.put("msg", "账号已删除");
            }else {
                map.put("status", false);
                map.put("msg", "账号密码错误或者不存在");
            }
        }
//        System.out.println("map:"+map);//调试
        return map;
    }

    /**
    *@description：获取管理员信息
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--16:58
    */
    public Admin getAdmin(String userID){
        return  adminMapper.getAdmin(userID);
    }

    /**
    *@description：修改密码
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--23:08
    */
    public int updatePassword(String ano , String newPassword){
        return adminMapper.updatePassword(ano, newPassword);
    }
}
