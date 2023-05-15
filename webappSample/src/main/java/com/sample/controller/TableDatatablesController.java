package com.sample.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.form.TableDatatablesForm;
import com.sample.form.TableDatatablesRecord;
import com.sample.form.TableUpdateForm;
import com.sample.service.TableDatatablesService;
import com.sample.util.MessageUtil;

/**
 * @author msend
 * 一覧更新Controller
 */
@Controller
@RequestMapping("/table_datatables")
public class TableDatatablesController {

	private static final Logger logger = LoggerFactory.getLogger(TableDatatablesController.class);

	@Autowired
	private SmartValidator smartValidator;
	@Autowired
	TableDatatablesService sv;
	@Autowired
	private MessageUtil message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public TableUpdateForm resetForm(TableUpdateForm form) {

		// プルダウンリストのリセット
		form.setDepartmentList(sv.getDepartmentList());
		return form;
	}

	/**
	 * 初期表示
	 * @param form フォームオブジェクト
	 * @return ページ名
	 */
	@GetMapping("/")
	public String init(TableUpdateForm form) {

		// 初期値
		form.setRetired(false);

		return "table_datatables";
	}

	/**
	 * 検索
	 * @param form フォームオブジェクト
	 * @return ページ名
	 */
	@RequestMapping(path = "/search", method = { RequestMethod.POST })
	@ResponseBody
	public List<TableDatatablesRecord> search(TableDatatablesForm form) {

		// 検索
		return sv.search(form);
	}



}
