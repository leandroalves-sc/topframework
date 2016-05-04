package com.topsoft.topframework.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.plaf.ScrollPaneUI;

import com.alee.laf.scroll.WebScrollPane;

public class LazScrollPane extends WebScrollPane {

	private static final long serialVersionUID = 6222048206230106231L;

	public LazScrollPane(Component c) {

		super(c);
	}

	@Override
	public void setUI(ScrollPaneUI ui) {

		super.setUI(ui);

		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (getViewport() != null)
			getViewport().setBackground(Color.white);
	}
}