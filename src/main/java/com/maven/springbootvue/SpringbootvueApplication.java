package com.maven.springbootvue;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {PageHelperAutoConfiguration.class})	#解决mybatis内存分页插件循环依赖的异常问题
@SpringBootApplication
@MapperScan(value = "com.maven.springbootvue.Mapper")
public class SpringbootvueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootvueApplication.class, args);
	}

}
