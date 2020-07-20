package com.restapi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.restapi.model.Show;

public class ShowMapper implements RowMapper<Show> 
{

	public static final int INDEX_SHOWS_ID = 1;
	public static final int INDEX_SHOWS_SCREENNO = 2;
	public static final int INDEX_SHOWS_DATE = 3;
	public static final int INDEX_SHOWS_TIME = 4;
	public static final int INDEX_SHOWS_MOVIEID = 5;
	
	@Override
	public Show mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt(INDEX_SHOWS_ID);
		int screenNo = rs.getInt(INDEX_SHOWS_SCREENNO);
		String date = rs.getString(INDEX_SHOWS_DATE);
		String time = rs.getString(INDEX_SHOWS_TIME);
		int movieId = rs.getInt(INDEX_SHOWS_MOVIEID);
		
		return new Show(id, screenNo, date, time, movieId);
		
	}

}
