package com.topsoft.topframework.swing.table.editor;

import com.alee.utils.swing.WebDefaultCellEditor;
import com.topsoft.topframework.swing.LazComboBox;

public class LazComboCellEditor<T extends Object> extends WebDefaultCellEditor<LazComboBox<T>> {

	private static final long serialVersionUID = 1500957274392787509L;

	public LazComboCellEditor(LazComboBox<T> combo) {
		super(combo);
	}
}