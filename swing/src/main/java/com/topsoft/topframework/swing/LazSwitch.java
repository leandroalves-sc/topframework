package com.topsoft.topframework.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.extended.button.WebSwitch;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.util.LazSwingUtils;

public class LazSwitch extends WebSwitch implements LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = 4023388880931342475L;

	public LazSwitch() {

		super();

		getLeftComponent().setText("YES");
		getRightComponent().setText("NO");
	}

	@Override
	public void setSelected(boolean flag) {
		super.setSelected(flag);
		fireActionEvent();
	}

	@Override
	public void setSelected(boolean selected, boolean animate) {
		super.setSelected(selected, animate);
		fireActionEvent();
	}

	@Override
	public void setVisible(boolean visible) {

		LazPanel panel = LazSwingUtils.getParent(this, LazPanel.class);

		if (panel != null)
			panel.setLabelVisible(this, visible);

		super.setVisible(visible);
	}

	public void fireActionEvent() {

		ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SWITCH_STATE_CHANGED");

		for (ActionListener listener : getActionListeners())
			listener.actionPerformed(action);
	}

	@Override
	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	@Override
	public ActionListener[] getActionListeners() {
		return listenerList.getListeners(ActionListener.class);
	}
}