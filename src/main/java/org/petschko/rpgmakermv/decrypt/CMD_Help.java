package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:34
 *
 * Notes: CMD_Help Class
 */
class CMD_Help implements CMD_Command {
	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	@Override
	public void run(String[] args) {
		App.showMessage("Invalid Command \"" + args[1] + "\"!", CMD.STATUS_WARNING);
		App.showMessage("");
		printHelp();
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("Help:");
		App.showMessage("");
		App.showMessage("Usage: java -jar \"RPG Maker MV Decrypter.jar\" [command] [help|args...]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Commands:");
		App.showMessage(CMD.HELP_INDENT + "  help    - Shows this message");
		App.showMessage(CMD.HELP_INDENT + "  decrypt - Decrypts Files of a Game-Directory");
		App.showMessage(CMD.HELP_INDENT + "  encrypt - (Re-)Encrypts resource Files");
		App.showMessage(CMD.HELP_INDENT + "  restore - Restores images of a Game-Directory");
		//App.showMessage(CMD.HELP_INDENT + "  restoreproject - Restores a RPG-MV/MZ Project (Makes it editable again)");
		App.showMessage(CMD.HELP_INDENT + "  key     - Detects the Key and Displays it");
		//App.showMessage(CMD.HELP_INDENT + "  open - Opens an encrypted Image");
		App.showMessage(CMD.HELP_INDENT + "  update - Updates this Program");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Display detailed help for each command:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" [command] help");
		App.showMessage("");
	}
}
