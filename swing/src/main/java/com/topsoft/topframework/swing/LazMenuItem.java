package com.topsoft.topframework.swing;

import javax.swing.Icon;

import com.alee.laf.menu.WebMenuItem;
import com.topsoft.topframework.base.util.LazImage;

public class LazMenuItem extends WebMenuItem {

	private static final long serialVersionUID = 1850499146802537877L;

	public LazMenuItem() {
		super();
	}

	public LazMenuItem(String text) {
		super(text);
	}

	public LazMenuItem(String text, LazImage image) {
		super(text, image.getIcon());
	}

	public LazMenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public LazMenuItem(String text, char mnemonic) {
		super(text, mnemonic);
	}
}
