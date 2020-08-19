package com.restapi.service;

import java.util.List;

import com.restapi.model.QueryResult;
import com.restapi.model.Show;

public interface ShowService {

	public Show addShow(int screenNo, String date, String time, int movieId);
	public List<QueryResult> getShows();
	public List<Boolean> getShowStatus(int showId);
	public Show getShowById(int showId);
}
