package com.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author msend
 * ユーティリティ
 */
public final class Util {

	/**
	 * 引数を文字列に変更
	 * ※ nullの場合はnullで返却
	 * @param obj オブジェクト
	 * @return 文字列
	 */
	public final static String convertToString(Object obj) {

		if (obj == null)
			return null;

		return String.valueOf(obj);
	}

	/** 
	 * 日時を指定の日時フォーマットの文字列に変換
	 * @param obj オブジェクト
	 * @param fromFormat 変換前日時フォーマット
	 * @param toFormat 変換後日時フォーマット
	 * @return 日時文字列
	 */
	public final static String convertDateTimeString(Object obj, String fromFormat, String toFormat) {

		if (obj == null)
			return null;

		SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat);
		SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat);

		try {
			Date dt = sdfFrom.parse(String.valueOf(obj));
			return sdfTo.format(dt);
		} catch (ParseException ex) {
			return null;
		}

	}
	
	/**
	 * 指定フォーマットの現在日時文字列を取得
	 * @param format 日付フォーマット
	 * @return 日時文字列
	 */
	public final static String getNow(String format) {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return dtf.format(ldt);
	}
}
