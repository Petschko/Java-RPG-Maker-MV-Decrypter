package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.lib.Const;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;

/**
 * @author Peter Dragicevic
 */
public class CMD {
	public static final int STATUS_OK = 0;
	public static final int STATUS_INFO = 2;
	public static final int STATUS_WARNING = 1;
	public static final int STATUS_ERROR = -1;
	public static final String HELP_INDENT = "  ";
	public static final String LINE_CMD = "------------------------------------------------------------------------------";
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
	public CMD(String[] args) {
		this.args = args;
	}

	/**
	 * Runs the Command
	 */
	public void runCMD() {
		// Show Welcome-Message
		System.out.println(LINE_CMD);
		System.out.println(Config.PROGRAM_NAME + " - " + Config.VERSION + " by " + Const.CREATOR + " | Command-Line Version");
		System.out.println(LINE_CMD);

		Update.checkForUpdates();

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
		I_CMD cmdHandler;
		try {
			subCommand = args[1].toLowerCase();
		} catch(ArrayIndexOutOfBoundsException arEx) {
			subCommand = null;
		}


		switch(args[0]) {
			case CMD_OPEN_IMG:
				cmdHandler = new Open();
				break;
			case CMD_DECRYPT:
				cmdHandler = new Decrypt();
				break;
			case CMD_ENCRYPT:
				cmdHandler = new Encrypt();
				break;
			case CMD_RESTORE:
				cmdHandler = new Restore();
				break;
			case CMD_RESTORE_PROJECT:
				cmdHandler = new RestoreProject();
				break;
			case CMD_GET_KEY:
			case CMD_GET_KEY_2:
			case CMD_GET_KEY_3:
			case CMD_GET_KEY_4:
			case CMD_GET_KEY_5:
				cmdHandler = new DetectKey();
				break;
			case UPDATE:
				cmdHandler = new Update();
				break;
			default:
				boolean invalidCommand = false;
				if(! isHelpCmd(args[0]))
					invalidCommand = true;

				cmdHandler = new Help();

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
		I_CMD cmdCommand = new Help();

		switch(command) {
			case CMD_OPEN_IMG:
				cmdCommand = new Open();
				break;
			case CMD_DECRYPT:
				cmdCommand = new Decrypt();
				break;
			case CMD_ENCRYPT:
				cmdCommand = new Encrypt();
				break;
			case CMD_RESTORE:
				cmdCommand = new Restore();
				break;
			case CMD_RESTORE_PROJECT:
				cmdCommand = new RestoreProject();
				break;
			case CMD_GET_KEY:
			case CMD_GET_KEY_2:
			case CMD_GET_KEY_3:
			case CMD_GET_KEY_4:
			case CMD_GET_KEY_5:
				cmdCommand = new DetectKey();
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
