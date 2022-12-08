package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Mapper.AdminMapper;
import com.maven.springbootvue.Pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/18--16:54
 */
@Service
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

    /**
    *@description：根据账号进行查询，若账号为空，则查询全部账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/26--13:28
    */
    public List<UserInfo> getAdmins(String ano){
        List<UserInfo> admins = adminMapper.getAdmins(ano);
        return admins;
    }

    /**
    *@description：修改账号状态
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/27--13:51
    */
    public Integer updateStatus(String userID , Integer isdelete){
        return adminMapper.updateStatus(isdelete,userID);
    }

    /**
    *@description：添加单个管理员用户
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--15:04
    */
    public Map<String,Object> insertAdminOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = adminMapper.insertAdminOne(userInfo);
        if(res == 1){//影响的记录数为1则添加单条记录成功
            map.put("success",true);
            map.put("msg","添加成功");
        }else {
            map.put("success",false);
            map.put("msg","添加失败");
        }
        return map;
    }

    /**
    *@description：删除单个管理员用户
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:48
    */
    public Map<String,Object> deleteAdminOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = adminMapper.deleteAdminOne(userInfo);
        if(res == 1){//影响的记录数为1则删除单条记录成功
            map.put("success",true);
            map.put("msg","删除成功");
        }else {
            map.put("success",false);
            map.put("msg","删除失败");
        }
        return map;
    }

    /**
    *@description：修改单个用户信息
    *@param  `
    *@return
    *@Author 谢秉均
    *@date 2022/11/24--10:35
    */
    public Map<String,Object> updateAdminOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = adminMapper.updateAdminOne(userInfo);
        if(res == 1){//影响的记录数为1则修改单条记录成功
            map.put("success",true);
            map.put("msg","更新信息成功");
        }else {
            map.put("success",false);
            map.put("msg","更新信息失败");
        }
        return map;
    }

}
