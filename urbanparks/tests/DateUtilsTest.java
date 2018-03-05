package urbanparks.tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.DateUtils;

public class DateUtilsTest {

	LocalDateTime today;
	LocalDateTime tomorrow;
	LocalDateTime todayNextYear;
	
	@Before
	public void setupDateUtils() {
		today = LocalDateTime.now();
		tomorrow = LocalDateTime.now().plusDays(1);
		todayNextYear = LocalDateTime.now().plusYears(1);
	}
	
	
	@Test
	public void are2DatesOnSameDay_sameDay_True() {
		assertTrue(DateUtils.are2DatesOnSameDay(today, today));
	}
	
	@Test
	public void are2DatesOnSameDay_differentDay_False() {
		assertFalse(DateUtils.are2DatesOnSameDay(today, tomorrow));
	}
	
	@Test
	public void are2DatesOnSameDay_differentYear_False() {
		assertFalse(DateUtils.are2DatesOnSameDay(today, todayNextYear));
	}
	
	@Test
	public void daysBetweenNowAndDate_zeroDayDifference_0() {
		assertEquals(DateUtils.daysBetweenNowAndDate(today), 0);
	}
	
	@Test
	public void daysBetweenNowAndDate_oneDayDifference_1() {
		assertEquals(DateUtils.daysBetweenNowAndDate(tomorrow), 1);
	}
	
	@Test
	public void daysBetweenNowAndDate_oneYearDifference_365() {
		assertEquals(DateUtils.daysBetweenNowAndDate(todayNextYear), 365);
	}
	
	@Test
	public void daysBetween2Dates_zeroDayDifference_0() {
		assertEquals(DateUtils.daysBetween2Dates(today, today), 0);
	}
	
	@Test
	public void daysBetween2Dates_oneDayDifference_1() {
		assertEquals(DateUtils.daysBetween2Dates(today, tomorrow), 1);
	}
	
	@Test
	public void daysBetween2Dates_oneYearDifference_365() {
		assertEquals(DateUtils.daysBetween2Dates(today, todayNextYear), 365);
	}
	
	@Test
	public void formatDateTime_03062018_equals() {
		LocalDateTime mar318 = LocalDateTime.of(2018, 3, 6, 0, 0);
		assertEquals(DateUtils.formatDateTime(mar318), "2018-03-06 00:00");
	}

}
