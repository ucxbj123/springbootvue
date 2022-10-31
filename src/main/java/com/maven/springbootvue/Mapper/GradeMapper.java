package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Grade;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GradeMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("name") String name);

    int insert(Grade record);

    Grade selectByPrimaryKey(@Param("id") Integer id, @Param("name") String name);

    List<Grade> selectAll();

    int updateByPrimaryKey(Grade record);

    /**
     * 根据年级编号、年级名称动态获取数据
     * @param gno
     * @param name
     * @return
     */
    List<Grade> getGrade(@Param("gno") String gno, @Param("name") String name);

}