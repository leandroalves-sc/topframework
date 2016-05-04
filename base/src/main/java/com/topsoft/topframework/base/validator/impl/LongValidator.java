package com.topsoft.topframework.base.validator.impl;

import com.topsoft.topframework.base.validator.Validator;

public class LongValidator extends Validator {

	private boolean required;
	private Long minValue;
	private Long maxValue;

	public LongValidator min(Long minValue) {
		this.minValue = minValue;
		return this;
	}

	public LongValidator max(Long maxValue) {
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

			long longValue = Long.parseLong(obValue.toString());

			if (minValue != null && longValue < minValue)
				return false;
			else if (maxValue != null && longValue > maxValue)
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
