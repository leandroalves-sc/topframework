package com.topsoft.topframework.swing.event;

import java.util.EventListener;

public interface LazSearchInputListener extends EventListener{
	
	public void itemFound( LazSearchInputEvent event );
	public void itemAdded( LazSearchInputEvent event );
	public void itemRemoved( LazSearchInputEvent event );
	public void itemsCleared( LazSearchInputEvent event );
}