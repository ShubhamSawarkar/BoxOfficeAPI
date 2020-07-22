package com.restapi.dao;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
import com.restapi.mapper.UserMapper;
import com.restapi.model.ApiUser;
import com.restapi.utils.EncryptedPasswordUtils;

@Repository
@Transactional
public class UserDAO extends JdbcDaoSupport{
	
	public static final String TABLE_USERS = "User";
	public static final String TABLE_USER_ROLE = "User_Role";

	public static final String COLUMN_USER_USERNAME = "User_Name";
	public static final String COLUMN_USERS_ID = "ID";
	public static final String COLUMN_USERS_PASSWORD = "Password";
	public static final String COLUMN_USERS_ENABLED = "Enabled";
	
	public static final String COLUMN_USER_ROLE_ID = "ID";
	public static final String COLUMN_USER_ROLE_USERID = "User_ID";
	public static final String COLUMN_USER_ROLE_ROLEID = "Role_ID";
	
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
			
			//ApiUser user = jdbcTemplate.queryForObject(query,  params, mapper);
			return user;
		}catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean addUser(String userName, String password, boolean isAdmin)
	{
		if(userExists(userName))
		{
			return false;
		}
		
		String query1 = "INSERT INTO " + TABLE_USERS + "(" + COLUMN_USERS_ID + ", " + COLUMN_USER_USERNAME + ", " + COLUMN_USERS_PASSWORD + ", " + COLUMN_USERS_ENABLED
						+ ") VALUES(?, ?, ?, ?)";
		
		String query2 = "INSERT INTO " + TABLE_USER_ROLE + "(" + COLUMN_USER_ROLE_ID + ", " + COLUMN_USER_ROLE_USERID + ", " + COLUMN_USER_ROLE_ROLEID +
						") VALUES(?, ?, ?)";
		
		int userId = nextUserId();
		int userRole;
		
		if(isAdmin)
		{
			userRole = 1;
		}else
		{
			userRole = 2;
		}
		
		Object[] params1 = new Object[] { userId, userName, EncryptedPasswordUtils.encryptPassword(password), true};
		
		Object[] params2 = new Object[] { nextUserRoleId(), userId, userRole};
		
		try
		{
			this.getJdbcTemplate().update(query1, params1);
			this.getJdbcTemplate().update(query2, params2);
			return true;
		}catch(EmptyResultDataAccessException e)
		{
			return false;
		}
 	}
	
	private boolean userExists(String userName) throws EmptyResultDataAccessException
	{
		String query = "SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_USERNAME + " = ?";
		Object[] params = new Object[] { userName };
		
			int count = this.getJdbcTemplate().queryForObject(query, params, Integer.class);
			
			if(count == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
	}
	
	private int nextUserId() throws EmptyResultDataAccessException
	{
		String query = "SELECT IFNULL(MAX(" + COLUMN_USERS_ID + "), 0) FROM " + TABLE_USERS;
		
		return (int) this.getJdbcTemplate().queryForObject(query, Integer.class) + 1;
	}
	
	private int nextUserRoleId() throws EmptyResultDataAccessException
	{
		String query = "SELECT IFNULL(MAX(" + COLUMN_USER_ROLE_ID + "), 0) FROM " + TABLE_USER_ROLE;
		
		return (int) this.getJdbcTemplate().queryForObject(query, Integer.class) + 1;
	}
}
