package com.topsoft.topframework.swing.event;

import javax.swing.JComponent;

public class LazSearchInputEvent extends LazEvent<LazSearchInputListener>{

	private static final long serialVersionUID = 6928420751420109027L;

	public static final int ITEM_FOUND    = 1;
	public static final int ITEM_ADDED    = 2;
	public static final int ITEM_REMOVED  = 3;
	public static final int ITEM_CLEARED  = 4;
	
	public LazSearchInputEvent( JComponent comp, int id ){
		super( comp, id );
	}
	
	public String paramString() {

		switch( id ){

			case ITEM_FOUND:
				return "ITEM_FOUND";
			case ITEM_ADDED:
				return "ITEM_ADDED";
			case ITEM_REMOVED:
				return "ITEM_REMOVED";
			case ITEM_CLEARED:
				return "ITEM_CLEARED";
			default:
				return "unknown type";
		}
	}
	
	@Override
	public void dispatchEvent( LazSearchInputListener listener ){
		
		switch( getID() ){
		
			case LazSearchInputEvent.ITEM_ADDED:
				listener.itemAdded( this );
			break;
			
			case LazSearchInputEvent.ITEM_CLEARED:
				listener.itemsCleared( this );
			break;
			
			case LazSearchInputEvent.ITEM_FOUND:
				listener.itemFound( this );
			break;
			
			case LazSearchInputEvent.ITEM_REMOVED:
				listener.itemRemoved( this );
			break;			
		}
	}
}