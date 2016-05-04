package com.topsoft.topframework.base.util;

import javax.swing.ImageIcon;

public enum LazImage {

	OK,
	ADD,
	EDIT,
	REMOVE,
	CANCEL,
	SAVE,

	SEARCH,
	REFRESH,

	CLEAR_LOV,
	LOV,

	FIRST_PAGE("images/paging/bt-first.png"),
	LAST_PAGE("images/paging/bt-last.png"),
	NEXT_PAGE("images/paging/bt-next.png"),
	PREV_PAGE("images/paging/bt-previous.png"),
	
	MINIMIZE("images/window/bt-minimize.png"),
	MAXIMIZE("images/window/bt-maximize.png"),
	RESTORE("images/window/bt-restore.png"),
	CLOSE("images/window/bt-close.png"),

	MOVE,
	LEFT,
	RIGHT,
	UP,
	DOWN,

	DATABASE_TEST,
	RUN,
	RUNNING,

	CONNECT,
	DISCONNECT,

	HELP,
	SETTINGS,

	ON,
	OFF,

	LOG,

	DATE,

	GREEN,
	RED,
	ORANGE,
	YELLOW;

	private String imageSource;
	private ImageIcon icon;

	LazImage() {

		this.imageSource = "images/button/bt-" + name().toLowerCase() + ".png";
		this.setIcon(imageSource);
	}

	LazImage(String imageSource) {

		this.imageSource = imageSource;
		this.setIcon(imageSource);
	}

	public void setIcon(String imageSource) {
		
		this.icon = new ImageIcon(getClass().getClassLoader().getResource(imageSource)) {

			private static final long serialVersionUID = -5308479047044186149L;

			@Override
			public boolean equals(Object obj) {

				if (obj != null)
					return toString().equals(obj.toString());

				return super.equals(obj);
			}
		};
	}

	public ImageIcon getIcon() {
		return icon;
	}
}