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
     * 根据年级编号、年级名称动态获取数据，已过滤isdelete为1的数据
     * @param gno
     * @param name
     * @return
     */
    List<Grade> getGrade(@Param("gno") String gno, @Param("name") String name);

    /**
     * 根据年级编号、年级名称动态获取数据，包括isdelete为1的数据
     * @param gno
     * @param name
     * @return
     */
    List<Grade> getGradeAll(@Param("gno") String gno, @Param("name") String name);

    /**
     * 根据年级编号与名称进行更改
     * @param grade
     * @return
     */
    Integer updateByGnoAndName(Grade grade);

    /**
     * 根据年级编号更改
     * @param grade
     * @return
     */
    Integer updateByGno(Grade grade);


    /**
     * 根据年级编号和名称删除年级信息，实际是更改isdelete = 1;或者用于年级信息的状态更改
     * @param grade
     * @return
     */
    Integer deleteGrade(Grade grade);

}