package com.Jsoup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.Jsoup")//扫描注解包
@SpringBootApplication
public class JsoupApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsoupApplication.class, args);
	}
}
