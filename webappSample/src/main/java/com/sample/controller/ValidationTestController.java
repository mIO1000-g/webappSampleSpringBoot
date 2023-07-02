package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.form.ValidationTestForm;

@RequestMapping("/validation_test")
@Controller
public class ValidationTestController {

	@Autowired
	private SmartValidator smartValidator;
	
	@RequestMapping("/")
	public String init(@ModelAttribute ValidationTestForm form) {
		return "validation_test";
	}

	@RequestMapping("/validate")
	public String validate(@ModelAttribute @Validated ValidationTestForm form, BindingResult br) {
		
		if (br.hasErrors()) {
			// エラーがある場合
			System.out.println(br.getFieldErrors());
			return "validation_test";
		}
		
		return "validation_test";
	}

}
