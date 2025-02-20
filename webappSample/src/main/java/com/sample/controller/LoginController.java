package com.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value="/login", params="failure")
	public String loginFail(Model model) {
		model.addAttribute("failureMessage", "ログインに失敗しました");
		return "login";
	}
	
	@GetMapping(value="/login", params="logout")
	public String logout(Model model) {
		model.addAttribute("logoutMessage", "ログアウトしました");
		return "login";
	}
}
