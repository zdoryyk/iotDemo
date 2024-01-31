package com.iot.iotDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IotDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotDemoApplication.class, args);
	}

}
