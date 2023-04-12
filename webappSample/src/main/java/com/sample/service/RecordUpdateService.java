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
import com.sample.dao.RecordUpdateDao;
import com.sample.entity.Employee;
import com.sample.exception.ApplicationCustomException;
import com.sample.form.RecordUpdateForm;
import com.sample.util.MessageUtil;
import com.sample.util.Util;

@Service
public class RecordUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(RecordUpdateService.class);

	@Autowired
	private MessageUtil message;
	@Autowired
	private RecordUpdateDao dao;
	@Autowired
	private EmployeeDao empDao;

	public void init(RecordUpdateForm form) {
		Employee employee = empDao.selectByPk(form.getEmployeeId());
		if (employee == null) {
			// TODO:エラー
			logger.warn("データがありません");
			return;
		}
		form.setName(employee.name);
		form.setGender(employee.gender);
		form.setBirthday(Util.convertDateTimeString(employee.birthday, "yyyyMMdd", "yyyy-MM-dd"));
		form.setEnteringDate(Util.convertDateTimeString(employee.enteringDate, "yyyyMMdd", "yyyy-MM-dd"));
		form.setRetirementDate(Util.convertDateTimeString(employee.retirementDate, "yyyyMMdd", "yyyy-MM-dd"));
		form.setDepartmentId(employee.departmentId);
		form.setUpdateDate(employee.updateDate);
	}

	@Transactional
	public void insert(RecordUpdateForm form) {

		Employee employee = new Employee();
		employee.employeeId = form.getEmployeeId();
		employee.name = form.getName();
		employee.gender = form.getGender();
		employee.birthday = Util.convertDateTimeString(form.getBirthday(), "yyyy-MM-dd", "yyyyMMdd");
		employee.enteringDate = Util.convertDateTimeString(form.getEnteringDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.retirementDate = Util.convertDateTimeString(form.getRetirementDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.departmentId = form.getDepartmentId();
		// TODO:セッションからユーザ情報取得
		employee.insertUser = "insert";
		employee.insertDate = Util.getNow("yyyyMMddHHmmss");
		employee.updateUser = "insert";
		employee.updateDate = Util.getNow("yyyyMMddHHmmss");

		empDao.insert(employee);

	}

	@Transactional
	public void update(RecordUpdateForm form) {

		Employee employee = null;
		try {
			System.out.println("１");
			logger.info("★update");
			logger.debug("★update");
			employee = empDao.selectByPkWithLock(form.getEmployeeId());
		} catch (CannotAcquireLockException ex) {
			throw new ApplicationCustomException(message.getMessage("WCOM00004", null));
		}
		if (employee == null) {
			System.out.println("２");
			logger.debug("データなし");
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}
		if (!employee.updateDate.equals(form.getUpdateDate())) {
			System.out.println("３");
			logger.debug("更新日時不一致 " + employee.updateDate + " " + form.getUpdateDate());
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}

		employee.employeeId = form.getEmployeeId();
		employee.name = form.getName();
		employee.gender = form.getGender();
		employee.birthday = Util.convertDateTimeString(form.getBirthday(), "yyyy-MM-dd", "yyyyMMdd");
		employee.enteringDate = Util.convertDateTimeString(form.getEnteringDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.retirementDate = Util.convertDateTimeString(form.getRetirementDate(), "yyyy-MM-dd", "yyyyMMdd");
		employee.departmentId = form.getDepartmentId();
		// TODO:セッションからユーザ情報取得
		employee.updateUser = "update";
		employee.updateDate = Util.getNow("yyyyMMddHHmmss");

		// TODO:SQLファイル化
		// 更新
		empDao.update(employee);

	}

	@Transactional
	public void delete(RecordUpdateForm form) {

		Employee employee = null;
		try {
			employee = empDao.selectByPkWithLock(form.getEmployeeId());
		} catch (CannotAcquireLockException ex) {
			throw new ApplicationCustomException(message.getMessage("WCOM00004", null));
		}

		if (employee == null) {
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}
		if (!employee.updateDate.equals(form.getUpdateDate())) {
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}

		// 削除
		empDao.delete(employee);
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
}
