package com.restapi.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
	public static final String COLUMN_SEATS_SCREENNO = "Screen_No";
		
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
	
}
