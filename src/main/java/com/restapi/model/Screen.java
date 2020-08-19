package com.restapi.model;

import java.util.List;

public class Screen implements Comparable<Screen>{
	private int ID;
	private int screenNo;
	private Show show;
	private List<Row> rows;
	
	public void setID(int id)
	{
		this.ID = id;
	}
	
	public void setScreenNo(int num)
	{
		this.screenNo = num;
	}

	public void setShow(Show show)
	{
		this.show = show;
	}
	
	public void setRows(List<Row> rows)
	{
		this.rows = rows;
	}
	
	public int getScreenNo() {
		return screenNo;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public Show getShow()
	{
		return this.show;
	}
	
	@Override
	public int compareTo(Screen screen) {
		if((screen != null))
		{
			if(screen.ID > 0)
			{
				return this.ID - screen.ID;
			}else
			{
				return -1;
			}
		}else
		{
			return -1;
		}
	}

	
}
