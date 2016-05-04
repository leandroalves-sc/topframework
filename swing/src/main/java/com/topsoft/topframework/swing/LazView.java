package com.topsoft.topframework.swing;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventListener;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alee.laf.rootpane.WebFrame;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazPageEvent;
import com.topsoft.topframework.swing.event.LazPageListener;
import com.topsoft.topframework.swing.event.LazTableEvent;
import com.topsoft.topframework.swing.event.LazTableListener;
import com.topsoft.topframework.swing.util.LazSwingConstants;
import com.topsoft.topframework.swing.util.LazSwingUtils;
import com.topsoft.topframework.swing.util.LazViewKeeper;

import net.miginfocom.swing.MigLayout;

public abstract class LazView extends WebFrame implements ApplicationContextAware, WindowListener, ActionListener, LazTableListener, LazPageListener {

	private static final long serialVersionUID = -8842570179610684297L;
	
	protected ApplicationContext context;

	public LazView() {
		this("", null);
	}

	public LazView(String title) {
		this(title, null);
	}

	public LazView(String title, LayoutManager layout) {

		super(title);

		if (layout != null)
			setLayout(layout);
		
		setRootPane( new LazRootPane( this ) );
	}

	@Override
	protected void frameInit() {

		super.frameInit();

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.addWindowListener(this);

		rootPane.registerKeyboardAction(this, "ESCAPE_PRESSED", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		
		this.context = context;

		createView();
		addListeners();
	}

	public static LazView openView(LazView view) {

		view.setVisible(true);
		view.requestFocus();

		return view;
	}

	@Override
	public void setVisible(boolean visible) {

		if (!isVisible())
			LazSwingUtils.centerWindow(this);

		super.setVisible(visible);

		if (isVisible()) {

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

					JComponent first = LazSwingUtils.getFirstTextComponent(getContentPane());

					if (first != null)
						first.requestFocus();
				}
			});
		}
	}

	@Override
	public void setLayout(LayoutManager layout) {

		if (layout instanceof MigLayout) {

			MigLayout mig = (MigLayout) layout;

			Object lc = mig.getLayoutConstraints();

			if (lc == null)
				lc = "";

			mig.setLayoutConstraints((LazSwingConstants.DEBUG ? "debug" + (lc.toString().length() > 0 ? "," : "") : "") + lc);
		}

		super.setLayout(layout);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void addListeners() {

		if (LazActionDispatcher.class.isAssignableFrom(this.getClass()))
			((LazActionDispatcher) this).addActionListener(this);

		for (Component component : LazSwingUtils.getAllComponents(this))
			if (LazActionDispatcher.class.isAssignableFrom(component.getClass()))
				if (!hasEvent(((LazActionDispatcher) component), this))
					((LazActionDispatcher) component).addActionListener(this);
	}

	public boolean hasEvent(LazActionDispatcher<?> dispatcher, EventListener l) {

		for (EventListener listener : dispatcher.getActionListeners())
			if (listener == l)
				return true;

		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		LazSwingUtils.setEnabled(this, enabled);
	}

	@Override
	public void requestFocus() {

		super.requestFocus();

		if (!isVisible())
			setVisible(true);
	}

	@Override
	public void windowClosed(WindowEvent e) {

		if (getName() != null)
			LazViewKeeper.removeView(getName());
	}

	public void showWaitCursor() {
		LazSwingUtils.showWaitCursor(this);
	}

	public void showDefaultCursor() {
		LazSwingUtils.showDefaultCursor(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if ("ESCAPE_PRESSED".equals(e.getActionCommand()))
			dispose();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void tableEvent(LazTableEvent e) {
	}

	@Override
	public void pageEvent(LazPageEvent e) {
	}

	public abstract void createView();
}