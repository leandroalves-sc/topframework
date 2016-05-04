package com.topsoft.topframework.swing.table;

import java.util.List;

import com.topsoft.topframework.swing.LazComboBox;
import com.topsoft.topframework.swing.table.editor.LazComboCellEditor;

public class LazTableNestedComboColumn<T extends Object> extends LazTableNestedColumn {

	private static final long serialVersionUID = 6527943964457122330L;

	private List<T> values;

	public LazTableNestedComboColumn(String columnName, String nestedColumn, T[] values) {

		super(columnName, nestedColumn);

		setCellEditor(new LazComboCellEditor<T>(new LazComboBox<T>(values)));
	}

	public List<T> getValues() {
		return values;
	}
}