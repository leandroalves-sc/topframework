package com.topsoft.topframework.swing.event;

import java.util.EventListener;

public interface LazActionDispatcher<T extends EventListener> {

	public void addActionListener(T listener);

	public void removeActionListener(T listener);

	public T[] getActionListeners();
}