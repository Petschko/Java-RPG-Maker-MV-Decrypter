package org.petschko.lib.update;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Peter Dragicevic [peter&#064;petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 05.10.2017
 * Time: 14:37
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Update Class
 */
public class Update {
	private UpdateCache updateCache;
	private URL checkVersionUrl;
	private URL whatsNewUrl = null;
	private URL downloadURL = null;
	private Version currentVersion;
	private Version newestVersion = null;
	private boolean hasNewVersion = false;
	private boolean ignoreCache = false;

	/**
	 * Update Constructor
	 *
	 * @param checkVersionUrl - URL to get the newest Version-Number
	 * @param currentVersion - Current Version
	 * @throws IOException - IO-Exception
	 */
	public Update(String checkVersionUrl, String currentVersion) throws IOException {
		this.updateCache = new UpdateCache();
		this.checkVersionUrl = new URL(checkVersionUrl);
		this.currentVersion = new Version(currentVersion);

		this.init();
	}

	/**
	 * Update Constructor
	 *
	 * @param checkVersionUrl - URL to get the newest Version-Number
	 * @param currentVersion - Current Version
	 * @param cacheTime - Cache-Time in Sec
	 * @throws IOException - IO-Exception
	 */
	public Update(String checkVersionUrl, String currentVersion, long cacheTime) throws IOException {
		this.updateCache = new UpdateCache(cacheTime);
		this.checkVersionUrl = new URL(checkVersionUrl);
		this.currentVersion = new Version(currentVersion);

		this.init();
	}

	/**
	 * Update Constructor
	 *
	 * @param checkVersionUrl - URL to get the newest Version-Number
	 * @param currentVersion - Current Version
	 * @param ignoreCache - Should the Cache be ignored?
	 * @throws IOException - IO-Exception
	 */
	public Update(String checkVersionUrl, String currentVersion, boolean ignoreCache) throws IOException {
		this.updateCache = new UpdateCache();
		this.checkVersionUrl = new URL(checkVersionUrl);
		this.currentVersion = new Version(currentVersion);
		this.ignoreCache = ignoreCache;

		this.init();
	}

	/**
	 * Gets the Whats-New URL
	 *
	 * @return - Whats-New URL
	 */
	public URL getWhatsNewUrl() {
		return whatsNewUrl;
	}

	/**
	 * Initiates this instance (may loads cache)
	 */
	private void init() throws IOException {
		if(this.ignoreCache) {
			this.getUpdateInfo();
		} else {
			if(this.updateCache.loadCache()) {
				this.newestVersion = this.updateCache.newestVersionCache;
				this.downloadURL = this.updateCache.cachedDownloadUrl;
				this.whatsNewUrl = this.updateCache.cachedWhatsNewUrl;
			} else {
				this.getUpdateInfo();
			}
		}

		this.checkVersion();
	}

	/**
	 * Get all information from the File for the Update process
	 */
	private void getUpdateInfo() throws IOException {
		// Read the Update-URL
		InputStream content = this.checkVersionUrl.openStream();


		// Convert the read Content to Strings
		int c;
		int currentString = 0;
		StringBuilder version = new StringBuilder();
		StringBuilder downloadUrl = new StringBuilder();
		StringBuilder whatsNewUrl = new StringBuilder();

		while(true) {
			try {
				c = content.read();

				// Exit loop if file reaches end
				if(c == -1)
					break;

				if(c == (int) ';')
					currentString++;
				else {
					switch(currentString) {
						case 0:
							version.append((char) c);
							break;
						case 1:
							downloadUrl.append((char) c);
							break;
						case 2:
							whatsNewUrl.append((char) c);
							break;
						default:
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}

		this.newestVersion = new Version(version.toString().trim());

		try {
			this.downloadURL = new URL(downloadUrl.toString().trim());
			this.whatsNewUrl = new URL(whatsNewUrl.toString().trim());
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}

		this.savesCache();
	}

	/**
	 * Saves new Data to the Cache
	 */
	private void savesCache() {
		this.updateCache.newestVersionCache = this.newestVersion;
		this.updateCache.cachedDownloadUrl = this.downloadURL;
		this.updateCache.cachedWhatsNewUrl = this.whatsNewUrl;

		this.updateCache.saveCache();
	}

	/**
	 * Checks if the Version is the newest
	 */
	private void checkVersion() {
		if(this.newestVersion == null)
			return;

		this.hasNewVersion = ! this.currentVersion.versionsEqual(this.newestVersion);
	}

	/**
	 * Shows if the current Version is the newest
	 *
	 * @return - Is newest Version
	 */
	public boolean isHasNewVersion() {
		return this.hasNewVersion;
	}

	/**
	 * Get the newest Version-Number
	 *
	 * @return - Newest Version-Number or null if could not read Update-URL
	 */
	public String getNewestVersion() {
		return newestVersion.getVersion();
	}

	/**
	 * Get the current Version
	 *
	 * @return - Current Version
	 */
	public String getCurrentVersion() {
		return currentVersion.getVersion();
	}

	/**
	 * Starts the updater but not relaunch after update
	 *
	 * @param targetJar - Target jar which should be updated
	 * @param gui - Should the Updater show a GUI window
	 */
	public void runUpdate(String targetJar, boolean gui) throws UpdateException {
		this.runUpdate(targetJar, gui, false, null);
	}


	/**
	 * Starts the updater
	 *
	 * @param targetJar - Target jar which should be updated
	 * @param gui - Should the Updater show a GUI window
	 * @param relaunch - Relaunch this Program after Update
	 * @param relaunchArgs - Args for relaunch, can be null if none
	 */
	public void runUpdate(String targetJar, boolean gui, boolean relaunch, @Nullable String[] relaunchArgs) throws UpdateException {
		File updaterFile = new File("update.jar");
		File targetJarFile = new File(targetJar);

		if(this.newestVersion == null)
			throw new UpdateException("Newest Version is not set!", this.currentVersion);

		if(this.newestVersion.versionsEqual(new Version("")))
			throw new UpdateException("Newest Version is empty...", this.currentVersion);

		if(this.newestVersion.versionsEqual(this.currentVersion))
			throw new UpdateException("This Program is already up to date!", this.currentVersion, this.newestVersion);

		if(! targetJarFile.exists() || targetJarFile.isDirectory())
			throw new UpdateException("Can not find the Target-Jar", this.currentVersion);

		if(! updaterFile.exists() || updaterFile.isDirectory())
			throw new UpdateException("Updater not found!", this.currentVersion);

		String[] run = {
			"java",
			"-jar",
			"update.jar",
			"\"" + targetJar + "\"",
			"\"" + this.downloadURL.toString() + "\"",
			gui ? "true" : "false",
			relaunch ? "true" : "false"
		};

		// Add args
		if(relaunchArgs != null) {
			String[] tmp = new String[run.length + relaunchArgs.length];
			int i;
			for(i = 0; i < run.length; i++)
				tmp[i] = run[i];

			int n = i;
			for(; i < tmp.length; i++)
				tmp[i] = relaunchArgs[i - n];

			run = tmp;
		}

		try {
			Runtime.getRuntime().exec(run);
		} catch (Exception e) {
			throw new UpdateException(e.getMessage(), this.currentVersion, e);
		}
		System.exit(0);
	}
}
