package com.topsoft.topframework.swing.model;

import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.topsoft.topframework.swing.LazTree;
import com.topsoft.topframework.swing.table.LazTableColumn;

public abstract class LazTreeTableModel<T extends Object> extends LazTableModel<T>implements TreeModel {

	private static final long serialVersionUID = -513806838906180605L;

	protected Object root;
	private LazTree<?> tree;

	protected EventListenerList listenerList = new EventListenerList();

	public LazTreeTableModel(Object root, List<LazTableColumn> columns) {

		super(columns);

		this.root = root;
	}

	public LazTree<?> getTree() {

		return tree;
	}

	@Override
	public Object getAttributeValueAt(T element, int column) {
		return super.getAttributeValueAt(element, column);
	}

	@Override
	public Object getChild(Object parent, int index) {
		return null;
	}

	public void setTree(LazTree<?> tree) {

		this.tree = tree;

		tree.addTreeExpansionListener(new TreeExpansionListener() {

			public void treeExpanded(TreeExpansionEvent event) {
				fireTableDataChanged();
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				fireTableDataChanged();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public T getRowElementAt(int row) {

		TreePath treePath = tree.getPathForRow(row);
		return treePath == null ? null : (T) treePath.getLastPathComponent();
	}

	public Class<?> getColumnClass(int column) {
		return column == 0 ? LazTreeTableModel.class : super.getColumnClass(column);
	}

	public int getRowCount() {
		return tree == null ? 0 : tree.getRowCount();
	}

	@Override
	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

	@Override
	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {

		for (int i = 0; i < getChildCount(parent); i++)
			if (getChild(parent, i).equals(child))
				return i;

		return -1;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}
}