package com.sanriyue.mall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagement
public class MallManageWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallManageWebApplication.class, args);
	}

}
