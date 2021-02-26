package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.gui.notification.ErrorWindow;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 11.12.2016
 * Time: 20:02
 * Update: -
 * Version: 0.0.1
 *
 * Notes: App Class
 */
public class App {
	private static Boolean useGUI = true;
	private static GUI gui;
	private static CMD cmd;
	static String outputDir;
	static Preferences preferences;

	/**
	 * Main-Class
	 *
	 * @param args - Optional Arguments from Command-Line
	 */
	public static void main(String[] args) {
		// Check whats given from CMD
		if(args.length > 0) {
			useGUI = false;
			cmd = new CMD(args);
		}

		if(useGUI) {
			// Use GUI
			preferences = new Preferences(Config.preferencesFile);
			outputDir = App.preferences.getConfig(Preferences.lastOutputDir, Config.defaultOutputDir);
			gui = new GUI();
		} else {
			// Use Command-Line Version
			cmd.runCMD();
		}
	}

	/**
	 * Shows the given message if no GUI is enabled
	 *
	 * @param msg - Message to display
	 * @param messageStatus - Status of the Message
	 */
	static void showMessage(String msg, int messageStatus) {
		String status;

		switch(messageStatus) {
			case CMD.STATUS_ERROR:
				status = "[ERROR]: ";
				break;
			case CMD.STATUS_WARNING:
				status = "[WARN]: ";
				break;
			case CMD.STATUS_OK:
				status = "[SUCCESS]: ";
				break;
			default:
				status = "[INFO]: ";
		}

		if(! App.useGUI)
			System.out.println(status + msg);
	}

	/**
	 * Shows the given message if no GUI is enabled
	 *
	 * @param msg - Message to display
	 */
	static void showMessage(String msg) {
		showMessage(msg, CMD.STATUS_INFO);
	}

	/**
	 * Saves the settings, close the GUI and quit the Program
	 */
	static void closeGUI() {
		if(! App.preferences.save()) {
			ErrorWindow errorWindow = new ErrorWindow("Can't save Settings...", ErrorWindow.ERROR_LEVEL_ERROR, false);
			errorWindow.show();
		}

		App.gui.dispose();
		System.exit(0);
	}
}
