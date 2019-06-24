package com.challenge.agoda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Util class that contains helper functions for Bug Tracking System.
public final class Util {

	public static final String WEEK_PREFIX = "W";

	public static final String ZERO_PREFIX = "0";

	public static final String SPACE_SEPARATOR = " ";

	public static final String HYPHEN_SEPARATOR = "-";

	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	public static String getDate(final String timestamp) throws ParseException {
		String resultDate = "";
		if (timestamp == null || timestamp.length() == 0) {
			return resultDate;
		}
		String[] times = timestamp.split(SPACE_SEPARATOR);
		String[] date = times[0].split(HYPHEN_SEPARATOR);
		if (date.length == 0) {
			return resultDate;
		}
		int weekOfYear = getWeekNumber(times[0]);
		if (weekOfYear == -1) {
			return resultDate;
		}
		String weekOfTheYear = Integer.toString(weekOfYear);
		if (weekOfYear < 10) {
			weekOfTheYear = ZERO_PREFIX + weekOfTheYear;
		}
		resultDate = date[0] + WEEK_PREFIX + weekOfTheYear;
		return resultDate;
	}

	public static int getWeekNumber(final String date) throws ParseException {
		if (date == null || date.length() == 0) {
			return -1;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		Date dateObj = simpleDateFormat.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static boolean isWeekInDesiredFormat(final String inputWeek) {
		if (inputWeek == null || inputWeek.length() != 7) {
			return false;
		}
		if (inputWeek.charAt(4) != 'W') {
			return false;
		}
		int weekNumber = Integer.parseInt(inputWeek.substring(5));
		if (weekNumber < 1 || weekNumber > 53) {
			return false;
		}
		return true;
	}
}
