package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sample.form.TableReferenceForm;
import com.sample.service.TableReferenceService;

@Controller
public class TableReferenceController {

	@Autowired
	TableReferenceService sv;

	@GetMapping("/table_reference")
	public String init(@ModelAttribute("form") TableReferenceForm form) {
		
		form.setRetired(false);
		
		sv.init(form);

		return "table_reference";
	}

}
