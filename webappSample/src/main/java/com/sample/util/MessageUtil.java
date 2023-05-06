package com.sample.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author msend
 * メッセージ部品
 */
@Component
public class MessageUtil {

	@Autowired
	MessageSource ms;
	@Autowired
	LocaleResolver lr;
	
	/**
	 * メッセージ取得
	 * @param messageId メッセージID
	 * @param args メッセージパラメータ
	 * @return メッセージ
	 */
	public String getMessage(String messageId, Object[] args) {
		
		return ms.getMessage(messageId, args, messageId, LocaleContextHolder.getLocale());
	}
}
