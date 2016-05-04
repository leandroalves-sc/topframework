package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.panel.WebPanel;
import com.topsoft.topframework.base.util.SystemUtil;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazPageEvent;
import com.topsoft.topframework.swing.event.LazPageListener;
import com.topsoft.topframework.swing.event.LazSearchInputEvent;
import com.topsoft.topframework.swing.event.LazSearchInputListener;
import com.topsoft.topframework.swing.event.LazTableEvent;
import com.topsoft.topframework.swing.event.LazTableListener;
import com.topsoft.topframework.swing.util.LazSwingConstants;
import com.topsoft.topframework.swing.util.LazSwingUtils;

import net.miginfocom.swing.MigLayout;

public class LazPanel extends WebPanel implements ActionListener, ChangeListener, KeyListener, LazTableListener, LazPageListener, LazSearchInputListener {

	private static final long serialVersionUID = -3690089944541017593L;

	private Map<Component, JLabel> labels;

	public LazPanel() {

		super();

		this.labels = new HashMap<Component, JLabel>();
	}

	public LazPanel(Color backgroundColor) {

		this();

		setOpaque(true);
		setBackground(backgroundColor);
	}

	public LazPanel(LayoutManager layout) {

		this();
		setLayout(layout);
	}

	public LazPanel(LayoutManager layout, Color backgroundColor) {

		this(backgroundColor);

		setLayout(layout);
	}

	@Override
	public void setLayout(LayoutManager layout) {

		if (layout instanceof MigLayout) {

			MigLayout mig = (MigLayout) layout;

			String lc = SystemUtil.NVL(mig.getLayoutConstraints());

			lc += (lc.toString().length() > 0 ? "," : "") + " hidemode 3";

			if (LazSwingConstants.DEBUG)
				lc += (lc.toString().length() > 0 ? "," : "") + " debug";

			mig.setLayoutConstraints(lc);
		}

		super.setLayout(layout);
	}

	public void setComponentConstraints(Component comp, Object constraints) {

		if (MigLayout.class.isAssignableFrom(getLayout().getClass())) {

			MigLayout layout = (MigLayout) getLayout();
			layout.setComponentConstraints(comp, constraints);
		}
	}

	public void setRowConstraints(Object constraints) {

		if (MigLayout.class.isAssignableFrom(getLayout().getClass())) {

			MigLayout layout = (MigLayout) getLayout();
			layout.setRowConstraints(constraints);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void addListeners() {

		if (LazActionDispatcher.class.isAssignableFrom(this.getClass()))
			((LazActionDispatcher) this).addActionListener(this);

		for (Component component : LazSwingUtils.getAllComponents(this))
			if (LazActionDispatcher.class.isAssignableFrom(component.getClass()))
				if (!hasEvent(((LazActionDispatcher) component), this))
					((LazActionDispatcher) component).addActionListener(this);
	}

	public boolean hasEvent(LazActionDispatcher<?> dispatcher, EventListener l) {

		for (EventListener listener : dispatcher.getActionListeners())
			if (listener == l)
				return true;

		return false;
	}

	@Override
	public Component add(String labelTitle, Component comp) {

		add(labelTitle, null, comp, null);

		return comp;
	}

	public void add(String labelTitle, Component comp, Object constraints) {
		add(labelTitle, null, comp, constraints);
	}

	public void add(String labelTitle, Object labelConstraints, Component comp) {
		add(labelTitle, labelConstraints, comp, null);
	}

	public void add(String labelTitle, Object labelConstraints, Component comp, Object compConstraints) {

		if (labelTitle != null) {

			LazLabel label = new LazLabel(labelTitle);

			labels.put(comp, label);
			add(label, labelConstraints);
		}

		add(comp, compConstraints);
	}

	public void setLabelVisible(Component comp, boolean visible) {

		JLabel label = labels.get(comp);

		if (label != null)
			label.setVisible(visible);
	}

	@Override
	public void setEnabled(boolean enabled) {
		LazSwingUtils.setEnabled(this, enabled);
	}

	public void addSeparator(String text) {
		LazSwingUtils.addSeparator(this, text);
	}

	public void addOldSeparator(String text) {
		LazSwingUtils.addOldSeparator(this, text);
	}

	public void showHandCursor() {
		LazSwingUtils.showHandCursor(this);
	}

	public void showWaitCursor() {
		LazSwingUtils.showWaitCursor(this);
	}

	public void showDefaultCursor() {
		LazSwingUtils.showDefaultCursor(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void stateChanged(ChangeEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void tableEvent(LazTableEvent e) {
	}

	@Override
	public void pageEvent(LazPageEvent e) {
	}

	@Override
	public void itemFound(LazSearchInputEvent event) {
	}

	@Override
	public void itemAdded(LazSearchInputEvent event) {
	}

	@Override
	public void itemRemoved(LazSearchInputEvent event) {
	}

	@Override
	public void itemsCleared(LazSearchInputEvent event) {
	}
}