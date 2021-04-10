package com.springboot.basics.utils;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String msg;

	public FileStorageException(final String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

}
