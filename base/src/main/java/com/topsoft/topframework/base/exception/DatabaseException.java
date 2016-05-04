package com.topsoft.topframework.base.exception;


/**
 * 
 * @author Leandro Alves
 * 
 */
public class DatabaseException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String message, Throwable t) {
		super(message, t);
	}

	public DatabaseException(Throwable t) {
		
		super("An unexpected database error occurred, details below." , t);
	}
}