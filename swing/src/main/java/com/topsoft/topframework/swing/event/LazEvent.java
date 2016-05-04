package com.topsoft.topframework.swing.event;

import java.awt.AWTEvent;
import java.awt.Container;
import java.util.EventListener;

import com.topsoft.topframework.base.exception.BusinessException;
import com.topsoft.topframework.swing.util.LazSwingUtils;

public abstract class LazEvent<T extends EventListener> extends AWTEvent {

	private static final long serialVersionUID = -4216470531342603863L;

	public LazEvent(Object comp, Integer id) {
		super(comp, id);
	}

	@SuppressWarnings("unchecked")
	public Class<T> getListenerClass() {
		return (Class<T>) this.getClass();
	}

	public static <T extends EventListener> void dispatchEvent(LazActionDispatcher<T> dispatcher, LazEvent<T> lazEvent) {

		try {

			if (Container.class.isAssignableFrom(lazEvent.getSource().getClass()))
				LazSwingUtils.showWaitCursor((Container) lazEvent.getSource());

			for (T event : dispatcher.getActionListeners())
				lazEvent.dispatchEvent(event);
		}
		catch (BusinessException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {

			if (Container.class.isAssignableFrom(lazEvent.getSource().getClass()))
				LazSwingUtils.showDefaultCursor((Container) lazEvent.getSource());
		}
	}

	public abstract void dispatchEvent(T listener);
}