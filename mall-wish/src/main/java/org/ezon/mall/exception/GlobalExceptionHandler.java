package org.ezon.mall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 찜 예외 처리
	@ExceptionHandler(WishException.class)
	public ResponseEntity<WishErrorResponse> handleWishException(WishException ex) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if(ex.getErrorCode() == WishErrorCode.NOT_FOUND) {
			status = HttpStatus.NOT_FOUND;
		} else if(ex.getErrorCode() == WishErrorCode.INVALID_REQUEST) {
			status = HttpStatus.BAD_REQUEST;
		}
		WishErrorResponse error = new WishErrorResponse(false, ex.getMessage(), ex.getErrorCode().name());
		return ResponseEntity.status(status).body(error);
	}

	// 그 외 모든 예외
	@ExceptionHandler(Exception.class)
	public ResponseEntity<WishErrorResponse> handleOtherExceptions(Exception ex) {
		ex.printStackTrace();
		WishErrorResponse error = new WishErrorResponse(false, "서버 오류가 발생했습니다.", WishErrorCode.UNKNOWN_ERROR.name());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	// 에러 응답 DTO
	public static class WishErrorResponse {
		private boolean success;
		private String message;
		private String code;

		public WishErrorResponse(boolean success, String message, String code) {
			this.success = success;
			this.message = message;
			this.code = code;
		}

		public boolean isSuccess() {
			return success;
		}
		public String getMessage() {
			return message;
		}
		public String getCode() {
			return code;
		}
	}
}
