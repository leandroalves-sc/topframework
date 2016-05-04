package com.topsoft.topframework.base.validator.impl;

import com.topsoft.topframework.base.validator.Validator;

public class DoubleValidator extends Validator {

	private boolean required;
	private Double minValue;
	private Double maxValue;

	public DoubleValidator min(Double minValue) {
		this.minValue = minValue;
		return this;
	}

	public DoubleValidator max(Double maxValue) {
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

			double doubleValue = Double.parseDouble(obValue.toString());

			if (minValue != null && doubleValue < minValue)
				return false;
			else if (maxValue != null && doubleValue > maxValue)
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
