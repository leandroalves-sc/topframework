package com.topsoft.topframework.swing.model;

import javax.swing.SpinnerModel;

public interface LazSpinnerModel<T extends Object> extends SpinnerModel {

	public T getValue();

	public T getNextValue();

	public T getPreviousValue();
}