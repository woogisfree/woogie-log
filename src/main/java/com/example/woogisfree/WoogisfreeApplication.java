package com.example.woogisfree;

import com.example.woogisfree.global.common.file.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class WoogisfreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoogisfreeApplication.class, args);
	}

}
