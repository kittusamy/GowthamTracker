package com.struts.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationUtil {

	public static int isInteger(String input) {
		try {
			Integer.parseInt(input);
			return (String.valueOf(input).length());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static Integer stringToInteger(String input) {
		try {
			return Integer.parseInt(input);

		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean betweenExclusive(int x, int min, int max) {
		return x > min && x < max;
	}

	public static Date getBusinessDay(final Date date, final int businessDaysFromDate) {

		final int max = 60;
		if (date == null) {
			return getBusinessDay(new Date(), businessDaysFromDate);
		} else if (date != null && (businessDaysFromDate < 0 || businessDaysFromDate > max)) {
			return getBusinessDay(date, 0);
		} else {
			final Calendar baseDateCal = Calendar.getInstance();
			baseDateCal.setTime(date);
			for (int i = 1; i <= businessDaysFromDate; i++) {
				baseDateCal.add(Calendar.DATE, 1);
				while (baseDateCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| baseDateCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					baseDateCal.add(Calendar.DATE, 1);
				}
			}
			return baseDateCal.getTime();
		}
	}


	public static Map<String, String> getEffortTypeMap() {
		Map<String, String> effortTypeMap = new HashMap<String, String>();
		effortTypeMap.put("DSG", "DSG - Design phase");
		effortTypeMap.put("BLD", "BLD - Build phase");
		effortTypeMap.put("SIT", "SIT - SIT phase");
		effortTypeMap.put("UAT", "UAT - UAT phase");
		effortTypeMap.put("IMP", "IMP - Implementation");
		return effortTypeMap;
	}}
