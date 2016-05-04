package com.topsoft.topframework.swing.model;

import java.util.Vector;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.topsoft.topframework.swing.table.LazTableColumn;

public class LazTableColumnModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = 8359728091777410910L;

	protected Vector<LazTableColumn> allTableColumns = new Vector<LazTableColumn>();

	public LazTableColumnModel(Object[] columnsObj) {

		for (int i = 0; i < columnsObj.length; i++) {

			LazTableColumn tableColumn = null;

			if (columnsObj[i] instanceof String)
				tableColumn = new LazTableColumn(columnsObj[i].toString());
			else if (columnsObj[i] instanceof LazTableColumn)
				tableColumn = (LazTableColumn) columnsObj[i];
			else
				throw new RuntimeException("Tipo de coluna inv�lido. Deve ser String/LazTableColumn");

			tableColumn.setModelIndex(i);
			addColumn(tableColumn);
		}
	}

	@Override
	public void addColumn(TableColumn column) {

		if (!(column instanceof LazTableColumn))
			throw new RuntimeException("Tipo de coluna inv�lido. Deve ser LazTableColumn");

		allTableColumns.add((LazTableColumn) column);

		super.addColumn(column);
	}

	@Override
	public void removeColumn(TableColumn column) {

		int allColumnsIndex = allTableColumns.indexOf(column);

		if (allColumnsIndex != -1)
			allTableColumns.remove(allColumnsIndex);

		super.removeColumn(column);
	}

	@Override
	public void moveColumn(int oldIndex, int newIndex) {

		if ((oldIndex < 0) || (oldIndex >= getColumnCount()) || (newIndex < 0) || (newIndex >= getColumnCount()))
			throw new IllegalArgumentException("moveColumn() - Index out of range");

		LazTableColumn fromColumn = getColumn(oldIndex);
		LazTableColumn toColumn = getColumn(newIndex);

		int allColumnsOldIndex = allTableColumns.indexOf(fromColumn);
		int allColumnsNewIndex = allTableColumns.indexOf(toColumn);

		if (oldIndex != newIndex) {

			allTableColumns.remove(allColumnsOldIndex);
			allTableColumns.add(allColumnsNewIndex, fromColumn);
		}

		super.moveColumn(oldIndex, newIndex);
	}

	@Override
	public int getColumnCount() {
		return super.getColumnCount();
	}

	@Override
	public LazTableColumn getColumn(int columnIndex) {
		return (LazTableColumn) super.getColumn(columnIndex);
	}

	public String getColumnName(int col) {
		return getColumn(col).getColumnName();
	}

	public Class<?> getColumnClass(int col) {
		return getColumn(col).getColumnClass();
	}

	public void setColumnVisible(TableColumn column, boolean visible) {

		if (!visible) {

			super.removeColumn(column);
		}
		else {

			for (int invisibleIndex = 0, visibleIndex = 0; invisibleIndex < allTableColumns.size(); ++invisibleIndex) {

				LazTableColumn visibleColumn = (visibleIndex < tableColumns.size() ? getColumn(visibleIndex) : null);
				LazTableColumn testColumn = allTableColumns.get(invisibleIndex);

				if (testColumn == column) {

					if (visibleColumn != column) {

						super.addColumn(column);
						super.moveColumn(tableColumns.size() - 1, visibleIndex);
					}

					return;
				}

				if (testColumn == visibleColumn)
					++visibleIndex;
			}
		}
	}

	public Vector<LazTableColumn> getAllTableColumns() {
		return allTableColumns;
	}

	public void setAllColumnsVisible() {

		int noColumns = allTableColumns.size();

		for (int columnIndex = 0; columnIndex < noColumns; ++columnIndex) {

			LazTableColumn visibleColumn = (columnIndex < tableColumns.size() ? getColumn(columnIndex) : null);
			LazTableColumn invisibleColumn = allTableColumns.get(columnIndex);

			if (visibleColumn != invisibleColumn) {

				super.addColumn(invisibleColumn);
				super.moveColumn(tableColumns.size() - 1, columnIndex);
			}
		}
	}

	public LazTableColumn getColumnByModelIndex(int modelColumnIndex) {

		for (int columnIndex = 0; columnIndex < allTableColumns.size(); ++columnIndex) {

			LazTableColumn column = allTableColumns.get(columnIndex);

			if (column.getModelIndex() == modelColumnIndex)
				return column;
		}

		return null;
	}

	public boolean isColumnVisible(TableColumn aColumn) {
		return tableColumns.indexOf(aColumn) >= 0;
	}

	public boolean isColumnVisible(int position) {
		return isColumnVisible(getColumnByModelIndex(position));
	}
}