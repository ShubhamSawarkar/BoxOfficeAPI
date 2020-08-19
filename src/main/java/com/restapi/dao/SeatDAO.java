package com.restapi.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.model.AdminControl;
import com.restapi.model.Seat;

import com.restapi.mapper.SeatMapper;

@Repository
@Transactional
public class SeatDAO extends JdbcDaoSupport{
	
	public static final String TABLE_SEATS = "Seats";
	public static final String TABLE_RESERVATION = "Reservation";
	public static final String COLUMN_SEATS_SCREENNO = "Screen_No";
	public static final String COLUMN_SEATS_ROWNO = "Row_No";
	public static final String COLUMN_SEATS_SEATNO = "Seat_No";
	private static final String COLUMN_RESERVATION_SEATID = "Seat_ID";
	private static final String COLUMN_RESERVATION_SHOWID = "Show_ID";
		
	@Autowired
	public SeatDAO(DataSource dataSource)
	{
		this.setDataSource(dataSource);
	}

	public List<Seat> getSeats(int screenNo)
	{
		String query = "SELECT * FROM " + TABLE_SEATS + " WHERE " + COLUMN_SEATS_SCREENNO + " = ?";
		
		Object[] params = new Object[] { screenNo };
		
		//List<Seat> seats = this.getJdbcTemplate().queryForList(query, params, Seat.class);
		
		SeatMapper mapper = new SeatMapper();
		
		List<Seat> seats = this.getJdbcTemplate().query(query, params, mapper);
		
		if(seats.isEmpty())
		{
			return null;
		}
		
		Collections.sort(seats);
		return seats;
	}
	
	public boolean addSeat(int screenNo, int rowNo, double price, String category, boolean spaceAfter)
	{
		Seat seat = new Seat();
		seat.setScreenNo(screenNo);
		seat.setRowNo(rowNo);
		seat.setPrice(price);
		seat.setCategory(category);
		seat.setSpaceAfter(spaceAfter);
		
		return AdminControl.addSeat(seat);
	}
	
	public boolean changePrice(int screenNo, int rowNo, int seatNo, double price)
	{
		Seat seat = new Seat();
		seat.setScreenNo(screenNo);
		seat.setRowNo(rowNo);
		seat.setSeatNo(seatNo);
		seat.setPrice(price);
		
		return AdminControl.changePrice(seat);
	}
	
	public Seat getSeat(int screenNo, int rowNo, int seatNo)
	{
		String query = "SELECT * FROM " + TABLE_SEATS + " WHERE " + COLUMN_SEATS_SCREENNO + " = ? AND " + COLUMN_SEATS_ROWNO + " = ? AND " + COLUMN_SEATS_SEATNO
						+ " = ?";
		
		Object[] params = new Object[] { screenNo, (char) (64+rowNo), seatNo };
		
		SeatMapper mapper = new SeatMapper();
		
		try
		{
			return this.getJdbcTemplate().queryForObject(query, params, mapper);
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	public boolean isReserved(int seatId, int showId)
	{
		String query = "SELECT COUNT(*) FROM " + TABLE_RESERVATION + " WHERE " + COLUMN_RESERVATION_SEATID + " = ? AND " +
				  COLUMN_RESERVATION_SHOWID + " = ?"; 
		
		Object[] params = new Object[] { seatId, showId };
		
		try
		{
			int count = this.getJdbcTemplate().queryForObject(query, params, Integer.class);
			
			if(count == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			return false;
		}
	}
	
	public List<Integer> getScreens()
	{
		String query = "SELECT DISTINCT(" + COLUMN_SEATS_SCREENNO + ") FROM " + TABLE_SEATS;
		
		try
		{
			System.out.println("here!");
			return this.getJdbcTemplate().queryForList(query, Integer.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			System.out.println("error!");
			return null;
		}
	}
}
