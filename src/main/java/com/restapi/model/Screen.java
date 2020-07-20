package com.restapi.model;

import java.util.List;

public class Screen implements Comparable{
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
	public int compareTo(Object screen) {
		if((screen != null) && screen instanceof Screen)
		{
			if(((Screen) screen).ID > 0)
			{
				return this.ID - ((Screen) screen).ID;
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
