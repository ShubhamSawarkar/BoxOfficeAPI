package com.restapi.dao;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.mapper.MovieMapper;
import com.restapi.model.Movie;

@Repository
@Transactional
public class MovieDAO extends JdbcDaoSupport
{
	public static final String TABLE_MOVIES = "Movies";
	
	public static final String COLUMN_MOVIES_ID = "Movie_ID";
	public static final String COLUMN_MOVIES_TITLE = "Title";
	public static final String COLUMN_MOVIES_RUNTIME = "Runtime";
	public static final String COLUMN_MOVIES_RELEASEDT = "Release_Date";
	
	@Autowired
	public MovieDAO(DataSource dataSource)
	{
		this.setDataSource(dataSource);
	}
	
	public List<Movie> getMovies()
	{
		String query = "SELECT * FROM " + TABLE_MOVIES;
		MovieMapper mapper = new MovieMapper();
		
		try
		{
			List<Movie> movies = this.getJdbcTemplate().query(query, mapper);
			
			Iterator<Movie> iterator = movies.iterator();
			
			while(iterator.hasNext())
			{
				if(!iterator.next().isValid())
				{
					iterator.remove();
				}
			}
			return movies;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	public synchronized boolean addMovie(Movie movie)
	{
		String query = "INSERT INTO " + TABLE_MOVIES + "(" + COLUMN_MOVIES_ID + ", " + COLUMN_MOVIES_TITLE + ", " + COLUMN_MOVIES_RUNTIME + ", " + COLUMN_MOVIES_RELEASEDT
						+ ") VALUES(?, ?, ?, ?)";
		
		Object[] params = new Object[] { nextMovieId(), movie.getTitle(), movie.getRunTime(), movie.getReleaseDt() };
		
		try
		{
			this.getJdbcTemplate().update(query, params);
			return true;
		}catch(EmptyResultDataAccessException e)
		{
			return false;
		}
	}
	
	public int nextMovieId() throws EmptyResultDataAccessException
	{
		String query = "SELECT IFNULL(MAX(" + COLUMN_MOVIES_ID + "), 0) FROM " + TABLE_MOVIES;
		
			int Id = this.getJdbcTemplate().queryForObject(query, Integer.class);
			return Id + 1;
	}
	
	public Movie getMovie(String title, int runtime, String releaseDt)
	{
		String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_MOVIES_TITLE + " = ? AND " + COLUMN_MOVIES_RUNTIME + " = ? AND " + COLUMN_MOVIES_RELEASEDT + " = ?";
		
		Object[] params = new Object[] {title, runtime, releaseDt};
		
		MovieMapper mapper = new MovieMapper();
		
		try
		{
			return this.getJdbcTemplate().queryForObject(query,  params, mapper);
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
}
