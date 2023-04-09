package com.sample.form;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern="yyyy-MM-dd")
	String birthday;

	@NotBlank
	@DateTimeFormat(pattern="yyyy-MM-dd")
	String enteringDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	String retirementDate;

	@NotBlank
	String departmentId;

	String updateDate;

	String screenMode;

	List<Map<String, String>> departmentList;
}
