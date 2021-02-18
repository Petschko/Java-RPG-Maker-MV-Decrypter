package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 22:11
 *
 * Notes: CMD Class
 */
class CMD {
	static final int STATUS_OK = 0;
	static final int STATUS_INFO = 2;
	static final int STATUS_WARNING = 1;
	static final int STATUS_ERROR = -1;
	static final String HELP_INDENT = "  ";
	private static final String CMD_HELP = "help";
	private static final String CMD_HELP_2 = "-help";
	private static final String CMD_HELP_3 = "--help";
	private static final String CMD_HELP_4 = "/?";
	private static final String CMD_OPEN_IMG = "open";
	private static final String CMD_DECRYPT = "decrypt";
	private static final String CMD_ENCRYPT = "encrypt";
	private static final String CMD_RESTORE = "restore";
	private static final String CMD_RESTORE_PROJECT = "restoreproject";
	private static final String CMD_GET_KEY = "key";
	private static final String CMD_GET_KEY_2 = "detect";
	private static final String CMD_GET_KEY_3 = "getkey";
	private static final String CMD_GET_KEY_4 = "detectkey";
	private static final String CMD_GET_KEY_5 = "keydetect";

	private final String[] args;

	/**
	 * CMD Constructor
	 *
	 * @param args - CMD Args
	 */
	CMD(String[] args) {
		this.args = args;
	}

	/**
	 * Runs the Command
	 */
	void runCMD() {
		// Show Welcome-Message
		System.out.println("------------------------------------------------------------------------------");
		System.out.println(Config.programName + " - " + Config.version + " by " + Const.creator + " | Command-Line Version");
		System.out.println("------------------------------------------------------------------------------");

		sanitizeArgs();
		processArgs();

		exitCMD(0);
	}

	/**
	 * Sanitize the first args
	 */
	private void sanitizeArgs() {
		args[0] = args[0].toLowerCase().trim();
		args[0] = args[0].replaceAll("-", "");
		args[0] = args[0].replaceAll("/", "");

		if(args.length >= 2) {
			if(isHelpCmd(args[1].toLowerCase().trim()))
				args[1] = args[1].toLowerCase().trim();
		}
	}

	/**
	 * Process Command-Line Arguments
	 */
	private void processArgs() {
		// Get the Sub command
		String subCommand;
		CMD_Command cmdHandler;
		try {
			subCommand = args[1].toLowerCase();
		} catch(ArrayIndexOutOfBoundsException arEx) {
			subCommand = null;
		}


		switch(args[0]) {
			case CMD_OPEN_IMG:
				cmdHandler = new CMD_Open();
				break;
			case CMD_DECRYPT:
				cmdHandler = new CMD_Decrypt();
				break;
			case CMD_ENCRYPT:
				cmdHandler = new CMD_Encrypt();
				break;
			case CMD_RESTORE:
				cmdHandler = new CMD_Restore();
				break;
			case CMD_RESTORE_PROJECT:
				cmdHandler = new CMD_RestoreProject();
				break;
			case CMD_GET_KEY:
			case CMD_GET_KEY_2:
			case CMD_GET_KEY_3:
			case CMD_GET_KEY_4:
			case CMD_GET_KEY_5:
				cmdHandler = new CMD_DetectKey();
				break;
			default:
				boolean invalidCommand = false;
				if(! isHelpCmd(args[0]))
					invalidCommand = true;

				cmdHandler = new CMD_Help();

				// Help is handles a bit different
				if(invalidCommand)
					cmdHandler.run(args);
				else
					cmdHandler.printHelp();

				exitCMD(invalidCommand ? STATUS_WARNING : STATUS_OK);
				return;
		}

		if(isHelpCmd(subCommand))
			cmdHandler.printHelp();
		else
			cmdHandler.run(args);
	}

	/**
	 * Shows if the current command is a help command
	 *
	 * @return - Is the command a help command
	 */
	private boolean isHelpCmd(String command) {
		if(command == null)
			return false;

		return command.equals(CMD_HELP) || command.equals(CMD_HELP_2) || command.equals(CMD_HELP_3) || command.equals(CMD_HELP_4);
	}

	/**
	 * Exit the Program with a Message
	 *
	 * @param status - Exit-Status-Code
	 */
	static void exitCMD(int status) {
		App.showMessage("Done.");
		System.exit(status);
	}
}
