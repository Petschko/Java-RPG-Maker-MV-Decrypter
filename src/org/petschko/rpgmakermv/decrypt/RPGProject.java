package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.NotNull;
import org.json.JSONException;
import org.petschko.lib.File;
import sun.dc.path.PathException;
import java.util.ArrayList;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 23.12.2016
 * Time: 11:19
 * Update: -
 * Version: 0.1.0
 *
 * todo check if its a real rpg maker dir not more above
 *
 * Notes: RPG-Project-Class
 */
class RPGProject {
	private String path;
	private String outputPath = Config.defaultOutputDir;
	private File system = null;
	private String encryptionKeyName = "encryptionKey";
	private boolean isEncrypted = true;
	private ArrayList<File> files = new ArrayList<>();
	private ArrayList<File> encryptedFiles = new ArrayList<>();

	/**
	 * RPGProject Constructor
	 *
	 * @param path - Path to the RPG-Maker-Project
	 * @throws PathException - Path doesn't exists exception
	 */
	RPGProject(@NotNull String path) throws PathException {
		if(File.existsDir(path))
			throw new PathException("Project-Path doesn't exists!");

		this.setPath(path);
		this.loadFiles();
		this.findSystemFile();
		this.checkIfEncrypted();

		if(this.isEncrypted())
			this.findEncryptedFiles();
	}

	/**
	 * Returns the Path of the Project
	 *
	 * @return - Path of the Project
	 */
	String getPath() {
		return path;
	}

	/**
	 * Sets the Path of the Project
	 *
	 * @param path - Path of the Project
	 */
	private void setPath(@NotNull String path) {
		this.path = File.ensureDSonEndOfPath(path);
	}

	/**
	 * Returns the Output (Save-Dir)-Path of the Project
	 *
	 * @return - Output (Save-Dir)-Path of Project
	 */
	String getOutputPath() {
		return outputPath;
	}

	/**
	 * Sets the Output (Save-Dir)-Path of the Project
	 *
	 * @param outputPath - Output (Save-Dir)-Path of the Project
	 */
	void setOutputPath(@NotNull String outputPath) {
		this.outputPath = File.ensureDSonEndOfPath(outputPath);
	}

	/**
	 * Returns the System-File
	 *
	 * @return - System-File or null if not set
	 */
	File getSystem() {
		return system;
	}

	/**
	 * Sets the System-File (with encryption key)
	 *
	 * @param system - System-File
	 */
	void setSystem(@NotNull File system) {
		this.system = system;
	}

	/**
	 * Returns the EncryptionKeyName
	 *
	 * @return - EncryptionKeyName
	 */
	String getEncryptionKeyName() {
		return encryptionKeyName;
	}

	/**
	 * Sets the EncryptionKeyName
	 *
	 * @param encryptionKeyName - EncryptionKeyName
	 */
	void setEncryptionKeyName(String encryptionKeyName) {
		this.encryptionKeyName = encryptionKeyName;
	}

	/**
	 * Set the EncryptionKeyName to the Default-Value
	 */
	void setEncryptionKeyNameToDefault() {
		this.setEncryptionKeyName("encryptionKey");
	}

	/**
	 * Returns true if Project is encrypted
	 *
	 * @return - true if Project is encrypted else false
	 */
	boolean isEncrypted() {
		return isEncrypted;
	}

	/**
	 * Sets to true if the Project is Encrypted
	 *
	 * @param encrypted - true if the Project is encrypted else false
	 */
	private void setEncrypted(boolean encrypted) {
		isEncrypted = encrypted;
	}

	/**
	 * Returns the File List of the Project
	 *
	 * @return - File List
	 */
	ArrayList<File> getFiles() {
		return files;
	}

	/**
	 * Returns the Encryption-File List
	 *
	 * @return - Encryption-File List
	 */
	ArrayList<File> getEncryptedFiles() {
		return encryptedFiles;
	}

	/**
	 * Load all Files of the Project into an ArrayList
	 */
	private void loadFiles() {
		java.io.File projectPath = new java.io.File(this.getPath());
		ArrayList<java.io.File> files = File.readDirFiles(projectPath);

		for(java.io.File file : files) {
			try {
				this.getFiles().add(new File(file.getCanonicalPath()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the Encryption-Key is Found within the System-File
	 */
	private void checkIfEncrypted() {
		Decrypter d = new Decrypter();

		try {
			d.detectEncryptionKey(this.getSystem(), this.getEncryptionKeyName());
		} catch(JSONException e) {
			this.setEncrypted(false);
		}

		if(d.getDecryptCode() != null)
			this.setEncrypted(true);
		else {
			// Test default names
			String decryptKey = Finder.testEncryptionKeyNames(this.getSystem());

			if(decryptKey != null) {
				this.setEncryptionKeyName(decryptKey);
				this.setEncrypted(true);
			}
		}
	}

	/**
	 * Find all Encrypted-Files of the Project and save them into an ArrayList
	 */
	private void findEncryptedFiles() {
		if(! this.isEncrypted())
			return;

		for(File file : this.getFiles()) {
			if(Finder.isFileEncryptedExt(file.getExtension()))
				this.getEncryptedFiles().add(file);
		}
	}

	/**
	 * Finds the System-File and assign it
	 */
	private void findSystemFile() {
		File system = Finder.findSystemFile(this.getPath());

		if(system != null)
			this.setSystem(system);
	}

	void decryptFiles(boolean ignoreFakeHeader) throws JSONException {
		// Check if Output-Dir exists
		if(! File.existsDir(this.getOutputPath())) {
			App.showMessage("Output-dir \"" + this.getOutputPath() + "\" doesn't exists!");
			return;
		}

		Decrypter decrypter = new Decrypter();

		decrypter.detectEncryptionKey(this.getSystem(), this.getEncryptionKeyName());
		decrypter.setIgnoreFakeHeader(ignoreFakeHeader);

		for(int i = 0; i < this.getEncryptedFiles().size(); i++) {
			File currentFile = this.getEncryptedFiles().get(i);

			try {
				decrypter.decryptFile(currentFile);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				this.saveFile(currentFile);
			}
		}
	}

	boolean restoreProjectFile() {
		// todo implement create the project file and check if all is on the right spot and decrypted

		return false;
	}

	private String projectPathToOutputPath(String path) {
		return this.getOutputPath() + path.substring(this.getPath().length());
	}

	private void saveFile(File file) {
		String newPath = this.projectPathToOutputPath(file.getFileDirectoryPath());

		// Check if dir exists if not create it
		if(File.existsDir(newPath, true)) {
			file.changePathToFile(newPath);
			App.showMessage("Save File to: " + file.getFilePath());
			file.save();
		} else
			App.showMessage("Can't create Directory for File: " + newPath + file.getFullFileName());

		// Clean up Memory
		file.unloadContent();
	}
}
