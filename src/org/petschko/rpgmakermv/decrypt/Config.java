package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 11.12.2016
 * Time: 21:24
 * Update: -
 * Version: 0.1.0
 *
 * Notes: Config class
 */
class Config {
	// Program Info
	static final String version = "v0.1.3 Alpha";
	static final String programName = "RPG-Maker MV Decrypter";
	static final String projectPageURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter";
	static final String projectBugReportURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/issues";
	static final String projectLicenceURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/blob/master/LICENCE";
	static final String authorImage = "/org/petschko/icons/petschko_icon.png";

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
