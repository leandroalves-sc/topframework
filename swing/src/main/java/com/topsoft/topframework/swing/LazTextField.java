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
import javax.swing.text.Document;

import com.alee.laf.text.WebTextField;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.LazSwingUtils;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazTextField extends WebTextField implements FocusListener, DocumentListener, Validatable, LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = 4431868719864996018L;

	private List<Validator> validators;
	private boolean isValid = true, required;

	public LazTextField() {
		this("", null);
	}

	public LazTextField(String text) {
		this(text, null);
	}

	public LazTextField(String text, Document doc) {

		super();

		if (doc != null)
			setDocument(doc);
		else
			getDocument().addDocumentListener(this);

		setText(text);
	}

	@Override
	public void setDocument(Document doc) {

		super.setDocument(doc);
		doc.addDocumentListener(this);
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

	@Override
	public void setVisible(boolean visible) {

		LazPanel panel = LazSwingUtils.getParent(this, LazPanel.class);

		if (panel != null)
			panel.setLabelVisible(this, visible);

		super.setVisible(visible);
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