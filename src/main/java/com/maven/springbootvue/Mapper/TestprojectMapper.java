package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Testproject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestprojectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Testproject record);

    Testproject selectByPrimaryKey(Integer id);

    List<Testproject> selectAll();

    int updateByPrimaryKey(Testproject record);

    //灵活通过字段的值进行条件过滤查询
    List<Testproject> selectByCondition(Testproject testproject);
}