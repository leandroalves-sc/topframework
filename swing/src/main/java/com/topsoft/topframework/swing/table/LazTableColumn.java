package com.topsoft.topframework.swing.table;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import com.topsoft.topframework.swing.model.LazTreeTableModel;
import com.topsoft.topframework.swing.table.renderer.LazGenericCellRenderer;

public class LazTableColumn extends TableColumn {

	private static final long serialVersionUID = 1469379843301899021L;

	private Integer orientation;
	private Class<?> columnClass;

	public LazTableColumn(String columnName) {
		this(columnName, String.class, null);
	}

	public LazTableColumn(String columnName, Class<?> columnClass) {
		this(columnName, columnClass, null);
	}

	public LazTableColumn(String columnName, Integer orientation) {
		this(columnName, String.class, orientation);
	}

	public LazTableColumn(String columnName, Class<?> columnClass, Integer orientation) {

		super();

		this.orientation = orientation;
		this.setHeaderValue(columnName);
		this.setColumnClass(columnClass);

		if (getColumnClass() != Boolean.class && getColumnClass() != LazTreeTableModel.class)
			setCellRenderer(new LazGenericCellRenderer(orientation == null ? SwingConstants.LEFT : orientation));
	}

	public void setColumnClass(Class<?> columnClass) {
		this.columnClass = columnClass;
	}

	public Class<?> getColumnClass() {

		if (columnClass == null)
			return String.class;

		return columnClass;
	}

	public int getOrientation() {
		return orientation == null ? SwingConstants.LEFT : orientation;
	}

	public String getColumnName() {
		return getHeaderValue() == null ? "" : getHeaderValue().toString();
	}
}