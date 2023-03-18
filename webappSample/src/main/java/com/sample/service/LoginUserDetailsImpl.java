//package com.sample.service;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.sample.entity.MUser;
//
//public class LoginUserDetailsImpl implements UserDetails {
//
//	private MUser user;
//
//	public LoginUserDetailsImpl(MUser user) {
//		this.user = user;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return AuthorityUtils.createAuthorityList("ROLE_" + this.user.getRole());
//	}
//
//	@Override
//	public String getUsername() {
//		return user.getUserName();
//	}
//
//	@Override
//	public String getPassword() {
//		return user.getPassward();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//}
