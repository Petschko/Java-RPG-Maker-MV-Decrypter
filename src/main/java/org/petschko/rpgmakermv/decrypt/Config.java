package org.petschko.rpgmakermv.decrypt;

/**
 * @author Peter Dragicevic
 */
public class Config {
	// Program Info
	public static final String VERSION_NUMBER = "0.3.3";
	public static final String VERSION = "v" + VERSION_NUMBER + " Alpha";
	public static final String PROGRAM_NAME = "RPG-Maker MV/MZ Decrypter";
	public static final String PROJECT_PAGE_URL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter";
	public static final String PROJECT_BUG_REPORT_URL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/issues";
	public static final String PROJECT_LICENCE_URL = "https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/blob/master/LICENCE";
	public static final String AUTHOR_IMAGE = "/icons/petschko_icon.png";
	public static final String UPDATE_URL = "https://raw.githubusercontent.com/Petschko/Java-RPG-Maker-MV-Decrypter/master/version.txt";

	// File-Path-Settings
	public static final String DEFAULT_OUTPUT_DIR = "output";
	public static final String PREFERENCES_FILE = "config.pref";

	// Misc Settings
	public static final long UPDATE_CHECK_EVERY_SECS = 172800;
	public static final String THIS_JAR_FILE_NAME = "RPG Maker MV Decrypter.jar";

	/**
	 * Constructor
	 */
	private Config() {
		// VOID - This is a Static-Class
	}
}
