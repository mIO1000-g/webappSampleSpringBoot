package com.sample.exception;

public class ApplicationCustomException extends RuntimeException {

	/**
	 * アプリケーションカスタム例外
	 * @param message
	 */
	public ApplicationCustomException(String message) {
		super(message);
	}
}
