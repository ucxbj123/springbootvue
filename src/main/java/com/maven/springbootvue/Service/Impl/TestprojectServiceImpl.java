package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Mapper.TestprojectMapper;
import com.maven.springbootvue.Mapper.TeststandardMapper;
import com.maven.springbootvue.Pojo.Testproject;
import com.maven.springbootvue.Util.CreateStandardCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 谢秉均
 * @description 检验标准逻辑封装
 * @date 2023/2/1--11:40
 */
@Service
@Transactional
public class TestprojectServiceImpl {

    @Autowired
    private TestprojectMapper testprojectMapper;

    @Autowired
    private TeststandardMapper teststandardMapper;

    /**
    *@description：新建新的校验项目
    *@param
    *@return
    *@Author 谢秉均
    *@date 2023/2/1--11:51
    */
    public int InsertTestProject(Testproject testproject){
        //获取当天时间并转成字符串用于查询
        LocalDateTime time = LocalDateTime.now();
        String timecode = time.toString().split("T")[0].replace("-","");
        //查询当天已创建的编码，根据个数再生成新的编码
        Testproject t1 = new Testproject();
        t1.setStandardcode(timecode);
        List<Testproject> list = testprojectMapper.selectByCondition(t1);
        //新的编号
        String standardcode = CreateStandardCodeUtil.getStandardCode(list.size());
        //插入新的项目
        testproject.setStandardcode(standardcode);
        testproject.setCreatetime(time);
        testproject.setIsenabled(true);
        int num = testprojectMapper.insert(testproject);
        return num;
    }

    //返回所有的项目
    public List<Testproject> getTestproject(){
        return testprojectMapper.selectAll();
    }



}
