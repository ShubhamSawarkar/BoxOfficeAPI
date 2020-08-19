package com.restapi.service;

import com.restapi.model.AdminControl;
import com.restapi.model.QueryError;
import com.restapi.model.QueryResult;
import com.restapi.model.Seat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.dao.SeatDAO;
import com.restapi.dao.ShowDAO;;

@Service
public class SeatServiceImpl implements SeatService {
	
	@Autowired
	private SeatDAO seatDao;
	
	@Autowired
	private ShowDAO showDao;

	@Override
	public QueryResult reserve(int screenNo, int rowNo, int seatNo, int showId) 
	{	
		Seat seat = new Seat();
		seat.setScreenNo(screenNo);
		seat.setRowNo(rowNo);
		seat.setSeatNo(seatNo);
		
		if(AdminControl.reserve(seat, showDao.getShowById(showId)))
		{
			QueryResult result = seatDao.getSeat(screenNo, rowNo, seatNo);
			if(result == null)
			{
				return new QueryError("Seat reserved successfully, but unable to fetch!", QueryResult.STATUS_QUERY_OK);
			}
			else
			{
				return result;
			}
		}
		else
		{
			return new QueryError("Unable to Reserve Seat!", QueryResult.STATUS_QUERY_ERROR);
		}
	}

	@Override
	public QueryResult addSeat(int screenNo, int rowNo, double price, String category, boolean spaceAfter) 
	{
		if(seatDao.addSeat(screenNo, rowNo, price, category, spaceAfter))
		{
			Seat seat = new Seat();
			seat.setScreenNo(screenNo);
			seat.setRowNo(rowNo);
			seat.setPrice(price);
			seat.setCategory(category);
			seat.setSpaceAfter(spaceAfter);
			
			return seat;
		}
		else
		{
			return new QueryError("Unable to add Seat!", QueryResult.STATUS_QUERY_ERROR);
		}
	}

	@Override
	public QueryResult changePrice(int screenNo, int rowNo, int seatNo, double price) 
	{
		if(seatDao.changePrice(screenNo, rowNo, seatNo, price))
		{
			return seatDao.getSeat(screenNo, rowNo, seatNo);
		}
		else
		{
			return new QueryError("Unable to change price!", QueryResult.STATUS_QUERY_ERROR);
		}
	}

	@Override
	public List<QueryResult> getSeats(int screenNo) 
	{
		List<Seat> seats = seatDao.getSeats(screenNo);
		
		if((screenNo <= 0) || (seats == null))
		{
			ArrayList<QueryResult> errors = new ArrayList<QueryResult>();
			errors.add(new QueryError("Unable to find requested screen!"));
			return errors;
		}
		else
		{
			seats.sort(null);
			return new ArrayList<QueryResult>(seats);
		}
	}
	
	@Override
	public List<Integer> getScreens()
	{
		return seatDao.getScreens();
	}

}
