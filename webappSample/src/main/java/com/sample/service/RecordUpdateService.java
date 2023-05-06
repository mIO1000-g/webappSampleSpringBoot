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

/**
 * @author msend
 * 単票更新Service
 */
@Service
public class RecordUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(RecordUpdateService.class);

	@Autowired
	private MessageUtil message;
	@Autowired
	private RecordUpdateDao dao;
	@Autowired
	private EmployeeDao empDao;

	/**
	 * 初期表示
	 * @param form フォームオブジェクト
	 */
	public void init(RecordUpdateForm form) {

		// 従業員マスタ検索
		Employee employee = empDao.selectByPk(form.getEmployeeId());
		if (employee == null) {
			// データがない場合
			logger.warn(message.getMessage("WCOM00005", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00005", null));
		}
		
		// 取得したデータをフォームに設定
		form.setName(employee.name);
		form.setGender(employee.gender);
		form.setBirthday(Util.convertDateTimeString(employee.birthday, "yyyyMMdd", "yyyy-MM-dd"));
		form.setEnteringDate(Util.convertDateTimeString(employee.enteringDate, "yyyyMMdd", "yyyy-MM-dd"));
		form.setRetirementDate(Util.convertDateTimeString(employee.retirementDate, "yyyyMMdd", "yyyy-MM-dd"));
		form.setDepartmentId(employee.departmentId);
		form.setUpdateDate(employee.updateDate);
		
		return;
	}

	/**
	 * 登録
	 * @param form フォームオブジェクト
	 */
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

	/**
	 * 更新
	 * @param form フォームオブジェクト
	 */
	@Transactional
	public void update(RecordUpdateForm form) {

		Employee employee = null;
		try {
			logger.debug("★update");
			// 検索
			employee = empDao.selectByPkWithLock(form.getEmployeeId());
		} catch (CannotAcquireLockException ex) {
			// 排他ロックエラーが発生した場合
			logger.warn(message.getMessage("WCOM00004", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00004", null));
		}
		if (employee == null) {
			// データがない場合
			logger.warn(message.getMessage("WCOM00003", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}
		if (!employee.updateDate.equals(form.getUpdateDate())) {
			// 楽観排他エラーの場合
			logger.debug("更新日時不一致 " + employee.updateDate + " " + form.getUpdateDate());
			logger.warn(message.getMessage("WCOM00003", null));
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

	/**
	 * 削除
	 * @param form フォームオブジェクト
	 */
	@Transactional
	public void delete(RecordUpdateForm form) {

		Employee employee = null;
		try {
			employee = empDao.selectByPkWithLock(form.getEmployeeId());
		} catch (CannotAcquireLockException ex) {
			// 排他ロックエラーが発生した場合
			logger.warn(message.getMessage("WCOM00004", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00004", null));
		}
		if (employee == null) {
			// データがない場合
			logger.warn(message.getMessage("WCOM00003", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}
		if (!employee.updateDate.equals(form.getUpdateDate())) {
			// 楽観排他エラーの場合
			logger.debug("更新日時不一致 " + employee.updateDate + " " + form.getUpdateDate());
			logger.warn(message.getMessage("WCOM00003", null));
			throw new ApplicationCustomException(message.getMessage("WCOM00003", null));
		}

		// 削除
		empDao.delete(employee);
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
