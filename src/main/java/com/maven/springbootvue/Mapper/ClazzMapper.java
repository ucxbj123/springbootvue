package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Clazz;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClazzMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Clazz record);

    Clazz selectByPrimaryKey(Integer id);

    List<Clazz> selectAll();

    int updateByPrimaryKey(Clazz record);
}