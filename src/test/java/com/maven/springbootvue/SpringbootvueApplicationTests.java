package com.maven.springbootvue;

import com.maven.springbootvue.Dto.UserTypeEnum;
import com.maven.springbootvue.Mapper.TeststandardMapper;
import com.maven.springbootvue.Pojo.Teststandard;
import com.maven.springbootvue.Util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootvueApplicationTests {

	@Autowired
	TeststandardMapper teststandardMapper;

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
		LocalDateTime nowtime = LocalDateTime.now();
		Teststandard teststandard = new Teststandard();
		teststandard.setStandardcode("123");
		teststandard.setCreatetime(nowtime);
		System.out.println(nowtime);
		teststandardMapper.insert(teststandard);
		System.out.println(teststandardMapper.selectAll());
	}

}
