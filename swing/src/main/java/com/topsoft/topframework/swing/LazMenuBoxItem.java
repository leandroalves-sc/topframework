package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.plaf.PanelUI;

import com.topsoft.topframework.swing.util.LazSwingUtils;

import net.miginfocom.swing.MigLayout;

public class LazMenuBoxItem extends LazPanel {

	private static final long serialVersionUID = 32203248900357626L;

	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;

	private int index;
	private int labelOrientation;
	private boolean selected;

	private LazPanel labelPanel;
	private LazLabel labelText, labelIcon;
	private Image coloredIcon, grayIcon;

	public LazMenuBoxItem(URL iconURL, String text) {

		super(new MigLayout());

		createIcons(iconURL);

		labelText = new LazLabel(text, SwingConstants.CENTER);
		labelText.setFont(new Font("Nunito-Regular", Font.PLAIN, 14));
		labelText.setForeground(Color.black);

		labelPanel = new LazPanel(new MigLayout("ins 5", "[]", "[]"));
		labelPanel.setBackground(SystemColor.controlLtHighlight);
		labelPanel.add(labelText, "wmax 210");

		addComponents();

		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				showHandCursor();
			}

			public void mouseExited(MouseEvent e) {
				showDefaultCursor();
			}
		});
	}

	@Override
	public void setUI(PanelUI ui) {

		super.setUI(ui);

		this.setEnabled(true);
		this.setBackground(Color.white);
		this.setLabelOrientation(SwingConstants.BOTTOM);
	}

	private void createIcons(URL iconURL) {

		Image srcImg = new ImageIcon(iconURL).getImage();

		coloredIcon = LazSwingUtils.resizeImage(srcImg, WIDTH, HEIGHT);
		grayIcon = GrayFilter.createDisabledImage(coloredIcon);
	}

	private void addComponents() {

		removeAll();

		if (labelOrientation == SwingConstants.LEFT || labelOrientation == SwingConstants.TOP) {

			add(labelPanel);
			add(labelIcon = new LazLabel(new ImageIcon(coloredIcon)));
		}
		else {
			add(labelIcon = new LazLabel(new ImageIcon(coloredIcon)));
			add(labelPanel);
		}
	}

	public String getText() {
		return labelText.getText();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	protected int getLabelOrientation() {
		return labelOrientation;
	}

	protected void setLabelOrientation(int labelOrientation) {
		this.labelOrientation = labelOrientation;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		labelPanel.setBackground(isSelected() && isEnabled() ? SystemColor.activeCaption : Color.white);
		labelText.setForeground(isSelected() && isEnabled() ? Color.white : isEnabled() ? Color.black : Color.gray);
		labelIcon.setIcon(new ImageIcon(isEnabled() ? coloredIcon : grayIcon));
	}
}