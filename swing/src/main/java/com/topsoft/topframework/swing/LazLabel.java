package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.plaf.LabelUI;

import com.alee.laf.label.WebLabel;
import com.alee.managers.tooltip.TooltipManager;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.LazSwingUtils;

public class LazLabel extends WebLabel implements MouseListener, FocusListener {

	private static final long serialVersionUID = 1034354903755642360L;

	private boolean listenerAdded;
	private boolean buttonMode;

	public LazLabel() {
		super();
	}

	public LazLabel(String text) {
		super(text);
	}

	public LazLabel(String text, Font font) {

		super(text);
		setFont(font);
	}

	public LazLabel(String text, Font font, Color foregroundColor) {

		super(text);

		setFont(font);
		setForeground(foregroundColor);
	}

	public LazLabel(String text, Color foregroundColor) {

		super(text);

		setForeground(foregroundColor);
	}

	public LazLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public LazLabel(String text, int horizontalAlignment, int verticalAlignment) {

		super(text, horizontalAlignment);

		setVerticalAlignment(verticalAlignment);
	}

	public LazLabel(Icon icon) {
		this(null, icon, null, null);
	}

	public LazLabel(LazImage image) {
		this(null, image.getIcon(), null, null);
	}

	public LazLabel(LazImage image, String tooltip, ActionListener listener) {
		this(null, image.getIcon(), tooltip, listener);
	}

	public LazLabel(LazImage image, ActionListener listener) {
		this(null, image.getIcon(), null, listener);
	}

	public LazLabel(String text, Icon icon, String tooltip, ActionListener listener) {

		if (text != null)
			setText(text);

		if (icon != null)
			setIcon(icon);

		if (tooltip != null)
			TooltipManager.addTooltip(this, tooltip);

		if (icon != null && text == null)
			buttonMode = true;

		if (listener != null)
			addActionListener(listener);

		setButtonMode(buttonMode);
	}

	@Override
	public void setUI(LabelUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
		setForeground(Color.black);

		addFocusListener(this);
	}

	public void setOpaqueBackground(Color bg) {
		setBackground(bg);
		setOpaque(true);
	}

	public void setButtonMode(boolean buttonMode) {

		this.buttonMode = buttonMode;

		if (buttonMode && !listenerAdded)
			addMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		if (buttonMode && isEnabled())
			LazSwingUtils.showHandCursor(this);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		LazSwingUtils.showDefaultCursor(this);
	}

	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	public ActionListener[] getActionListeners() {
		return (ActionListener[]) (listenerList.getListeners(ActionListener.class));
	}

	@Override
	public void focusGained(FocusEvent e) {
		transferFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (isEnabled())
			for (ActionListener listener : getActionListeners())
				listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "IMAGE_CLICK"));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
}