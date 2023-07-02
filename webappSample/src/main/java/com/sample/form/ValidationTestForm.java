package com.sample.form;

import javax.validation.constraints.NotBlank;

import com.sample.validator.Date;

import lombok.Data;

@Data
public class ValidationTestForm {

	@NotBlank
	private String strRequired;
	
	private String strAll;
	
	private String strAlphaNumericSymbol;

	private String strInteger;
	
	@Date
	private String strDate;

	private String strTime;
	
	private String strDateTImestamp;
	
	private String employeeId;
	
	private String startDate;
	
	private String endDate;

	private String password;
}
