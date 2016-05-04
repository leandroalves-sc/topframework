package com.topsoft.topframework.swing.lov;

import com.topsoft.topframework.swing.LazSearchInput;

public interface LazLov<T extends Object> {

	public void openLov(LazSearchInput<T> caller);

	public T searchByCode(Object code);
}
