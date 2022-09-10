package com.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String convertToString(Object obj) {

		if (obj == null)
			return "";

		return String.valueOf(obj);
	}

	public static String convertDateTimeString(Object obj, String fromFormat, String toFormat) {

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
}
