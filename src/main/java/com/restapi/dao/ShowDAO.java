package com.restapi.dao;

import com.restapi.model.AdminControl;
import com.restapi.model.Show;

import java.util.Iterator;
import java.util.List;

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
	private static final String COLUMN_SHOWS_TIME = "Time";
	private static final String COLUMN_SHOWS_DATE = "Date";
	private static final String COLUMN_SHOWS_SCREENNO = "Screen_No";
	private static final String COLUMN_SHOWS_MOVIEID = "Movie_ID";
	
	@Autowired
	public ShowDAO(DataSource dataSource)
	{
		this.setDataSource(dataSource);
	}
	
	public ShowDAO()
	{
		
	}
	
	public Show getShowById(int showId)
	{
		String query = "SELECT * " + " FROM " + TABLE_SHOWS + " WHERE " + COLUMN_SHOWS_ID + " = ?";
		
		Object[] params = new Object[] { showId };
		
		ShowMapper mapper = new ShowMapper();
		
		try
		{
			Show show = this.getJdbcTemplate().queryForObject(query, params, mapper);
			
			//Show show = jdbcTemplate.queryForObject(query,  params, mapper);
			return show;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	public boolean addShow(Show show)
	{	
		return AdminControl.addShow(show);
	}
	
	public List<Show> getShows() 
	{
		String query = "SELECT * FROM " + TABLE_SHOWS;
		
		ShowMapper mapper = new ShowMapper();
		
		try
		{
			List<Show> shows = this.getJdbcTemplate().query(query, mapper);
			
			Iterator<Show> iterator = shows.iterator();
			
			while(iterator.hasNext())
			{
				if(!iterator.next().isValid())
				{
					iterator.remove();
				}
			}
			
			return shows;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	public Show findShow(int screenNo, String date, String time, int movieId)
	{
		String query = "SELECT * FROM " + TABLE_SHOWS + " WHERE " + COLUMN_SHOWS_SCREENNO + " = ? AND " + COLUMN_SHOWS_DATE + " = ? AND " +
						COLUMN_SHOWS_TIME + " = ? AND " + COLUMN_SHOWS_MOVIEID + " = ?";
		
		Object[] params = new Object[] {screenNo, date, time, movieId};
		
		ShowMapper mapper = new ShowMapper();
		
		try
		{
			return this.getJdbcTemplate().queryForObject(query,  params, mapper);
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
}
