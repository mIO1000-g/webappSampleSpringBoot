package com.sample.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author msend
 * Webアプリケーション全般設定
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${page-line-max}")
	private Integer pageLineMax;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		// PageableHandlerMethodArgumentResolverの実装クラスのうち、ページネーション関連のクラスを利用
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		// 1ページに表示する最大件数を設定する
		resolver.setMaxPageSize(pageLineMax);
		// Controllerのハンドラメソッド引数に独自のオブジェクトをバインドできるようにする
		argumentResolvers.add(resolver);
	}

}
