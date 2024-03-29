package com.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.dto.FieldErrorDto;
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
//@Validated
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

	/**
	 * 確定（FormData利用）
	 * @param form フォームオブジェクト
	 * @param br BindingResult
	 * @return レスポンスDTO
	 */
	@RequestMapping(path = "/confirm_formdata", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseDto confirmFormData(TableDatatablesConfirmForm form, BindingResult br) {

		// 単項目チェック
		validate(form.getDetail(), br);
		
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		if (br.hasErrors()) {
			// エラーがある場合
			List<FieldErrorDto> fieldErrors = getFieldErrors(br);
			ResponseDto rd = new ResponseDto(message.getMessage("WCOM00002", null), "NG", fieldErrors);
			return rd;
		}
		
		sv.confirm(form.getDetail());

		ResponseDto rd = new ResponseDto(message.getMessage("WCOM00001", null), "OK", null);

		return rd;
	}

	/**
	 * 確定（JsonからFormクラスにバインド）
	 * @param form フォームオブジェクト
	 * @param br BindingResult
	 * @return レスポンスDTO
	 */
	@RequestMapping(path = "/confirm_json2form", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseDto confirmJson2Form(@RequestBody TableDatatablesConfirmForm form, BindingResult br) {

		// 単項目チェック
		validate(form.getDetail(), br);
		
		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		if (br.hasErrors()) {
			// エラーがある場合
			List<FieldErrorDto> fieldErrors = getFieldErrors(br);
			ResponseDto rd = new ResponseDto(message.getMessage("WCOM00002", null), "NG", fieldErrors);
			return rd;
		}
		
		sv.confirm(form.getDetail());

		ResponseDto rd = new ResponseDto(message.getMessage("WCOM00001", null), "OK", null);

		return rd;
	}

	/**
	 * 確定（JsonからListにバインド）
	 * @param form フォームオブジェクト
	 * @param br BindingResult
	 * @return レスポンスDTO
	 */
	@RequestMapping(path = "/confirm_json2list", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseDto confirmJson2List(@RequestBody List<TableDatatablesRecord> list, BindingResult br) {

		// 単項目チェック
		validate(list, br);

		logger.debug("エラー件数=" + Integer.toString(br.getErrorCount()));
		if (br.hasErrors()) {
			// エラーがある場合
			List<FieldErrorDto> fieldErrors = getFieldErrors(br);
			ResponseDto rd = new ResponseDto(message.getMessage("WCOM00002", null), "NG", fieldErrors);
			return rd;
		}
		
		sv.confirm(list);

		ResponseDto rd = new ResponseDto(message.getMessage("WCOM00001", null), "OK", null);

		return rd;
	}

	private void validate(List<TableDatatablesRecord> list, BindingResult br) {
		// NOTE:リクエストハンドラの引数にアノテーションをつけてValidationも可能だが、
		// 自動でBindingResultに格納されるパスが、結果をクライアントに戻したときに結局使いづらいため、自前で制御する。
		
		for (int i = 0; i < list.size(); i++) {
			TableDatatablesRecord record = list.get(i);
			// 選択した行のみ単項目チェック対象とする
			// エラー対象のフィールドを設定するために、一時的にパスをプッシュ
			br.pushNestedPath("list[" + record.getEmployeeId() + "]");
			// SmartValidatorに、検証対象のオブジェクトを渡す
			smartValidator.validate(record, br);
			// 追加したパスをリセット
			br.popNestedPath();
		}
	}

	private List<FieldErrorDto> getFieldErrors(BindingResult br) {
		// BindingResultのパスから、キー情報とフィールドを取得する正規表現
		String regex = "\\[(.+?)\\]\\.(.+?)$";
		Pattern p = Pattern.compile(regex);
		
		List<FieldErrorDto> list = new ArrayList<>();

		// BindingResultからエラーメッセージを全て取得
		for (FieldError fe : br.getFieldErrors()) {
			FieldErrorDto dto = new FieldErrorDto();
			Matcher m = p.matcher(fe.getField());
			if (m.find()) {
				// 単項目チェックエラー格納用DTOに詰めなおす
				System.out.println(m.group(1) + "-" + m.group(2)  + "=" + fe.getDefaultMessage());
				dto.setKey(m.group(1));
				dto.setColumn(m.group(2));
				dto.setErrorMessage(fe.getDefaultMessage());
				list.add(dto);
			}
		}

		return list;
	}
}
