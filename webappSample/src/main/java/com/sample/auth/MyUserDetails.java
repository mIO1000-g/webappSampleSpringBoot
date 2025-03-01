package com.sample.auth;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sample.entity.MUser;

public class MyUserDetails implements UserDetails {
	
	private final MUser user;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public MyUserDetails(MUser user, Collection<? extends GrantedAuthority> authorities){
		this.user = user;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getPassword() {
		// TODO:現時点は平文でエンコード
		return "{noop}" + this.user.password;
	}

	@Override
	public String getUsername() {
		return this.user.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		LocalDate now = LocalDate.now();
		LocalDate accountValidDateEnd = LocalDate.parse(this.user.accountValidDateEnd, DateTimeFormatter.ofPattern("yyyyMMdd"));
		return now.isBefore(accountValidDateEnd);
	}

	@Override
	public boolean isAccountNonLocked() {
		return "0".equals(this.user.accountLockFlag) ? true : false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		LocalDate now = LocalDate.now();
		LocalDate passwordLastUpdate = LocalDate.parse(this.user.passwordLastUpdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
		return now.isAfter(passwordLastUpdate);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public MUser getUser() {
		return this.user;
	}

}
