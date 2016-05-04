package com.topsoft.topframework.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class ObjectUtils {

	private static final String NESTED_OPERATOR = ".";
	private static final String ARRAY_OPERATOR = "#";

	@SuppressWarnings("unchecked")
	public static Object getNestedValue(Object dto, String property) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		if (property.indexOf(NESTED_OPERATOR) != -1) {

			String methodName = property.substring(0, property.indexOf(NESTED_OPERATOR));
			property = property.substring(property.indexOf(NESTED_OPERATOR) + 1, property.length());

			if (methodName.contains(ARRAY_OPERATOR)) {

				int indexList = Integer.parseInt(methodName.substring(methodName.indexOf(ARRAY_OPERATOR) + 1, methodName.length()));
				methodName = methodName.substring(0, methodName.indexOf(ARRAY_OPERATOR));

				List<Object> list = (List<Object>) MethodUtils.invokeMethod(dto, "get" + StringUtils.capitalize(methodName));

				if (list.size() > indexList)
					return getNestedValue(list.get(indexList), property);
				else
					return null;
			}
			else {

				Object obj = MethodUtils.invokeMethod(dto, "get" + StringUtils.capitalize(methodName));

				if (obj != null)
					return getNestedValue(initializeAndUnproxy(obj), property);
			}
		}
		else {

			if ("toString".equals(property) && dto != null)
				return dto.toString();
			else {

				String methodName = getFullMethodName(dto, property);

				if (methodName != null)
					return MethodUtils.invokeMethod(dto, methodName);
			}
		}

		return null;
	}

	private static String getFullMethodName(Object dto, String property) {

		String[] prefixs = new String[] { "get", "is", "has" };

		for (Method method : dto.getClass().getMethods())
			for (String prefix : prefixs)
				if (method.getName().equals(prefix + StringUtils.capitalize(property)))
					return prefix + StringUtils.capitalize(property);

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {

		if (entity != null && entity instanceof HibernateProxy) {

			Hibernate.initialize(entity);
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}

		return entity;
	}
}
