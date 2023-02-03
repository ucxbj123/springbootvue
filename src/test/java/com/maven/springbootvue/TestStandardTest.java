package com.maven.springbootvue;

import com.maven.springbootvue.Dto.StandardResult;
import com.maven.springbootvue.Mapper.TeststandardMapper;
import com.maven.springbootvue.Pojo.Teststandard;
import com.maven.springbootvue.Util.StandardUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 谢秉均
 * @date 2023/2/2--20:53
 */

@SpringBootTest
public class TestStandardTest {

    @Autowired
    TeststandardMapper teststandardMapper;

    @Test
    public void test1(){//校验逻辑测试
        List<Teststandard> list = teststandardMapper.getTeststandard("Dream-2023020200001");
        List<StandardResult> results = StandardUtil.Check(list,27, "51680589**E22131aYHB1A19001");
//        for (Teststandard t: list){
//            System.out.println("type:"+t.getStandardproject()+" "+StandardUtil.checkType(t));
//            System.out.println("参考值："+t.getCheckvalue()+"-"+t.getCheckvalue1());
//            StandardResult result = StandardUtil.checkValue(t,"51680589**E22131aYHB1A19001",27);
////            System.out.println("逻辑值："+t.getLogicvalue());
//            System.out.println(result);
//        }
        for (StandardResult t: results){
            System.out.println(t);
        }
//        System.out.println("type:"+list.get(2).getStandardproject()+" "+StandardUtil.checkType(list.get(2)));
//        StandardResult result = StandardUtil.checkValue(list.get(2),"51680589**E220215YHB1A19001",27);
//        System.out.println(result);
//        StandardResult result = StandardUtil.checkValue(list.get(0),"51680589**E220809YHB1A19001",27);

//        System.out.println("类型:"+StandardUtil.checkType(list.get(2)));

    }
}
