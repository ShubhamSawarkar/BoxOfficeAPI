package com.restapi.service;

import java.util.List;

import com.restapi.model.QueryResult;

public interface SeatService {

	public QueryResult reserve(int screenNo, int rowNo, int seatNo, int showId);
	public QueryResult addSeat(int screenNo, int rowNo, double price, String category, boolean spaceAfter);
	public QueryResult changePrice(int screenNo, int rowNo, int seatNo, double price);
	public List<QueryResult> getSeats(int screenNo);
	public List<Integer> getScreens();
}
