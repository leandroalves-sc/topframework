package com.topsoft.topframework.swing.model;

import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerDateModel;

public class LazSpinnerDateModel<T extends Date> extends SpinnerDateModel implements LazSpinnerModel<T> {

	private static final long serialVersionUID = -7706788831837948979L;

	public LazSpinnerDateModel(Date value, Comparable<?> start, Comparable<?> end, int calendarField) {
		super(value, start, end, calendarField);
	}

	public LazSpinnerDateModel() {
		this(new Date(), null, null, Calendar.DAY_OF_MONTH);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getNextValue() {
		return (T) super.getNextValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getPreviousValue() {
		return (T) super.getPreviousValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) super.getValue();
	}
}