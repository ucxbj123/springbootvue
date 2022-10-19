package com.maven.springbootvue;

import com.maven.springbootvue.Util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootvueApplicationTests {

	@Test
	void contextLoads() {//测试JWT工具
		String token = JWTUtil.sign("396012","student","123456");
		System.out.println("token:"+token);
		System.out.println("token信息:"+JWTUtil.getUsertype(token)+" "+JWTUtil.getUserID(token));

		try{
			TimeUnit.SECONDS.sleep(1);//设置延迟执行检验超时功能
			System.out.println(JWTUtil.verify(token,"396012","123456","student"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
