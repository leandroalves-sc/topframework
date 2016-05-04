package com.topsoft.topframework.swing;

import com.topsoft.topframework.base.util.LazImage;

public enum LazButtonType {

	OK("Ok", LazImage.OK),
	ADD("New", LazImage.ADD),
	REMOVE("Remove", LazImage.REMOVE),
	SAVE("Save", LazImage.SAVE),
	SEARCH("Search", LazImage.SEARCH),
	CONNECT("Connect", LazImage.CONNECT),
	DISCONNECT("Disconnect", LazImage.DISCONNECT),
	CANCEL("Cancel", LazImage.CANCEL),
	RUN("Run", LazImage.RUN),
	HELP("Help", LazImage.HELP);

	private String text;
	private LazImage image;

	LazButtonType(String text, LazImage image) {
		this.text = text;
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public LazImage getImage() {
		return image;
	}
}