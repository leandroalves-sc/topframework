package com.topsoft.topframework.swing.event;

import javax.swing.JComponent;

public class LazPageEvent extends LazEvent<LazPageListener>{

	private static final long serialVersionUID = 6928420751420109027L;

	public static final int PAGE_CHANGED = 1;

	private int page;
    private int pageSize;

	public LazPageEvent( JComponent comp, int id, int page, int pageSize ){

		super( comp, id );

		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage(){
		return page;
	}
	
	public int getPageSize(){
		return pageSize;
	}
	
	public String paramString(){

		String typeStr;

		switch( id ){

			case PAGE_CHANGED:
				typeStr = "PAGE_CHANGED";
			break;
	
			default:
				typeStr = "unknown type";
		}

		return typeStr + ",page=" + page + ",pageSize=" + pageSize;
	}
	
	@Override
	public void dispatchEvent( LazPageListener listener ){
		listener.pageEvent( this );
	}
}