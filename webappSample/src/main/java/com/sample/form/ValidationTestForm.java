package com.sample.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sample.validator.Date;
import com.sample.validator.Groups.First;
import com.sample.validator.Groups.Second;
import com.sample.validator.Groups.Third;
import com.sample.validator.SingleCheck;

import lombok.Data;

@Data
public class ValidationTestForm {

	@NotBlank
	private String strRequired;
	
	@SingleCheck(isRequired=true)
	private String strAll;
	
	private String strAlphaNumericSymbol;

	@NotBlank
	@Pattern(regexp="[0-9]*", groups = First.class)
	@Size(max = 5, groups = Second.class)
	@Max(value = 10000, groups = Third.class)
	private String strInteger;
	
	@NotBlank
	// TODO 多言語化（propertiesファイルが使えない）
	@Date(fieldName="")
	private String strDate;

	private String strTime;
	
	private String strDateTImestamp;
	
	private String employeeId;
	
	private String startDate;
	
	private String endDate;

	private String password;
}
