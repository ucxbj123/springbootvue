package com.maven.springbootvue;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Mapper.GradeMapper;
import com.maven.springbootvue.Pojo.Grade;
import com.maven.springbootvue.Service.Impl.GradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 谢秉均
 * @description
 * @date 2022/11/4--11:58
 */
@SpringBootTest
public class PagehelperTest {

    @Autowired
    GradeMapper gradeMapper;

    /**
     * 分页插件测试
     */
    @Test
    public void PagehelperCacheTest(){
        PageHelper.startPage(1,1);
        List<Grade> list = gradeMapper.selectAll();
        PageInfo<Grade> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
    }
}
