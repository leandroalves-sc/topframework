package com.topsoft.topframework.swing.event;

import javax.swing.JComponent;

public class LazTableEvent extends LazEvent<LazTableListener> {

	private static final long serialVersionUID = 6928420751420109027L;

	public static final int ROW_SELECTED = 1;
	public static final int IMAGE_CLICKED = 2;

	private int row;
	private int column;

	public LazTableEvent(JComponent source, int id, int row, int column) {

		super(source, id);

		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String paramString() {

		String typeStr;

		switch (id) {

			case ROW_SELECTED:
				typeStr = "ROW_SELECTED";
			break;

			case IMAGE_CLICKED:
				typeStr = "IMAGE_CLICKED";
			break;

			default:
				typeStr = "unknown type";
		}

		return typeStr + ",row=" + row + ",column=" + column;
	}

	@Override
	public void dispatchEvent(LazTableListener listener) {
		listener.tableEvent(this);
	}
}