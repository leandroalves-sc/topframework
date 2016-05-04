package com.topsoft.topframework.swing;

import java.awt.Component;

import com.topsoft.topframework.swing.event.LazFormListener;
import com.topsoft.topframework.swing.util.LazViewKeeper;

import net.miginfocom.swing.MigLayout;

public class LazFormView<E extends Object> extends LazView {

	private static final long serialVersionUID = 3976443079627181383L;

	private Component caller;

	protected LazFormView(LazForm<E> form, E dto) {

		super("", new MigLayout("fill, wrap 1", "[grow,fill]", "[grow,fill]5[baseline]"));

		add(form, "grow");
	}

	public static <E extends Object> LazFormView<E> openForm(LazForm<E> form, E dto) {
		return openForm(null, form, dto);
	}

	public static <E extends Object> LazFormView<E> openForm(LazForm<E> form, E dto, boolean readOnly) {
		return openForm(null, form, dto, readOnly);
	}

	public static <E extends Object> LazFormView<E> openForm(Component caller, LazForm<E> form, E dto) {
		return openForm(caller, form, dto, false);
	}

	@SuppressWarnings("unchecked")
	public static <E extends Object> LazFormView<E> openForm(Component caller, LazForm<E> form, E dto, boolean readOnly) {

		LazFormView<E> view = (LazFormView<E>) LazViewKeeper.getViewFor(form);

		if (view == null)
			LazViewKeeper.addView(form, view = new LazFormView<E>(form, dto));

		if (caller != null && LazFormListener.class.isAssignableFrom(caller.getClass())) {

			view.caller = caller;

			LazFormListener listener = (LazFormListener) caller;

			if (!form.hasEvent(listener))
				form.addActionListener(listener);
		}

		form.refreshFor(dto, readOnly);

		if (LazViewCapable.class.isAssignableFrom(form.getClass())) {

			LazViewCapable<?> formView = (LazViewCapable<?>) form;
			view.setResizable(formView.isResizable());
			view.setTitle(formView.getTitle());
			view.setSize(formView.getSize());
		}

		view.setVisible(true);
		view.requestFocus();

		return view;
	}

	public Component getCaller() {
		return caller;
	}

	@Override
	public void createView() {
	}
}