package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.form.ValidationTestForm;
import com.sample.validator.SingleItemValidateSequence;

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
	public String validate(@ModelAttribute @Validated({SingleItemValidateSequence.class}) ValidationTestForm form, BindingResult br) {

		if (br.hasErrors()) {
			// エラーがある場合
			System.out.println(br.getFieldErrors());
			return "validation_test";
		}
		
		return "validation_test";
	}
	
	@RequestMapping("/smart_validate")
	public String smartValidate(@ModelAttribute ValidationTestForm form, BindingResult br) {

		//smartValidator.validateValue(ValidationTestForm.class, "strRequired", form.getStrRequired(), br, SingleItemValidateSequence.class);
		
		//smartValidator.validateValue(ValidationTestForm.class, "strDate", form.getStrDate(), br, SingleItemValidateSequence.class);
		
		//smartValidator.validateValue(ValidationTestForm.class, "strInteger", form.getStrInteger(), br, SingleItemValidateSequence.class);
		
		smartValidator.validate(form, br);
		
		if (br.hasErrors()) {
			// エラーがある場合
			System.out.println(br.getFieldErrors());
			return "validation_test";
		}
		
		return "validation_test";
	}

}
