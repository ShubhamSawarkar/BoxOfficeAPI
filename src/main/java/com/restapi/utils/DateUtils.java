package com.restapi.utils;

import java.util.ArrayList;
import java.util.List;

public class DateUtils {

	public static String format(String date)
	{
		char[] dtArr = date.toCharArray();
		int delimitCount = 0;
		int digitCount = 0;
		int day = 0, month = 0, year = 0;
		List<Character> delimeters = new ArrayList<>();
		List<Character> digits = new ArrayList<>();
		for(int i=0; i<10; i++)
		{
			digits.add((char) (i + '0'));
		}
		delimeters.add('/');
		delimeters.add('-');
		
		if(dtArr.length != 10)
		{
			return null;
		}
		
		for(char element : dtArr)
		{
			if(delimeters.contains(element))
			{
				delimitCount++;
				
				if((digitCount == 2) && (delimitCount == 1))
				{
					day = Integer.parseInt(date.substring(0, 2));
				}
				else if((digitCount == 4) && (delimitCount == 1))
				{
					year = Integer.parseInt(date.substring(0, 4));
				}
				else if((digitCount == 2) && (delimitCount == 2))
				{
					if((day == 0) && (year != 0))
					{
						day = Integer.parseInt(date.substring(8, 10));
						month = Integer.parseInt(date.substring(5, 7));
					}
					else if((day != 0) && (year == 0)) 
					{
						month = Integer.parseInt(date.substring(3, 5));
						year = Integer.parseInt(date.substring(6, 10));
					}
					else
					{
						return null;
					}
				}
				else
				{
					return null;
				}
				
				digitCount = 0;
			}
			else if(digits.contains(element))
			{
				digitCount++;
			}
			else
			{
				return null;
			}
		}
		
		if((day > 0) && (month > 0) && (year > 0) && (day <= 31) && (month <= 12))
		{
			return ((day < 10) ? "0" : "") + day + "/" + ((month < 10) ? "0" : "") + month + "/" + year;
		}
		return null;
	}
}
