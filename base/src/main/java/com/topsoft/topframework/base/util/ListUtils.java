package com.topsoft.topframework.base.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.topsoft.topframework.base.domain.Entity;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.base.validator.impl.IntegerValidator;

public class ListUtils {

	private static final String OBJECT_SEPARATOR = ".";

	public static <ID extends Serializable, T extends Entity<?>> List<ID> listOf(List<T> list, String nestedAttribute, Class<ID> type) {
		return listOf(list, nestedAttribute, type, false);
	}

	public static <ID extends Serializable, T extends Entity<?>> List<ID> listOf(List<T> list, String nestedAttribute, Class<ID> type, boolean includeNull) {

		List<ID> retorno = new ArrayList<ID>();

		try {

			for (T dto : list) {

				ID id = getNestedValue(type, dto, nestedAttribute);

				if (id != null || includeNull)
					retorno.add(id);
			}
		}
		catch (Exception e) {
			return null;
		}

		return retorno;
	}

	@SuppressWarnings("unchecked")
	private static <ID extends Serializable, T extends Entity<?>> ID getNestedValue(Class<ID> type, T dto, String attribute) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, InvocationTargetException, InstantiationException {

		if (attribute.indexOf(OBJECT_SEPARATOR) != -1) {

			String dtoClass = attribute.substring(0, attribute.indexOf(OBJECT_SEPARATOR));
			Field field = getField(dto, dtoClass);

			if (field != null) {

				dtoClass = field.getName();

				Method methodGet = dto.getClass().getMethod("get" + Character.toUpperCase(dtoClass.charAt(0)) + dtoClass.substring(1, dtoClass.length()));
				T innerDTO = (T) methodGet.invoke(dto);

				if (innerDTO == null)
					return null;

				return getNestedValue(type, innerDTO, attribute.substring(attribute.indexOf(OBJECT_SEPARATOR) + 1, attribute.length()));
			}
		}
		else {

			Method methodGet = dto.getClass().getMethod("get" + Character.toUpperCase(attribute.charAt(0)) + attribute.substring(1, attribute.length()));

			Object ob = methodGet.invoke(dto);

			if (ob != null && ob instanceof String && type.getName().equals(Integer.class.getName()) && Validator.use(IntegerValidator.class).isValid(ob))
				ob = new Integer(ob.toString().trim());

			return (ID) ob;
		}

		return null;
	}

	private static <T extends Entity<?>> Field getField(T dto, String attribute) {

		for (Class<?> classe = dto.getClass(); classe != null; classe = classe.getSuperclass())
			for (Field field : classe.getDeclaredFields())
				if (!Modifier.isFinal(field.getModifiers()) && field.getName().equalsIgnoreCase(attribute))
					return field;

		return null;
	}
}
