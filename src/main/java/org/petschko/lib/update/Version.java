package org.petschko.lib.update;

/**
 * @author Peter Dragicevic
 */
public class Version {
	private String version;

	/**
	 * Version constructor
	 *
	 * @param version - Version
	 */
	public Version(String version) {
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

	/**
	 * Checks if this Version is lower than the other Version
	 *
	 * @param version - Version to compare
	 * @return - This Version is lower than the other
	 */
	public boolean thisIsLowerThan(Version version) {
		String[] thisVersion = this.version.split("\\.");
		String[] otherVersion = version.version.split("\\.");

		// Ensure same length arrays
		if(thisVersion.length > otherVersion.length) {
			String[] tmp = new String[thisVersion.length];

			for(int i = 0; i < tmp.length; i++) {
				if(i < otherVersion.length)
					tmp[i] = otherVersion[i];
				else
					tmp[i] = "0";
			}

			otherVersion = tmp;
		} else if(otherVersion.length > thisVersion.length) {
			String[] tmp = new String[otherVersion.length];

			for(int i = 0; i < tmp.length; i++) {
				if(i < thisVersion.length)
					tmp[i] = thisVersion[i];
				else
					tmp[i] = "0";
			}

			thisVersion = tmp;
		}

		// Compare Versions
		for(int n = 0; n < thisVersion.length; n++) {
			int thisNumber = Integer.parseInt(thisVersion[n]);
			int otherNumber = Integer.parseInt(otherVersion[n]);

			if(thisNumber < otherNumber)
				return true;
		}

		return false;
	}
}
