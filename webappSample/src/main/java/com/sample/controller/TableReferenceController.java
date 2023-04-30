package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.form.TableReferenceForm;
import com.sample.form.TableReferenceRecord;
import com.sample.service.TableReferenceService;

@Controller
@RequestMapping("/table_reference")
public class TableReferenceController {

	@Autowired
	TableReferenceService sv;

	@GetMapping("/")
	public String init(TableReferenceForm form) {

		form.setRetired(false);

		return "table_reference";
	}

	//@PostMapping("/search")
	public String search(TableReferenceForm form) {

		sv.search(form);

		return "table_reference";
	}

	@RequestMapping(path = "/search", method= {RequestMethod.GET, RequestMethod.POST})
	public String search(TableReferenceForm form, Model model, Pageable pageable) {

		Page<TableReferenceRecord> page = sv.search(form, pageable);
		model.addAttribute("page", page);

		return "table_reference";
	}

}
