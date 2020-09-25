package com.ct.gim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MergeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MergeApplication.class);
	}

}
