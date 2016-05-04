package com.topsoft.topframework.base.validator.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.topsoft.topframework.base.validator.Validator;

public class HostnameValidator extends Validator {

	private Pattern ipPattern, hostnamePattern;

	private static final String IP_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
	private static final String HOSTNAME_PATTERN = "(?=^.{1,253}$)(^(((?!-)[a-zA-Z0-9-]{1,63}(?<!-))|((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,63})$)";

	public HostnameValidator() {

		ipPattern = Pattern.compile(IP_PATTERN);
		hostnamePattern = Pattern.compile(HOSTNAME_PATTERN);
	}

	@Override
	public boolean isValid(Object obj) {

		if (obj == null)
			return false;

		String text = obj.toString();

		if (StringUtils.isNumeric(text.replace(".", ""))) {

			if (ipPattern.matcher(obj.toString()).matches())
				return true;
		}
		else if (hostnamePattern.matcher(obj.toString()).matches())
			return true;

		return false;
	}

	public String getErrorMessage() {
		return "Invalid hostname or IP";
	}
}