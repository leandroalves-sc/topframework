package com.topsoft.topframework.swing;

import java.util.List;

public abstract class LazFormList<T extends Object> extends LazForm<List<T>> {

	private static final long serialVersionUID = 7636850848211695691L;

	@Override
	public LazButtonType[] getButtons() {
		return null;
	}

	@Override
	protected void saveForm() {
	}
}