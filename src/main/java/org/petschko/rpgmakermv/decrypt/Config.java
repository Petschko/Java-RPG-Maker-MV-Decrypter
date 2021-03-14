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
public class Config {
	// Program Info
	public static final String versionNumber = "0.3.3";
	public static final String version = "v" + versionNumber + " Alpha";
	public static final String programName = "RPG-Maker MV/MZ Decrypter";
	public static final String projectPageURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter";
	public static final String projectBugReportURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/issues";
	public static final String projectLicenceURL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/blob/master/LICENCE";
	public static final String authorImage = "/icons/petschko_icon.png";
	public static final String updateUrl = "https://raw.githubusercontent.com/Petschko/Java-RPG-Maker-MV-Decrypter/master/version.txt";

	// File-Path-Settings
	public static final String defaultOutputDir = "output";
	public static final String preferencesFile = "config.pref";

	// Misc Settings
	public static final long updateCheckEverySecs = 172800;
	public static final String jarFileUpdate = "RPG Maker MV Decrypter.jar";

	/**
	 * Constructor
	 */
	private Config() {
		// VOID - This is a Static-Class
	}
}
