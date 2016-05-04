package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.PanelUI;

import com.alee.extended.painter.AbstractPainter;
import com.alee.laf.text.WebTextField;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.base.util.ObjectUtils;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazEvent;
import com.topsoft.topframework.swing.event.LazSearchInputEvent;
import com.topsoft.topframework.swing.event.LazSearchInputListener;
import com.topsoft.topframework.swing.event.LazTableEvent;
import com.topsoft.topframework.swing.lov.LazLov;
import com.topsoft.topframework.swing.model.LazTableModel;
import com.topsoft.topframework.swing.table.LazTableColumn;
import com.topsoft.topframework.swing.table.LazTableImageColumn;
import com.topsoft.topframework.swing.table.LazTableNestedColumn;
import com.topsoft.topframework.swing.text.LazTextDocument;
import com.topsoft.topframework.swing.util.ValidatorUtil;

import net.miginfocom.swing.MigLayout;

public class LazSearchInput<T extends Object> extends LazPanel implements ActionListener, Validatable, LazActionDispatcher<LazSearchInputListener> {

	private static final long serialVersionUID = 6257876347002255314L;

	private static final int CODE_WIDTH = 80;

	private LazTextField txfCode, txfDescription;
	private LazLabel btnLov, btnAdd, btnClear;
	private boolean multiSelect, editableDescription;

	private T selectedItem;
	private LazTable<T> table;
	private String codeMethod = "databaseID";
	private String descriptionMethod = "toString";
	private String errorMsg = "Data not found";
	private LazLov<T> lov;
	private Class<?> codeType;
	private LazTableModel<T> tableModel;
	private List<Validator> validators;
	private boolean required;
	private boolean isValid = true;
	private int codeLength = 10;

	public LazSearchInput(LazLov<T> lov) {
		this(lov, false);
	}

	public LazSearchInput(LazLov<T> lov, boolean multiSelect) {

		super();

		this.setUndecorated(false);
		this.multiSelect = multiSelect;
		this.codeType = Integer.class;
		this.lov = lov;

		setLayout(new MigLayout("fill, gapx 5, ins 0, hidemode 3", "[pref!][pref!][grow,fill][pref!][pref!]"));
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

		txfCode = new LazTextField() {

			private static final long serialVersionUID = 6781847766847959601L;

			@Override
			protected void paintBorder(Graphics g) {

				Insets insets = getParent().getInsets();

				g.setColor(new Color(170, 170, 170));
				g.drawLine(getWidth() - 1, insets.top + 1, getWidth() - 1, getHeight() - insets.top - insets.bottom - 1);
			}

			@Override
			public void paint(Graphics g) {

				Insets insets = getParent().getInsets();

				g.setColor(isEditable() && isEnabled() ? Color.white : UIManager.getColor("TextField.inactiveBackground"));
				g.fillRect(insets.left + 1, insets.top + 1, getWidth() - insets.left - insets.right - 1, getHeight() - insets.top - insets.bottom - 2);

				super.paint(g);
			}
		};

		txfDescription = new LazTextField() {

			private static final long serialVersionUID = 4274851550384419115L;

			@Override
			protected void paintBorder(Graphics g) {

				Insets insets = getParent().getInsets();

				g.setColor(new Color(170, 170, 170));

				g.drawLine(1, insets.top + 1, 1, getHeight() - insets.top - insets.bottom - 1);
				g.drawLine(getWidth() - 1, insets.top + 1, getWidth() - 1, getHeight() - insets.top - insets.bottom - 1);
			}

			@Override
			public void paint(Graphics g) {

				Insets insets = getParent().getInsets();

				g.setColor(isEditable() && isEnabled() ? Color.white : UIManager.getColor("TextField.inactiveBackground"));
				g.fillRect(insets.left + 1, insets.top + 1, getWidth() - insets.left - insets.right - 1, getHeight() - insets.top - insets.bottom - 2);

				super.paint(g);
			}
		};

		add(txfCode);
		add(btnLov = new LazLabel(LazImage.LOV));
		add(txfDescription, "growx");
		add(btnAdd = new LazLabel(LazImage.ADD));
		add(btnClear = new LazLabel(LazImage.CLEAR_LOV), multiSelect ? "gapright 5" : "");

		Vector<LazTableColumn> columns = new Vector<LazTableColumn>();
		columns.add(new LazTableImageColumn(LazImage.REMOVE, "Excluir"));
		columns.add(new LazTableNestedColumn("Description", descriptionMethod));

		table = new LazTable<T>(tableModel = new LazTableModel<T>(columns));
		table.addActionListener(this);
		table.setColumnWidths(new double[] { 25, 1 });
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setDrawStripes(false);
		table.setVisible(isMultiSelect());

		add(table, "h 80!, gapleft 5, gapright 2, gapbottom 2, grow, span 5, newline");

		txfCode.setPainter(new CustomPainter());
		txfCode.setPreferredWidth(CODE_WIDTH);
		txfCode.setDocument(new LazTextDocument(10, codeType == Integer.class, false));
		txfCode.setInputVerifier(new CodeVerifier());

		txfDescription.setPainter(new CustomPainter());
		txfDescription.setEditable(false);

		btnLov.setButtonMode(true);
		btnAdd.setButtonMode(true);
		btnClear.setButtonMode(true);

		btnLov.addActionListener(this);
		btnAdd.addActionListener(this);
		btnClear.addActionListener(this);

		btnAdd.setVisible(multiSelect);
	}

	public void setTableHeight(int height) {

		remove(table);
		add(table, "h " + height + "!, gapleft 5, gapright 2, gapbottom 2, grow, span 5, newline");
	}

	public void showAddButton(boolean b) {
		btnAdd.setVisible(b);
	}

	private class CustomPainter extends AbstractPainter<WebTextField> {

		@Override
		public void paint(Graphics2D arg0, Rectangle arg1, WebTextField arg2) {

			setBackground(Color.white);
			setBorderColor(Color.white);
		}
	}

	@Override
	public void setUI(PanelUI ui) {

		super.setUI(ui);

		setPaintFocus(true);
		setPaintBackground(false);
		setMargin(2, 2, 2, 0);
		setRound(2);
	}

	@Override
	protected void paintBorder(Graphics g) {

		Insets margin = getMargin();

		g.setColor(isValid ? new Color(128, 128, 128) : Color.RED);
		g.drawRoundRect(margin.left, margin.top, getWidth() - 5, getHeight() - 5, getRound(), getRound());

		if (multiSelect)
			g.drawLine(margin.left, txfCode.getHeight() - 1, getWidth() - margin.left - margin.right - 1, txfCode.getHeight() - 1);
	}

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(3, 3, getWidth() - 7, getHeight() - 7);

		setPaintFocus(isValid && hasFocus());

		super.paint(g);
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

	@Override
	public void setEnabled(boolean enabled) {

		btnLov.setEnabled(enabled);
		btnAdd.setEnabled(enabled);
		btnClear.setEnabled(enabled);

		txfCode.setEditable(enabled);
		txfDescription.setEditable(enabled && editableDescription);

		validateData();
	}

	@Override
	public boolean isEnabled() {
		return txfCode.isEditable();
	}

	public Class<?> getCodeType() {
		return codeType;
	}

	public void setCodeType(Class<?> codeType) {

		this.codeType = codeType;

		if (txfCode.getDocument() != null && txfCode.getDocument() instanceof LazTextDocument)
			((LazTextDocument) txfCode.getDocument()).setValues(codeLength, codeType == Integer.class, false);
	}

	public void setCodeLength(int codeLength) {

		this.codeLength = codeLength;
		this.setCodeType(codeType);
	}

	public void setCodeWidth(int width) {
		txfCode.setPreferredWidth(width);
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		table.setVisible(this.multiSelect = multiSelect);
	}

	public T getSelectedItem() {
		return selectedItem;
	}

	public List<T> getSelectedItems() {
		return tableModel.getData();
	}

	public void setSelectedItems(List<T> list) {

		tableModel.setData(list);
		table.clearSelection();

		validateData();
	}

	public void setSelectedItem(T selectedItem) {

		this.selectedItem = selectedItem;

		if (selectedItem == null) {

			txfCode.clear();
			txfDescription.clear();

			if (editableDescription)
				txfDescription.setEditable(true);
		}
		else {

			try {

				Object code = ObjectUtils.getNestedValue(selectedItem, codeMethod);
				Object description = ObjectUtils.getNestedValue(selectedItem, descriptionMethod);

				if (code != null)
					txfCode.setText(code.toString().trim());

				if (description != null)
					txfDescription.setText(description.toString().trim());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		validateData();
	}

	public void removeAllItems() {
		setSelectedItem(null);
		tableModel.removeAll();
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCodeMethod() {
		return codeMethod;
	}

	public void setCodeMethod(String codeMethod) {

		this.codeMethod = codeMethod;

		if (getSelectedItem() != null)
			setSelectedItem(getSelectedItem());
	}

	public String getDescriptionMethod() {
		return descriptionMethod;
	}

	public void setDescriptionMethod(String descriptionMethod) {

		this.descriptionMethod = descriptionMethod;

		if (selectedItem != null)
			setSelectedItem(selectedItem);

		if (multiSelect) {

			if (table.getColumnModel().getColumnCount() > 1) {

				LazTableColumn column = table.getColumnModel().getColumn(1);

				if (column instanceof LazTableNestedColumn)
					((LazTableNestedColumn) table.getColumnModel().getColumn(1)).setNestedColumn(descriptionMethod);

				tableModel.fireTableDataChanged();
			}
		}
	}

	public boolean isEditableDescription() {
		return editableDescription;
	}

	public void setEditableDescription(boolean editableDescription) {
		this.editableDescription = editableDescription;
	}

	private void onAddItem() {

		if (btnAdd.isEnabled() && selectedItem != null) {

			tableModel.addRow(selectedItem);
			setSelectedItem(null);

			LazEvent.dispatchEvent(this, new LazSearchInputEvent(this, LazSearchInputEvent.ITEM_ADDED));

			validateData();
		}
	}

	private void onClearItem() {

		if (btnClear.isEnabled() && selectedItem != null) {

			setSelectedItem(null);
			LazEvent.dispatchEvent(this, new LazSearchInputEvent(this, LazSearchInputEvent.ITEM_CLEARED));

			validateData();
		}
	}

	private void onRemoveItem(int row) {

		tableModel.removeRowElementAt(row);
		LazEvent.dispatchEvent(this, new LazSearchInputEvent(this, LazSearchInputEvent.ITEM_REMOVED));

		validateData();
	}

	@Override
	public void tableEvent(LazTableEvent event) {

		if (event.getID() == LazTableEvent.IMAGE_CLICKED)
			onRemoveItem(event.getRow());
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source == btnLov && lov != null && btnLov.isEnabled())
			lov.openLov(this);
		else if (source == btnAdd)
			onAddItem();
		else if (source == btnClear)
			onClearItem();
	}

	@Override
	public void addActionListener(LazSearchInputListener listener) {
		listenerList.add(LazSearchInputListener.class, listener);
	}

	@Override
	public void removeActionListener(LazSearchInputListener listener) {
		listenerList.remove(LazSearchInputListener.class, listener);
	}

	@Override
	public LazSearchInputListener[] getActionListeners() {
		return (LazSearchInputListener[]) (listenerList.getListeners(LazSearchInputListener.class));
	}

	private class CodeVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {

			if (txfCode.getText().trim().length() > 0) {

				T dto = lov.searchByCode(txfCode.getText());

				if (dto == null) {

					LazAlert.showWarning(errorMsg);
					return false;
				}
				else {

					setSelectedItem(dto);
				}
			}

			return true;
		}
	}
}