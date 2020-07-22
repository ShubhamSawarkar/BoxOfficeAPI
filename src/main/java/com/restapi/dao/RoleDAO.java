package com.restapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import javax.sql.DataSource;

@Repository
@Transactional
public class RoleDAO extends JdbcDaoSupport{
	
	public static final String TABLE_USER_ROLE = "User_Role";
	public static final String TABLE_ROLE = "Role";
	
	public static final String COLUMN_USER_ROLE_ROLEID = "Role_ID";
	public static final String COLUMN_USER_ROLE_USERID = "User_ID";
	public static final String COLUMN_ROLE_ID = "ID";
	public static final String COLUMN_ROLE_ROLENAME = "Role_Name";
	

	@Autowired
	public RoleDAO(DataSource dataSource)
	{
		this.setDataSource(dataSource);
	}
	
	public List<String> getRoleNames(long userId)
	{
		String query = "SELECT r." + COLUMN_ROLE_ROLENAME + " FROM " + TABLE_USER_ROLE + " ur, " + TABLE_ROLE + " r WHERE ur." +
						COLUMN_USER_ROLE_ROLEID + " = r." + COLUMN_ROLE_ID + " AND ur." + COLUMN_USER_ROLE_USERID + " = ?";
		
		Object[] params = new Object[] { userId };
		
		List<String> roles = this.getJdbcTemplate().queryForList(query, params, String.class);
		
		return roles;
	}
}
