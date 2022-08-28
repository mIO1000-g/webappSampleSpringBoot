package com.sample.form;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableReferenceForm {


	private String employeeId;
	private String name;
	private String gender;
	private String enteringYear;
	private boolean retired;
	private String departmentId;
	
	private List<TableReferenceRecord> detail;
	
}
