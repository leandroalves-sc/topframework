package com.topsoft.topframework.swing.util;

import java.util.Hashtable;

public class LazViewKeeper{
	
    private static Hashtable<Object,Object> viewKeeper;

    public LazViewKeeper(){}

    public static void initializeViewKeeper(){
    	
        viewKeeper = new Hashtable<Object,Object>();
    }

    public static Object getViewFor( Object key ){
    	
    	if( viewKeeper == null )
    		initializeViewKeeper();
        
    	return viewKeeper.get( key );
    }

    public static void addView( Object key, Object view ){
        
    	if( viewKeeper == null )
            initializeViewKeeper();

    	viewKeeper.put( key, view );
    }

    public static void removeView( Object key ){

    	if( viewKeeper == null )
            initializeViewKeeper();

    	viewKeeper.remove( key );
    }

     public static Hashtable<Object,Object> getViews(){
        return viewKeeper;
    }
}