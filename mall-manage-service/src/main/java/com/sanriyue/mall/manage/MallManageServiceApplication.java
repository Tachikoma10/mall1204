package com.sanriyue.mall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.sanriyue.mall.manage.mapper")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.sanriyue.mall")
public class MallManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallManageServiceApplication.class, args);
	}

}
