package com.maven.springbootvue.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Mapper.GradeMapper;
import com.maven.springbootvue.Pojo.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢秉均
 * @description
 * @date 2022/10/31--16:50
 */
@Service
@Transactional
public class GradeServiceImpl {

    @Autowired
    private GradeMapper gradeMapper;

    /**
    *@description：获取年级的分页的数据
    *@param  gno 年级编号
     * @param  name 年级名称
     * @param pagesize 每页显示数量
     * @param currentPage 当前页显示
    *@return
    *@Author 谢秉均
    *@date 2022/10/31--16:53
    */
    public PageInfo<Grade> getPageGrade(String gno, String name, Integer pagesize, Integer currentPage){
        PageHelper.startPage(currentPage,pagesize);
        List<Grade> list = gradeMapper.getGrade(gno,name);
        PageInfo<Grade> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加新年级记录
     * @param grade
     * @return
     */
    public Integer InsertGrade(Grade grade){
        List<Grade> grades = gradeMapper.getGradeAll(grade.getGno(),null);
        //执行结果
        Integer res = null;
        if( grades.size() == 1 && grades.get(0).getIsdelete() < 1){//年级已存在，通过编号验证
            return -1;
        }else if ( grades.size() == 1 && grades.get(0).getIsdelete() == 1){//有年级信息，处于已删除状态，进行状态修改
            //处于已删除的数据变更为未删除，等于做了添加操作
            grade.setIsdelete(0);
            res = gradeMapper.updateByGno(grade);
        }else if ( grades.size() < 1 ){//不存在相应的数据,执行添加操作
            res = gradeMapper.insert(grade);
        }
        return res;
    }

    /**
     * 更新年级信息
     * @param grade
     * @return
     */
    public Integer updateGrade(Grade grade){
        //能进行年级信息更改的数据都是处于未删除的数据，设为0，避免状态被更改为已删除
        grade.setIsdelete(0);
        Integer res = gradeMapper.updateByGnoAndName(grade);
        return res;
    }

    /**
     * 根据年级编号和名称删除年级信息，实际是更改isdelete = 1
     * @param grade
     */
    public Map<String,Object> deleteGrade(Grade grade){
        Integer res = gradeMapper.deleteGrade(grade);
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
