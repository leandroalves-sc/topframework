package com.topsoft.topframework.base.exception;

/**
 * 
 * @author Leandro Alves
 * 
 */
public class AuthenticationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
		super("User does not authenticated, please log in.");
	}
}