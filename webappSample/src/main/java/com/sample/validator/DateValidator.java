package com.sample.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<Date, String> {
	
	// アノテーションのインスタンス
	private Date dateAnnotation;
	
	// バリデーターを初期化。
	@Override
	public void initialize(Date constraintAnnotation) {
		this.dateAnnotation = constraintAnnotation;
	}
	
	// 検証ロジックの実装。このメソッドは同時にアクセスされる可能性があるため、スレッドセーフにする必要がある。
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if (value == null || value.isEmpty()) {
			return true;
		}
		
		boolean result = false;
		
		try {
			String checkDate = value.replace("-", "").replace("/", "");
			DateTimeFormatter.ofPattern("uuuuMMdd")
				.withResolverStyle(ResolverStyle.STRICT)
				.parse(checkDate, LocalDate::from);
			result = true;
		} catch (Exception e) {

		}
		
		
		return result;
	}

}
