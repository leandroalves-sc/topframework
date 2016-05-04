package com.topsoft.topframework.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;

import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.base.validator.impl.DoubleValidator;
import com.topsoft.topframework.base.validator.impl.IntegerValidator;

public class SystemUtil {

	public static String NVL(Object value) {

		if (value == null)
			return "";

		if (value.toString().endsWith("\n")) {

			value = value.toString().substring(0, value.toString().length() - 1);
		}

		return value.toString().trim();
	}

	public static String getHostName() {

		try {

			return NVL(InetAddress.getLocalHost().getHostName()).toUpperCase();
		}
		catch (UnknownHostException e) {
			return "";
		}
	}

	public static boolean isDoubleOk(Object str) {
		return Validator.use(DoubleValidator.class).isValid(str);
	}

	public static boolean isIntOk(Object str) {
		return Validator.use(IntegerValidator.class).isValid(str);
	}

	public static java.util.Date truncDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
}