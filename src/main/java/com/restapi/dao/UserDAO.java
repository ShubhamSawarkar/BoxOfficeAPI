package com.restapi.dao;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
import com.restapi.mapper.UserMapper;
import com.restapi.model.ApiUser;

@Repository
@Transactional
public class UserDAO extends JdbcDaoSupport{

	public static final String COLUMN_USER_USERNAME = "User_Name";
	
	@Autowired
	public UserDAO(DataSource dataSource)
	{
		this.setDataSource(dataSource);
	}
	
	public ApiUser findUserAccount(String userName)
	{
		String query = UserMapper.BASE_SQL + "WHERE " + COLUMN_USER_USERNAME + " = ?";
		
		Object[] params = new Object[] { userName };
		UserMapper mapper = new UserMapper();
		
		try
		{
			ApiUser user = this.getJdbcTemplate().queryForObject(query, params, mapper);
			return user;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
}
