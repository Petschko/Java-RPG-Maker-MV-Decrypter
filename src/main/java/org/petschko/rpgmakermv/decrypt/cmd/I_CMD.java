package org.petschko.rpgmakermv.decrypt.cmd;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 22:59
 *
 * Notes: I_CMD
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
