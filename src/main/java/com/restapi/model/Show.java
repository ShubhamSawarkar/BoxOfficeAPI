package com.restapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Show implements QueryResult
{
	private int id;
	private int screenNo;
	private ShowTime showTime;
	private int movieID;
	
	public Show()
	{
		
	}
	
	public Show(int id, int screenNo, String showDate, String showTime, int movieId)
	{
		this.id = id;
		this.screenNo = screenNo;
		setShowDate(showDate);
		setShowTime(showTime);
		this.movieID = movieId;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public int getScreenNo() {
		return screenNo;
	}
	public void setScreenNo(int screenNo) {
		this.screenNo = screenNo;
	}
	
	public String getShowTime() 
	{
		return showTime.getHours() + ":" + showTime.getMinutes();
	}
	
	public void setShowTime(String showTime)
	{
		if(this.showTime == null)
		{
			this.showTime = new ShowTime();
		}
		try
		{
			String[] time = showTime.split(":", 2);
			this.showTime.setHours(Integer.parseInt(time[0]));
			this.showTime.setMinutes(Integer.parseInt(time[1]));
		}catch(NumberFormatException e1)
		{
			this.showTime.setHours(-1);
			this.showTime.setMinutes(-1);
		}
		
	}
	
	public void setShowDate(String showDate)
	{
		if(this.showTime == null)
		{
			this.showTime = new ShowTime();
		}
		try
		{
			String[] date = showDate.split("/", 3);
			this.showTime.setDay(Integer.parseInt(date[0]));
			this.showTime.setMonth(Integer.parseInt(date[1]));
			this.showTime.setYear(Integer.parseInt(date[2]));
		}catch(NumberFormatException e)
		{
			this.showTime.setDay(0);
			this.showTime.setMonth(0);
			this.showTime.setYear(0);
		}
		
	}
	
	public String getShowDate()
	{
		return showTime.getDay() + "/" + showTime.getMonth() + "/" + showTime.getYear();
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	
	public ShowTime getDateTime()
	{
		return this.showTime;
	}
	
	public boolean isValid()
	{
		if((screenNo > 0) && (movieID > 0) && (showTime != null))
		{
			//System.out.println("[DEBUG] : Up to time!");
			return showTime.isValid();
		}else
		{
			//System.out.println("[DEBUG] : Variables not initialised!");
			return false;
		}
	}
	
	@Override
	public boolean equals(Object show)
	{
		if(this == show)
		{
			return true;
		}
		
		if(show instanceof Show)
		{
			if((this.screenNo == ((Show) show).screenNo) && (this.getShowDate() == ((Show) show).getShowDate()) && (this.getShowTime() == ((Show) show).getShowTime()) && (this.getMovieID() == ((Show) show).getMovieID()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public class ShowTime
	{
		private int day;
		private int month;
		private int year;
		
		private int hours;
		private int minutes;
		
		public ShowTime()
		{
			
		}
		
		public ShowTime(int day, int month, int year, int hours, int minutes)
		{
			this.day = day;
			this.month = month;
			this.year = year;
			this.hours = hours;
			this.minutes = minutes;
		}
		
		public ShowTime(int day, int month, int year)
		{
			this(day, month, year, 0, 0);
		}
		
		public boolean isValid()
		{
			 LocalDateTime now = LocalDateTime.now();
			 if((day > 0) && (month > 0) && (year > 0) && (hours >= 0) && (minutes >= 0))
			 {
				 //System.out.println("[DEBUG] : Comparing...");
				 return now.isBefore(LocalDateTime.of(year, month, day, hours, minutes));
			 }else
			 {
					//System.out.println("[DEBUG] : Invalid Show Date or Time!");
				 return false;
			 }
		}

		public int getDay() {
			return day;
		}

		public int getMonth() {
			return month;
		}

		public int getYear() {
			return year;
		}

		public int getHours() {
			return hours;
		}

		public int getMinutes() {
			return minutes;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public void setHours(int hours) {
			this.hours = hours;
		}

		public void setMinutes(int minutes) {
			this.minutes = minutes;
		}
		
		
	}
}
