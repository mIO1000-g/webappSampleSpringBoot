package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.form.TableReferenceForm;
import com.sample.service.TableReferenceService;

@Controller
@RequestMapping("table_reference")
public class TableReferenceController {

	@Autowired
	TableReferenceService sv;

	@GetMapping("")
	public String init(@ModelAttribute("form") TableReferenceForm form) {
		
		form.setRetired(false);

		return "table_reference";
	}
	
	@PostMapping("/search")
	public String search(@ModelAttribute("form") TableReferenceForm form) {
		
		sv.search(form);

		return "table_reference";
	}

}
