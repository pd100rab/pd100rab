package com.springboot.basics.utils;

public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String msg;

	public InvalidDataException(final String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

}
