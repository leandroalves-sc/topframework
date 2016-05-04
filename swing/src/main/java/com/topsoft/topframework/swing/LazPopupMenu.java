package com.topsoft.topframework.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.alee.laf.menu.WebPopupMenu;
import com.topsoft.topframework.swing.event.LazMenuItemEvent;
import com.topsoft.topframework.swing.event.LazMenuItemListener;

public class LazPopupMenu extends WebPopupMenu implements ActionListener {

	private static final long serialVersionUID = -9042253834750241089L;

	public LazPopupMenu() {
		super();
	}

	@Override
	public JMenuItem add(JMenuItem menuItem) {

		menuItem.addActionListener(this);

		return super.add(menuItem);
	}

	public void setAllItemsVisible(boolean b) {

		for (Component comp : getComponents())
			comp.setVisible(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof LazMenuItem)
			for (LazMenuItemListener listener : getMenuItemListeners())
				listener.menuItemClicked(new LazMenuItemEvent(e.getSource(), LazMenuItemEvent.MENU_ITEM_CLICKED, "clicked"));
	}

	public void addMenuItemListener(LazMenuItemListener l) {
		listenerList.add(LazMenuItemListener.class, l);
	}

	public void removeActionListener(LazMenuItemListener l) {
		listenerList.remove(LazMenuItemListener.class, l);
	}

	public LazMenuItemListener[] getMenuItemListeners() {
		return (LazMenuItemListener[]) (listenerList.getListeners(LazMenuItemListener.class));
	}
}