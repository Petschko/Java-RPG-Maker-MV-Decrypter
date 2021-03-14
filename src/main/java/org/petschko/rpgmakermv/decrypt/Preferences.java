package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.UserPref;

import java.util.Properties;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 30.12.2016
 * Time: 22:32
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Preferences Class
 */
class Preferences extends UserPref {
	static final String IGNORE_FAKE_HEADER = "ignoreFakeHeader";
	static final String LOAD_INVALID_RPG_DIRS = "loadInvalidRPGDirs";
	static final String OVERWRITE_FILES = "overwriteFiles";
	static final String CLEAR_OUTPUT_DIR_BEFORE_DECRYPT = "clearOutputDirBeforeDecrypt";
	static final String LAST_OUTPUT_DIR = "lastOutputDir";
	static final String LAST_OUTPUT_PARENT_DIR = "lastOutputParentDir";
	static final String LAST_RPG_DIR = "lastRPGParentDir";
	static final String DECRYPTER_VERSION = "decrypterVersion";
	static final String DECRYPTER_REMAIN = "decrypterRemain";
	static final String DECRYPTER_SIGNATURE = "decrypterSignature";
	static final String DECRYPTER_HEADER_LEN = "decrypterHeaderLen";
	static final String AUTO_CHECK_FOR_UPDATES = "autoCheckForUpdates";

	/**
	 * UserPrefs Constructor
	 *
	 * @param filePath - File-Path to UserPref-File
	 */
	Preferences(String filePath) {
		super(filePath);
	}

	/**
	 * Load the default-values for Preferences
	 *
	 * @return - true on success else false
	 */
	@Override
	public boolean loadDefaults() {
		Properties p = new Properties();

		p.setProperty(Preferences.IGNORE_FAKE_HEADER, "true");
		p.setProperty(Preferences.OVERWRITE_FILES, "false");
		p.setProperty(Preferences.LOAD_INVALID_RPG_DIRS, "false");
		p.setProperty(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "false");
		p.setProperty(Preferences.LAST_OUTPUT_DIR, Config.DEFAULT_OUTPUT_DIR);
		p.setProperty(Preferences.LAST_OUTPUT_PARENT_DIR, ".");
		p.setProperty(Preferences.LAST_RPG_DIR, ".");
		p.setProperty(Preferences.DECRYPTER_VERSION, Decrypter.DEFAULT_VERSION);
		p.setProperty(Preferences.DECRYPTER_REMAIN, Decrypter.DEFAULT_REMAIN);
		p.setProperty(Preferences.DECRYPTER_SIGNATURE, Decrypter.DEFAULT_SIGNATURE);
		p.setProperty(Preferences.DECRYPTER_HEADER_LEN, Integer.toString(Decrypter.DEFAULT_HEADER_LEN));
		p.setProperty(Preferences.AUTO_CHECK_FOR_UPDATES, "true");

		this.setProperties(p);

		return true;
	}
}
