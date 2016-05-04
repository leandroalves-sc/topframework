package com.topsoft.topframework.swing.event;

import java.util.EventListener;

public interface LazPathFieldListener extends EventListener{
	
	public void onConnecting( LazPathFieldEvent event );
	public void onConnected( LazPathFieldEvent event );
	public void onDisconnected( LazPathFieldEvent event );
}
