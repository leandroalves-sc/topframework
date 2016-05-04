package com.topsoft.topframework.swing;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.plaf.ButtonUI;

import com.alee.laf.button.WebToggleButton;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;

public class LazToggleButton extends WebToggleButton implements LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = -3808620790813617292L;

	public LazToggleButton() {
		super();
	}

	public LazToggleButton(String text) {
		this(null, text, false, null);
	}

	public LazToggleButton(LazImage image, String text) {
		this(image.getIcon(), text, false, null);
	}

	public LazToggleButton(String text, boolean selected) {
		this(null, text, selected, null);
	}

	public LazToggleButton(String text, boolean selected, String actionCommand) {
		this(null, text, selected, actionCommand);
	}

	public LazToggleButton(Icon icon, String text, boolean selected, String actionCommand) {

		super(text, icon, selected);

		if (actionCommand != null)
			setActionCommand(actionCommand);
	}

	@Override
	public void setUI(ButtonUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
		setIconTextGap(10);
	}
}