package org.petschko.lib.gui.notification;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
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
	public InfoWindow(@NotNull String message) {
		super(message, null);
	}

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param title - Title for this Notification
	 */
	public InfoWindow(@NotNull String message, @Nullable String title) {
		super(message, title);
	}
}
