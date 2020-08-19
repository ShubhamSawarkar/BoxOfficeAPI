package com.restapi.service;

import com.restapi.model.QueryResult;

public interface UserService {

	public QueryResult addUser(String userName, String password, boolean isAdmin);
	public boolean isAdmin();
}
