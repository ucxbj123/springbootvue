package com.maven.springbootvue.Service.Impl;

import com.maven.springbootvue.Mapper.TeststandardMapper;
import com.maven.springbootvue.Pojo.Teststandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 谢秉均
 * @description
 * @date 2023/2/2--14:31
 */
@Service
@Transactional
public class TeststandardServiceImpl {

    @Autowired
    TeststandardMapper teststandardMapper;


    /**
    *@description：保存检验标准的检验项
    *@param
    *@return
    *@Author 谢秉均
    *@date 2023/2/2--14:54
    */
    public Boolean saveTestStandard(List<Teststandard> teststandards, String standardcode){
        //先删除standardcode的检验项
        teststandardMapper.deleteByStandardcode(standardcode);
        //批量插入standardcode的所有的检验项
        if(teststandards.size() > 0){
            LocalDateTime time = LocalDateTime.now();
            for(Teststandard t: teststandards){
                t.setCreatetime(time);
            }
            teststandardMapper.insertStandardprojectBatch(teststandards);
        }
        return true;
    }

    /**
    *@description：根据检验标准编号查询检验项
    *@param
    *@return
    *@Author 谢秉均
    *@date 2023/2/2--15:50
    */
    public List<Teststandard> getStandards(String standardcode){
        return teststandardMapper.getTeststandard(standardcode);
    }
}
