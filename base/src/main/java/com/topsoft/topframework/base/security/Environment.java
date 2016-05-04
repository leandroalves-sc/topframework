package com.topsoft.topframework.base.security;

import java.util.Properties;

public enum Environment{
	
	PROD,
	DEV,
	QA;
	
	private Properties properties;
	
	Environment(){
		this.properties = new Properties();
	}
	
	public boolean isProduction(){
		return this == PROD;
	}
	
	public boolean isDevelopment(){
		return this == DEV;
	}
	
	public boolean isQA(){
		return this == QA;
	}	
	
	public String getProperty( String key ){
		return properties.getProperty( key );
	}
	
	public Object addProperty( String key, String value ){
		return properties.put( key, value );
	}
	
	public Properties getProperties(){
		return properties;
	}
}