package com.maven.springbootvue.Mapper;

import com.maven.springbootvue.Pojo.Clazz;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClazzMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Clazz record);

    Clazz selectByPrimaryKey(Integer id);

    List<Clazz> selectAll();

    int updateByPrimaryKey(Clazz record);

    /**
     * 根据班级编号、班级名称动态获取数据，已过滤isdelete为1的数据
     * @param cno
     * @param name
     * @return
     */
    List<Clazz> getClazz(@Param("cno") String cno, @Param("name") String name);

    /**
     * 根据班级编号、班级名称动态获取数据，包括isdelete为1的数据
     * @param cno
     * @param name
     * @return
     */
    List<Clazz> getClazzAll(@Param("cno") String cno, @Param("name") String name);

    /**
     * 根据班级编号更改，整个数据进行修改
     * @param clazz
     * @return
     */
    Integer updateByCno(Clazz clazz);


    /**
     * 根据班级编号 OR 班级名称更改，整个数据进行修改
     * @param clazz
     * @return
     */
    Integer updateByCnoOrName(Clazz clazz);

    /**
     * 根据班级编号和名称删除班级信息，实际是更改isdelete = 1;或者用于班级信息的状态更改
     * @param clazz
     * @return
     */
    Integer deleteClazz(Clazz clazz);

    /**
     * 更新班级的总人数
     * @param cno 班级编码
     * @param number 总人数
     * @return
     */
    Integer updateClazzNumber(@Param("cno")String cno,@Param("number")Integer number);
}