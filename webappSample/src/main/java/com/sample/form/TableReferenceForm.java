package com.sample.form;

import java.util.List;

import lombok.Data;

@Data
public class TableReferenceForm {

	private String employeeId;
	private String name;
	// NOTE:checkboxはbooleanとする
	// Thymeleaf側でth:fieldで出力することでチェックなしの場合でもデフォルト値がセットされるようにする
	private boolean gender_m;
	private boolean gender_f;
	private String enteringYear;
	private boolean retired;
	private String departmentId;

	private List<TableReferenceRecord> detail;

}
