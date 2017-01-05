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
 * Notes: Class ErrorWindow
 */
public class ErrorWindow extends NotificationWindow {
	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param errorLevel - Error-Level of this Notification
	 * @param stopProgram - Stop Program when Displaying this
	 */
	public ErrorWindow(@NotNull String message, int errorLevel, boolean stopProgram) {
		super(message, null, errorLevel, stopProgram);
	}

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param errorLevel - Error-Level of this Notification
	 * @param stopProgram - Stop Program when Displaying this
	 * @param e - Exception of this Notification
	 */
	public ErrorWindow(@NotNull String message, int errorLevel, boolean stopProgram, @Nullable Exception e) {
		super(message, null, errorLevel, stopProgram, e);
	}

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param title - Title for this Notification
	 * @param errorLevel - Error-Level of this Notification
	 * @param stopProgram - Stop Program when Displaying this
	 * @param e - Exception of this Notification
	 */
	public ErrorWindow(@NotNull String message, @Nullable String title, int errorLevel, boolean stopProgram, @Nullable Exception e) {
		super(message, title, errorLevel, stopProgram, e);
	}
}
