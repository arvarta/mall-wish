package org.ezon.mall.exception;

public class WishException extends RuntimeException {
	
	private final WishErrorCode errorCode;

	public WishException(String message, WishErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public WishErrorCode getErrorCode() {
		return errorCode;
	}

}
