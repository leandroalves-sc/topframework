package com.topsoft.topframework.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;

import com.topsoft.topframework.swing.model.LazTreeTableModel;

public class LazTreeTable<T extends Object> extends LazTable<T> {

	private static final long serialVersionUID = -748877399673658157L;

	private TreeTableCellRenderer tree;

	public LazTreeTable(LazTreeTableModel<T> treeTableModel) {

		super(treeTableModel);

		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		setDefaultRenderer(LazTreeTableModel.class, tree = new TreeTableCellRenderer(treeTableModel));
		setDefaultEditor(LazTreeTableModel.class, new TreeTableCellEditor());

		tree.setRowHeight(getRowHeight());
		tree.setSelectionModel(new DefaultTreeSelectionModel() {

			private static final long serialVersionUID = -5060296554261972578L;

			{
				setSelectionModel(listSelectionModel);
			}
		});

		treeTableModel.setTree(tree);
	}

	public void setTreeModel(TreeModel dataModel) {

		tree.setModel(dataModel);
		tree.expandAll();
	}

	public int getEditingRow() {

		return (getColumnClass(editingColumn) == LazTreeTableModel.class) ? -1 : editingRow;
	}

	@SuppressWarnings("rawtypes")
	public class TreeTableCellRenderer extends LazTree implements TableCellRenderer {

		private static final long serialVersionUID = -1264864954556492491L;

		protected int visibleRow;

		public TreeTableCellRenderer(TreeModel model) {

			super(model);
		}

		public void setBounds(int x, int y, int w, int h) {

			super.setBounds(x, 0, w, LazTreeTable.this.getHeight());
		}

		public void paint(Graphics g) {

			g.translate(0, -visibleRow * getRowHeight());

			super.paint(g);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

			visibleRow = row;

			return this;
		}
	}

	public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = -6125188408902539063L;

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
			return tree;
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}
	}
}