package com.restapi.dao;

import com.restapi.model.AdminControl;
import com.restapi.model.Show;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.mapper.ShowMapper;

@Repository
@Transactional
public class ShowDAO extends JdbcDaoSupport
{
	public static final String TABLE_SHOWS = "Shows";
	public static final String COLUMN_SHOWS_ID = "Show_ID";
	
	@Autowired
	public ShowDAO(DataSource dataSource)
	{
		this.setDataSource(dataSource);
	}
	
	public Show getShowById(int showId)
	{
		String query = "SELECT * " + " FROM " + TABLE_SHOWS + " WHERE " + COLUMN_SHOWS_ID + " = ?";
		
		Object[] params = new Object[] { showId };
		
		ShowMapper mapper = new ShowMapper();
		
		try
		{
			Show show = this.getJdbcTemplate().queryForObject(query, params, mapper);
			return show;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	public boolean addShow( int screenNo, String date, String time, int movieId)
	{
		Show show = new Show();
		show.setScreenNo(screenNo);
		show.setShowDate(date);
		show.setShowTime(time);
		show.setMovieID(movieId);
		
		return AdminControl.addShow(show);
	}
}
