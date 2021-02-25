package org.petschko.lib.update;

import org.jetbrains.annotations.NotNull;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: https://petschko.org/
 * Date: 05.05.2019
 * Time: 17:16
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Version Class
 */
public class Version {
	private String version;

	/**
	 * Version constructor
	 *
	 * @param version - Version
	 */
	public Version(@NotNull String version) {
		this.version = version;
	}

	/**
	 * Get the Version
	 *
	 * @return - Version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Compares 2 Versions
	 *
	 * @param version - Version to compare
	 * @return - Versions are equal
	 */
	public boolean versionsEqual(Version version) {
		return this.version.equals(version.getVersion());
	}
}
