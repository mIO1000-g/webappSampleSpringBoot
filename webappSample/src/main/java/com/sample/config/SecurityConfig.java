package com.sample.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author msend
 * セキュリティ設定クラス
 */
@Configuration
public class SecurityConfig {

	/**
	 * ログイン、ログアウト、認可の設定
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//http.mvcMatcher("/table_reference/**").csrf().disable();
		http.csrf().disable();
		
		http.formLogin(login -> login
					.loginProcessingUrl("/login")
					.loginPage("/login")
					.defaultSuccessUrl("/menu")
					.failureUrl("/login?error")
					.permitAll()
				).logout(logout -> logout
						.logoutSuccessUrl("/")
				).authorizeHttpRequests(authz -> authz
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()		// 静的リソースを保護対象外とする
					.mvcMatchers("/").permitAll()
					.mvcMatchers("/menu").permitAll()
					.mvcMatchers("/table_reference").permitAll()
					.antMatchers("/table_reference/**").permitAll()
//					.mvcMatchers("/general").hasRole("GENERAL")
//					.mvcMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated()	// 上記以外は全て認可必要
				);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
