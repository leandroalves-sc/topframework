package com.topsoft.topframework.base.exception;

/**
 * 
 * @author Leandro Alves
 * 
 */
public class AuthorizationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public AuthorizationException() {
		super("User does not have access to this feature.");
	}
}