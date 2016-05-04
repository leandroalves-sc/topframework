package com.topsoft.topframework.base.security;

import org.springframework.security.core.userdetails.User;

public interface SecurityContext {

	Environment getEnvironment();
	User getUser();
}