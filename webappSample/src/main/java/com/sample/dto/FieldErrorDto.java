package com.sample.dto;

import lombok.Data;

@Data
public class FieldErrorDto {

	private String key;
	private String column;
	private String errorMessage;
}
