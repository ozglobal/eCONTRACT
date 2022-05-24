package com.oz.unitel.utils;

import java.util.Date;

public class DateDiffUtil {
	public static int getDateDiff(Date startDate, Date endDate, String type)
	{
		int sec = 1000;
		int min = 60;
		int hour = 60;
		int day = 24;
		int convert = 0;
		
		long time = (endDate.getTime() - startDate.getTime());
		
		if(type.equalsIgnoreCase("sec"))
		{
			convert = sec;
		}
		else if(type.equalsIgnoreCase("min"))
		{
			convert = sec*min;
		}else if(type.equalsIgnoreCase("hour"))
		{
			convert = sec*min*hour;
		}
		else
		{
			convert = sec*min*hour*day;
		}
		return (int)(time/convert);
	}
}
