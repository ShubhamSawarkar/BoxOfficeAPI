package com.restapi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Movie implements QueryResult 
{
	private int movieID;
	private String title;
	private int runTime;
	private String releaseDate;
	private LocalDate releaseDt;
	//private LocalDate lastShowDt;
	
	public Movie()
	{
		
	}
	
	public Movie(int id, String title, int runtime, String releaseDt)
	{
		this.movieID = id;
		this.title = title;
		this.runTime = runtime;
		this.setReleaseDt(releaseDt);
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getRunTime() {
		return runTime;
	}
	
	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}
	
	public String getReleaseDt() {
		return releaseDt.getDayOfMonth() + "/" + releaseDt.getMonthValue() + "/" + releaseDt.getYear();
	}
	
	public void setReleaseDt(String releaseDt) 
	{
		try 
		{
			this.releaseDate = releaseDt;
			String[] date = releaseDt.split("/", 3);
			this.releaseDt = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
		}
		catch(NumberFormatException e)
		{
			this.releaseDt = null;
		}
		
	}
	
	/*
	 * public String getLastShowDt() { return lastShowDt.getDayOfMonth() + "/" +
	 * lastShowDt.getMonthValue() + "/" + lastShowDt.getYear(); } public void
	 * setLastShowDt(String lastShowDt) { String[] date = lastShowDt.split(":", 3);
	 * this.lastShowDt =
	 * LocalDate.of(Integer.valueOf(date[2]),Integer.valueOf(date[1]),
	 * Integer.valueOf(date[0])); }
	 */
	
	public void setReleaseDate(String date)
	{
		this.setReleaseDt(date);
	}
	
	public LocalDate getLocalReleaseDt()
	{
		return this.releaseDt;
	}
	
	public void setReleaseDtStr(String date)
	{
		this.setReleaseDt(date);
		this.releaseDate = date;
	}
	
	public String relaseDtStr()
	{
		return this.releaseDate;
	}
	
	@Override
	public boolean equals(Object movie)
	{
		if(this == movie)
		{
			return true;
		}
		
		if(movie instanceof Movie)
		{
			if((this.getTitle() == ((Movie) movie).getTitle()) && (this.getRunTime() == ((Movie) movie).getRunTime()) && (this.getReleaseDt() == ((Movie) movie).getReleaseDt()))
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
	
	public boolean isValid()
	{
		/*
		 * if(this.getLocalReleaseDt() == null) { return false; }
		 * 
		 * LocalDate today = LocalDate.now();
		 * 
		 * if(this.getLocalReleaseDt().isBefore(today)) { return true; } else { return
		 * false; }
		 */
		return true;
	}
}
