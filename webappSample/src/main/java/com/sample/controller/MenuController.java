package com.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author msend
 * メニュー画面Controller
 */
@Controller
public class MenuController {

	@GetMapping("/menu")
	public String login() {
		return "menu";
	}
}
