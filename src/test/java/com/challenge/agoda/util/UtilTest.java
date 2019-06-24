package com.challenge.agoda.util;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class UtilTest {

	private final static String timestamp = "2019-01-15 12:00pm UTC";

	private final static String badTimestamp = "20190115";

	@Test
	public void testSuccessfulGetDate() throws ParseException {
		String date = Util.getDate(timestamp);
		assertEquals("2019W03", date);
	}

	@Test
	public void testGetDate_NullTimestamp() throws ParseException {
		assertEquals("", Util.getDate(null));
	}

	@Test(expected = ParseException.class)
	public void testGetDate_Exception() throws ParseException {
		String date = Util.getDate(badTimestamp);
	}

	@Test
	public void testSuccessfulGetWeekNumber() throws ParseException {
		int date = Util.getWeekNumber(timestamp);
		assertEquals(3, date);
	}

	@Test
	public void testGetWeekNumber_NullDate() throws ParseException {
		int date = Util.getWeekNumber(null);
		assertEquals(-1, date);
	}

	@Test(expected = ParseException.class)
	public void testGetWeekNumber_Exception() throws ParseException {
		int date = Util.getWeekNumber(badTimestamp);
	}

	@Test
	public void testSuccessfulIsWeekInDesiredFormat() throws ParseException {
		assertTrue(Util.isWeekInDesiredFormat("2019W01"));
	}

	@Test
	public void testIsWeekInDesiredFormat_InvalidWeekLength() throws ParseException {
		assertFalse(Util.isWeekInDesiredFormat("2019W031"));
	}

	@Test
	public void testIsWeekInDesiredFormat_InvalidFormat() throws ParseException {
		assertFalse(Util.isWeekInDesiredFormat("2019R031"));
	}

	@Test
	public void testIsWeekInDesiredFormat_OutsideRange() throws ParseException {
		assertFalse(Util.isWeekInDesiredFormat("2019W55"));
	}

	@Test
	public void testIsWeekInDesiredFormat_NullWeek() throws ParseException {
		assertFalse(Util.isWeekInDesiredFormat(null));
	}
}
