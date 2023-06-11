package com.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dao.EmployeeDao;
import com.sample.dao.TableDatatablesDao;
import com.sample.form.TableDatatablesRecord;
import com.sample.form.TableDatatablesSearchForm;
import com.sample.util.MessageUtil;
import com.sample.util.Util;

/**
 * @author msend
 * 一覧更新Service
 */
@Service
public class TableDatatablesService {
	

	private static final Logger logger = LoggerFactory.getLogger(TableDatatablesService.class);

	@Autowired
	private MessageUtil message;
	@Autowired
	public TableDatatablesDao dao;
	@Autowired
	private EmployeeDao empDao;


	/**
	 * 検索
	 * @param form フォームオブジェクト
	 */
	public List<TableDatatablesRecord> search(TableDatatablesSearchForm form) {

		// 検索
		List<Map<String, Object>> detail = dao.selectDetail(form);

		List<TableDatatablesRecord> list = new ArrayList<>();

		int cnt = 1;

		for (Map<String, Object> map : detail) {

			TableDatatablesRecord rec = new TableDatatablesRecord();

			rec.setNo(Integer.toString(cnt++));
			rec.setEmployeeId(Util.convertToString(map.get("employee_id")));
			rec.setNewLine(false);
			rec.setName(Util.convertToString(map.get("name")));
			rec.setGender(Util.convertToString(map.get("gender")));
			rec.setBirthday(Util.convertDateTimeString(map.get("birthday"), "yyyyMMdd", "yyyy-MM-dd"));
			rec.setEnteringDate(Util.convertDateTimeString(map.get("entering_date"), "yyyyMMdd", "yyyy-MM-dd"));
			rec.setRetirementDate(Util.convertDateTimeString(map.get("retirement_date"), "yyyyMMdd", "yyyy-MM-dd"));
			rec.setDepartmentId(Util.convertToString(map.get("department_id")));
			rec.setUpdateDate(Util.convertToString(map.get("update_date")));

			list.add(rec);
		}

		return list;
	}

	/**
	 * 部署ブルダウンリスト取得
	 * @return 部署ブルダウンリスト
	 */
	public List<Map<String, String>> getDepartmentList() {

		List<Map<String, String>> list = new ArrayList<>();
		for (Map<String, Object> rec : dao.selectDepartmentList()) {
			Map<String, String> map = new HashMap<>();
			map.put("departmentId", (String) rec.get("department_id"));
			map.put("departmentName", (String) rec.get("department_name"));
			list.add(map);
		}
		return list;

	}

}
