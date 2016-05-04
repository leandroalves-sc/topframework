package com.topsoft.topframework.base.mail.domain;

import java.io.InputStream;

public class Attachment {

    private String name;
    private InputStream input;

	public Attachment(){}
	
	public Attachment( String name, InputStream input ){
		
		this.name = name;
		this.input = input;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}
}