package org.petschko.lib.update;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * @author Peter Dragicevic
 */
class UpdateCache {
	private static final String PROP_NEWEST_VERSION_CACHE = "newestVersionCache";
	private static final String PROP_CACHED_DOWNLOAD_URL = "cachedDownloadUrl";
	private static final String PROP_WHATS_NEW_URL = "cachedWhatsNewUrl";
	private static final String PROP_LAST_CHECK = "lastCheck";
	private static final String FILE_PATH = "updateCache.pref";

	private File propertyFile = null;
	private Properties properties = null;
	private long currentTime;
	private long checkAgainAfterSec = 86400; // Default 1 Day
	private long lastCheck = 0;
	Version newestVersionCache = null;
	URL cachedDownloadUrl = null;
	URL cachedWhatsNewUrl = null;

	/**
	 * UpdateCache constructor
	 */
	UpdateCache() {
		this.setTime();
	}

	/**
	 * UpdateCache constructor
	 *
	 * @param checkAgainAfterSec - Checks again after secs from last check, this overwrites default value
	 */
	UpdateCache(long checkAgainAfterSec) {
		this.setTime();
		this.checkAgainAfterSec = checkAgainAfterSec;
	}

	/**
	 * Sets current time
	 */
	private void setTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		this.currentTime = timestamp.getTime() / 1000;
	}

	/**
	 * Loads the cache from a File
	 *
	 * @return - true if cache was loaded successfully else false if either not all data provided or need new check
	 */
	boolean loadCache() {
		this.propertyFile = new File(FILE_PATH);
		if(! this.propertyFile.exists() || this.propertyFile.isDirectory()) {
			this.propertyFile = null;
			return false;
		}

		this.properties = new Properties();
		Reader reader;
		try {
			reader = new FileReader(this.propertyFile);
			this.properties.load(reader);
		} catch(Exception e) {
			return false;
		}

		// Set the values
		this.lastCheck = Long.parseLong(this.properties.getProperty(PROP_LAST_CHECK, "0"));
		String cachedVersion = this.properties.getProperty(PROP_NEWEST_VERSION_CACHE, "");
		this.newestVersionCache = cachedVersion.equals("") ? null : new Version(cachedVersion);
		try {
			this.cachedDownloadUrl = new URL(this.properties.getProperty(PROP_CACHED_DOWNLOAD_URL, ""));
		} catch(MalformedURLException e) {
			this.cachedDownloadUrl = null;
		}
		try {
			this.cachedWhatsNewUrl = new URL(this.properties.getProperty(PROP_WHATS_NEW_URL, ""));
		} catch(MalformedURLException e) {
			this.cachedWhatsNewUrl = null;
		}

		if(this.cacheToOld())
			return false;

		return this.newestVersionCache != null && this.cachedDownloadUrl != null && this.cachedWhatsNewUrl != null;
	}

	/**
	 * Saves the cache to a File
	 */
	void saveCache() {
		this.lastCheck = this.currentTime;

		if(this.propertyFile == null)
			this.propertyFile = new File(FILE_PATH);

		// Create missing file
		if(! this.propertyFile.exists()) {
			try {
				new FileWriter(this.propertyFile).close();
			} catch(IOException e) {
				e.printStackTrace();

				return;
			}

			if(! this.propertyFile.exists())
				return;
		}

		if(this.properties == null)
			this.properties = new Properties();

		// Set the new values
		this.properties.setProperty(PROP_LAST_CHECK, String.valueOf(this.lastCheck));
		this.properties.setProperty(PROP_NEWEST_VERSION_CACHE, this.newestVersionCache.getVersion());
		this.properties.setProperty(PROP_CACHED_DOWNLOAD_URL, this.cachedDownloadUrl.toString());
		this.properties.setProperty(PROP_WHATS_NEW_URL, this.cachedWhatsNewUrl.toString());

		try {
			FileWriter fileWriter = new FileWriter(FILE_PATH);
			this.properties.store(fileWriter, "Update Cache File");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows if the cache is to old
	 *
	 * @return - Cache is to Old
	 */
	private boolean cacheToOld() {
		return this.currentTime > this.lastCheck + this.checkAgainAfterSec;
	}
}
