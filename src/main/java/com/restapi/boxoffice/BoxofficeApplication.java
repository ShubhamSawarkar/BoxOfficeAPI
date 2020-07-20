package com.restapi.boxoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.restapi.model.AdminControl;
import com.restapi.model.Seat;
import com.restapi.model.Show;

import com.restapi.dao.SeatDAO;

@SpringBootApplication
public class BoxofficeApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BoxofficeApplication.class, args);
	}
}
