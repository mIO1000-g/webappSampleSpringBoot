package com.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.exception.ApplicationCustomException;
import com.sample.form.RecordUpdateForm;
import com.sample.service.RecordUpdateService;
import com.sample.util.Constant;

@Controller
@RequestMapping("/record_update")
public class RecordUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(RecordUpdateController.class);

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
	public String confirm(@Validated @ModelAttribute(name = "recordUpdateForm") RecordUpdateForm form, BindingResult br,
			RedirectAttributes redirect) {
		logger.info("エラー件数=" + Integer.toString(br.getErrorCount()));
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		if (br.hasErrors()) {
			return "record_update";
		}

		try {
			if (Constant.SCREEN_MODE_ADD.equals(form.getScreenMode())) {
				sv.insert(form);
			} else {
				sv.update(form);
			}
		} catch (ApplicationCustomException ex) {
			logger.warn(ex.getMessage());
			return "record_update";
		}

		form.setScreenMode(Constant.SCREEN_MODE_EDIT);
		redirect.addFlashAttribute(form);
		return "redirect:/record_update/";
	}

	@PostMapping("/delete")
	public String delete(@Validated @ModelAttribute(name = "recordUpdateForm") RecordUpdateForm form, BindingResult br,
			RedirectAttributes redirect) {
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		if (br.hasErrors()) {
			return "record_update";
		}

		try {
			sv.delete(form);
		} catch (ApplicationCustomException ex) {
			logger.warn(ex.getMessage());
			return "record_update";
		}

		form.setScreenMode(Constant.SCREEN_MODE_EDIT);
		redirect.addFlashAttribute(form);
		return "redirect:/record_update/";
	}
}
