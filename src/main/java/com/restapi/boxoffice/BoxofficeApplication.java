package com.restapi.boxoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.restapi"})
public class BoxofficeApplication
{	
	public static void main(String[] args) 
	{	
		SpringApplication.run(BoxofficeApplication.class, args);
	}
}
