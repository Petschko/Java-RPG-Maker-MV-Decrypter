package org.petschko.lib.gui.notification;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Peter Dragicevic
 */
abstract class NotificationWindow {
	public static final int ERROR_LEVEL_FATAL = 4;
	public static final int ERROR_LEVEL_ERROR = 3;
	public static final int ERROR_LEVEL_WARNING = 2;
	public static final int ERROR_LEVEL_NOTICE = 1;
	public static final int ERROR_LEVEL_INFO = 0;

	private int errorLevel;
	private String title;
	private String message;
	private boolean stopProgram = false;
	private Exception e = null;

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param title - Title for this Notification
	 */
	NotificationWindow(String message, String title) {
		this.errorLevel = ERROR_LEVEL_INFO;
		this.setTitle(title);
		this.message = message;
	}

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param title - Title for this Notification
	 * @param errorLevel - Error-Level of this Notification
	 */
	NotificationWindow(String message, String title, int errorLevel) {
		this.errorLevel = errorLevel;
		this.setTitle(title);
		this.message = message;
	}

	/**
	 * NotificationWindow Constructor
	 *
	 * @param message - Message for this Notification
	 * @param title - Title for this Notification
	 * @param errorLevel - Error-Level of this Notification
	 * @param stopProgram - Stop Program when Displaying this
	 */
	NotificationWindow(String message, String title, int errorLevel, boolean stopProgram) {
		this.errorLevel = errorLevel;
		this.setTitle(title);
		this.message = message;
		this.stopProgram = stopProgram;
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
	NotificationWindow(String message, String title, int errorLevel, boolean stopProgram, Exception e) {
		this.errorLevel = errorLevel;
		this.setTitle(title);
		this.message = message;
		this.stopProgram = stopProgram;
		this.e = e;
	}

	/**
	 * Returns the Error-Level for this Notification
	 *
	 * @return - Error-Level for this Notification
	 */
	public int getErrorLevel() {
		return errorLevel;
	}

	/**
	 * Set the Error-Level for this Notification
	 *
	 * @param errorLevel - Error-Level for this Notification
	 */
	protected void setErrorLevel(int errorLevel) {
		this.errorLevel = errorLevel;
	}

	/**
	 * Returns the Title for this Notification
	 *
	 * @return - Title for this Notification
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Title for this Notification
	 *
	 * @param title - Title for this Notification
	 */
	public void setTitle(String title) {
		if(title == null)
			this.setDefaultTitle();
		else
			this.title = title;
	}

	/**
	 * Returns the Message that will shown
	 *
	 * @return - Notification Message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the Message for this Notification
	 *
	 * @param message - Message to display
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns if the Program will stop when showing this Dialog
	 *
	 * @return - true if the Program will stop on showing else false
	 */
	public boolean isStopProgram() {
		return stopProgram;
	}

	/**
	 * Set if the Program should stop if the Window is shown
	 *
	 * @param stopProgram - true if the Program should stop on showing else false
	 */
	public void setStopProgram(boolean stopProgram) {
		this.stopProgram = stopProgram;
	}

	/**
	 * Returns the Exception of the Dialog
	 *
	 * @return - Exception or null
	 */
	public Exception getE() {
		return e;
	}

	/**
	 * Sets the Exception for this Dialog
	 *
	 * @param e - Exception
	 */
	public void setE(Exception e) {
		this.e = e;
	}

	/**
	 * Sets the Title depending on the Error-Level
	 */
	protected void setDefaultTitle() {
		switch(this.errorLevel) {
			case ERROR_LEVEL_NOTICE:
				this.title = "Notice";
				break;
			case ERROR_LEVEL_WARNING:
				this.title = "Warning";
				break;
			case ERROR_LEVEL_ERROR:
				this.title = "Error";
				break;
			case ERROR_LEVEL_FATAL:
				this.title = "Fatal-Error";
				break;
			case ERROR_LEVEL_INFO:
			default:
				this.title = "Info";
		}
	}

	/**
	 * Returns the JOptionPane Type depending on the Error-Level
	 *
	 * @return - JOptionPane Type
	 */
	protected int getJOptionType() {
		switch(this.errorLevel) {
			case ERROR_LEVEL_WARNING:
				return JOptionPane.WARNING_MESSAGE;
			case ERROR_LEVEL_ERROR:
			case ERROR_LEVEL_FATAL:
				return JOptionPane.ERROR_MESSAGE;
			case ERROR_LEVEL_NOTICE:
			case ERROR_LEVEL_INFO:
			default:
				return JOptionPane.INFORMATION_MESSAGE;
		}
	}

	/**
	 * Returns the Message with Exception-Trace if there is an Exception set
	 *
	 * @return - Full-Message
	 */
	protected String getFullMessage() {
		if(this.e != null) {
			StringWriter stringWriter = new StringWriter();
			this.e.printStackTrace(new PrintWriter(stringWriter));

			return this.message + " | Exception-Trace: " + stringWriter.toString();
		}

		return this.message;
	}

	/**
	 * Shows this Notification-Window
	 */
	public void show() {
		this.show(null);
	}

	/**
	 * Shows this Notification-Window
	 *
	 * @param parentComponent - Parent Component or null for none
	 */
	public void show(Component parentComponent) {
		JOptionPane.showConfirmDialog(parentComponent, this.getFullMessage(), this.getTitle(), JOptionPane.DEFAULT_OPTION, this.getJOptionType());
	}
}
