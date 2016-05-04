package com.topsoft.topframework.base.validator.impl;

import com.topsoft.topframework.base.validator.Validator;

public class NotNullValidator extends Validator {

	@Override
	public boolean isValid(Object obj) {
		return obj != null;
	}

	public String getErrorMessage() {
		return "Required to select an option";
	}
}