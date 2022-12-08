package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Class;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 谢秉均
 * @description
 * @date 2022/12/7--14:57
 */
@Repository
public interface ClassMapper {

    //根据教师编号(tno)或教师姓名(name)或任课班级编号(cno)查询教师的任课信息
    List<Class> getCLass(@Param("tno")String tno, @Param("name")String name, @Param("cno") String cno);

    //批量插入多条任课记录
    Integer insertCLassBatch(List<Class> classes);

    //批量删除多条任课记录
    Integer deleteCLassBatch(@Param("classes") List<Class> classes, @Param("tno") String tno);
}
