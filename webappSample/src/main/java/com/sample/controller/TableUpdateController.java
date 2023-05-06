package com.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
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
import com.sample.form.TableUpdateRecord;
import com.sample.service.TableUpdateService;
import com.sample.util.MessageUtil;

/**
 * @author msend
 * 一覧更新Controller
 */
@Controller
@RequestMapping("/table_update")
public class TableUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(TableUpdateController.class);

	@Autowired
	private SmartValidator smartValidator;
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

		return "table_update";
	}

	/**
	 * 検索
	 * @param form フォームオブジェクト
	 * @return ページ名
	 */
	@RequestMapping(path = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(TableUpdateForm form) {

		// 検索
		sv.search(form);

		return "table_update";
	}

	/**
	 * 確定
	 * @param form フォームオブジェクト
	 * @param br BindingResultオブジェクト
	 * @param model Model
	 * @param redirect リダイレクトでフラッシュスコープを使うためのオブジェクト
	 * @return ページ名
	 */
	@PostMapping("/confirm")
	public String confirm(@ModelAttribute(name = "tableUpdateForm") TableUpdateForm form, BindingResult br,
			Model model, RedirectAttributes redirect) {

		for (int i = 0; i < form.getDetail().size(); i++) {
			TableUpdateRecord record = form.getDetail().get(i);
			if (record.isChecked()) {
				// 選択した行のみ単項目チェック対象とする
				// エラー対象のフィールドを設定するために、一時的にパスをプッシュ
				br.pushNestedPath("detail[" + i + "]");
				// SmartValidatorに、検証対象のオブジェクトを渡す
				smartValidator.validate(record, br);
				// 追加したパスをリセット
				br.popNestedPath();
			}
		}

		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		if (br.hasErrors()) {
			// 単項目チェックでエラーが発生した場合
			model.addAttribute("message", message.getMessage("WCOM00002", null));
			return "table_update";
		}

		try {
			// 確定
			sv.confirm(form);

		} catch (ApplicationCustomException ex) {
			// 業務エラーが発生した場合、メッセージとして返す
			model.addAttribute("message", ex.getMessage());
			return "table_update";
		}

		redirect.addFlashAttribute(form);
		return "redirect:/table_update/";
	}

}
