package com.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sample.dao.TableReferenceDao;
import com.sample.form.TableReferenceForm;
import com.sample.form.TableReferenceRecord;

@Service
public class TableReferenceService {


	public TableReferenceDao dao = new TableReferenceDao();

	public TableReferenceForm init(TableReferenceForm form) {

		List<HashMap<String, Object>> detail = dao.selectDetail();

		List<TableReferenceRecord> list = new ArrayList<>();

		for (HashMap<String, Object> map : detail) {

			TableReferenceRecord rec = new TableReferenceRecord();

			rec.setNo(map.get("no").toString());
			rec.setEmployeeId(map.get("employeeId").toString());
			rec.setName(map.get("name").toString());
			rec.setGenderName(map.get("genderName").toString());
			rec.setBirthday(map.get("birthday").toString());
			rec.setEnteringDate(map.get("enteringDate").toString());
			rec.setRetirementDate(map.get("retirementDate").toString());
			rec.setDepartmentId(map.get("departmentId").toString());
			rec.setDepartmentName(map.get("departmentName").toString());
			rec.setUpdateDate(map.get("updateDate").toString());

			list.add(rec);
		}

		form.setDetail(list);

		return form;
	}

}
