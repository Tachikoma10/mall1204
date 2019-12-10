package com.sanriyue.mall.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sanriyue.mall")
public class MallItemWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallItemWebApplication.class, args);
	}

}
