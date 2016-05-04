package com.topsoft.topframework.swing;

import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.plaf.ButtonUI;

import com.alee.laf.button.WebButton;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;

public class LazButton extends WebButton implements LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = -3808620790813617292L;

	private LazImage image;
	private LazButtonType type;

	public LazButton() {
		super();
	}

	public LazButton(String text) {
		this(null, text);
	}

	public LazButton(LazButtonType type) {

		this(type.getImage(), type.getText());

		this.type = type;
	}

	public LazButton(LazImage image) {
		this(image, "");
	}

	public LazButton(LazImage image, String text) {

		this();

		setText(text);

		this.image = image;
	}

	@Override
	public void setUI(ButtonUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
		setIconTextGap(10);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled((getType() != null && (getType() == LazButtonType.CANCEL || getType() == LazButtonType.OK)) || enabled);
	}

	public void setType(LazButtonType type) {

		this.type = type;
		this.image = type.getImage();

		repaint();
	}

	public LazButtonType getType() {
		return type;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (image != null) {

			setIcon(image.getIcon());
			setIconTextGap(10);
		}
	}
}