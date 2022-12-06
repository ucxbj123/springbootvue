package com.maven.springbootvue.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maven.springbootvue.Dto.StudentDto;
import com.maven.springbootvue.Dto.TeacherDto;
import com.maven.springbootvue.Dto.UserInfo;
import com.maven.springbootvue.Mapper.ClazzMapper;
import com.maven.springbootvue.Mapper.StudentMapper;
import com.maven.springbootvue.Pojo.Student;
import com.maven.springbootvue.Pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 谢秉均
 * @date 2022/10/17--23:44
 */
@Service
@Transactional  //开启事务 默认传播行为是：Propagation.REQUIRED；隔离级别：为数据源的默认隔离级别，mysql是REPEATABLE_READ（可重复读），能解决脏读、不可重复读问题
public class StudentServiceImpl {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    //学生登录验证
    public Map<String,Object> loginForm(String sno , String password){
        Map<String,Object> map = new HashMap<>();//存储验证结果
        Student student = studentMapper.Login(sno,password);
        System.out.println(student); //调试-查看查询的结果
        if (student != null && student.getIsdelete() != 1 ){
            map.put("status",true);
        }else {
            if(student != null && student.getIsdelete() == 1){
                map.put("status", false);
                map.put("msg", "账号已删除");
            }else {
                map.put("status", false);
                map.put("msg", "账号密码错误或者不存在");
            }

        }
        return map;
    }

    //获取单个学生信息
    public Student getStudent(String userID){
        return studentMapper.getStudent(userID);
    }

    /**
    *@description：修改密码
    *@Param:
    *@return:
    *@Author: 谢秉均
    *@date: 2022/10/18--23:30
    */
    public int updatePassword(String sno , String newPassword){
        return studentMapper.updatePassword(sno, newPassword);
    }

    /**
    *@description：根据账号进行查询，若账号为空，则查询全部账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/26--15:46
    */
    public List<UserInfo> getStudents(String sno){
        List<UserInfo> students = studentMapper.getStudents(sno);
        return students;
    }

    /**
    *@description：修改账号状态
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/10/27--13:52
    */
    public Integer updateStatus(String userID , Integer isdelete){
        return studentMapper.updateStatus(isdelete,userID);
    }

    /**
    *@description：添加学生账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:17
    */
    public Map<String,Object> insertStudentOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = studentMapper.insertStudentOne(userInfo);
        if(res == 1){//影响的记录数为1则添加单条记录成功
            map.put("success",true);
            map.put("msg","添加成功");
        }else {
            map.put("success",false);
            map.put("msg","添加失败");
        }
        return map;
    }

    /**
    *@description：删除学生账号
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/22--17:57
    */
    public Map<String,Object> deleteStudentOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = studentMapper.deleteStudentOne(userInfo);
        if(res == 1){//影响的记录数为1则删除单条记录成功
            map.put("success",true);
            map.put("msg","删除成功");
        }else {
            map.put("success",false);
            map.put("msg","删除失败");
        }
        return map;
    }

    /**
    *@description：修改单个用户信息
    *@param
    *@return
    *@Author 谢秉均
    *@date 2022/11/24--11:19
    */
    public Map<String,Object> updateStudentOne(UserInfo userInfo){
        Map<String, Object> map =new HashMap<>();
        Integer res = studentMapper.updateStudentOne(userInfo);
        if(res == 1){//影响的记录数为1则修改单条记录成功
            map.put("success",true);
            map.put("msg","更新信息成功");
        }else {
            map.put("success",false);
            map.put("msg","更新信息失败");
        }
        return map;
    }

    /**
    *@description：根据姓名、账号、性别、账号是否正常动态获取分页的学生信息
     *@param currentPage 当前页
     * @param pagesize  每页的记录数
    *@return
    *@Author 谢秉均
    *@date 2022/12/1--16:42
    */
    public Map<String,Object> getPageStudent(StudentDto studentDto, Integer currentPage, Integer pagesize){
        //分页
        PageHelper.startPage(currentPage,pagesize);
        List<Student> list = studentMapper.selectDynamic(studentDto);
        PageInfo<Student> pageInfo = new PageInfo<>(list);

        //获取分页后的数据
        List<Student> list1 = pageInfo.getList();

        //声明一个列表存储处理后的数据，用于返回前端
        List<Student> list2 = new ArrayList<>();

        if(studentDto.getIsdelete()){//若为true则过滤已禁用的账号，否则查询全部的教师账号进行返回
            for( int i = 0; i < list1.size(); i++){
                if(list1.get(i).getIsdelete() != 1){
                    list2.add(list1.get(i));
                }
            }
        }else {
            list2 = list1;
        }

        //返回pageinfo与处理好的数据
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("students",list2);
        map.put("total",pageInfo.getTotal());
        return map;
    }

    /**
    *@description：通过班级编码查询学生，若cno=null则查询未分配班级的学生，若cno=''则查询全部学生
    *@param  cno 班级编码
    *@return
    *@Author 谢秉均
    *@date 2022/12/5--11:06
    */
    public List<Student> selectByCno(String cno){
        List<Student> list = studentMapper.selectByCno(cno);
        return list;
    }

    /**
    *@description：
    *@param  user  进行分配的班级学生名单
     * @param cno 班级编号
     * @param clazz_name 班级名称
     * @param shift 是否分配班级 true：修改学生的所属班级为cno；false：重置学生所属班级为null；取消分配
    *@return
    *@Author 谢秉均
    *@date 2022/12/5--15:41
    */
    public Integer updateClazzMore(List<String> user, String cno, String clazz_name, Boolean shift){
        //new一个新的学生列表，用于执行Mapper的修改班级功能
        List<Student> students = new ArrayList<>();

        if(shift){//true：修改学生的所属班级为cno；false：重置学生所属班级为null；取消分配
            for (int i = 0; i < user.size(); i++){
                Student student = new Student();//循环必须新建一个对象，否则内容添加到students的内容会是同一个学生信息
                student.setClazz_name(clazz_name);
                student.setCno(cno);
                student.setSno(user.get(i));
                students.add(student);
            }
        }else {
            for (int i = 0; i < user.size(); i++){
                Student student = new Student();
                student.setClazz_name(null);
                student.setCno(null);
                student.setSno(user.get(i));
                students.add(student);
            }
        }
        System.out.println("封装好的学生："+students);
        Integer res = studentMapper.updateClazzMore(students);
        return res;

    }

    /**
    *@description：更新班级的最新总人数
    *@param  cno 班级编码
    *@return
    *@Author 谢秉均
    *@date 2022/12/5--17:34
    */
    public void updateClazzNumber(String cno){
        //查询班级cno的总人数
        List<Student> list = studentMapper.selectByCno(cno);
        Integer number = list.size();
        //更新班级cno的最新总人数
        clazzMapper.updateClazzNumber(cno, number);
    }


}
