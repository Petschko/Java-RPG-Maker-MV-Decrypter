package org.petschko.rpgmakermv.decrypt.cmd;

/**
 * @author Peter Dragicevic
 */
interface I_CMD {
	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	void run(String[] args);

	/**
	 * Prints help for the command
	 */
	void printHelp();
}
