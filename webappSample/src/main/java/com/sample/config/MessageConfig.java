package com.sample.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author msend
 * メッセージ設定
 */
@Configuration
public class MessageConfig implements WebMvcConfigurer {

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		// プロパティファイルの基底名を設定
		messageSource.setBasename("i18n/messages");
		// ネイティブコードのエンコーディングを指定
		// NOTE:指定しないと日本語のプロパティファイルが文字化けする
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	/**
	 * 切り替えたロケールを保存するロケールリゾルバー
	 * @return LocaleResolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		// HTTPセッションに保存したロケールを利用
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		// デフォルトロケールの指定
		resolver.setDefaultLocale(Locale.JAPANESE);
		return resolver;
	}

	/**
	 * UIを使用してロケールを切り替える機能をサポートするインターセプタ
	 * @return LocaleChangeInterceptor
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		// ロケールのリクエストパラメータのnameを指定
		lci.setParamName("locale");
		return lci;
	}

	/**
	 * インターセプタ登録
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

}
