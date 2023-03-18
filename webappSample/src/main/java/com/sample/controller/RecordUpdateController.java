package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.form.RecordUpdateForm;
import com.sample.service.RecordUpdateService;
import com.sample.util.Constant;

@Controller
@RequestMapping("/record_update")
public class RecordUpdateController {

	@Autowired
	RecordUpdateService sv;
	
	@ModelAttribute
	public RecordUpdateForm resetForm(RecordUpdateForm form) {
		form.setDepartmentList(sv.getDepartmentList());
		return form;
	}

	@GetMapping("/")
	public String init(RecordUpdateForm form) {
		if (Constant.SCREEN_MODE_EDIT.equals(form.getScreenMode())) {
			sv.init(form);
		}
		return "record_update";
	}

	@PostMapping("/confirm")
	public String confirm(@Validated @ModelAttribute(name="recordUpdateForm") RecordUpdateForm form, BindingResult br, RedirectAttributes redirect) {
		System.out.println(br.getErrorCount());
		if (br.hasErrors()) {
			
			return "record_update";
		}
		
		if (Constant.SCREEN_MODE_ADD.equals(form.getScreenMode())) {
			sv.insert(form);
		} else {
			sv.update(form);
		}
		
		form.setScreenMode(Constant.SCREEN_MODE_EDIT);
		redirect.addFlashAttribute(form);
		return "redirect:/record_update/";
	}
	
	@PostMapping("/delete")
	public String delete(@Validated RecordUpdateForm form, BindingResult br, RedirectAttributes redirect) {
		sv.delete(form);
		form.setScreenMode(Constant.SCREEN_MODE_EDIT);
		redirect.addFlashAttribute(form);
		return "redirect:/record_update/";
	}
}
