package org.petschko.lib.gui.notification;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 05.01.2017
 * Time: 14:01
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Class InfoWindow
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
