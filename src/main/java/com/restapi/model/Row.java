package com.restapi.model;

import java.util.List;

public class Row {
	private String rowNo;
	private Screen screen;
	
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	
	public void setScreen(Screen screen)
	{
		this.screen =screen;
	}
	
	public void setSeats(List<Seat> seats)
	{
	}
	
	public String getRowNo() {
		return rowNo;
	}

	public Screen getScreen() {
		return screen;
	}

}
