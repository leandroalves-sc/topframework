package com.topsoft.topframework.base.validator.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.topsoft.topframework.base.validator.Validator;

public class DateValidator extends Validator {

	private Date minDate;
	private Date maxDate;

	public DateValidator min(Date minDate) {
		this.minDate = minDate;
		return this;
	}

	public DateValidator max(Date maxDate) {
		this.maxDate = maxDate;
		return this;
	}

	@Override
	public boolean isValid(Object strDate) {

		if (strDate == null)
			return false;

		try {

			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(strDate.toString());

			if (minDate != null && date.before(minDate))
				return false;
			else if (maxDate != null && date.after(maxDate))
				return false;

			return true;
		}
		catch (ParseException e) {
			return false;
		}
	}

	public String getErrorMessage() {
		return "Invalid date";
	}
}