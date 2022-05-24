package com.oz.unitel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class CalendarUtils {
	
	public static final int WEEK = 1;
	public static final int MONTH = 2;
	public static final int QUARTER = 3;
	public static final int YEAR = 4;
	
	public static int getDayOfWeekInMonth(Calendar _cal, int _year, int _month, int _day, int _dayOfWeekInMonth) {
		_cal.set(Calendar.DAY_OF_WEEK, _day);
		_cal.set(Calendar.MONTH, _month);
		_cal.set(Calendar.YEAR, _year);
		_cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, _dayOfWeekInMonth);

	    return _cal.get(Calendar.DATE);
	}
	
	public static boolean validateDateInMonth(Calendar _cal, int _year, int _month, int _day, int _dayOfWeekInMonth) {
		_cal.set(Calendar.MONTH, _month);
		_cal.set(Calendar.YEAR, _year);
		
		int monthBefore = _cal.get(Calendar.MONTH);
		_cal.set(Calendar.DAY_OF_WEEK, _day);
		_cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, _dayOfWeekInMonth);
		int monthAfter = _cal.get(Calendar.MONTH);
		
		if(monthBefore == monthAfter) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 두 날짜 사이의 날짜 계산 함수 
	 * @param startDate - 시작일 
	 * @param endDate - 종료일 
	 * @param type - (1 : 주, 2 :월, 3 : 연도)
	 * @param serviceStartDate - 서비스 시작일(기준일) 이 날자 기준 주별, 월별, 년별 계산 
	 * @param isStartDate - 시작일 포함 여부 
	 * @param isEndDate - 종료일 포함 여부 
	 * @return List<Date>
	 */
	public static List<Date> getBetweenDateList(Date startDate, Date endDate, int type, Date serviceStartDate, boolean isStartDate, boolean isEndDate)
	{
		List<Date> betweenDate = new ArrayList<Date>();
		Calendar start = new GregorianCalendar();
		Calendar end = new GregorianCalendar();
		Calendar serviceStart = new GregorianCalendar();
		Calendar targetDate = null; 
		
		start.setTime(startDate);
		end.setTime(endDate);
		serviceStart.setTime(serviceStartDate);

		//시작일 포함일 경우(시작일과 serviceStart가 다를경우) 
		if(isStartDate && start.compareTo(serviceStart) < 0)
		{
			betweenDate.add(start.getTime());
		}
		
		//서비스 시작일 추가 
		targetDate = serviceStart;
		betweenDate.add(targetDate.getTime());
		
		//주별 계산 로직 
		if(type == WEEK)
		{
			while(true)
			{
				targetDate.add(Calendar.DATE, 7);
				if(targetDate.compareTo(end) > 0)
				{
					break;
				}
				
				betweenDate.add(targetDate.getTime());
			}
		}
		//월별 계산 로직 
		else if(type == MONTH)
		{
			while(true)
			{
				targetDate.add(Calendar.MONTH, 1);
				if(targetDate.compareTo(end) > 0)
				{
					break;
				}
				
				betweenDate.add(targetDate.getTime());
			}
		}
		//년별 계산 로직 
		else if(type == YEAR)
		{
			while(true)
			{
				targetDate.add(Calendar.YEAR, 1);
				if(targetDate.compareTo(end) > 0)
				{
					break;
				}
				
				betweenDate.add(targetDate.getTime());
			}
		}
		else
		{
			while(true)
			{
				targetDate.add(Calendar.DATE, 1);
				if(targetDate.compareTo(end) > 0)
				{
					break;
				}
				
				betweenDate.add(targetDate.getTime());
			}
		}
		
		//종료일 포함 여부 및 중복 체크 
		if(isEndDate && betweenDate.lastIndexOf(end.getTime()) == -1)
		{
			betweenDate.add(end.getTime());
		}
		
		return betweenDate;
	}
	
	/**
	 * 분기 계산 
	 * @param month - 월 
	 * @return int 분기 
	 */
	public static int getQuarter(int month)
	{
		return (int) Math.ceil(month / 3.0);
	}
	
	public static int getDayCount(int type, Calendar today)
	{
		Integer[] months = null;
		switch (type) {
		case YEAR:
			months = new Integer[12];
			for(int i=0; i<months.length; i++)
			{
				months[i] = i+1;
			}
			break;
		case QUARTER :
			months = new Integer[3];
			int quarter = getQuarter(today.get(Calendar.MONTH)+1);
			int startMonth = ((quarter-1) * 3) + 1;
			
			for(int i = 0; i<months.length ; i++)
			{
				months[i] = startMonth++;
			}
			break;
			
		case MONTH :
			months = new Integer[1];
			months[0] = today.get(Calendar.MONTH)+1;
			break;
			
		default:
			break;
		}
		
		int result = 0;
		Calendar target = Calendar.getInstance();
		target.setTime(today.getTime());
		
		for(int i=0; i<months.length; i++)
		{
			target.set(Calendar.MONTH, months[i]+1);
			result += target.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		return result;
	}
	
	public static Date iso8601(String date){
		SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		try {
			return iso8601.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String iso8601(Object date){
		SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return iso8601.format(date);
	}
}
