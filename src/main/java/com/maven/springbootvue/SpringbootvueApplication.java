package com.maven.springbootvue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.maven.springbootvue.Mapper")
public class SpringbootvueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootvueApplication.class, args);
	}

}
