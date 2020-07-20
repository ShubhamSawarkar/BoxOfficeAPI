package com.restapi.mapper;

import java.sql.SQLException;
import java.sql.ResultSet;
import com.restapi.model.ApiUser;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<ApiUser> {
	
	public static final String TABLE_USER = "User";
	public static final int INDEX_USER_ID = 1;
	public static final int INDEX_USER_USERNAME = 2;
	public static final int INDEX_USER_PASSWORD = 3;

	public static String BASE_SQL = "SELECT * FROM " + TABLE_USER + " ";
	
	@Override
	public ApiUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Long userId = rs.getLong(INDEX_USER_ID);
		String userName = rs.getString(INDEX_USER_USERNAME);
		String encryptedPassword = rs.getString(INDEX_USER_PASSWORD);
		
		return new ApiUser(userId, userName, encryptedPassword);
	}
	
	

}
