package com.topsoft.topframework.swing.event;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;

public class LazMenuBoxEvent extends AWTEvent {

	private static final long serialVersionUID = 6928420751420109027L;

	public static final int MENU_BOX_ITEM_CLICKED = 1;

    String actionCommand;
    long when;
    int modifiers;

    public LazMenuBoxEvent( Object source, int id, String command ){
        this( source, id, command, 0 );
    }

    public LazMenuBoxEvent( Object source, int id, String command, int modifiers ){
        this( source, id, command, 0, modifiers );
    }

    public LazMenuBoxEvent( Object source, int id, String command, long when, int modifiers ){
        
    	super(source, id);
        
    	this.actionCommand = command;
        this.when = when;
        this.modifiers = modifiers;
    }
        
    public String getActionCommand() {
        return actionCommand;
    }

    public long getWhen() {
        return when;
    }

    public int getModifiers() {
        return modifiers;
    }

    public String paramString() {
        
    	String typeStr;
        
    	switch(id) {
          case MENU_BOX_ITEM_CLICKED:
              typeStr = "MENU_BOX_ITEM_CLICKED";
              break;
          default:
              typeStr = "unknown type";
        }
    	
        return typeStr + ",cmd="+actionCommand+",when="+when+",modifiers="+KeyEvent.getKeyModifiersText(modifiers);
    }
}