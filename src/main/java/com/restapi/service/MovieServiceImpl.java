package com.restapi.service;

import com.restapi.model.QueryResult;
import com.restapi.utils.DateUtils;
import com.restapi.model.Movie;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.dao.MovieDAO;

@Service
public class MovieServiceImpl implements MovieService
{
	@Autowired
	private MovieDAO movieDao;

	@Override
	public List<QueryResult> getMovies() 
	{
		List<Movie> movies = movieDao.getMovies();
		List<QueryResult> results = new ArrayList<>();
		
		for(Movie movie : movies)
		{
			results.add(toResult(movie));
		}
		return results;
	}

	@Override
	public Movie addMovie(String title, int runtime, String releaseDt) 
	{
		releaseDt = DateUtils.format(releaseDt);
		if(releaseDt == null)
		{
			return null;
		}
		Movie movie = new Movie(movieDao.nextMovieId(), title, runtime, releaseDt);
		
		if((movie == null) || (movie.getTitle() == null) || (movie.getTitle().isBlank()) || (movie.getRunTime() <= 0))
		{
			return null;
		}
		else
		{
			if(movieDao.addMovie(movie))
			{
				//return movieDao.getMovie(title, runtime, releaseDt);
				return movie;
			}
			else
			{
				return null;
			}
		}
	}
	
	private QueryResult toResult(Movie movie)
	{
		return new QueryResult() {
			int id = movie.getMovieID();
			String title = movie.getTitle();
			int runtime = movie.getRunTime();
			String releaseDt = movie.getReleaseDt();
			
			public int getId() {
				return id;
			}
			public String getTitle() {
				return title;
			}
			public int getRuntime() {
				return runtime;
			}
			public String getReleaseDt() {
				return releaseDt;
			}
		};
	}
	
	@Override
	public List<Movie> getMovieObjects()
	{
		return movieDao.getMovies();
	}

}
