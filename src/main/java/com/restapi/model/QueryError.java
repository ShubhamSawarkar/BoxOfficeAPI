package com.restapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class QueryError implements QueryResult {

	private final String description;
	private String queryStatus;
	
	public QueryError(String description)
	{
		this.description = description;
	}
	
	public QueryError(String description, String status)
	{
		this.description = description;
		this.queryStatus = status;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getStatus()
	{
		return this.queryStatus;
	}
}
