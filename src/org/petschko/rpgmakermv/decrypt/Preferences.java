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
	static final String ignoreFakeHeader = "ignoreFakeHeader";
	static final String loadInvalidRPGDirs = "loadInvalidRPGDirs";
	static final String overwriteFiles = "overwriteFiles";
	static final String clearOutputDirBeforeDecrypt = "clearOutputDirBeforeDecrypt";
	static final String lastOutputDir = "lastOutputDir";
	static final String lastOutputParentDir = "lastOutputParentDir";
	static final String lastRPGDir = "lastRPGParentDir";
	static final String decrypterVersion = "decrypterVersion";
	static final String decrypterRemain = "decrypterRemain";
	static final String decrypterSignature = "decrypterSignature";
	static final String decrypterHeaderLen = "decrypterHeaderLen";
	static final String autoCheckForUpdates = "autoCheckForUpdates";

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

		p.setProperty(Preferences.ignoreFakeHeader, "true");
		p.setProperty(Preferences.overwriteFiles, "false");
		p.setProperty(Preferences.loadInvalidRPGDirs, "false");
		p.setProperty(Preferences.clearOutputDirBeforeDecrypt, "false");
		p.setProperty(Preferences.lastOutputDir, Config.defaultOutputDir);
		p.setProperty(Preferences.lastOutputParentDir, ".");
		p.setProperty(Preferences.lastRPGDir, ".");
		p.setProperty(Preferences.decrypterVersion, Decrypter.defaultVersion);
		p.setProperty(Preferences.decrypterRemain, Decrypter.defaultRemain);
		p.setProperty(Preferences.decrypterSignature, Decrypter.defaultSignature);
		p.setProperty(Preferences.decrypterHeaderLen, Integer.toString(Decrypter.defaultHeaderLen));
		p.setProperty(Preferences.autoCheckForUpdates, "true");

		this.setProperties(p);

		return true;
	}
}
