package com.topsoft.topframework.base.exception;

import java.util.Date;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String dateTime;

	public BusinessException(String message, Throwable t) {

		super(message, t);

		this.dateTime = new Date().toString();
	}

	public BusinessException(String message) {
		this(message, null);
	}

	public String getDateTime() {
		return dateTime;
	}
}