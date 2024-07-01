package com.rosenzest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.rosenzest.server.base.annotation.EnableJwt;

@SpringBootApplication()
@ServletComponentScan
@EnableJwt
@MapperScan("com.rosenzest.electric.**.mapper")
public class ElectricApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricApiApplication.class, args);
	}

}
