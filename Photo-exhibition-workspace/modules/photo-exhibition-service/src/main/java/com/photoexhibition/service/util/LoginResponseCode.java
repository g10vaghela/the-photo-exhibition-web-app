package com.photoexhibition.service.util;

public enum LoginResponseCode {
	SUCCESS("success",1),
	OTP_SENT("otp sent",2),
	ERROR("error",3);

	private final String message;
	private final int value;

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	private LoginResponseCode(String message, int value) {
		this.message = message;
		this.value = value;
	}
}
