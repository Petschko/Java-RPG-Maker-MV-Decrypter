package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.Nullable;
import org.json.JSONException;
import org.petschko.lib.Const;
import org.petschko.lib.File;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 26.12.2016
 * Time: 00:02
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Finder Class
 */
class Finder {
	/**
	 * Constructor
	 */
	private Finder() {
		// VOID - This is a Static-Class
	}

	/**
	 * Search all know places for the System-File
	 *
	 * @param projectDir - Directory of the Project
	 * @return - System-File-Object if found else null
	 */
	public static File findSystemFile(@Nullable String projectDir) {
		String[] filePaths = new String[]{
				"data" + Const.ds + "System.json",
				"www" + Const.ds + "data" + Const.ds + "System.json"
		};
		File systemJsonFile = null;

		for(String filePath : filePaths) {
			java.io.File systemFile = new java.io.File(projectDir + filePath);

			if(systemFile.exists()) {
				try {
					systemJsonFile = new File(projectDir + filePath);
				} catch(Exception e) {
					e.printStackTrace();
				}

				return systemJsonFile;
			}
		}

		return null;
	}

	/**
	 * Check if the given Extension is an Encrypted Extension
	 *
	 * @param extension - File-Extension to check
	 * @return - true if the Extension is an encrypted File-extension else false
	 */
	public static boolean isFileEncryptedExt(@Nullable String extension) {
		if(extension == null)
			extension = "";

		switch(extension) {
			case "rpgmvp":
			case "rpgmvm":
			case "rpgmvo":
				return true;
			default:
				return false;
		}
	}

	/**
	 * Check if Encryption-Key name is may different from given - Tests all know Encryption-Key Names
	 *
	 * @param systemFile - System.json File
	 * @return - Key-Name or null if not found
	 */
	public static String testEncryptionKeyNames(File systemFile) {
		String[] keyNames = new String[]{"encryptionKey"};
		Decrypter d = new Decrypter();
		String result = null;

		for(String keyName : keyNames) {
			try {
				d.detectEncryptionKey(systemFile, keyName);

				if(d.getDecryptCode() != null) {
					result = keyName;
					break;
				}
			} catch(JSONException e) {
				// Void
			}
		}

		return result;
	}
}
