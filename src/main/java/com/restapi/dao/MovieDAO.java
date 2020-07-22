package com.restapi.dao;

import java.time.LocalDate;
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
			return movies;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	public synchronized boolean addMovie(Movie movie)
	{
		if((movie == null) || (movie.getTitle() == null) || (movie.getTitle().isBlank()) || (movie.getRunTime() <= 0))
		{
			return false;
		}
		
		LocalDate today = LocalDate.now();
		if(movie.getLocalReleaseDt().isBefore(today))
		{
			return false;
		}
		
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
	
	private int nextMovieId() throws EmptyResultDataAccessException
	{
		String query = "SELECT IFNULL(MAX(" + COLUMN_MOVIES_ID + "), 0) FROM " + TABLE_MOVIES;
		
			int Id = this.getJdbcTemplate().queryForObject(query, Integer.class);
			return Id + 1;
	}
}
