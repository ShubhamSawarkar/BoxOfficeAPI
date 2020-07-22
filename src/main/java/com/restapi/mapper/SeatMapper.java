package com.restapi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.restapi.model.Seat;

public class SeatMapper implements RowMapper<Seat> 
{
	private static final int INDEX_SEATS_ID = 1;
	private static final int INDEX_SEATS_SCREENNO = 2;
	private static final int INDEX_SEATS_ROWNO = 3;
	private static final int INDEX_SEATS_SEATNO = 4;
	private static final int INDEX_SEATS_CATEGORY = 5;
	private static final int INDEX_SEATS_PRICE = 6;
	private static final int INDEX_SEATS_SPACEAFTER = 7;

	@Override
	public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Seat seat = new Seat();
		
		seat.setSeatID(rs.getInt(INDEX_SEATS_ID));
		seat.setScreenNo(rs.getInt(INDEX_SEATS_SCREENNO));
		seat.setRowNo(rs.getString(INDEX_SEATS_ROWNO));
		seat.setSeatNo(rs.getInt(INDEX_SEATS_SEATNO));
		seat.setCategory(rs.getString(INDEX_SEATS_CATEGORY));
		seat.setPrice(rs.getDouble(INDEX_SEATS_PRICE));
		seat.setSpaceAfter(rs.getBoolean(INDEX_SEATS_SPACEAFTER));
		
		return seat;
	}

}
