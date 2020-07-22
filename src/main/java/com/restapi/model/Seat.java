package com.restapi.model;

public class Seat {
	private int seatID;
	private int screenNo;
	private int rowNo;
	private int seatNo;
	private double price;
	private String category;
	private boolean spaceAfter;
	
	public Seat() {
		
	}
	
	public Seat(int seatID, int screenNo, int rowNo, int seatNo, double price, String category, boolean spaceAfter) {
		this.seatID = seatID;
		this.screenNo = screenNo;
		this.rowNo = rowNo;
		this.seatNo = seatNo;
		this.price = price;
		this.category = category;
		this.spaceAfter = spaceAfter;
	}
	public int getSeatID() {
		return seatID;
	}
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	public int getScreenNo() {
		return screenNo;
	}
	public void setScreenNo(int screenNo) {
		this.screenNo = screenNo;
	}
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public int getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isSpaceAfter() {
		return spaceAfter;
	}
	public void setSpaceAfter(boolean spaceAfter) {
		this.spaceAfter = spaceAfter;
	}
	
	public char getCharRowNo()
	{
		return (char) (this.rowNo + 64);
	}
	
	public void setRowNo(String rowNo)
	{
		if(rowNo.isBlank())
		{
			this.rowNo = 0;
		}
		char row = rowNo.charAt(0);
		if((row >= 'A') && (row <= 'Z'))
		{
			this.rowNo = row - 64;
		}
		else
		{
			this.rowNo = 0;
		}
	}
}
