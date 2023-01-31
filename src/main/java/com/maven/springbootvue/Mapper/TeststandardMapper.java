package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Teststandard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeststandardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teststandard record);

    Teststandard selectByPrimaryKey(Integer id);

    List<Teststandard> selectAll();

    int updateByPrimaryKey(Teststandard record);
}