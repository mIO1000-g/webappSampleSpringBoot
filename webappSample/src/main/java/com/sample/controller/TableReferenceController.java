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

/**
 * @author msend
 * 一覧参照Controller
 */
@Controller
@RequestMapping("/table_reference")
public class TableReferenceController {

	@Autowired
	TableReferenceService sv;

	/**
	 * 初期表示
	 * @param form フォームオブジェクト
	 * @return ページ名
	 */
	@GetMapping("/")
	public String init(TableReferenceForm form) {

		// 初期値
		form.setRetired(false);

		return "table_reference";
	}

	
	/**
	 * 検索（ページングなし）
	 * @param form フォームオブジェクト
	 * @return ページ名
	 */
	//@PostMapping("/search")
	public String search(TableReferenceForm form) {

		// 検索
		sv.search(form);

		return "table_reference";
	}

	/**
	 * 検索（ページングあり）
	 * @param form form フォームオブジェクト
	 * @param model Model
	 * @param pageable ページャブルオブジェクト
	 * @return ページ名
	 */
	@RequestMapping(path = "/search", method= {RequestMethod.GET, RequestMethod.POST})
	public String search(TableReferenceForm form, Model model, Pageable pageable) {

		// 検索
		Page<TableReferenceRecord> page = sv.search(form, pageable);
		// ページング情報をModelにセット
		model.addAttribute("page", page);

		return "table_reference";
	}

}
