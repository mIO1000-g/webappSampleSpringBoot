package com.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dao.TableReferenceDao;
import com.sample.form.TableReferenceForm;
import com.sample.form.TableReferenceRecord;
import com.sample.util.Util;

@Service
public class TableReferenceService {

	@Autowired
	public TableReferenceDao dao;
	
	public TableReferenceForm search(TableReferenceForm form) {

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

		return form;
	}

}
