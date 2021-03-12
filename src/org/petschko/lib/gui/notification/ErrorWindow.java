package org.petschko.lib.gui.notification;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
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
	public ErrorWindow(String message, int errorLevel, boolean stopProgram) {
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
	public ErrorWindow(String message, int errorLevel, boolean stopProgram, Exception e) {
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
	public ErrorWindow(String message, String title, int errorLevel, boolean stopProgram, Exception e) {
		super(message, title, errorLevel, stopProgram, e);
	}
}
