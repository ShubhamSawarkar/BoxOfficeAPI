package com.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.dao.ShowDAO;
import com.restapi.model.QueryResult;
import com.restapi.model.Show;
import com.restapi.model.Seat;
import com.restapi.dao.SeatDAO;

@Service
public class ShowServiceImpl implements ShowService {
	
	@Autowired
	private ShowDAO showDao;
	
	@Autowired
	private SeatDAO seatDao;

	@Override
	public Show addShow(int screenNo, String date, String time, int movieId) 
	{
		Show show = new Show();
		show.setScreenNo(screenNo);
		show.setShowDate(date);
		show.setShowTime(time);
		show.setMovieID(movieId);
		
		if(show.isValid() && showDao.addShow(show))
		{
			//System.out.println(showDao.findShow(screenNo, date, time, movieId));
			//return showDao.findShow(screenNo, date, time, movieId);
			return show;
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<QueryResult> getShows() 
	{	
		return new ArrayList<QueryResult>(showDao.getShows());
	}
	
	@Override
	public List<Boolean> getShowStatus(int showId)
	{
		List<Seat> seats = seatDao.getSeats(showDao.getShowById(showId).getScreenNo());
		List<Boolean> status = new ArrayList<>();
		
		for(Seat seat : seats)
		{
			if(seatDao.isReserved(seat.getSeatID(), showId))
			{
				status.add(false);
			}
			else
			{
				status.add(true);
			}
		}
		
		return status;
	}
	
	@Override
	public Show getShowById(int showId)
	{
		return showDao.getShowById(showId);
	}

}
