package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Teststandard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeststandardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teststandard record);

    Teststandard selectByPrimaryKey(Integer id);

    List<Teststandard> selectAll();

    int updateByPrimaryKey(Teststandard record);

    //根据校验标准编号进行对应检验项的删除
    int deleteByStandardcode(@Param("standardcode") String standardcode);

    //批量插入检验标准的检验项
    int insertStandardprojectBatch(@Param("items") List<Teststandard> items);

    //根据校验标准编号进行查询
    List<Teststandard> getTeststandard(@Param("standardcode") String standardcode);
}