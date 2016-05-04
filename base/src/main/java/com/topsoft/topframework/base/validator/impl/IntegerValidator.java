package com.topsoft.topframework.base.validator.impl;

import com.topsoft.topframework.base.validator.Validator;

public class IntegerValidator extends Validator {

	private boolean required;
	private Integer minValue;
	private Integer maxValue;

	public IntegerValidator min(Integer minValue) {
		this.minValue = minValue;
		return this;
	}

	public IntegerValidator max(Integer maxValue) {
		this.maxValue = maxValue;
		return this;
	}

	@Override
	public boolean isValid(Object obValue) {

		if (obValue == null)
			return minValue == null;

		if (!required && (obValue == null || obValue.toString().length() == 0))
			return true;

		try {

			int intValue = Integer.parseInt(obValue.toString());

			if (minValue != null && intValue < minValue)
				return false;
			else if (maxValue != null && intValue > maxValue)
				return false;
		}
		catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	public String getErrorMessage() {

		if (minValue != null && maxValue != null)
			return String.format("Value needs to be between %d and %d", minValue, maxValue);
		else if (maxValue == null)
			return String.format("Values needs to be equal or greater than %d", minValue);

		return String.format("Value needs to be equals or less than %d", maxValue);
	}
}
