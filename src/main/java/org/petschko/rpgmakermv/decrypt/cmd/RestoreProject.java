package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.rpgmakermv.decrypt.App;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:37
 *
 * Notes: RestoreProject Class
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
		App.showMessage("Usage: java -jar \"RPG Maker MV Decrypter.jar\" restoreproject");
		App.showMessage("");
	}
}
