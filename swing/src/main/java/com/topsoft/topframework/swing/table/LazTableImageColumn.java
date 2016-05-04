package com.topsoft.topframework.swing.table;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.swing.table.renderer.LazImageCellRenderer;

public class LazTableImageColumn extends LazTableColumn {

	private static final long serialVersionUID = 6527943964457122330L;

	public LazTableImageColumn(LazImage type, String toolTip) {
		this(type.getIcon(), toolTip, null);
	}

	public LazTableImageColumn(LazImage type, String toolTip, String enableMethod) {
		this(type.getIcon(), toolTip, enableMethod);
	}

	public LazTableImageColumn(String iconMethod, String toolTipMethod) {

		super("", Image.class, SwingConstants.CENTER);
		setCellRenderer(new LazImageCellRenderer(iconMethod, toolTipMethod));
	}

	public LazTableImageColumn(Icon icon, String toolTip, String enabledMethod) {

		super("", Image.class, SwingConstants.CENTER);
		setCellRenderer(new LazImageCellRenderer(icon, toolTip, enabledMethod));
	}

	public LazTableImageColumn(TableCellRenderer customRenderer) {

		super("", Image.class, SwingConstants.CENTER);
		setCellRenderer(customRenderer);
	}
}