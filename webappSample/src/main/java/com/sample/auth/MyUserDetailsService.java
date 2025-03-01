package com.sample.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sample.dao.MUserDao;
import com.sample.entity.MUser;

@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	MUserDao MUserDao;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		MUser user = MUserDao.selectByPk(userId);
		if (user == null) {
			throw new UsernameNotFoundException(userId);
		}
		return new MyUserDetails(user, null);
	}

}
