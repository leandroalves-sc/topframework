package com.topsoft.topframework.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.plaf.ButtonUI;

import com.alee.laf.radiobutton.WebRadioButton;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;

public class LazRadioButton extends WebRadioButton implements LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = 5472540314408993620L;

	public LazRadioButton() {
		super();
	}

	public LazRadioButton(String text) {
		this(text, false, null);
	}

	public LazRadioButton(String text, boolean selected) {
		this(text, selected, null);
	}

	public LazRadioButton(String text, boolean selected, String actionCommand) {

		this();

		setText(text);
		setSelected(selected);
		setActionCommand(actionCommand);
	}

	@Override
	public void setSelected(boolean b) {

		super.setSelected(b);
		fireActionEvent();
	}

	public void fireActionEvent() {

		ActionEvent action = new ActionEvent(this, 0, "");

		for (ActionListener listener : getActionListeners())
			listener.actionPerformed(action);
	}

	@Override
	public void setUI(ButtonUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
	}
}