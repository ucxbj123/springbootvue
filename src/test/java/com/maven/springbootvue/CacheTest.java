package com.maven.springbootvue;

import com.maven.springbootvue.Mapper.AdminMapper;
import com.maven.springbootvue.Mapper.TeacherMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 谢秉均
 * @date 2022/10/24--21:27
 * 测试mybatis-plus集成第三方缓存ehcache的效果
 * 说明：本次测试只有AdminMapper.xml使用<cache></cache>开启了二级缓存
 * 本项目集成shiro进行认证管理，使用的service都进行了事务管理，但是由于部分的方法内只有一个sql操作，因而部分service的一级缓存不生效，
 *      需要开启二级缓存减少数据库大量的IO访问压力
 */
@SpringBootTest
public class CacheTest {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    AdminMapper adminMapper;

    /**
     * 使一级缓存失效的四种情况：
     * 1) 不同的SqlSession对应不同的一级缓存
     * 2) 同一个SqlSession但是查询条件不同
     * 3) 同一个SqlSession两次查询期间执行了任何一次增删改操作
     * 4) 同一个SqlSession两次查询期间手动清空了缓存
     * 说明：如果想看是否是同一个sqlsession，日志调为debug，查看【Creating a new SqlSession】创建了几个sqlsession
     */

    /**
     * 测试springboot默认使用哪个sqlsession创建mapper
     * 结论：
     * 1、springboot默认使用SqlSessionTemplate动态代理生成Mapper对象，因为SqlSessionTemplate是线程安全的，在执行完语句后，sqlsession会关闭，
     *  因而sqlsession不是同一个，一级缓存会失效。
     * 2、如果使用sqlSessionFactory创建sqlsession使用，此时线程不是安全的，但是一级缓存是生效的。
     */

    @Test
    public void TestOneCache(){

        //1、直接使用已经通过动态代理生成的mapper执行select会执行两次查询
//        System.out.println(teacherMapper.getTeacher("389432"));
//        System.out.println(teacherMapper.getTeacher("389432"));

        //2、使用sqlSessionFactory创建sqlsession生成mapper执行select只会执行一次查询
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
//        System.out.println(mapper.getTeacher("389432"));
//        System.out.println(mapper.getTeacher("389432"));

        //3、使用sqlSessionTemplate生成mapper执行select会执行两次
        TeacherMapper mapper2 = sqlSessionTemplate.getMapper(TeacherMapper.class);
        System.out.println(mapper2.getTeacher("389432"));
        System.out.println(mapper2.getTeacher("389432"));
    }

    /**
     * 在使用sqlSessionTemplate或者已经动态代理生成的mapper对像（线程安全的）
     * 如果有事务，同一个事务中相同的查询使用的相同的SqlSessioon，此时一级缓存是生效的，只会执行一次select
     */
    @Test
    @Transactional
    public void TestOneCache2(){
        System.out.println(teacherMapper.getTeacher("389432"));
        System.out.println(teacherMapper.getTeacher("389432"));
    }

    /**
     * 二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被
     * 缓存；此后若再次执行相同的查询语句，结果就会从缓存中获取
     * 二级缓存开启的条件：
     * a>在核心配置文件中，设置全局配置属性cacheEnabled="true"，默认为true，不需要设置
     * b>在映射文件中设置标签<cache />
     * c>二级缓存必须在SqlSession关闭或提交之后有效
     * d>查询的数据所转换的实体类类型必须实现序列化的接口
     * 使二级缓存失效的情况：
     * 两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效
     */

    /**
     * springboot开启二级缓存的配置：
     * 1、application.yml配置开启二级缓存：mybatis-plus.configuration.cache-enabled=true （默认好像是开启的）
     * 2、在Mapper.xml文件配置<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
     *
     * 测试开启二级缓存后，动态代理生成线程安全Mapper对象效果：
     * 只会执行一次select语句，二级缓存生效；但是一样会创建两次sqlsession
     *
     */
    @Test
//    @Transactional
    public void TestTwoCache(){
        System.out.println(adminMapper.getAdmin("456"));
        System.out.println(adminMapper.getAdmin("456"));
    }
}
