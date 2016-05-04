package com.topsoft.topframework.swing;

import java.awt.Container;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.utils.SwingUtils;

public class LazButtonGroup extends WebButtonGroup {

	private static final long serialVersionUID = 6195161984586942878L;

	public LazButtonGroup() {
		super();
	}

	public static ButtonGroup groupButtons(Container container) {
		return SwingUtils.groupButtons(container);
	}

	public static ButtonGroup groupButtons(AbstractButton... buttons) {
		return SwingUtils.groupButtons(buttons);
	}
}