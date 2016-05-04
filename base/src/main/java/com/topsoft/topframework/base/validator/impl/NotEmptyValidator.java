package com.topsoft.topframework.base.validator.impl;

import java.util.List;

import com.topsoft.topframework.base.validator.Validator;

public class NotEmptyValidator extends Validator {

	@Override
	public boolean isValid(Object obj) {

		if (obj == null)
			return false;

		if (List.class.isAssignableFrom(obj.getClass())) {

			List<?> list = (List<?>) obj;
			return !list.isEmpty();
		}

		return obj.toString().length() > 0;
	}

	public String getErrorMessage() {
		return "Required field";
	}
}