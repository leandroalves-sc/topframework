package com.topsoft.topframework.swing.model;

import javax.swing.SpinnerNumberModel;

import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.base.validator.impl.IntegerValidator;

public class LazSpinnerNumberModel<T extends Number> extends SpinnerNumberModel implements LazSpinnerModel<T> {

	private static final long serialVersionUID = -3554892291969396923L;

	private boolean circular;

	public LazSpinnerNumberModel(Integer value, Integer minimum, Integer maximum, Integer stepSize) {
		this(value, minimum, maximum, stepSize, false);
	}

	public LazSpinnerNumberModel(Integer value, Integer minimum, Integer maximum, Integer stepSize, boolean circular) {
		super(value, minimum, maximum, stepSize);

		this.circular = circular;
	}

	@Override
	public void setValue(Object value) {

		if (value != null && String.class.isAssignableFrom(value.getClass()) && Validator.use(IntegerValidator.class).isValid(value.toString()))
			super.setValue(Integer.parseInt(value.toString()));
		else
			super.setValue(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) super.getValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getNextValue() {

		if (circular && getMaximum().compareTo(getValue()) == 0)
			return (T) getMinimum();

		return (T) super.getNextValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getPreviousValue() {

		if (circular && getMinimum().compareTo(getValue()) == 0)
			return (T) getMaximum();

		return (T) super.getPreviousValue();
	}

	public boolean isCircular() {
		return circular;
	}

	public void setCircular(boolean circular) {
		this.circular = circular;
	}
}