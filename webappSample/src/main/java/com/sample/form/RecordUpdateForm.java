package com.sample.form;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RecordUpdateForm {

	@NotBlank
	@Size(max=10)
	String employeeId;

	@NotBlank
	@Size(max=30)
	String name;

	@NotBlank
	String gender;

	@NotBlank
	String birthday;

	@NotBlank
	String enteringDate;
	
	String retirementDate;

	@NotBlank
	String departmentId;

	String updateDate;

	String screenMode;

	List<Map<String, String>> departmentList;
}
