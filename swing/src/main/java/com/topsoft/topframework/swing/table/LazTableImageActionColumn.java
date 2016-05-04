package com.topsoft.topframework.swing.table;

import javax.swing.Icon;
import javax.swing.table.TableCellRenderer;

import com.topsoft.topframework.base.util.LazImage;

public class LazTableImageActionColumn extends LazTableImageColumn {

	private static final long serialVersionUID = -955503244121959973L;

	public LazTableImageActionColumn(LazImage type, String toolTip) {
		super(type, toolTip);
	}

	public LazTableImageActionColumn(LazImage type, String toolTip, String enableMethod) {
		super(type, toolTip, enableMethod);
	}

	public LazTableImageActionColumn(String iconMethod, String toolTipMethod) {
		super(iconMethod, toolTipMethod);
	}

	public LazTableImageActionColumn(Icon icon, String toolTip, String enabledMethod) {
		super(icon, toolTip, enabledMethod);
	}

	public LazTableImageActionColumn(TableCellRenderer customRenderer) {
		super(customRenderer);
	}
}