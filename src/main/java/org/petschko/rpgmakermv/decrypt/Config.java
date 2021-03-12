package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 11.12.2016
 * Time: 21:24
 * Update: 04.05.2019
 * Version: 0.1.1
 *
 * Notes: Config class
 */
class Config {
	// Program Info
	static final String versionNumber = "0.3.0";
	static final String version = "v" + versionNumber + " Alpha";
	static final String programName = "RPG-Maker MV/MZ Decrypter";
	static final String projectPageURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter";
	static final String projectBugReportURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/issues";
	static final String projectLicenceURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/blob/master/LICENCE";
	static final String authorImage = "/org/petschko/icons/petschko_icon.png";
	static final String updateUrl = "https://raw.githubusercontent.com/Petschko/Java-RPG-Maker-MV-Decrypter/master/version.txt";

	// File-Path-Settings
	static final String defaultOutputDir = "output";
	static final String preferencesFile = "config.pref";

	// Misc Settings
	static final long updateCheckEverySecs = 172800;
	static final String jarFileUpdate = "RPG Maker MV Decrypter.jar";

	/**
	 * Constructor
	 */
	private Config() {
		// VOID - This is a Static-Class
	}
}
