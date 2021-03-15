package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;
import org.petschko.lib.File;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.rpgmakermv.decrypt.cmd.CMD;
import org.petschko.rpgmakermv.decrypt.gui.GUI;

/**
 * @author Peter Dragicevic
 */
public class App {
	private static Boolean useGUI = true;
	private static GUI gui;
	private static CMD cmd;
	public static String outputDir;
	public static Preferences preferences;

	/**
	 * Main-Class
	 *
	 * @param args - Optional Arguments from Command-Line
	 */
	public static void main(String[] args) {
		// Ensure System output dir always exists
		if(! File.existsDir(Config.DEFAULT_OUTPUT_DIR))
			File.createDirectory(Config.DEFAULT_OUTPUT_DIR);

		// Check whats given from CMD
		if(args.length > 0) {
			useGUI = false;
			cmd = new CMD(args);
		}

		if(useGUI) {
			// Show something when its started via .bat or shell file
			System.out.println(Config.PROGRAM_NAME + " - " + Config.VERSION + " by " + Const.CREATOR);

			// Use GUI
			preferences = new Preferences(Config.PREFERENCES_FILE);
			outputDir = App.preferences.getConfig(Preferences.LAST_OUTPUT_DIR, Config.DEFAULT_OUTPUT_DIR);
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
	public static void showMessage(String msg, int messageStatus) {
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
	public static void showMessage(String msg) {
		showMessage(msg, CMD.STATUS_INFO);
	}

	/**
	 * Saves the settings, close the GUI and quit the Program
	 */
	public static void closeGUI() {
		if(! App.preferences.save()) {
			ErrorWindow errorWindow = new ErrorWindow("Can't save Settings...", ErrorWindow.ERROR_LEVEL_ERROR, false);
			errorWindow.show();
		}

		App.gui.dispose();
		System.exit(0);
	}
}
