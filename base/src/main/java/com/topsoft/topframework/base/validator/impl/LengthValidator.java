package com.topsoft.topframework.base.validator.impl;

import com.topsoft.topframework.base.validator.Validator;

public class LengthValidator extends Validator {

	private Integer minLength;
	private Integer maxLength;

	public LengthValidator min(Integer minLength) {
		this.minLength = minLength;
		return this;
	}

	public LengthValidator max(Integer maxLength) {
		this.maxLength = maxLength;
		return this;
	}

	@Override
	public boolean isValid(Object obValue) {

		if (obValue == null)
			return minLength == null;

		int intValue = obValue.toString().length();

		if (minLength != null && intValue < minLength)
			return false;
		else if (maxLength != null && intValue > maxLength)
			return false;

		return true;
	}

	public String getErrorMessage() {

		if (minLength != null && maxLength != null)
			return String.format("Field needs to have between %d and %d characters", minLength, maxLength);
		else if (maxLength == null)
			return String.format("Field needs to have a mininum of %d characters", minLength);

		return String.format("Field needs to have a maximum of %d characters", maxLength);
	}
}
