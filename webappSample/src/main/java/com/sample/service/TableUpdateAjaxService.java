package com.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.dao.EmployeeDao;
import com.sample.dao.TableUpdateAjaxDao;
import com.sample.entity.Employee;
import com.sample.exception.ApplicationCustomException;
import com.sample.form.TableUpdateAjaxForm;
import com.sample.form.TableUpdateAjaxRecord;
import com.sample.util.MessageUtil;
import com.sample.util.Util;

/**
 * @author msend
 * 一覧更新Service
 */
@Service
public class TableUpdateAjaxService {
	

	private static final Logger logger = LoggerFactory.getLogger(TableUpdateAjaxService.class);

	@Autowired
	private MessageUtil message;
	@Autowired
	public TableUpdateAjaxDao dao;
	@Autowired
	private EmployeeDao empDao;


	/**
	 * 検索
	 * @param form フォームオブジェクト
	 */
	public List<TableUpdateAjaxRecord> search(TableUpdateAjaxForm form) {

		// 検索
		List<Map<String, Object>> detail = dao.selectDetail(form);

		List<TableUpdateAjaxRecord> list = new ArrayList<>();

		int cnt = 1;

		for (Map<String, Object> map : detail) {

			TableUpdateAjaxRecord rec = new TableUpdateAjaxRecord();

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
	
	/**
	 * 確定
	 * @param list 更新対象明細リスト
	 */
	@Transactional
	public void confirm(TableUpdateAjaxForm form) {
		
		for (TableUpdateAjaxRecord rec : form.getDetail()) {
			
			if (rec.isNewLine()) {
				// 新規行の場合、登録
				insert(rec);
			} else {
				// 上記以外の場合、更新
				update(rec);
			}
		}
		
	}

	private void insert(TableUpdateAjaxRecord record) {

		Employee employee = new Employee();
		employee.employeeId = record.getEmployeeId();
		employee.name = record.getName();
		employee.gender = record.getGender();
		employee.birthday = Util.convertDateTimeString(record.getBirthday(), "yyyy-MM-dd", "yyyyMMdd");
		employee.enteringDate = Util.convertDateTimeString(record.getEnteringDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.retirementDate = Util.convertDateTimeString(record.getRetirementDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.departmentId = record.getDepartmentId();
		// TODO:セッションからユーザ情報取得
		employee.insertUser = "insert";
		employee.insertDate = Util.getNow("yyyyMMddHHmmss");
		employee.updateUser = "insert";
		employee.updateDate = Util.getNow("yyyyMMddHHmmss");

		empDao.insert(employee);

	}
	
	private void update(TableUpdateAjaxRecord record) {

		Employee employee = null;
		try {
			logger.debug("★update");
			employee = empDao.selectByPkWithLock(record.getEmployeeId());
		} catch (CannotAcquireLockException ex) {
			logger.warn(message.getMessage("WCOM00004", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00004", null));
		}
		if (employee == null) {
			logger.warn(message.getMessage("WCOM00003", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}
		if (!employee.updateDate.equals(record.getUpdateDate())) {
			logger.debug("更新日時不一致 " + employee.updateDate + " " + record.getUpdateDate());
			logger.warn(message.getMessage("WCOM00003", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}

		employee.employeeId = record.getEmployeeId();
		employee.name = record.getName();
		employee.gender = record.getGender();
		employee.birthday = Util.convertDateTimeString(record.getBirthday(), "yyyy-MM-dd", "yyyyMMdd");
		employee.enteringDate = Util.convertDateTimeString(record.getEnteringDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.retirementDate = Util.convertDateTimeString(record.getRetirementDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.departmentId = record.getDepartmentId();
		// TODO:セッションからユーザ情報取得
		employee.updateUser = "update";
		employee.updateDate = Util.getNow("yyyyMMddHHmmss");

		// TODO:SQLファイル化
		// 更新
		empDao.update(employee);

	}
	
	
}
