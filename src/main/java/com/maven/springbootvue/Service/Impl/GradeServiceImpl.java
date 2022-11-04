package com.maven.springbootvue.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Mapper.GradeMapper;
import com.maven.springbootvue.Pojo.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
