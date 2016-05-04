package com.topsoft.topframework.swing.util;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.base.validator.impl.NotEmptyValidator;
import com.topsoft.topframework.swing.LazDateField;
import com.topsoft.topframework.swing.LazSearchInput;
import com.topsoft.topframework.swing.LazTable;

public class ValidatorUtil {

	public static boolean validate(Component comp) {
		return validate(comp, getValue(comp));
	}

	public static boolean validate(Component comp, Object value) {

		TooltipManager.removeTooltips(comp);

		if (comp instanceof JTextComponent && !((JTextComponent) comp).isEditable())
			return true;
		else if (!comp.isEnabled() || !comp.isVisible())
			return true;

		JPanel panel = LazSwingUtils.getParent(comp, JPanel.class);

		if (panel != null && !panel.isVisible())
			return true;

		if (comp instanceof Validatable) {

			Validatable validatable = (Validatable) comp;
			List<String> errors = new ArrayList<String>();

			if (validatable.isRequired())
				errors.addAll(getValidationErrors(Validator.use(NotEmptyValidator.class), value));

			errors.addAll(getValidationErrors(validatable.getValidators(), value));

			if (!errors.isEmpty()) {

				TooltipManager.setTooltip(comp, convertErrorListToHTML(errors), comp instanceof JTable ? TooltipWay.up : TooltipWay.down, 0);
				return false;
			}
		}

		return true;
	}

	public static boolean validate(List<Validator> validators, Object value) {

		for (Validator validator : validators)
			if (!validator.isValid(value))
				return false;

		return true;
	}

	public static boolean validate(Validator validator, Object value) {
		return validator.isValid(value);
	}

	public static String convertErrorListToHTML(List<String> errors) {

		StringBuilder msg = new StringBuilder("<html>");

		for (String error : errors)
			msg.append((msg.length() == 6 ? "" : "<br>") + error);

		msg.append("</html>");

		return msg.toString();
	}

	public static List<String> getValidationErrors(List<Validator> validators, Object value) {

		List<String> errors = new ArrayList<String>();

		if (validators != null)
			for (Validator validator : validators)
				errors.addAll(getValidationErrors(validator, value));

		return errors;
	}

	public static List<String> getValidationErrors(Validator validator, Object value) {

		List<String> errors = new ArrayList<String>();

		if (!validator.isValid(value))
			errors.add(validator.getErrorMessage());

		return errors;
	}

	private static Object getValue(Component comp) {

		if (comp instanceof JTextField)
			return ((JTextField) comp).getText();
		else if (comp instanceof JComboBox)
			return ((JComboBox<?>) comp).getSelectedItem();
		else if (comp instanceof JTextArea)
			return ((JTextArea) comp).getText();
		else if (comp instanceof LazDateField)
			return ((LazDateField) comp).getDate();
		else if (comp instanceof JSpinner)
			return ((JSpinner) comp).getValue();
		else if (comp instanceof LazSearchInput<?>) {

			LazSearchInput<?> searchInput = (LazSearchInput<?>) comp;
			return searchInput.isMultiSelect() ? searchInput.getSelectedItems() : searchInput.getSelectedItem();
		}
		else if (comp instanceof LazTable) {

			LazTable<?> table = (LazTable<?>) comp;
			return table.getModel().getData();
		}

		return null;
	}
}