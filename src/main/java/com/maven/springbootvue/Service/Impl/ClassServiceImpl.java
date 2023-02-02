package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Mapper.ClassMapper;
import com.maven.springbootvue.Mapper.ClazzMapper;
import com.maven.springbootvue.Pojo.Class;
import com.maven.springbootvue.Pojo.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/12/7--15:07
 */
@Service
@Transactional
public class ClassServiceImpl {

    //教师任课的DAO接口
    @Autowired
    ClassMapper classMapper;

    //班级DAO接口
    @Autowired
    ClazzMapper clazzMapper;

    /**
    *@description：获取全部
    *@param tno 教师编号
     * @param Classes 是否仅获取tno教师的任课信息。true：仅获取教师任课信息;false：同时获取所有班级信息
    *@return
    *@Author 谢秉均
    *@date 2022/12/7--15:15
    */
    public Map<String, Object> getCLazzInfo(String tno, Boolean Classes){
        //存储结果
        Map<String,Object> map = new LinkedHashMap<>();
        //获取教师的任课信息
        List<Class> classes = classMapper.getCLass(tno, null, null);
        map.put("class",classes);
        //根据AllClasses的值判断是否获取所有班级信息
        if(!Classes){
            List<Clazz> clazzes = clazzMapper.getClazz(null,null);
            map.put("clazz",clazzes);
        }
        return map;
    }

    /**
    *@description：批量添加教师任课班级信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/7--17:15
    */
    public Integer insertClassBatch(List<Class> list){
        Integer res = 0;
        if(list.size() > 0){
            res = classMapper.insertCLassBatch(list);
        }
        return res;
    }

    /**
    *@description：批量删除教师tno的任课信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/12/8--11:13
    */
    public Integer deleteClassBatch(@RequestBody List<Class> list){
        Integer res = 0;
        if(list.size() > 0){//获取教师编号
            String tno = list.get(0).getTno();
            res = classMapper.deleteCLassBatch(list,tno);
        }
        return res;
    }

    /**
    *@description：修改任课内容
    *@Param: tno 教师编号   cno 班级编号 content 任课内容
    *@return:
    *@Author: 谢秉均
    *@date: 2022/12/9--9:53
    */
    public Integer updateContent(String tno, String cno, String content){
        Integer res = classMapper.updateContent(tno,cno,content);
        return res;
    }

    /**
    *@description：根据教师编号和班级编号获取任课内容
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/12/9--10:16
    */
    public String getContentByTnoAndCno(String tno, String cno){
        String content = "";
        List<Class> list = classMapper.getCLass(tno,null,cno);
        if(list.size() >= 1){
            content = list.get(0).getContent();
        }
        return content;
    }
}
