package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 22:59
 *
 * Notes: CMD_Command Class
 */
interface CMD_Command {
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
