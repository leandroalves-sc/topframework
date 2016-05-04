package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.PanelUI;

import org.apache.commons.lang3.ArrayUtils;

import com.topsoft.topframework.swing.event.LazMenuBoxEvent;
import com.topsoft.topframework.swing.event.LazMenuBoxListener;
import com.topsoft.topframework.swing.util.LazSwingUtils;

import net.miginfocom.swing.MigLayout;

public class LazMenuBox extends LazPanel implements MouseListener, KeyListener {

	private static final long serialVersionUID = -5280058501851953656L;

	private JLabel labelDBName, labelDBIcon, labelChubbName, labelVersionIcon, labelVersion;
	private int itemCount, itemsPerRow, labelOrientation;
	private LazMenuBoxItem selectedItem;
	private LazPanel menuPanel;

	public LazMenuBox() {

		this(SwingConstants.BOTTOM, 3);
	}

	public LazMenuBox(int labelOrientation, int itemsPerRow) {

		super(new MigLayout("fill"));

		setLabelOrientation(labelOrientation);
		setItemsPerRow(itemsPerRow);

		menuPanel = new LazPanel(new MigLayout("fillx,wrap " + itemsPerRow, "[center,grow]", "20[top,90]"));
		menuPanel.setBackground(Color.white);
		menuPanel.setFocusable(true);

		add(getPanelTitle(), "north");
		add(menuPanel, "grow");
		add(getStatusBarPane(), "south");
	}

	@Override
	public void setUI(PanelUI ui) {

		super.setUI(ui);

		setBackground(Color.white);
		setFocusable(true);
		addKeyListener(this);
	}

	private LazPanel getPanelTitle() {

		int width = 40;
		int height = 46;

		Image srcImg = new ImageIcon(getClass().getResource("/images/chubb.png")).getImage();

		LazPanel panel = new LazPanel(new MigLayout("fillx, ins 5"));
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.setBackground(Color.lightGray);

		panel.add(new JLabel(new ImageIcon(LazSwingUtils.resizeImage(srcImg, width, height))), "gaptop 5, gapbottom 5, gapleft 10, west");
		panel.add(getLblChubbdoBrasil(), "center");

		return panel;
	}

	private LazPanel getStatusBarPane() {

		LazPanel panel = new LazPanel(new MigLayout("fillx,ins 0", "[]", "[pref!,nogrid]"));
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		panel.setBackground(Color.white);

		panel.add(getPanelVersion());
		panel.add(getLazuwsDB(), "tag right");

		return panel;
	}

	private LazPanel getLazuwsDB() {

		LazPanel panel = new LazPanel(new MigLayout("gap 5 0"));
		panel.setBackground(Color.white);

		panel.add(labelDBIcon = new JLabel());
		panel.add(labelDBName = new JLabel());

		return panel;
	}

	public void setDBName(String dbName) {

		labelDBName.setText(dbName);
		labelDBIcon.setIcon(new ImageIcon(getClass().getResource("/images/button/bt-database.png")));
	}

	private LazPanel getPanelVersion() {

		LazPanel panel = new LazPanel(new MigLayout("gap 0"));
		panel.setBackground(Color.white);

		panel.add(labelVersionIcon = new JLabel());
		panel.add(labelVersion = new JLabel());

		return panel;
	}

	public void setAppVersion(String version) {

		labelVersion.setText(version);
		labelVersionIcon.setIcon(new ImageIcon(getClass().getResource("/images/button/bt-version.png")));
	}

	private JLabel getLblChubbdoBrasil() {

		labelChubbName = new javax.swing.JLabel();
		labelChubbName.setForeground(new Color(0, 86, 161));
		labelChubbName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelChubbName.setText("CHUBB DO BRASIL");
		labelChubbName.setFont(new Font("HelveticaNeue LT 63 MdEx Heavy", Font.PLAIN, 30));

		return labelChubbName;
	}

	public void setChubbNameColor(Color c) {
		labelChubbName.setForeground(c);
	}

	@Override
	public void keyPressed(KeyEvent event) {

		if (event.getID() == KeyEvent.KEY_PRESSED && event.getSource() instanceof Component) {

			if (selectedItem != null) {

				switch (event.getKeyCode()) {

					case KeyEvent.VK_ENTER:
						openSeletectedItem();
					break;

					case KeyEvent.VK_UP:
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
						move(selectedItem, event.getKeyCode());
					break;
				}
			}
		}
	}

	private void move(LazMenuBoxItem fromItem, int direction) {

		int index = fromItem.getIndex();

		switch (direction) {

			case KeyEvent.VK_LEFT:
				index = index == 0 ? itemCount - 1 : index - 1;
			break;

			case KeyEvent.VK_RIGHT:
				index = index == itemCount - 1 ? 0 : index + 1;
			break;

			case KeyEvent.VK_UP:

				int count = 0;

				if (index < ((int) itemCount % itemsPerRow))
					count = (int) itemCount % itemsPerRow;
				else if (index < itemsPerRow)
					count = itemsPerRow + ((int) itemCount % itemsPerRow);
				else
					count = itemsPerRow;

				for (int i = 0; i < count; i++)
					index = index == 0 ? itemCount - 1 : index - 1;

			break;

			case KeyEvent.VK_DOWN:

				count = 0;

				if (itemCount % itemsPerRow == 0 || itemCount - index > itemsPerRow)
					count = itemsPerRow;
				else if (itemCount - index <= ((int) itemCount % itemsPerRow))
					count = (int) itemCount % itemsPerRow;
				else
					count = itemsPerRow + ((int) itemCount % itemsPerRow);

				for (int i = 0; i < count; i++)
					index = index == itemCount - 1 ? 0 : index + 1;

			break;
		}

		LazMenuBoxItem nextItem = getMenuItemAt(index);

		if (nextItem.isEnabled())
			setSelectedItem(nextItem);
		else
			move(nextItem, direction);
	}

	public void addMenuItem(LazMenuBoxItem menuItem) {

		MigLayout migLayout = new MigLayout("center," + (labelOrientation == SwingConstants.TOP || labelOrientation == SwingConstants.BOTTOM ? "flowy" : "flowx"));
		migLayout.setColumnConstraints(labelOrientation == SwingConstants.TOP || labelOrientation == SwingConstants.BOTTOM ? "[center]" : (labelOrientation == SwingConstants.LEFT ? "[pref!]5[40]" : "[40]5[pref!]"));
		migLayout.setRowConstraints(labelOrientation == SwingConstants.LEFT || labelOrientation == SwingConstants.RIGHT ? "[]" : (labelOrientation == SwingConstants.TOP ? "[40]5[pref!]" : "[pref!]5[40]"));

		menuItem.setLayout(migLayout);
		menuItem.addMouseListener(this);
		menuItem.setIndex(itemCount++);
		menuItem.setLabelOrientation(labelOrientation);

		menuPanel.add(menuItem);

		if (selectedItem == null)
			setSelectedItem(menuItem);
	}

	public int getLabelOrientation() {
		return labelOrientation;
	}

	public void setLabelOrientation(int labelOrientation) {

		if (!ArrayUtils.contains(new int[] { SwingConstants.TOP, SwingConstants.BOTTOM, SwingConstants.LEFT, SwingConstants.RIGHT }, labelOrientation))
			throw new RuntimeException("Invalid menu orientation");

		this.labelOrientation = labelOrientation;
	}

	public int getItemsPerRow() {
		return itemsPerRow;
	}

	public void setItemsPerRow(int itemsPerRow) {

		if (itemsPerRow <= 0)
			throw new RuntimeException("Row quantity needs to be greater than 0");

		this.itemsPerRow = itemsPerRow;
	}

	public int getItemCount() {
		return itemCount;
	}

	public LazMenuBoxItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(LazMenuBoxItem item) {

		if (selectedItem != null)
			selectedItem.setSelected(false);

		item.setSelected(true);
		selectedItem = item;
	}

	public LazMenuBoxItem getMenuItemAt(int index) {

		if (menuPanel.getComponentCount() >= index)
			return (LazMenuBoxItem) menuPanel.getComponent(index);

		return null;
	}

	private void openSeletectedItem() {

		if (selectedItem != null)
			for (LazMenuBoxListener listener : getMenuBoxListeners())
				listener.menuBoxItemClicked(new LazMenuBoxEvent(getSelectedItem(), LazMenuBoxEvent.MENU_BOX_ITEM_CLICKED, "clicked"));
	}

	public void addMenuBoxListener(LazMenuBoxListener l) {
		listenerList.add(LazMenuBoxListener.class, l);
	}

	public void removeMenuBoxListener(LazMenuBoxListener l) {
		listenerList.remove(LazMenuBoxListener.class, l);
	}

	public LazMenuBoxListener[] getMenuBoxListeners() {
		return (LazMenuBoxListener[]) (listenerList.getListeners(LazMenuBoxListener.class));
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getSource() instanceof LazMenuBoxItem) {

			LazMenuBoxItem menuItem = (LazMenuBoxItem) e.getSource();

			if (menuItem.isEnabled()) {

				setSelectedItem(menuItem);

				if (e.getClickCount() == 2) {
					openSeletectedItem();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}