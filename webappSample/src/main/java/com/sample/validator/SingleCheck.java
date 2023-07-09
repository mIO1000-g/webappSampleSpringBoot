package com.sample.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented
// 独自のバリデータを実装する
@Constraint(validatedBy = { SingleCheckValidator.class })
//適用可能な箇所
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
//コンパイラによってクラス・ファイルに記録され、実行時にVMによって保持される
@Retention(RUNTIME)
// message属性で指定したメッセージを利用できるようにする。既成ルールを合成した場合に、付与しないと、既成ルールに指定したメッセージが利用される。
@ReportAsSingleViolation
public @interface SingleCheck {

	String fieldName() default "";
	boolean isRequired() default false;
	// TODO 列挙型にする
	String dataType() default "";
	int maxSize() default 0;
	String refMaster() default "";
	
	// Bean Validation として必要: エラーメッセージ
	String message() default "単項目チェックエラー";
	// Bean Validation として必要: ターゲットグループをカスタマイズする用
	Class<?>[] groups() default {};
	// Bean Validation として必要: メタデータ情報の拡張用
	Class<? extends Payload>[] payload() default {};
	
	// ひとつの対象に複数の @SingleCheck を指定するための設定
	@Documented
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	public @interface List {
		SingleCheck[] values();
	}

}
