package com.penn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsrApplication {

	public static void main(String[] args) {
		System.out.println("=============spring boot begin start=============================");
		SpringApplication.run(AsrApplication.class, args);
		System.out.println("=============spring boot start success=============================");
	}

}
