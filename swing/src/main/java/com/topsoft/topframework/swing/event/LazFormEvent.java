package com.topsoft.topframework.swing.event;

import javax.swing.JComponent;

public class LazFormEvent extends LazEvent<LazFormListener>{

	private static final long serialVersionUID = 6928420751420109027L;

	public static final int LOAD        = 1;
	public static final int BEFORE_SAVE = 2;
	public static final int SAVE        = 3;
	
	public LazFormEvent( JComponent source, int id ){
		
		super( source, id );
	}
	
	public String paramString() {

		String typeStr;

		switch( id ){

			case LOAD:
				typeStr = "LOAD";
			break;
			
			case BEFORE_SAVE:
				typeStr = "BEFORE_SAVE";
			break;

			case SAVE:
				typeStr = "SAVE";
			break;
			
			default:
				typeStr = "unknown type";
		}

		return typeStr;
	}
	
	@Override
	public void dispatchEvent( LazFormListener listener ){
		
		switch( getID() ){
			case LOAD:
				listener.onLoad( this );
			break;
			
			case BEFORE_SAVE:
				listener.onBeforeSave( this );
			break;			
			
			case SAVE:
				listener.onSave( this );
			break;			
		}
	}
}