package com.topsoft.topframework.swing;

import java.awt.Component;

import javax.swing.Icon;

import com.alee.laf.tabbedpane.WebTabbedPane;
import com.topsoft.topframework.base.util.SystemUtil;

import net.miginfocom.swing.MigLayout;

public class LazTabbedPane extends WebTabbedPane {

	private static final long serialVersionUID = 7521214563477419681L;

	@Override
	public void addTab(String title, Component component) {

		fixInsets(component);
		super.addTab(title, component);
	}

	@Override
	public void insertTab(String title, Icon icon, Component component, String tip, int index) {

		fixInsets(component);
		super.insertTab(title, icon, component, tip, index);
	}

	private void fixInsets(Component component) {

		if (LazPanel.class.isAssignableFrom(component.getClass())) {

			LazPanel panel = (LazPanel) component;

			if (MigLayout.class.isAssignableFrom(panel.getLayout().getClass())) {

				MigLayout mig = (MigLayout) panel.getLayout();

				String lc = SystemUtil.NVL(mig.getLayoutConstraints());

				if (lc != null && !lc.contains("ins "))
					lc += (lc.toString().length() > 0 ? "," : "") + " ins 10";

				mig.setLayoutConstraints(lc);
			}
		}
	}
}