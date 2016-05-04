package com.topsoft.topframework.swing.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.topsoft.topframework.base.util.ObjectUtils;
import com.topsoft.topframework.swing.table.LazTableColumn;
import com.topsoft.topframework.swing.table.LazTableNestedColumn;

public class LazTableModel<T extends Object> extends AbstractTableModel {

	private static final long serialVersionUID = -6689715962215612429L;

	private List<T> data;
	private LazTableColumnModel columnModel;

	public LazTableModel(List<LazTableColumn> columns) {
		this(null, columns.toArray());
	}

	public LazTableModel(Object[] columns) {
		this(null, columns);
	}

	public LazTableModel(List<T> data, List<LazTableColumn> columns) {
		this(data, columns.toArray());
	}

	public LazTableModel(List<T> data, Object[] columnsObj) {

		this.data = data;
		this.columnModel = new LazTableColumnModel(columnsObj);
	}

	@Override
	public int getRowCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnModel.getColumnName(column);
	}

	@Override
	public int getColumnCount() {
		return columnModel.getColumnCount();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnModel.getColumnClass(columnIndex);
	}

	@Override
	public final Object getValueAt(int row, int column) {

		try {

			T value = getRowElementAt(row);

			if (value != null) {

				LazTableColumn tableColumn = getColumnModel().getColumn(column);

				if (tableColumn instanceof LazTableNestedColumn) {

					if (!String.class.isAssignableFrom(value.getClass()))
						return ObjectUtils.getNestedValue(value, ((LazTableNestedColumn) tableColumn).getNestedColumn());
					else if ("this".equals(((LazTableNestedColumn) tableColumn).getNestedColumn()))
						return value;
				}
				else
					return getAttributeValueAt(value, column);
			}
		}
		catch (Exception e) {
			return "";
		}

		return "";
	}

	@Override
	public final void setValueAt(Object value, int row, int column) {

		if (row < data.size()) {

			T element = data.get(row);

			if (element != null)
				setAttributeValueAt(element, value, column);
		}
	}

	public T getRowElementAt(int row) {

		if (data != null && data.size() > 0 && row >= 0 && row < data.size())
			return data.get(row);

		return null;
	}

	public LazTableColumnModel getColumnModel() {
		return columnModel;
	}

	public void removeRowElementAt(int row) {

		this.data.remove(row);
		fireTableDataChanged();
	}

	public void removeRow(T row) {

		this.data.remove(row);
		fireTableDataChanged();
	}

	public void removeAll() {

		if (data != null && !data.isEmpty())
			data.clear();

		fireTableDataChanged();
	}

	public void addRow(T row) {

		if (data == null)
			data = new ArrayList<T>();

		data.add(row);
		fireTableDataChanged();
	}

	public void setData(List<T> data) {

		this.data = new ArrayList<T>(data);
		fireTableDataChanged();
	}

	public List<T> getData() {
		return data;
	}

	public int indexOf(Object obj) {
		return data == null ? -1 : data.indexOf(obj);
	}

	public void setAttributeValueAt(T element, Object value, int column) {
	}

	public Object getAttributeValueAt(T element, int column) {
		return "";
	}
}