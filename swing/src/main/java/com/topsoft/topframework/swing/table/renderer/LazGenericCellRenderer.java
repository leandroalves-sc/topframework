package com.topsoft.topframework.swing.table.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

public class LazGenericCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 2643146991324580163L;

	private int orientation = SwingConstants.RIGHT;

	public LazGenericCellRenderer(int aOrientation) {

		super();
		orientation = aOrientation;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		if (hasFocus && table.getModel().isCellEditable(row, col)) {

			TableCellEditor editor = table.getColumnModel().getColumn(col).getCellEditor();

			if (editor != null)
				return editor.getTableCellEditorComponent(table, value, isSelected, row, col);
		}

		c.setHorizontalAlignment(orientation);
		c.setBorder(BorderFactory.createEmptyBorder(0, (orientation == SwingConstants.LEFT ? 5 : 0), 0, orientation == SwingConstants.RIGHT ? 5 : 0));

		return c;
	}
}