package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.Nullable;
import org.petschko.lib.UserPref;

import java.util.Properties;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 30.12.2016
 * Time: 22:32
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Preferences Class
 */
class Preferences extends UserPref {
	static final String ignoreFakeHeader = "ignoreFakeHeader";
	static final String loadInvalidRPGDirs = "loadInvalidRPGDirs";
	static final String overwriteFiles = "overwriteFiles";
	static final String clearOutputDirBeforeDecrypt = "clearOutputDirBeforeDecrypt";
	static final String lastOutputDir = "lastOutputDir";
	static final String lastRPGDir = "lastRPGDir";


	/**
	 * UserPrefs Constructor
	 *
	 * @param filePath - File-Path to UserPref-File
	 */
	Preferences(@Nullable String filePath) {
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

		p.setProperty(Preferences.ignoreFakeHeader, "false");
		p.setProperty(Preferences.overwriteFiles, "false");
		p.setProperty(Preferences.loadInvalidRPGDirs, "false");
		p.setProperty(Preferences.clearOutputDirBeforeDecrypt, "true");
		p.setProperty(Preferences.lastOutputDir, Config.defaultOutputDir);
		p.getProperty(Preferences.lastRPGDir, ".");

		this.setProperties(p);

		return true;
	}
}
