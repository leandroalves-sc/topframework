package com.topsoft.topframework.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.alee.laf.menu.WebMenu;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.swing.event.LazMenuItemEvent;
import com.topsoft.topframework.swing.event.LazMenuItemListener;

public class LazMenu extends WebMenu implements ActionListener {

	private static final long serialVersionUID = -3675116291178522441L;

	public LazMenu() {
		super();
	}

	public LazMenu(String text) {
		super(text);
	}

	public LazMenu(String text, LazImage image) {
		super(text, image.getIcon());
	}

	public LazMenu(String text, char mnemonic) {

		super(text);
		setMnemonic(mnemonic);
	}

	@Override
	public JMenuItem add(JMenuItem menuItem) {

		menuItem.addActionListener(this);

		return super.add(menuItem);
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