package com.onionsquare.core.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil extends DateUtils {
	
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static String dateFormat = "MM/dd/yyyy";
	
	public static String getFormattedDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}
	
	public static Date getNullDate() {
		Date date =  DateUtil.getDate("0000-00-00 00:00:00","yyyy-MM-dd HH:mm:ss");
		return date ;
	}
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	} 

	public static Date getDate(String dateStr, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);//mm/dd/yyyyy
		if (Util.isNullOrEmpty(dateStr)) {
			return null;
		} 	
	
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			logger.warn(e.toString());				
		}
		
		return null;
	}

	public static String getFormattedStr(Date date, String pattern) {
		if(date == null || pattern == null){
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	
	public static String getPSTFormattedDateStr(Date date, String pattern){
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setTimeZone(TimeZone.getTimeZone("PST"));
		return formatter.format(date);
	}
	
	public static Date getDate(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);//mm/dd/yyyyy
		if (date == null) {
			return null;
		} 	
	
		try {
			return formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			logger.warn(e.toString());				
		}
		
		return null;
	}
	
	/**
	 * returns difference, in terms of number of days, between two date.
	 * essentially this method returns: (d1.toMillis() - d2.toMillis()) / (1000 *
	 * 60 * 60 * 24)
	 */
	public static double getNumOfDaysBetweenDates(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			throw new IllegalArgumentException("Both date1 and date2 must not be null.");
		}

		Calendar c1 = GregorianCalendar.getInstance();
		Calendar c2 = GregorianCalendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		long time1 = c1.getTimeInMillis() + c1.get(Calendar.DST_OFFSET);
		long time2 = c2.getTimeInMillis() + c2.get(Calendar.DST_OFFSET);
		return (time1 - time2) / (double) MILLIS_PER_DAY;
	}
	
	public static double getNumOfMintsBetweenDates(Date d1, Date d2) {
		return getDiffBetweenDates(d1, d2) / (double) MILLIS_PER_MINUTE;		
	}
	
	public static long getDiffBetweenDates(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			throw new IllegalArgumentException("Both date1 and date2 must not be null.");
		}

		Calendar c1 = GregorianCalendar.getInstance();
		Calendar c2 = GregorianCalendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		long time1 = c1.getTimeInMillis() + c1.get(Calendar.DST_OFFSET);
		long time2 = c2.getTimeInMillis() + c2.get(Calendar.DST_OFFSET);
		return (time1 - time2);
	}
	public static java.util.Date addMonths(java.util.Date aDate, int number){  
	    java.util.Calendar aCalendar = java.util.Calendar.getInstance();  
	    aCalendar.setTime(aDate);  
	    aCalendar.add(java.util.Calendar.MONTH, number);  
	    return aCalendar.getTime();  
	} 
	
}
