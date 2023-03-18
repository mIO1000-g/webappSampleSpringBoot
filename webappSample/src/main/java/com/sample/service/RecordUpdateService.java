package com.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.dao.EmployeeDao;
import com.sample.dao.RecordUpdateDao;
import com.sample.entity.Employee;
import com.sample.form.RecordUpdateForm;
import com.sample.util.Util;

@Service
public class RecordUpdateService {
	
	@Autowired
	private RecordUpdateDao dao;
	@Autowired
	private EmployeeDao empDao;
	
	public void init(RecordUpdateForm form) {
		Employee employee = empDao.selectByPk(form.getEmployeeId());
		if (employee == null) {
			// TODO:エラー
			return;
		}
		form.setName(employee.name);
		form.setGender(employee.gender);
		form.setBirthday(employee.birthday);
		form.setEnteringDate(employee.enteringDate);
		form.setRetirementDate(employee.retirementDate);
		form.setDepartmentId(employee.departmentId);
		form.setUpdateDate(employee.updateDate);
	}
	
	@Transactional
	public void insert(RecordUpdateForm form) {
		
		Employee employee = new Employee();
		employee.employeeId = form.getEmployeeId();
		employee.name = form.getName();
		employee.gender = form.getGender();
		employee.birthday = form.getBirthday();
		employee.enteringDate = form.getEnteringDate();
		employee.retirementDate = form.getRetirementDate();
		// TODO:セッション
		employee.insertUser = "insert";
		employee.insertDate = Util.getNow("yyyyMMddHHmmss");
		employee.updateUser = "insert";
		employee.updateDate = Util.getNow("yyyyMMddHHmmss");
		
		empDao.insert(employee);
		
		
	}
	
	@Transactional
	public void update(RecordUpdateForm form) {
		
		Employee employee = empDao.selectByPkWithLock(form.getEmployeeId());
		if (employee == null) {
			// TODO:エラー
			return;
		}
		
		employee.employeeId = form.getEmployeeId();
		employee.name = form.getName();
		employee.gender = form.getGender();
		employee.birthday = form.getBirthday();
		employee.enteringDate = form.getEnteringDate();
		employee.retirementDate = form.getRetirementDate();
		// TODO:セッション
		employee.updateUser = "update";
		employee.updateDate = Util.getNow("yyyyMMddHHmmss");
		
		// TODO:SQLファイル化
		empDao.update(employee);
		
		
	}
	
	@Transactional
	public void delete(RecordUpdateForm form) {
		
	}
	
	public List<Map<String, String>> getDepartmentList() {
		
		List<Map<String, String>> list = new ArrayList<>();
		for (Map<String, Object> rec : dao.selectDepartmentList()) {
			Map<String, String> map = new HashMap<>();
			map.put("departmentId", (String)rec.get("department_id"));
			map.put("departmentName", (String)rec.get("department_name"));
			list.add(map);
		}
		return list;

	}
}
