package com.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class Util {

	public final static String convertToString(Object obj) {

		if (obj == null)
			return "";

		return String.valueOf(obj);
	}

	public final static String convertDateTimeString(Object obj, String fromFormat, String toFormat) {

		if (obj == null)
			return "";

		SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat);
		SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat);

		try {
			Date dt = sdfFrom.parse(String.valueOf(obj));
			return sdfTo.format(dt);
		} catch (ParseException ex) {
			return "";
		}

	}
	
	public final static String getNow(String format) {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return dtf.format(ldt);
	}
}
