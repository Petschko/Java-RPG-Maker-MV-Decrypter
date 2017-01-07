package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;
import org.petschko.lib.gui.notification.ErrorWindow;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
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
	static String pathToProject;
	static String outputDir;
	static Preferences preferences;

	/**
	 * Main-Class
	 *
	 * @param args - Optional Arguments from Command-Line
	 */
	public static void main(String[] args) {
		// Check whats given from CMD
		if(args.length > 0)
			App.processArgs(args);

		if(App.useGUI) {
			// Use GUI
			App.preferences = new Preferences(Config.preferencesFile);
			App.outputDir = App.preferences.getConfig(Preferences.lastOutputDir, Config.defaultOutputDir);
			App.gui = new GUI();
		} else {
			// Use Command-Line Version
			try {
				RPGProject rpgProject = new RPGProject(App.pathToProject, false);
				Decrypter decrypter = new Decrypter();

				rpgProject.setOutputPath(App.outputDir);
				decrypter.setIgnoreFakeHeader(true);
				rpgProject.decryptFiles(decrypter);
			} catch(Exception e) {
				e.printStackTrace();
			}

			App.exitCMD(0);
		}
	}

	/**
	 * Process Command-Line Arguments
	 *
	 * @param args - Optional Arguments from Command-Line
	 */
	private static void processArgs(String[] args) {
		// Don't use GUI if using Command-Line
		App.useGUI = false;

		// Show Welcome-Message
		System.out.println(Config.programName + " - " + Config.version + " by " + Const.creator + " | Command-Line Version");

		// Check if help is needed
		if(args[0].equals("help") || args[0].equals("/?") || args[0].equals("--help")) {
			App.printHelp();
			App.exitCMD(0);
		}

		// Set Path to Project
		App.pathToProject = args[0];
		App.showMessage("Set Project-Dir to: \"" + App.pathToProject + "\"");

		// Set Output-Dir
		try {
			App.outputDir = args[1];
		} catch(ArrayIndexOutOfBoundsException arEx) {
			App.outputDir = Config.defaultOutputDir;
		}

		App.showMessage("Set Output-Dir to: \"" + App.outputDir + "\"");
	}

	/**
	 * Prints help for Command-Line usage
	 */
	private static void printHelp() {
		System.out.println("Usage: java -jar \"RPG Maker MV Decrypter.jar\" [path to decrypt project] [(optional) output path]");
	}

	/**
	 * Exit the Program with a Message
	 *
	 * @param status - Exit-Status-Code
	 */
	private static void exitCMD(int status) {
		System.out.println("Done.");
		System.exit(status);
	}

	/**
	 * Shows the given message if no GUI is enabled
	 *
	 * @param msg - Message to display
	 */
	static void showMessage(String msg) {
		if(! App.useGUI)
			System.out.println(msg);
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
