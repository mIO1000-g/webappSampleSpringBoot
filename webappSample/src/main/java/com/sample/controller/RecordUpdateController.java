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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.exception.ApplicationCustomException;
import com.sample.form.RecordUpdateForm;
import com.sample.service.RecordUpdateService;
import com.sample.util.Constant;
import com.sample.util.MessageUtil;

/**
 * @author msend
 * 単票更新Controller
 */
@Controller
@RequestMapping("/record_update")
public class RecordUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(RecordUpdateController.class);

	@Autowired
	private MessageUtil message;
	@Autowired
	RecordUpdateService sv;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// リクエストパラメータのうち、空文字をnullにする。
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public RecordUpdateForm resetForm(RecordUpdateForm form) {
		// プルダウンリストの初期化
		form.setDepartmentList(sv.getDepartmentList());
		return form;
	}

	/**
	 * 初期表示
	 * @param form フォームオブジェクト
	 * @param model Model
	 * @return ページ名
	 */
	@RequestMapping(path = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String init(RecordUpdateForm form, Model model) {
		if (Constant.SCREEN_MODE_EDIT.equals(form.getScreenMode())) {
			// 編集モードの場合

			try {
				// 検索
				sv.init(form);
			} catch (ApplicationCustomException ex) {
				// 業務エラーが発生した場合、メッセージとして返す
				model.addAttribute("message", ex.getMessage());
			}

		} else {
			// 上記以外は追加モードとみなす
			form.setScreenMode(Constant.SCREEN_MODE_ADD);
		}
		return "record_update";
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
	public String confirm(@Validated @ModelAttribute(name = "recordUpdateForm") RecordUpdateForm form, BindingResult br,
			Model model, RedirectAttributes redirect) {
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));

		if (br.hasErrors()) {
			// 単項目チェックエラーがある場合
			model.addAttribute("message", message.getMessage("WCOM00002", null));
			return "record_update";
		}

		try {
			if (Constant.SCREEN_MODE_ADD.equals(form.getScreenMode())) {
				// 追加モードの場合、登録
				sv.insert(form);
			} else {
				// 上記以外、更新
				sv.update(form);
			}
			// 編集モードに切り替え
			form.setScreenMode(Constant.SCREEN_MODE_EDIT);
		} catch (ApplicationCustomException ex) {
			// 業務エラーが発生した場合、メッセージとして返す
			model.addAttribute("message", ex.getMessage());
			return "record_update";
		}

		// リダイレクト
		redirect.addFlashAttribute(form);
		return "redirect:/record_update/";
	}

	/**
	 * 削除
	 * @param form フォームオブジェクト
	 * @param br BindingResultオブジェクト
	 * @param model Model
	 * @param redirect リダイレクトでフラッシュスコープを使うためのオブジェクト
	 * @return ページ名
	 */
	@PostMapping("/delete")
	public String delete(@Validated @ModelAttribute(name = "recordUpdateForm") RecordUpdateForm form, BindingResult br,
			Model model, RedirectAttributes redirect) {
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));

		if (br.hasErrors()) {
			// 単項目チェックエラーがある場合
			model.addAttribute("message", message.getMessage("WCOM00002", null));
			return "record_update";
		}

		try {
			// 削除
			sv.delete(form);
		} catch (ApplicationCustomException ex) {
			// 業務エラーが発生した場合、メッセージとして返す
			model.addAttribute("message", ex.getMessage());
			return "record_update";
		}

		// リダイレクト
		redirect.addFlashAttribute(form);
		return "redirect:/record_update/";
	}
}
