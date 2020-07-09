package com.eatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.eatin.storage.FileStorageProperties;

@EnableConfigurationProperties({ FileStorageProperties.class })
@SpringBootApplication
public class EatinApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatinApplication.class, args);
	}

}
