package com.topsoft.topframework.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.ButtonUI;

import com.alee.laf.checkbox.WebCheckBox;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.LazSwingUtils;

public class LazCheckBox extends WebCheckBox implements LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = 6208002417108162752L;

	public LazCheckBox() {
		super();
	}

	public LazCheckBox(String text) {
		this(text, null);
	}

	public LazCheckBox(String text, String actionCommand) {

		this();

		setText(text);
		setActionCommand(actionCommand);
	}

	@Override
	public void setUI(ButtonUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
	}

	@Override
	public void setSelected(boolean flag) {

		super.setSelected(flag);
		fireActionEvent();
	}

	public void fireActionEvent() {

		ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "CHECK_BOX_CLICKED");

		for (ActionListener listener : getActionListeners())
			listener.actionPerformed(action);
	}

	public void setSelectedQuiet(boolean flag) {

		List<ActionListener> listeners = new ArrayList<ActionListener>(Arrays.asList(getActionListeners()));

		for (ActionListener l : getActionListeners())
			removeActionListener(l);

		super.setSelected(flag);

		for (ActionListener l : listeners)
			addActionListener(l);
	}

	@Override
	public void setVisible(boolean visible) {

		LazPanel panel = LazSwingUtils.getParent(this, LazPanel.class);

		if (panel != null)
			panel.setLabelVisible(this, visible);

		super.setVisible(visible);
	}
}