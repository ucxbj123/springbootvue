package com.maven.springbootvue.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Mapper.ClazzMapper;
import com.maven.springbootvue.Pojo.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/11/17--15:08
 */
@Service
@Transactional
public class ClazzServiceImpl {

    @Autowired
    ClazzMapper clazzMapper;

    /**
    *@description：获取班级的分页的数据
     *@param  cno 班级编号
     * @param  name 班级名称
     * @param pagesize 每页显示数量
     * @param currentPage 当前页显示
    *@return
    *@Author 谢秉均
    *@date 2022/11/17--15:09
    */
    public PageInfo<Clazz> getPageClazz(String cno, String name, Integer pagesize, Integer currentPage){
        PageHelper.startPage(currentPage,pagesize);
        List<Clazz> list = clazzMapper.getClazz(cno,name);
        PageInfo<Clazz> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加新班级记录
     * @param clazz
     * @return
     */
    public Integer InsertClazz(Clazz clazz){
        List<Clazz> clazzes = clazzMapper.getClazzAll(clazz.getCno(),null);
        //执行结果
        Integer res = null;
        if( clazzes.size() == 1 && clazzes.get(0).getIsdelete() < 1){//班级已存在，通过编号验证
            return -1;
        }else if ( clazzes.size() == 1 && clazzes.get(0).getIsdelete() == 1){//有班级信息，处于已删除状态，进行状态修改
            //处于已删除的数据变更为未删除，等于做了更改操作
            clazz.setIsdelete(0);
            res = clazzMapper.updateByCno(clazz);
        }else if ( clazzes.size() < 1 ){//不存在相应的数据,执行添加操作
            res = clazzMapper.insert(clazz);
        }
        return res;
    }

    /**
     * 更新班级信息
     * @param clazz
     * @return
     */
    public Integer updateClazz(Clazz clazz){
        //能进行班级信息更改的数据都是处于未删除的数据，设为0，避免状态被更改为已删除
        clazz.setIsdelete(0);
        Integer res = clazzMapper.updateByCnoOrName(clazz);
        return res;
    }

    /**
     * 根据班级编号和名称删除班级信息，实际是更改isdelete = 1
     * @param clazz
     */
    public Map<String,Object> deleteClazz(Clazz clazz){
        Integer res = clazzMapper.deleteClazz(clazz);
        Map<String,Object> map = new LinkedHashMap<>();
        if (res > 0){
            map.put("success",true);
            map.put("msg","删除成功了");
        }else {
            map.put("success",false);
            map.put("msg","删除失败了");
        }
        return map;
    }
}
