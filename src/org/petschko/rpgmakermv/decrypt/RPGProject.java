package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 23.12.2016
 * Time: 11:19
 * Update: -
 * Version: 0.0.1
 *
 * Notes: RPG-Project-Class
 */
public class RPGProject {
	private String path;
	private String outputPath = Config.defaultOutputDir;
	private File system = null;
	private ArrayList<File> encryptedFiles = new ArrayList<>();

	/**
	 * RPGProject Constructor
	 *
	 * @param path - Path to the RPG-Maker-Project
	 */
	public RPGProject(@NotNull String path) {
		this.setPath(path);
		this.findSystemFile();
		this.findEncryptedFiles();
	}

	/**
	 * Returns the Path of the Project
	 *
	 * @return - Path of the Project
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the Path of the Project
	 *
	 * @param path - Path of the Project
	 */
	public void setPath(@NotNull String path) {
		this.path = path;
	}

	/**
	 * Returns the Output (Save-Dir)-Path of the Project
	 *
	 * @return - Output (Save-Dir)-Path of Project
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Sets the Output (Save-Dir)-Path of the Project
	 *
	 * @param outputPath - Output (Save-Dir)-Path of the Project
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * Returns the System-File
	 *
	 * @return - System-File or null if not set
	 */
	public File getSystem() {
		return system;
	}

	/**
	 * Sets the System-File (with encryption key)
	 *
	 * @param system - System-File
	 */
	public void setSystem(@NotNull File system) {
		this.system = system;
	}

	/**
	 * Returns the Encryption-File List
	 *
	 * @return - Encryption-File-List
	 */
	public ArrayList<File> getEncryptedFiles() {
		return encryptedFiles;
	}

	private void findEncryptedFiles() {
		// todo implement
	}

	private void findSystemFile() {
		// todo implement
	}
}
