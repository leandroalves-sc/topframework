package com.topsoft.topframework.base.validator.impl;

import com.topsoft.topframework.base.validator.Validator;

public class NoSpaceValidator extends Validator {

	@Override
	public boolean isValid(Object obj) {

		if (obj == null)
			return false;

		return !obj.toString().contains(" ");
	}

	public String getErrorMessage() {
		return "Field could not have space(s)";
	}
}