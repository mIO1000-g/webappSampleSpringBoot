package com.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.exception.ApplicationCustomException;
import com.sample.form.TableUpdateForm;
import com.sample.service.TableUpdateService;
import com.sample.util.MessageUtil;

@Controller
@RequestMapping("/table_update")
public class TableUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(TableUpdateController.class);

	@Autowired
	TableUpdateService sv;
	@Autowired
	private MessageUtil message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public TableUpdateForm resetForm(TableUpdateForm form) {
		form.setDepartmentList(sv.getDepartmentList());
		return form;
	}

	@GetMapping("/")
	public String init(TableUpdateForm form) {

		form.setRetired(false);

		return "table_update";
	}

	@RequestMapping(path = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(TableUpdateForm form) {
		System.out.println(form.getDepartmentId());
		sv.search(form);
		System.out.println(form.getDepartmentId());
		return "table_update";
	}

	@PostMapping("/confirm")
	public String confirm(@Validated @ModelAttribute(name = "tableUpdateForm") TableUpdateForm form, BindingResult br,
			Model model, RedirectAttributes redirect) {
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		logger.debug(br.getFieldErrors().toString());
		if (br.hasErrors()) {
			model.addAttribute("message", message.getMessage("WCOM00002", null));
			return "table_update";
		}

		try {
			sv.confirm(form);

		} catch (ApplicationCustomException ex) {
			model.addAttribute("message", ex.getMessage());
			return "table_update";
		}

		redirect.addFlashAttribute(form);
		return "redirect:/table_update/";
	}

}
