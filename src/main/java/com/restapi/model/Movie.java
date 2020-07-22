package com.restapi.model;

import java.time.LocalDate;

public class Movie {

	private int movieID;
	private String title;
	private int runTime;
	private String releaseDate;
	private LocalDate releaseDt;
	private LocalDate lastShowDt;
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
	public void setReleaseDt(String releaseDt) {
		this.releaseDate = releaseDt;
		String[] date = releaseDt.split("/", 3);
		this.releaseDt = LocalDate.of(Integer.valueOf(date[2]), Integer.valueOf(date[1]), Integer.valueOf(date[0]));
	}
	public String getLastShowDt() {
		return lastShowDt.getDayOfMonth() + "/" + lastShowDt.getMonthValue() + "/" + lastShowDt.getYear();
	}
	public void setLastShowDt(String lastShowDt) {
		String[] date = lastShowDt.split(":", 3);
		this.lastShowDt = LocalDate.of(Integer.valueOf(date[2]),Integer.valueOf(date[1]), Integer.valueOf(date[0]));
	}
	
	public LocalDate getLocalReleaseDt()
	{
		return this.releaseDt;
	}
	
	public void setReleaseDtStr(String date)
	{
		this.releaseDate = date;
	}
	
	public String relaseDtStr()
	{
		return this.releaseDate;
	}
	
	
}
