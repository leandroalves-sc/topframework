package com.topsoft.topframework.swing.event;

import java.util.EventListener;

import com.topsoft.topframework.base.exception.BusinessException;

public interface LazFormListener extends EventListener {

	public void onLoad(LazFormEvent event);

	public void onBeforeSave(LazFormEvent event) throws BusinessException;

	public void onSave(LazFormEvent event);
}
