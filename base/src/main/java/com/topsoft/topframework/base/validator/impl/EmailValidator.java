package com.topsoft.topframework.base.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.topsoft.topframework.base.validator.Validator;

public class EmailValidator extends Validator {

	@Override
	public boolean isValid(Object email) {

		if (email == null)
			return false;

		Pattern padrao = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher pesquisa = padrao.matcher(email.toString());

		return pesquisa.matches();
	}

	public String getErrorMessage() {
		return "Required to select an option";
	}
}