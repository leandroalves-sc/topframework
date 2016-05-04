package com.topsoft.topframework.base.security;

import com.topsoft.topframework.base.domain.User;

public interface SecurityContext {

	public static final String LOGIN_ATTRIBUTE_NAME = "LOGGED_USER_LOGIN";
	public static final String LOGGED_USER_ATTRIBUTE_NAME = "LOGGED_USER_OBJECT";

	public Environment getEnvironment();

	public User getLoggedUser();

	// boolean isUserAuthorized( Integer accessLevel, Long... applicationId );
	// boolean isUserAuthorized(Object request, Integer accessLevel, Long...
	// applicationId);
	// boolean isUserAuthorized(Integer accessLevel, String restrictions,
	// Long... applicationId);
	// boolean isUserAuthorized(Object request, Integer accessLevel, String
	// restrictions, Long... applicationId);
	// boolean isUserAuthorized(Object request);
	// boolean isUserAuthorized();
	// List<Long> getRequiredApplications();
	// void setRequiredApplications( List<Long> requiredApplications );
}