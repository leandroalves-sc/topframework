package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;

import com.alee.laf.text.WebPasswordField;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazPasswordField extends WebPasswordField implements FocusListener, DocumentListener, Validatable, LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = 3421144695724933224L;

	private List<Validator> validators;
	private boolean isValid = true, required;

	public LazPasswordField() {

		super();
		getDocument().addDocumentListener(this);
	}

	public LazPasswordField(String pass) {

		this();
		setText(pass);
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
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
	public void setText(String t) {
		super.setText(t);
		validateData();
	}

	public String getPasswordText() {
		return new String(getPassword());
	}

	@Override
	public List<Validator> getValidators() {
		return validators;
	}

	@Override
	public void validateData() {
		isValid = ValidatorUtil.validate(this);
		repaint();
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
	public void setUI(TextUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
		setSelectionColor(SystemColor.activeCaption);
		setSelectedTextColor(SystemColor.activeCaptionText);
		setMargin(0, 0, 0, 2);

		addFocusListener(this);
	}

	@Override
	public void setEditable(boolean b) {

		super.setEditable(b);

		if (getDocument() != null)
			validateData();

		if (b == false && hasFocus())
			transferFocus();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (!isValid && isEditable() && isEnabled()) {

			Insets margin = getMargin();

			g.setColor(Color.RED);
			g.drawRect(margin.left + getShadeWidth(), margin.top + getShadeWidth(), getWidth() - margin.left - margin.right - getShadeWidth() - 1, getHeight() - getShadeWidth() * 2 - 1 - margin.top - margin.bottom);
		}

		setBackground(!isEditable() || !isEnabled() ? UIManager.getColor("TextField.inactiveBackground") : UIManager.getColor("TextField.background"));
		setDrawFocus(isValid && hasFocus());
		setFocusable(isEditable() && isEnabled());
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
}
