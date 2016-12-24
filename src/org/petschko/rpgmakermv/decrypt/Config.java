package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 11.12.2016
 * Time: 21:24
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Config class
 */
class Config {
	// Program Info
	static final String creator = "Petschko";
	static final String version = "v0.1 Alpha";
	static final String programmName = "RPG-Maker MV Decrypter";

	// System-Settings
	static final String ds = System.getProperty("file.separator");

	// File-Path-Settings
	static final String defaultOutputDir = "output";

	/**
	 * Constructor
	 */
	private Config() {
		// VOID - This is a Static-Class
	}
}
