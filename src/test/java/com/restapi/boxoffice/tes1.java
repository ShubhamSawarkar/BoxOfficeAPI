package com.restapi.boxoffice;

import com.restapi.model.AdminControl;
import com.restapi.model.Seat;
import com.restapi.service.SeatService;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
public class tes1 {
	@Autowired SeatService service;
	public static void main(String agrv[])
	{
		tes1 t = new tes1();
		t.test();
		
	}
	
	public void test()
	{
		for(int row=1; row<=10; row++)
		{
				Seat seat = new Seat();
				seat.setScreenNo(1);
				seat.setRowNo(row);
				seat.setPrice(400.00);
				seat.setCategory("Diamond");
				seat.setSpaceAfter(false);
				AdminControl.addSeat(seat);
		
		}
	}
}
