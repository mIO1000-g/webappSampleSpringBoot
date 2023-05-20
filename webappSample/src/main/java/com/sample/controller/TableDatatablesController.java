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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.dto.ResponseDto;
import com.sample.form.TableDatatablesConfirmForm;
import com.sample.form.TableDatatablesRecord;
import com.sample.form.TableDatatablesSearchForm;
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

//	@ModelAttribute
//	public TableDatatablesForm resetForm(TableDatatablesForm form) {
//
//		// プルダウンリストのリセット
//		form.setDepartmentList(sv.getDepartmentList());
//		return form;
//	}

	/**
	 * 初期表示
	 * @param form フォームオブジェクト
	 * @return ページ名
	 */
	@GetMapping("/")
	public String init(TableDatatablesSearchForm form) {

		// プルダウンリストのセット
		form.setDepartmentList(sv.getDepartmentList());
		// 初期値
		form.setRetired(false);

		return "table_datatables";
	}

	/**
	 * 検索
	 * @param form 検索用フォームオブジェクト
	 * @return ページ名
	 */
	@RequestMapping(path = "/search", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseDto search(TableDatatablesSearchForm form) {

		// 検索
		List<TableDatatablesRecord> list = sv.search(form);
		
		ResponseDto rd = new ResponseDto("", "OK", list);

		return rd;
	}

	@RequestMapping(path = "/confirm", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseDto confirm(TableDatatablesConfirmForm form) {
		//System.out.println(detail);
		ResponseDto rd = new ResponseDto("", "OK", null);

		return rd;
	}

}
