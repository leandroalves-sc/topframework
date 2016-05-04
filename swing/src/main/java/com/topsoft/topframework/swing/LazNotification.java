package com.topsoft.topframework.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.alee.extended.time.ClockType;
import com.alee.extended.time.WebClock;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotificationPopup;
import com.topsoft.topframework.swing.util.LazSwingUtils;

public class LazNotification {

	public static void showNotification(String content) {
		showNotification(null, content, NotificationIcon.information, true);
	}

	public static void showNotification(Component comp, String content, NotificationIcon icon) {
		showNotification(comp, content, icon, true);
	}

	public static void showNotification(String content, NotificationIcon icon) {
		showNotification(null, content, icon, true);
	}

	public static void showNotification(String content, boolean autoHide) {
		showNotification(content, NotificationIcon.information, autoHide);
	}

	public static void showNotification(String content, NotificationIcon icon, boolean autoHide) {
		showNotification(null, content, icon, autoHide);
	}

	public static void showNotification(Component comp, String content, NotificationIcon icon, boolean autoHide) {
		showNotification(comp, content, icon, autoHide ? 3500 : 0);
	}

	public static void showNotification(Component comp, String content, NotificationIcon icon, int hideInMillis) {

		final WebNotificationPopup notificationPopup = new WebNotificationPopup();
		notificationPopup.setIcon(icon);
		notificationPopup.setContent(content);

		WebClock clock = null;

		if (hideInMillis > 0) {

			clock = new WebClock();
			clock.setClockType(ClockType.timer);
			clock.setTimeLeft(hideInMillis);
			clock.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					notificationPopup.hidePopup();
				}
			});
		}

		Component parent = null;

		if (comp != null) {

			LazFormView<?> view = LazSwingUtils.getParent(comp, LazFormView.class);

			if (view != null)
				parent = view.getCaller();

			if (parent == null)
				parent = LazSwingUtils.getParent(comp.getParent(), JFrame.class);
		}

		if (parent != null)
			NotificationManager.showNotification(parent, notificationPopup);
		else
			NotificationManager.showNotification(notificationPopup);

		if (hideInMillis > 0 && clock != null)
			clock.start();
	}
}
