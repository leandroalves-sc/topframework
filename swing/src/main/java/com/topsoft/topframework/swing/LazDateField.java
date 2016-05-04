package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.TextUI;

import com.alee.extended.date.DateSelectionListener;
import com.alee.extended.date.WebDateField;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazDateField extends WebDateField implements Validatable, DateSelectionListener {

	private static final long serialVersionUID = -5606839804577745160L;

	private List<Validator> validators;
	private boolean isValid = true;
	private boolean required;

	public LazDateField() {

		setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		addDateSelectionListener(this);
	}

	@Override
	public void setUI(TextUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
	}

	@Override
	public int getHeight() {
		return 27;
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

		if (!isValid) {

			Insets margin = getMargin();

			g.setColor(Color.RED);
			g.drawRect(margin.left + getShadeWidth(), getShadeWidth(), getWidth() - margin.left - getShadeWidth() - 3, getHeight() - getShadeWidth() * 2 - 1);
		}

		setDrawFocus(isValid && hasFocus());
		setFocusable(isEditable() && isEnabled());
	}

	@Override
	public void dateSelected(Date arg0) {
		validateData();
	}
}