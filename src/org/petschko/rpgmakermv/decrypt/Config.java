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
	static final String version = "v0.1 Alpha";
	static final String programName = "RPG-Maker MV Decrypter";
	static final String projectPageURL = "http://petschko.org";
	static final String projectBugReportURL = "http://petschko.org/issues/";

	// File-Path-Settings
	static final String defaultOutputDir = "output";
	static final String preferencesFile = "config.pref";

	/**
	 * Constructor
	 */
	private Config() {
		// VOID - This is a Static-Class
	}
}
