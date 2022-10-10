package com.sample.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sample.entity.MUser;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		
		// TODO:実際は、ここでユーザマスタを取得する。
		MUser userInfo = new MUser();
		
		userInfo.setUserId(userId);
		userInfo.setPassward(new BCryptPasswordEncoder().encode("adminp"));
		userInfo.setUserName("管理者");
		userInfo.setRole("Admin");
		
		// TODO:仮実装
		if (!"admin".equals(userId)) {
			throw new UsernameNotFoundException("user not found");
		}

		// TODO:マスタからの取得内容に応じ、パスワードチェック等を実施

		return new LoginUserDetailsImpl(userInfo);
	}
}
