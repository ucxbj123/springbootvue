package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Dto.StandardResult;
import com.maven.springbootvue.Mapper.TestprojectMapper;
import com.maven.springbootvue.Mapper.TeststandardMapper;
import com.maven.springbootvue.Pojo.Testproject;
import com.maven.springbootvue.Pojo.Teststandard;
import com.maven.springbootvue.Util.CreateStandardCodeUtil;
import com.maven.springbootvue.Util.StandardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
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

    //检验项的操作
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

    //启用或者禁用项目检验
    public void updateIsenabled(Boolean isenabled, Integer id){
        testprojectMapper.updateIsenabled(isenabled, id);
    }

    /**
    *@description：删除检验标准项目与明细的检验项
    *@param
    *@return
    *@Author 谢秉均
    *@date 2023/2/2--16:15
    */
    public void deleteProject(String standardcode){
        //删除检验标准项目
        testprojectMapper.deleteBystandardcode(standardcode);
        //删除检验项
        teststandardMapper.deleteByStandardcode(standardcode);
    }
    
    /**
    *@description：
    *@param  standardcode 检验标准编号
     * @param code 需要校验的编码
    *@return
    *@Author 谢秉均
    *@date 2023/2/3--13:55
    */
    public List<StandardResult> checkcode(String standardcode, String code){
        Testproject testproject = new Testproject();
        testproject.setStandardcode(standardcode);
        //获取检验标准的长度
        int len = testprojectMapper.selectByCondition(testproject).get(0).getChecklength();
        //获取检验标准明细
        List<Teststandard> teststandards = teststandardMapper.getTeststandard(standardcode);
        List<StandardResult> results;
        if(teststandards.size() > 0 && code.length() == len){
            //校验编码
            results = StandardUtil.Check(teststandards, len, code);
        }else if(code.length() != len) {
            results = new ArrayList<>();
            StandardResult result = new StandardResult();
            result.setSuccess(false);
            result.setMessage("条码长度不等于"+len);
            results.add(result);
        }else {
            results = new ArrayList<>();
            StandardResult result = new StandardResult();
            result.setSuccess(false);
            result.setMessage("检验标准未设置校验项");
            results.add(result);
        }
        return results;

    }



}
