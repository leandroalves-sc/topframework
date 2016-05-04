package com.topsoft.topframework.swing.table.renderer;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.alee.utils.ImageUtils;
import com.topsoft.topframework.base.util.ObjectUtils;
import com.topsoft.topframework.swing.LazLabel;
import com.topsoft.topframework.swing.LazTable;

public class LazImageCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 2862215405419689768L;

	protected JTable table;
	protected int row;

	private String iconMethod, toolTipMethod, enabledMethod;
	private Map<Icon, Icon> iconGrays;
	private boolean readOnly;

	public LazImageCellRenderer() {
		this.iconGrays = new HashMap<Icon, Icon>();
	}

	public LazImageCellRenderer(Icon icon, String toolTip) {

		this(icon, toolTip, null);
	}

	public LazImageCellRenderer(Icon icon, String toolTip, String enabledMethod) {

		this();

		setIcon(icon);
		setToolTipText(toolTip);
		this.enabledMethod = enabledMethod;
	}

	public LazImageCellRenderer(String iconMethod, String toolTipMethod) {

		this();

		this.iconMethod = iconMethod;
		this.toolTipMethod = toolTipMethod;
	}

	public LazImageCellRenderer(String iconMethod, String toolTipMethod, String enabledMethod) {

		this();

		this.iconMethod = iconMethod;
		this.toolTipMethod = toolTipMethod;
		this.enabledMethod = enabledMethod;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		this.table = table;
		this.row = row;

		LazLabel label = new LazLabel();
		label.setOpaque(true);

		if (isVisible()) {

			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setIcon(getIcon());
			label.setToolTipText(getToolTipText());

			if (readOnly || enabledMethod != null)
				label.setEnabled(isEnabled(row));
		}

		if (isSelected)
			label.setBackground(table.getSelectionBackground());

		return label;
	}

	public boolean isEnabled(int row) {

		if (readOnly)
			return false;

		if (enabledMethod != null) {

			LazTable<?> lazTable = (LazTable<?>) table;
			Object ob = lazTable.getModel().getRowElementAt(row);

			if (ob != null) {

				Object enabled = getValue(row, enabledMethod.charAt(0) == '!' ? enabledMethod.substring(1, enabledMethod.length()) : enabledMethod);

				if (enabled != null && enabled instanceof Boolean) {

					Boolean b = (Boolean) enabled;

					return enabledMethod.charAt(0) == '!' ? !b : b;
				}
			}
		}

		return super.isEnabled();
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public Icon getIcon() {

		Icon icon = super.getIcon();

		Object obj = getValue(row, iconMethod);

		if (obj != null && obj instanceof Icon)
			icon = (Icon) obj;

		if (isReadOnly()) {

			Icon iconGray = iconGrays.get(icon);

			if (iconGray == null) {

				ImageIcon imageIcon = (ImageIcon) icon;
				BufferedImage image = ImageUtils.createGrayscaleCopy(imageIcon.getImage());
				iconGrays.put(icon, iconGray = new ImageIcon(image));
			}

			icon = iconGray;
		}

		return icon;
	}

	@Override
	public String getToolTipText() {

		Object tooltip = getValue(row, toolTipMethod);

		if (tooltip != null)
			return tooltip.toString();

		return "";
	}

	public int getRow() {
		return row;
	}

	public Object getValue(int row, String method) {

		if (method != null && table instanceof LazTable) {

			try {

				LazTable<?> lazTable = (LazTable<?>) table;
				Object ob = lazTable.getModel().getRowElementAt(row);

				if (ob != null)
					return ObjectUtils.getNestedValue(ob, method);
			}
			catch (Exception e) {
				return null;
			}
		}

		return null;
	}
}