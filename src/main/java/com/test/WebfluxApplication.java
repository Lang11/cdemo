package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @program: client
 * @description:
 * @author: daniel
 * @create: 2019-01-14 21:56
 **/
@SpringBootApplication
@EnableWebFlux
public class WebfluxApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}
}
