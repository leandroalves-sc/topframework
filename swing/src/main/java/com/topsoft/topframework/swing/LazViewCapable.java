package com.topsoft.topframework.swing;

import java.awt.Dimension;

public interface LazViewCapable<T extends Object> {

	public String getTitle();

	public Dimension getSize();

	public boolean isResizable();
}
