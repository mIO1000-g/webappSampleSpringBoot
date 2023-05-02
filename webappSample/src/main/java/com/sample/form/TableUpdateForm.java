package com.sample.form;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import lombok.Data;

@Data
public class TableUpdateForm {

	private String employeeId;
	private String name;
	private boolean gender_m;
	private boolean gender_f;
	private String enteringYear;
	private boolean retired;
	private String departmentId;

	List<Map<String, String>> departmentList;

	@Valid
	private List<TableUpdateRecord> detail;

}
