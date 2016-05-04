package com.topsoft.topframework.swing;

import java.io.File;

import com.alee.extended.filechooser.WebFileChooserField;

public class LazFileChooserField extends WebFileChooserField{

	private static final long serialVersionUID = -6442586962539576866L;

	public LazFileChooserField(){
		
		super();
		
		setSelectedFile( File.listRoots()[0] );
		setMultiSelectionEnabled( false );
		setShowFileShortName( false );
		setShowRemoveButton( false );
	}
}