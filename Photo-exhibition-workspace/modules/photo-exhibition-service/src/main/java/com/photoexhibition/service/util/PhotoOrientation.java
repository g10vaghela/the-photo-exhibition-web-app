package com.photoexhibition.service.util;

public enum PhotoOrientation {
	LANDSCAPE("landscape",11),
	PORTRAIT("portrait",12);

	private final String message;
	private final int value;

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	private PhotoOrientation(String message, int value) {
		this.message = message;
		this.value = value;
	}
}