package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;

/**
 * @author Peter Dragicevic
 */
class RestoreProject implements I_CMD {
	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	@Override
	public void run(String[] args) {
		printHelp();
		CMD.exitCMD(CMD.STATUS_WARNING);
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("restoreproject -> !! NOT IMPLEMENTED YET !!", CMD.STATUS_WARNING);
		App.showMessage("");
		App.showMessage("Usage: java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" restoreproject");
		App.showMessage("");
	}
}
