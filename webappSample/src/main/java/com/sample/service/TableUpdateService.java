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
import com.sample.dao.TableUpdateDao;
import com.sample.entity.Employee;
import com.sample.exception.ApplicationCustomException;
import com.sample.form.TableUpdateForm;
import com.sample.form.TableUpdateRecord;
import com.sample.util.MessageUtil;
import com.sample.util.Util;

@Service
public class TableUpdateService {
	

	private static final Logger logger = LoggerFactory.getLogger(TableUpdateService.class);

	@Autowired
	private MessageUtil message;
	@Autowired
	public TableUpdateDao dao;
	@Autowired
	private EmployeeDao empDao;


	public void search(TableUpdateForm form) {

		List<Map<String, Object>> detail = dao.selectDetail(form);

		List<TableUpdateRecord> list = new ArrayList<>();

		int cnt = 1;

		for (Map<String, Object> map : detail) {

			TableUpdateRecord rec = new TableUpdateRecord();

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

		form.setDetail(list);

		return;
	}
	
	@Transactional
	public void confirm(TableUpdateForm form) {
		
		for (TableUpdateRecord rec : form.getDetail()) {
			
			if (!rec.isChecked()) continue;
			
			if (rec.isNewLine()) {
				insert(rec);
			} else {
				update(rec);
			}
		}
		
	}

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
	
	private void insert(TableUpdateRecord record) {

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
	
	private void update(TableUpdateRecord record) {

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
