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
	static final String LINE_CMD = "------------------------------------------------------------------------------";
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
	private static final String UPDATE = "update";

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
		System.out.println(LINE_CMD);
		System.out.println(Config.programName + " - " + Config.version + " by " + Const.creator + " | Command-Line Version");
		System.out.println(LINE_CMD);

		CMD_Update.checkForUpdates();

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
			// Lowercase everything for help & update
			if(isHelpCmd(args[0].toLowerCase().trim()) || isHelpCmd(args[1].toLowerCase().trim()) || args[0].toLowerCase().equals(UPDATE))
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
			case UPDATE:
				cmdHandler = new CMD_Update();
				break;
			default:
				boolean invalidCommand = false;
				if(! isHelpCmd(args[0]))
					invalidCommand = true;

				cmdHandler = new CMD_Help();

				// Help is handles a bit different
				if(invalidCommand)
					cmdHandler.run(args);
				else {
					// Check if 2nd param is command so make this an alias too
					if(subCommand != null) {
						printHelpForCmd(subCommand);
					} else
						cmdHandler.printHelp();
				}

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
	 * Prints out the help for a specific command
	 *
	 * @param command - Command to print help of
	 */
	private void printHelpForCmd(String command) {
		CMD_Command cmdCommand = new CMD_Help();

		switch(command) {
			case CMD_OPEN_IMG:
				cmdCommand = new CMD_Open();
				break;
			case CMD_DECRYPT:
				cmdCommand = new CMD_Decrypt();
				break;
			case CMD_ENCRYPT:
				cmdCommand = new CMD_Encrypt();
				break;
			case CMD_RESTORE:
				cmdCommand = new CMD_Restore();
				break;
			case CMD_RESTORE_PROJECT:
				cmdCommand = new CMD_RestoreProject();
				break;
			case CMD_GET_KEY:
			case CMD_GET_KEY_2:
			case CMD_GET_KEY_3:
			case CMD_GET_KEY_4:
			case CMD_GET_KEY_5:
				cmdCommand = new CMD_DetectKey();
				break;
			default:
				// Void
		}

		cmdCommand.printHelp();
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
