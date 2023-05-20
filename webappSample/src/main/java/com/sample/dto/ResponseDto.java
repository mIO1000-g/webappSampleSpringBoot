package com.sample.dto;

import lombok.Data;

@Data
public class ResponseDto {
	
	private String message;
	private String result;
	private Object data;

	public ResponseDto(String message, String result, Object data) {
		this.message = message;
		this.result = result;
		this.data = data;
	}
	
}
