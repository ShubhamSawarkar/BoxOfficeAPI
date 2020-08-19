package com.restapi.model;

import java.util.ArrayList;
import java.util.List;

public class QueryResultCollection<T extends QueryResult> implements QueryResult
{
	private List<T> results;
	
	public QueryResultCollection(List<T> results)
	{
		this.results = results;
	}
	
	public void add(T t)
	{
		if(results == null)
		{
			results = new ArrayList<T>();
		}
		results.add(t);
	}
}
