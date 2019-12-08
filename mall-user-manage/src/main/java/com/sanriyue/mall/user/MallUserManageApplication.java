package com.sanriyue.mall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.sanriyue.mall.user.mapper")
public class MallUserManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallUserManageApplication.class, args);
	}

}
