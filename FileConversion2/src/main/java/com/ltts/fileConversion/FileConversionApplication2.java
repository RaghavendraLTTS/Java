package com.ltts.fileConversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ltts.fileConversion.Config.FileProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class FileConversionApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(FileConversionApplication2.class, args);
	}

}
