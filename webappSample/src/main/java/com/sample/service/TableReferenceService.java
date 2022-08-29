package com.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dao.TableReferenceDao;
import com.sample.form.TableReferenceForm;
import com.sample.form.TableReferenceRecord;

@Service
public class TableReferenceService {

	@Autowired
	public TableReferenceDao dao;

	public TableReferenceForm init(TableReferenceForm form) {

		List<Map<String, Object>> detail = dao.selectDetail(form);

		List<TableReferenceRecord> list = new ArrayList<>();
		
		int cnt = 1;

		for (Map<String, Object> map : detail) {

			TableReferenceRecord rec = new TableReferenceRecord();

			rec.setNo(Integer.toString(cnt++));
			rec.setEmployeeId(String.valueOf(map.get("employee_id")));
			rec.setName(String.valueOf(map.get("name")));
			rec.setGenderName(String.valueOf(map.get("gender_name")));
			rec.setBirthday(String.valueOf(map.get("birthday")));
			rec.setEnteringDate(String.valueOf(map.get("entering_date")));
			rec.setRetirementDate(String.valueOf(map.get("retirement_date")));
			rec.setDepartmentId(String.valueOf(map.get("department_id")));
			rec.setDepartmentName(String.valueOf(map.get("department_name")));
			rec.setUpdateDate(String.valueOf(map.get("update_date")));

			list.add(rec);
		}

		form.setDetail(list);

		return form;
	}

}
