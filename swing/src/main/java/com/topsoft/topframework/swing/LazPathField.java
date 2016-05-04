package com.topsoft.topframework.swing;

import java.io.File;
import java.util.List;

import com.alee.extended.filechooser.WebPathField;
import com.alee.utils.FileUtils;

public class LazPathField extends WebPathField{

	private static final long serialVersionUID = -4571602315787231179L;

	public LazPathField(){

		super();

		setFilesDropEnabled( false );
	}
	
	public File[] getDiskRoots(){
		return FileUtils.getDiskRoots();
	}	
	
	public String getFileNames( List<File> files ){
		
		StringBuilder str = new StringBuilder();
		
		if( files != null )
			for( File file : files )
				str.append( file.getName() + ", " );
		
		return str.toString();
	}	
}