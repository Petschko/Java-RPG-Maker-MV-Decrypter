package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;
import org.petschko.lib.File;

import java.util.ArrayList;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 26.12.2016
 * Time: 00:02
 * Update: 03.02.2017
 * Version: 0.0.2
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
	static File findSystemFile(String projectDir) {
		String[] filePaths = new String[]{
				"data" + Const.DS + "System.json",
				"www" + Const.DS + "data" + Const.DS + "System.json"
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
	 * Check if Encryption-Key name is may different from given - Tests all know Encryption-Key Names
	 *
	 * @param systemFile - System.json File
	 * @return - Key-Name or null if not found
	 */
	static String testEncryptionKeyNames(File systemFile) {
		String[] keyNames = new String[]{"encryptionKey"};
		Decrypter d = new Decrypter();
		String result = null;

		for(String keyName : keyNames) {
			try {
				d.detectEncryptionKeyFromJson(systemFile, keyName);

				if(d.getDecryptCode() != null) {
					result = keyName;
					break;
				}
			} catch(Exception e) {
				// Void
			}
		}

		return result;
	}

	/**
	 * Checks if there is a RPG-Maker file within the Directory
	 *
	 * @param dir - Path to the Directory
	 * @return - true if a File was found else false
	 */
	static boolean verifyRPGDir(String dir) {
		if(dir == null) {
			Exception e = new Exception("Dir can't be null!");
			e.printStackTrace();
			return false;
		}

		ArrayList<java.io.File> mainDirFiles = File.readDirFiles(new java.io.File(dir), false);
		String[] rpgGameCommonFiles = new String[] {
				"Game.exe", // Mostly with an other name...
				"Game.rpgproject", // Mostly not in the Directory
				"d3dcompiler_47.dll",
				"ffmpegsumo.dll",
				"icudtl.dat",
				"libEGL.dll",
				"libGLESv2.dll",
				"nw.pak",
				"package.json",
				"pdf.dll"
		};

		// Check if a File is in there
		for(java.io.File currentFile : mainDirFiles) {
			for(String rpgFile : rpgGameCommonFiles) {
				if(rpgFile.equals(currentFile.getName()))
					return true;
				else if(rpgFile.toLowerCase().equals(currentFile.getName().toLowerCase()))
					return true;
			}
		}

		return false;
	}
}
