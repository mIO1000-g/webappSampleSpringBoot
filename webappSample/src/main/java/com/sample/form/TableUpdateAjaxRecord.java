package com.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TableUpdateAjaxRecord {

	//private boolean checked;
	private String no;
	@NotBlank
	@Size(max = 10)
	private String employeeId;
	@NotBlank
	@Size(max = 30)
	private String name;
	@NotBlank
	private String gender;
	@NotBlank
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String birthday;
	@NotBlank
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String enteringDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String retirementDate;
	@NotBlank
	private String departmentId;
	private boolean newLine;
	private String updateDate;
}
