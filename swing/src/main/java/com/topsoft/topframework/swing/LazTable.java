package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.plaf.TableUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.alee.laf.table.WebTable;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazEvent;
import com.topsoft.topframework.swing.event.LazTableEvent;
import com.topsoft.topframework.swing.event.LazTableListener;
import com.topsoft.topframework.swing.fonts.LazFonts;
import com.topsoft.topframework.swing.model.LazTableColumnModel;
import com.topsoft.topframework.swing.model.LazTableModel;
import com.topsoft.topframework.swing.table.LazTableColumn;
import com.topsoft.topframework.swing.table.LazTableImageActionColumn;
import com.topsoft.topframework.swing.table.renderer.LazImageCellRenderer;
import com.topsoft.topframework.swing.util.LazSwingUtils;
import com.topsoft.topframework.swing.util.ValidatorUtil;

public class LazTable<T extends Object> extends WebTable implements KeyListener, MouseListener, MouseMotionListener, Validatable, LazActionDispatcher<LazTableListener> {

	private static final long serialVersionUID = 6124627719246086551L;

	private List<Validator> validators;
	private boolean drawStripes = true, isValid = true, required, autoResizeToFit, needToResize;
	private Color defaultRowColor, alternateRowColor, overColor;
	private JPopupMenu popupMenu;
	private double[] columnWidths;
	private int rowOver = -1, columnOver = -1;

	public LazTable(LazTableModel<T> model) {

		super(model, model.getColumnModel());
	}

	@Override
	public void setUI(TableUI ui) {

		super.setUI(ui);

		setFont(LazFonts.BASE_FONT);

		getTableHeader().setFont(LazFonts.BASE_FONT_BOLD);
		getTableHeader().setReorderingAllowed(false);

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setSelectionBackground(new Color(127, 206, 255));
		setSelectionForeground(Color.black);

		setGridColor(new Color(204, 204, 204));

		showHorizontalLines = false;
		defaultRowColor = Color.white;
		alternateRowColor = new Color(244, 244, 244);
		overColor = new Color(178, 225, 255);
	}

	@Override
	public LazTableColumnModel getColumnModel() {
		return getModel().getColumnModel();
	}

	@Override
	@SuppressWarnings("unchecked")
	public LazTableModel<T> getModel() {
		return (LazTableModel<T>) super.getModel();
	}

	@Override
	public void tableChanged(TableModelEvent e) {

		super.tableChanged(e);

		clearSelection();
		setAutoResizeMode(getRowCount() == 0 || !autoResizeToFit ? JTable.AUTO_RESIZE_ALL_COLUMNS : JTable.AUTO_RESIZE_OFF);
		resizeColumns();

		validateData();
	}

	private void resizeColumns() {

		int columnMargin = 5;

		if (columnWidths != null && columnWidths.length == getColumnModel().getAllTableColumns().size()) {

			int leftoverWidth = getWidth();

			if (leftoverWidth > 0) {

				for (int i = 0; i < columnWidths.length; i++)
					if (columnWidths[i] > 1 && getColumnModel().isColumnVisible(i))
						leftoverWidth -= columnWidths[i];

				for (int i = 0; i < columnWidths.length; i++) {

					if (!getColumnModel().isColumnVisible(i))
						setColumnWidth(i, 0);
					else if (columnWidths[i] > 1)
						setColumnWidth(i, columnWidths[i]);
					else if (columnWidths[i] == 1)
						setColumnWidth(i, leftoverWidth);
					else
						setColumnWidth(i, leftoverWidth * columnWidths[i]);
				}

				needToResize = false;
			}
		}
		else if (autoResizeToFit) {

			for (int column = 0; column < getColumnCount(); column++) {

				TableColumn col = getColumnModel().getColumn(column);
				TableCellRenderer renderer = col.getHeaderRenderer();

				if (renderer == null)
					renderer = getTableHeader().getDefaultRenderer();

				Component comp = renderer.getTableCellRendererComponent(this, col.getHeaderValue(), false, false, 0, 0);
				int width = comp.getPreferredSize().width;

				for (int r = 0; r < getRowCount(); r++) {

					renderer = getCellRenderer(r, column);
					comp = renderer.getTableCellRendererComponent(this, getValueAt(r, column), false, false, r, column);

					width = Math.max(width, comp.getPreferredSize().width);
				}

				width += 2 * columnMargin;

				setColumnWidth(column, width);
			}
		}
	}

	private void setColumnWidth(int column, double width) {

		if (column < getColumnCount()) {

			TableColumn col = getColumnModel().getColumn(column);

			col.setPreferredWidth((int) width);
			col.setWidth((int) width);
		}
	}

	@Override
	public void setRowHeight(int rowHeight) {
		super.setRowHeight(rowHeight + 5);
	}

	public void addActionListener(LazTableListener l) {
		listenerList.add(LazTableListener.class, l);
	}

	public void removeActionListener(LazTableListener l) {
		listenerList.remove(LazTableListener.class, l);
	}

	public LazTableListener[] getActionListeners() {
		return (LazTableListener[]) (listenerList.getListeners(LazTableListener.class));
	}

	@Override
	public void addValidators(Validator... validators) {

		if (this.validators == null)
			this.validators = new ArrayList<Validator>();

		for (Validator validator : validators)
			this.validators.add(validator);

		validateData();
	}

	@Override
	public List<Validator> getValidators() {
		return validators;
	}

	@Override
	public void validateData() {
		isValid = ValidatorUtil.validate(this);
		repaint();
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
		validateData();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		super.valueChanged(e);
		validateData();
	}

	public T getSelectedItem() {
		return getModel().getRowElementAt(getSelectedRow());
	}

	@Override
	public void keyPressed(KeyEvent event) {

		if (event.getSource() == this && event.getKeyCode() == KeyEvent.VK_ENTER && getSelectedRow() <= 0)
			dispachTableEvent(LazTableEvent.ROW_SELECTED);
	}

	@Override
	public void setComponentPopupMenu(JPopupMenu popup) {
		this.popupMenu = popup;
	}

	protected boolean isShowPopupAllowed() {
		return true;
	}

	@Override
	public void mousePressed(MouseEvent event) {

		if (popupMenu != null) {

			int currentRow = rowAtPoint(event.getPoint());

			if (currentRow != -1) {

				setRowSelectionInterval(currentRow, currentRow);

				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) && isShowPopupAllowed())
					popupMenu.show(this, event.getX(), event.getY());
			}
		}

		if (event.getSource() == this && event.getClickCount() == 2)
			dispachTableEvent(LazTableEvent.ROW_SELECTED);
	}

	private void dispachTableEvent(int id) {
		dispachEvent(id, getSelectedRow(), getSelectedColumn());
	}

	private void dispachEvent(int id, int row, int column) {
		LazEvent.dispatchEvent(this, new LazTableEvent(this, id, row, column));
	}

	@Override
	public void mouseMoved(MouseEvent event) {

		rowOver = rowAtPoint(event.getPoint());
		columnOver = columnAtPoint(event.getPoint());

		if (getColumnModel().getColumnClass(columnOver) == Image.class && isCellEnabled(rowOver, columnOver))
			LazSwingUtils.showHandCursor(this);
		else
			LazSwingUtils.showDefaultCursor(this);

		repaint();
	}

	@Override
	public void mouseExited(MouseEvent event) {
		rowOver = columnOver = -1;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent event) {

		if (getActionListeners().length > 0) {

			int row = rowAtPoint(event.getPoint());
			int col = columnAtPoint(event.getPoint());

			if (getColumnModel().getColumnClass(col) == Image.class && isCellEnabled(row, col))
				dispachEvent(LazTableEvent.IMAGE_CLICKED, row, col);
		}
	}

	private boolean isCellEnabled(int row, int col) {

		TableCellRenderer cellRenderer = getCellRenderer(row, col);

		if (cellRenderer != null && cellRenderer instanceof LazImageCellRenderer) {

			LazImageCellRenderer renderer = (LazImageCellRenderer) cellRenderer;

			return renderer != null && renderer.isEnabled(row);
		}

		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {

		for (int i = 0; i < getColumnCount(); i++) {

			LazTableColumn tableColumn = getColumnModel().getColumnByModelIndex(i);

			if (tableColumn != null && LazTableImageActionColumn.class.isAssignableFrom(tableColumn.getClass())) {

				if (tableColumn.getCellRenderer() != null && LazImageCellRenderer.class.isAssignableFrom(tableColumn.getCellRenderer().getClass())) {

					LazImageCellRenderer renderer = (LazImageCellRenderer) tableColumn.getCellRenderer();

					if (renderer != null)
						renderer.setReadOnly(!enabled);
				}
			}
		}
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {

		Component comp = super.prepareRenderer(renderer, row, col);

		comp.setEnabled(isEnabled());

		if (drawStripes && !comp.getBackground().equals(getSelectionBackground()))
			comp.setBackground(row % 2 == 0 ? defaultRowColor : alternateRowColor);

		if (isEnabled() && row == rowOver && getSelectedRow() != rowOver)
			comp.setBackground(overColor);

		return comp;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return getPreferredSize().height < getParent().getHeight();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (!isValid)
			paintInvalidTable(g);

		if (drawStripes)
			paintEmptyRows(g);

		if (needToResize)
			resizeColumns();
	}

	private void paintInvalidTable(Graphics g) {

		JScrollPane scroll = LazSwingUtils.getParent(getParent(), JScrollPane.class);

		if (scroll != null) {

			g = scroll.getGraphics();

			Insets margin = scroll.getInsets();

			g.setColor(Color.RED);
			g.drawRect(margin.left - 1, margin.top - 1, scroll.getWidth() - margin.left - margin.right + 1, scroll.getHeight() - margin.top - margin.bottom + 1);
			g.setColor(UIManager.getColor("Table.gridColor"));

			if (scroll instanceof LazScrollPane)
				((LazScrollPane) scroll).setDrawFocus(isValid && hasFocus());

			scroll.repaint();
		}
	}

	private void paintEmptyRows(Graphics g) {

		Graphics newGraphics = g.create();
		newGraphics.setColor(UIManager.getColor("Table.gridColor"));

		Rectangle rectOfLastRow = getCellRect(getRowCount() - 1, 0, true);
		int firstNonExistentRowY = rectOfLastRow.y;

		if (getVisibleRect().height > firstNonExistentRowY) {

			int rowYToDraw = (firstNonExistentRowY - 1) + getRowHeight();
			int actualRow = getRowCount() > 0 ? getRowCount() - 1 : 0;

			while (rowYToDraw < getHeight()) {

				if (actualRow % 2 == 0) {

					newGraphics.setColor(alternateRowColor);
					newGraphics.fillRect(0, rowYToDraw, getWidth(), getRowHeight());
				}

				newGraphics.setColor(getGridColor());

				if (showHorizontalLines)
					newGraphics.drawLine(0, rowYToDraw, getWidth(), rowYToDraw);

				rowYToDraw += getRowHeight();
				actualRow++;
			}

			int x = 0;

			for (int i = 0; i < getColumnCount(); i++) {

				TableColumn column = getColumnModel().getColumn(i);
				x += column.getWidth();

				newGraphics.setColor(getGridColor());

				if (showVerticalLines)
					newGraphics.drawLine(x - 1, firstNonExistentRowY, x - 1, getHeight());
			}

			newGraphics.setColor(UIManager.getColor("Table.gridColor"));
			newGraphics.dispose();
		}
	}

	public boolean isAutoResizeToFit() {
		return autoResizeToFit;
	}

	public void setAutoResizeToFit(boolean autoResizeToFit) {
		this.autoResizeToFit = autoResizeToFit;
	}

	public boolean isDrawStripes() {
		return drawStripes;
	}

	public void setDrawStripes(boolean drawStripes) {
		this.drawStripes = drawStripes;
	}

	public Color getDefaultRowColor() {
		return defaultRowColor;
	}

	public void setDefaultRowColor(Color defaultRowColor) {
		this.defaultRowColor = defaultRowColor;
	}

	public Color getAlternateRowColor() {
		return alternateRowColor;
	}

	public void setAlternateRowColor(Color alternateRowColor) {
		this.alternateRowColor = alternateRowColor;
	}

	public double[] getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(double[] columnWidths) {
		this.columnWidths = columnWidths;
		this.needToResize = true;
	}

	public void setColumnVisible(int columnIndex, boolean visible) {

		LazTableColumnModel columnModel = getColumnModel();
		columnModel.setColumnVisible(columnModel.getColumnByModelIndex(columnIndex), visible);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
}