package com.photoexhibition.service.util;

public enum StatusValue {
	SELECT_ALL("Select All",0),
	ACTIVE("Active",1),
	IN_ACTIVE("InActive",2);

	private final String message;
	private final int value;

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	private StatusValue(String message, int value) {
		this.message = message;
		this.value = value;
	}
}