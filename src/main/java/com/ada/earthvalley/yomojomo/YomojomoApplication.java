package com.ada.earthvalley.yomojomo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class YomojomoApplication {

	public static void main(String[] args) {
		SpringApplication.run(YomojomoApplication.class, args);
	}

}
