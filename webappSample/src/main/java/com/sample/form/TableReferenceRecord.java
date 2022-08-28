package com.sample.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableReferenceRecord {

	private String no;
	private String employeeId;
	private String name;
	private String gender;
	private String genderName;
	private String birthday;
	private String enteringDate;
	private String retirementDate;
	private String departmentId;
	private String departmentName;
	private String updateDate;
}
