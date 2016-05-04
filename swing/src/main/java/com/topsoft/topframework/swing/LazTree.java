package com.topsoft.topframework.swing;

import javax.swing.plaf.TreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.alee.laf.tree.WebTree;
import com.alee.laf.tree.WebTreeUI;
import com.topsoft.topframework.swing.fonts.LazFonts;

public class LazTree<E extends DefaultMutableTreeNode> extends WebTree<E> {

	private static final long serialVersionUID = 3713800138164544688L;

	private LazTreeUI treeUI;

	public LazTree(TreeModel model) {

		super(model);
	}

	public void setUI(TreeUI ui) {

		super.setUI(treeUI = new LazTreeUI());

		setFont(LazFonts.BASE_FONT);
	}

	@Override
	public void setModel(TreeModel newModel) {

		super.setModel(newModel);
		treeUI.setModel(newModel);
	}

	private class LazTreeUI extends WebTreeUI {

		public LazTreeUI() {
		}

		@Override
		protected void setModel(TreeModel model) {
			super.setModel(model);
		}
	}
}