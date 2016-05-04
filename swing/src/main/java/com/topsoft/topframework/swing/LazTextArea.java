package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;

import com.alee.laf.text.WebTextArea;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.LazSwingUtils;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazTextArea extends WebTextArea implements FocusListener, DocumentListener, Validatable {

	private static final long serialVersionUID = 6208002417108162752L;

	private List<Validator> validators;
	private boolean isValid = true;
	private boolean required;

	public LazTextArea() {

		super();

		getDocument().addDocumentListener(this);
	}

	@Override
	public void setUI(TextUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
	}

	@Override
	public void focusGained(FocusEvent e) {
		selectAll();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		validateData();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		validateData();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		validateData();
	}

	@Override
	public void addValidators(Validator... validators) {

		if (this.validators == null)
			this.validators = new ArrayList<Validator>();

		for (Validator validator : validators)
			this.validators.add(validator);

		validateData();
	}

	@Override
	public List<Validator> getValidators() {
		return validators;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
		validateData();
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void validateData() {
		isValid = ValidatorUtil.validate(this);
		repaint();
	}

	@Override
	protected void paintBorder(Graphics g) {

		super.paintBorder(g);

		if (!isValid)
			paintInvalidTable(g);

		setFocusable(isEnabled());
	}

	private void paintInvalidTable(Graphics g) {

		JScrollPane scroll = LazSwingUtils.getParent(getParent(), JScrollPane.class);

		if (scroll != null) {

			g = scroll.getGraphics();

			Insets margin = scroll.getInsets();

			g.setColor(Color.RED);
			g.drawRect(margin.left - 1, margin.top - 1, scroll.getWidth() - margin.left - margin.right + 1, scroll.getHeight() - margin.top - margin.bottom + 1);
			g.setColor(UIManager.getColor("Table.gridColor"));

			if (scroll instanceof LazScrollPane)
				((LazScrollPane) scroll).setDrawFocus(isValid && hasFocus());

			scroll.repaint();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
}