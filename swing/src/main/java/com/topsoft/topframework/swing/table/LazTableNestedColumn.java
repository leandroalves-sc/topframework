package com.topsoft.topframework.swing.table;

public class LazTableNestedColumn extends LazTableColumn {

	private static final long serialVersionUID = 6527943964457122330L;

	private String nestedColumn;

	public LazTableNestedColumn(String columnName, String nestedColumn, Class<?> columnClass) {
		this(columnName, nestedColumn, columnClass, null);
	}

	public LazTableNestedColumn(String columnName, String nestedColumn) {
		this(columnName, nestedColumn, String.class, null);
	}

	public LazTableNestedColumn(String columnName, String nestedColumn, Integer orientation) {
		this(columnName, nestedColumn, String.class, orientation);
	}

	public LazTableNestedColumn(String columnName, String nestedColumn, Class<?> columnClass, Integer orientation) {

		super(columnName, columnClass, orientation);

		this.nestedColumn = nestedColumn;
	}

	public String getNestedColumn() {
		return nestedColumn;
	}

	public void setNestedColumn(String nestedColumn) {
		this.nestedColumn = nestedColumn;
	}
}