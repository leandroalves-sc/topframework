package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SpinnerUI;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.alee.laf.spinner.WebSpinner;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.model.LazSpinnerModel;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazSpinner<T extends Object> extends WebSpinner implements Validatable, ChangeListener, LazActionDispatcher<ChangeListener> {

	private static final long serialVersionUID = 8036140517885527026L;

	private List<Validator> validators;
	private boolean isValid = true;
	private boolean required;

	public LazSpinner() {
	}

	public LazSpinner(LazSpinnerModel<T> model) {
		this(model, null);
	}

	public LazSpinner(LazSpinnerModel<T> model, String pattern) {

		super(model);

		if (pattern != null && JSpinner.DateEditor.class.isAssignableFrom(getEditor().getClass())) {

			JSpinner.DateEditor de = (JSpinner.DateEditor) getEditor();

			DateFormatter formatter = new DateEditorFormatter((SpinnerDateModel) getModel(), new SimpleDateFormat(pattern));
			de.getTextField().setFormatterFactory(new DefaultFormatterFactory(formatter));
		}
	}

	private class DateEditorFormatter extends DateFormatter {

		private static final long serialVersionUID = 874907678771296203L;

		private final SpinnerDateModel model;

		DateEditorFormatter(SpinnerDateModel model, DateFormat format) {

			super(format);
			this.model = model;
		}

		@SuppressWarnings("rawtypes")
		public void setMinimum(Comparable min) {
			model.setStart(min);
		}

		public Comparable<?> getMinimum() {
			return model.getStart();
		}

		@SuppressWarnings("rawtypes")
		public void setMaximum(Comparable max) {
			model.setEnd(max);
		}

		public Comparable<?> getMaximum() {
			return model.getEnd();
		}
	}

	@Override
	public void setUI(SpinnerUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
		setRound(2);

		addChangeListener(this);
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

			g.setColor(Color.RED);
			g.drawRect(getShadeWidth(), getShadeWidth(), getWidth() - getShadeWidth() - 3, getHeight() - getShadeWidth() * 2 - 1);
		}

		setDrawFocus(isValid && hasFocus());
		setFocusable(isEnabled());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		validateData();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) super.getValue();
	}

	@Override
	public void addActionListener(ChangeListener listener) {
		addChangeListener(listener);
	}

	@Override
	public void removeActionListener(ChangeListener listener) {
		removeChangeListener(listener);
	}

	@Override
	public ChangeListener[] getActionListeners() {
		return getChangeListeners();
	}
}