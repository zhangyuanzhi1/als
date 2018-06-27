package com.als.account.permission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class PermissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PermissionApplication.class, args);
	}
}
