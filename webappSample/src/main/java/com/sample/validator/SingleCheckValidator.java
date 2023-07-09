package com.sample.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class SingleCheckValidator implements ConstraintValidator<SingleCheck, String> {
	
	private boolean isRequired;
	
	// バリデーターを初期化。
	@Override
	public void initialize(SingleCheck constraintAnnotation) {
		isRequired = constraintAnnotation.isRequired();
	}
	
	// 検証ロジックの実装。このメソッドは同時にアクセスされる可能性があるため、スレッドセーフにする必要がある。
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		//デフォルトのエラーメッセージは使用しない
		context.disableDefaultConstraintViolation();
		
		// 必須チェック
		if(isRequired && !StringUtils.hasText(value)) {
			context.buildConstraintViolationWithTemplate("{WCOM00006}").addConstraintViolation();

			return false;
		}
		
		return true;
	}

}
