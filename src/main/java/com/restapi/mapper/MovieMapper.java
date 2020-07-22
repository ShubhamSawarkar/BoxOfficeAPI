package com.restapi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.restapi.model.Movie;

public class MovieMapper implements RowMapper<Movie> {
	
	public static final int INDEX_MOVIES_ID = 1;
	public static final int INDEX_MOVIES_TITLE = 2;
	public static final int INDEX_MOVIES_RUNTIME = 3;
	public static final int INDEX_MOVIES_RELEASEDT = 4;

	@Override
	public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Movie movie = new Movie();
		
		movie.setMovieID(rs.getInt(INDEX_MOVIES_ID));
		movie.setTitle(rs.getString(INDEX_MOVIES_TITLE));
		movie.setRunTime(rs.getInt(INDEX_MOVIES_RUNTIME));
		movie.setReleaseDtStr(rs.getString(INDEX_MOVIES_RELEASEDT));
		
		return movie;
	}

}
