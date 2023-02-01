package com.maven.springbootvue;

import com.maven.springbootvue.Dto.UserTypeEnum;
import com.maven.springbootvue.Mapper.TestprojectMapper;
import com.maven.springbootvue.Mapper.TeststandardMapper;
import com.maven.springbootvue.Pojo.Testproject;
import com.maven.springbootvue.Pojo.Teststandard;
import com.maven.springbootvue.Service.Impl.TestprojectServiceImpl;
import com.maven.springbootvue.Util.CreateStandardCodeUtil;
import com.maven.springbootvue.Util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootvueApplicationTests {

	@Autowired
	TeststandardMapper teststandardMapper;

	@Autowired
	TestprojectMapper testprojectMapper;

	@Autowired
	TestprojectServiceImpl testprojectService;

	@Test
	void contextLoads() {//测试JWT工具
		String token = JWTUtil.sign("396012","student","123456");
		System.out.println("token:"+token);
		System.out.println("token信息:"+JWTUtil.getUsertype(token)+" "+JWTUtil.getUserID(token));

		try{
			TimeUnit.SECONDS.sleep(2);//设置延迟执行检验超时功能
			System.out.println(JWTUtil.verify(token,"396012","123456","student"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	void testEnum(){//测试枚举类型的输出
		System.out.println(UserTypeEnum.ADMIN);
		System.out.println(UserTypeEnum.ADMIN.getUsertype());
	}

	@Test
	void TestStandard(){//测试检验标准表的添加功能及时间获取是否正确
		//详细表
//		LocalDateTime nowtime = LocalDateTime.now();
//		Teststandard teststandard = new Teststandard();
//		teststandard.setStandardcode("123");
//		teststandard.setCreatetime(nowtime);
//		System.out.println(nowtime);
//		teststandardMapper.insert(teststandard);
//		System.out.println(teststandardMapper.selectAll());

		//总表
		LocalDateTime time =LocalDateTime.now();
		String timecode = time.toString().split("T")[0].replace("-","");
//		Testproject testproject = new Testproject();
//		testproject.setStandardcode("Dream-"+timecode+"00003");
//		testprojectMapper.insert(testproject);
//		System.out.println(testprojectMapper.selectAll());

		//格式化数据
		System.out.println(String.format("%05d",52));
		//插入新项目
		Testproject t1 = new Testproject();
		t1.setStandardname("项目1");
		testprojectService.InsertTestProject(t1);

	}

}
