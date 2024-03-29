package com.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.SelectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sample.dao.TableReferenceDao;
import com.sample.form.TableReferenceForm;
import com.sample.form.TableReferenceRecord;
import com.sample.util.Util;

/**
 * @author msend
 * 一覧参照Service
 */
@Service
public class TableReferenceService {
	

	private static final Logger logger = LoggerFactory.getLogger(TableReferenceService.class);

	@Autowired
	public TableReferenceDao dao;

	/**
	 * 検索（ページングなし）
	 * @param form フォームオブジェクト
	 */
	public void search(TableReferenceForm form) {

		List<Map<String, Object>> detail = dao.selectDetail(form);

		List<TableReferenceRecord> list = new ArrayList<>();

		int cnt = 1;

		for (Map<String, Object> map : detail) {

			TableReferenceRecord rec = new TableReferenceRecord();

			rec.setNo(Integer.toString(cnt++));
			rec.setEmployeeId(Util.convertToString(map.get("employee_id")));
			rec.setName(Util.convertToString(map.get("name")));
			rec.setGenderName(Util.convertToString(map.get("gender_name")));
			rec.setBirthday(Util.convertDateTimeString(map.get("birthday"), "yyyyMMdd", "yyyy/MM/dd"));
			rec.setEnteringDate(Util.convertDateTimeString(map.get("entering_date"), "yyyyMMdd", "yyyy/MM/dd"));
			rec.setRetirementDate(Util.convertDateTimeString(map.get("retirement_date"), "yyyyMMdd", "yyyy/MM/dd"));
			rec.setDepartmentId(Util.convertToString(map.get("department_id")));
			rec.setDepartmentName(Util.convertToString(map.get("department_name")));
			rec.setUpdateDate(Util.convertToString(map.get("update_date")));

			list.add(rec);
		}

		form.setDetail(list);

		return;
	}

	/**
	 * 検索（ページングあり）
	 * @param form フォームオブジェクト
	 * @param pageable ページャブルオブジェクト
	 * @return ページオブジェクト
	 */
	public Page<TableReferenceRecord> search(TableReferenceForm form, Pageable pageable) {

		// 検索オプション設定
		SelectOptions options = createSearchOptions(pageable);
		List<Map<String, Object>> detail = dao.selectDetailPageable(options, form);
		
		List<TableReferenceRecord> list = new ArrayList<>();

		// 「No」を取得するためにオフセット＋１を算出
		int cnt = pageable.getPageSize() * (pageable.getPageNumber()) + 1;

		for (Map<String, Object> map : detail) {

			TableReferenceRecord rec = new TableReferenceRecord();

			rec.setNo(Integer.toString(cnt++));
			rec.setEmployeeId(Util.convertToString(map.get("employee_id")));
			rec.setName(Util.convertToString(map.get("name")));
			rec.setGenderName(Util.convertToString(map.get("gender_name")));
			rec.setBirthday(Util.convertDateTimeString(map.get("birthday"), "yyyyMMdd", "yyyy/MM/dd"));
			rec.setEnteringDate(Util.convertDateTimeString(map.get("entering_date"), "yyyyMMdd", "yyyy/MM/dd"));
			rec.setRetirementDate(Util.convertDateTimeString(map.get("retirement_date"), "yyyyMMdd", "yyyy/MM/dd"));
			rec.setDepartmentId(Util.convertToString(map.get("department_id")));
			rec.setDepartmentName(Util.convertToString(map.get("department_name")));
			rec.setUpdateDate(Util.convertToString(map.get("update_date")));

			list.add(rec);
		}
		logger.debug("全体件数=" + options.getCount());

		// ページオブジェクトを生成
		Page<TableReferenceRecord> page = new PageImpl<>(list, pageable, options.getCount());
		form.setDetail(page.getContent());


		return page;
	}

	private SelectOptions createSearchOptions(Pageable pageable) {
		return SelectOptions.get()
				// オフセットの指定
				.offset(pageable.getPageSize() * (pageable.getPageNumber()))
				// １ページあたりの行数
				.limit(pageable.getPageSize())
				// 全行の行数を取得する
				.count();
	}

}
