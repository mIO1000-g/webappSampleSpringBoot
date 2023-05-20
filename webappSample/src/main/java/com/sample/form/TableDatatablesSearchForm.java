package com.sample.form;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TableDatatablesSearchForm {

	private String employeeId;
	private String name;
	private boolean gender_m;
	private boolean gender_f;
	private String enteringYear;
	private boolean retired;
	private String departmentId;

	private List<Map<String, String>> departmentList;

}
