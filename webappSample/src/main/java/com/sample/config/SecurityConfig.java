package com.sample.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author msend
 * セキュリティ設定クラス
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
//
//	/**
//	 * ログイン、ログアウト、認可の設定
//	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			// フォーム認証の適用
			.formLogin()
			.loginPage("/login")
			.usernameParameter("userId")
			.permitAll()	// NOTE:ログインページへ全てのユーザへのアクセス権を付与（しないと、ログイン画面を表示できないためリダイレクトループが発生）
			.failureUrl("/login?failure")
			.defaultSuccessUrl("/menu")
			.and()
			// 認可の適用
			.authorizeHttpRequests()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()		// 静的リソースを保護対象外とする
			.anyRequest().authenticated()
			.and()
			// ログアウト
			.logout()
			.permitAll()
			//.logoutSuccessUrl("/login?logout")
			;
		return http.build();
	}
//
//	@SuppressWarnings("deprecation")
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//
//		String idForEncode = "noop";
//		Map encoders = new HashMap<>();
//		encoders.put("bcrypt", new BCryptPasswordEncoder());
//		encoders.put("noop", NoOpPasswordEncoder.getInstance());
//
//		PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
//
//		return passwordEncoder;
//	}
//	//	

}
