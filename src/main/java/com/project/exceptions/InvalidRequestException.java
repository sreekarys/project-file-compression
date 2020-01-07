package com.project.exceptions;

public class InvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	private String key;

	public InvalidRequestException(String key, String value) {
		super("Invalid value: " + value + " provided for the field: " + key);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
