package com.restapi.service;

import java.util.List;

import com.restapi.model.Movie;
import com.restapi.model.QueryResult;

public interface MovieService 
{
	public List<QueryResult> getMovies();
	public Movie addMovie(String title, int runtime, String releaseDt);
	public List<Movie> getMovieObjects();
}
