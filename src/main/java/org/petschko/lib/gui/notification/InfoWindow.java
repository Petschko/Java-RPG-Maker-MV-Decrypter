package org.petschko.lib.gui.notification;

/**
 * @author Peter Dragicevic
 */
public class InfoWindow extends NotificationWindow {
	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 */
	public InfoWindow(String message) {
		super(message, null);
	}

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param title - Title for this Notification
	 */
	public InfoWindow(String message, String title) {
		super(message, title);
	}
}
