package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;

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
	static Boolean useGUI = true;
	static String pathToProject;
	static String outputDir;

	/**
	 * Main-Class
	 *
	 * @param args - Optional Arguments from Command-Line
	 */
	public static void main(String[] args) {
		// Check whats given from CMD
		if(args.length > 0)
			processArgs(args);

		if(useGUI) {
			// todo: load gui
			System.out.print("VOID");
		} else {
			// todo: Decrypt without GUI
			// todo check if output dirs really exists - better within class
			try {
				RPGProject rpgProject = new RPGProject(pathToProject);
				rpgProject.setOutputPath(outputDir);
				rpgProject.decryptFiles(false);
			} catch(Exception e) {
				e.printStackTrace();
			}

			exitCMD(0);
		}
	}

	/**
	 * Process Command-Line Arguments
	 *
	 * @param args - Optional Arguments from Command-Line
	 */
	private static void processArgs(String[] args) {
		// Don't use GUI if using Command-Line
		useGUI = false;

		// Show Welcome-Message
		System.out.println(Config.programName + " - " + Config.version + " by " + Const.creator + " | Command-Line Version");

		// Check if help is needed
		if(args[0].equals("help") || args[0].equals("/?") || args[0].equals("--help")) {
			printHelp();
			exitCMD(0);
		}

		// Set Path to Project
		pathToProject = args[0];
		System.out.println("Set Project-Dir to: \"" + pathToProject + "\"");

		// Set Output-Dir
		try {
			outputDir = args[1];
		} catch(ArrayIndexOutOfBoundsException arEx) {
			outputDir = Config.defaultOutputDir;
		}

		System.out.println("Set Output-Dir to: \"" + outputDir + "\"");
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
	public static void exitCMD(int status) {
		System.out.println("Done.");
		System.exit(status);
	}

	/**
	 * Shows the given message if no GUI is enabled
	 *
	 * @param msg - Message to display
	 */
	public static void showMessage(String msg) {
		if(useGUI)
			System.out.println(msg);
	}
}
