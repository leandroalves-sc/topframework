package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.MutableComboBoxModel;
import javax.swing.plaf.ComboBoxUI;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.combobox.WebComboBoxElement;
import com.topsoft.topframework.base.util.ObjectUtils;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.util.LazSwingUtils;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazComboBox<T extends Object> extends WebComboBox implements ItemListener, Validatable, LazActionDispatcher<ActionListener> {

	private static final long serialVersionUID = 6208002417108162752L;

	private List<Validator> validators;
	private boolean isValid = true, required = false;
	private String firstRowText;
	private String descriptionMethod;

	public LazComboBox() {
		this(new Object[] {});
	}

	public LazComboBox(Vector<T> items) {
		this(items.toArray());
	}

	public LazComboBox(List<T> items) {
		this(items.toArray());
	}

	public LazComboBox(Object[] items) {
		setModel(items);
	}

	@Override
	public void setUI(ComboBoxUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);
		setRenderer(new CellRenderer());
		addItemListener(this);
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
	public List<Validator> getValidators() {
		return validators;
	}

	@Override
	public void validateData() {
		isValid = ValidatorUtil.validate(this);
		repaint();
	}

	public void setModel(Vector<T> items) {
		setModel(new DefaultComboBoxModel(items.toArray()));
	}

	public void setModel(List<T> items) {
		setModel(new DefaultComboBoxModel(items.toArray()));
	}

	public void setModel(Object[] items) {
		setModel(new DefaultComboBoxModel(items));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return (T) super.getSelectedItem();
	}

	@Override
	public void setSelectedItem(Object anObject) {
		super.setSelectedItem(anObject);
		validateData();
	}

	private class CellRenderer extends WebComboBoxCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

			WebComboBoxElement comp = (WebComboBoxElement) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null && descriptionMethod != null) {

				try {

					Object description = ObjectUtils.getNestedValue(value, descriptionMethod);

					if (description != null)
						comp.setText(description.toString().trim());

				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			return comp;
		}
	}

	@Override
	public void setModel(ComboBoxModel aModel) {

		if (firstRowText != null) {

			Object item = aModel.getElementAt(0);

			if (item == null || item.toString() != firstRowText)
				((MutableComboBoxModel) aModel).insertElementAt(firstRowText, 0);
		}

		super.setModel(aModel);
		setSelectedIndex(-1);

		validateData();
	}

	public void refresh() {
		setModel(getModel());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		validateData();
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		validateData();
	}

	public void setFirstRowText(String text) {

		this.firstRowText = text == null ? "" : text;

		refresh();

		if (getItemCount() > 0)
			setSelectedIndex(0);
	}

	public void setDescriptionMethod(String descriptionMethod) {
		this.descriptionMethod = descriptionMethod;
	}

	@Override
	public void setVisible(boolean visible) {

		LazPanel panel = LazSwingUtils.getParent(this, LazPanel.class);

		if (panel != null)
			panel.setLabelVisible(this, visible);

		super.setVisible(visible);
	}

	@Override
	protected void paintBorder(Graphics g) {

		super.paintBorder(g);

		if (!isValid) {

			g.setColor(Color.RED);

			Insets margin = getInsets();
			g.drawRect(getShadeWidth(), getShadeWidth(), getWidth() - margin.left - getShadeWidth(), getHeight() - margin.top - getShadeWidth());
		}

		setDrawFocus(isValid && hasFocus());
	}
}