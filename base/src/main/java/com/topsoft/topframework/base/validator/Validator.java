package com.topsoft.topframework.base.validator;

public abstract class Validator {

	public static <T extends Validator> T use(Class<T> c) {

		try {

			return c.newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public abstract boolean isValid(Object value);

	public abstract String getErrorMessage();
}