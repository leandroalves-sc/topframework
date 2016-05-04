package com.topsoft.topframework.swing.event;

import javax.swing.JComponent;

public class LazPathFieldEvent extends LazEvent<LazPathFieldListener>{

	private static final long serialVersionUID = 6928420751420109027L;

	public static final int CONNECTING   = 1;
	public static final int CONNECTED    = 2;
	public static final int DISCONNECTED = 3;
	
	public LazPathFieldEvent( JComponent source, int id ){
		
		super( source, id );
	}
	
	public String paramString() {

		String typeStr;

		switch( id ){

			case CONNECTING:
				typeStr = "CONNECTING";
			break;
			
			case CONNECTED:
				typeStr = "CONNECTED";
			break;

			case DISCONNECTED:
				typeStr = "DISCONNECTED";
			break;
			
			default:
				typeStr = "unknown type";
		}

		return typeStr;
	}
	
	@Override
	public void dispatchEvent( LazPathFieldListener listener ){
		
		switch( getID() ){
			case CONNECTING:
				listener.onConnecting( this );
			break;
			
			case CONNECTED:
				listener.onConnected( this );
			break;			
			
			case DISCONNECTED:
				listener.onDisconnected( this );
			break;			
		}
	}
}