package org.petschko.rpgmakermv.decrypt;

import org.json.JSONException;
import org.petschko.lib.File;
import org.petschko.lib.exceptions.PathException;
import org.petschko.rpgmakermv.decrypt.cmd.CMD;

import java.util.ArrayList;

/**
 * @author Peter Dragicevic
 */
public class RPG_Project {
	private String path;
	private String outputPath = Config.DEFAULT_OUTPUT_DIR;
	private File system = null;
	private File projectFile = null;
	private File encryptedImgFile = null;
	private String encryptionKeyName = "encryptionKey";
	private boolean isEncrypted = true;
	private boolean isMV = true;
	private ArrayList<File> files = new ArrayList<>();
	private ArrayList<File> encryptedFiles = new ArrayList<>();
	private ArrayList<File> resourceFiles = new ArrayList<>();

	/**
	 * RPG_Project Constructor
	 *
	 * @param path - Path to the RPG-Maker-Project
	 * @param verifyRPGDir - true if the RPG-Maker-Directory should verified
	 * @throws PathException - Path doesn't exists/Not Valid-Dir exception
	 */
	public RPG_Project(String path, boolean verifyRPGDir) throws PathException {
		if(path == null)
			throw new PathException("Project-Path can't be null!", (String) null);
		if(! File.existsDir(path))
			throw new PathException("Project-Path doesn't exists!", path);

		this.setPath(path);

		// Check if Path is a Valid-RPG-Maker-Dir
		if(verifyRPGDir)
			if(! this.verifyDir())
				throw new PathException("Directory is not a Valid RPG-Maker-MV Directory!", path);

		this.loadFiles();
		this.findSystemFile();
		this.findProjectFile();

		if(this.getSystem() != null)
			this.checkIfEncrypted();

		if(this.isEncrypted())
			this.findEncryptedFiles();
		else
			this.findResourceFiles();
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
	private void setPath(String path) {
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
	public void setOutputPath(String outputPath) {
		if(outputPath == null) {
			PathException pe = new PathException("outputPath can't be null!", (String) null);
			pe.printStackTrace();
			return;
		}

		this.outputPath = File.ensureDSonEndOfPath(outputPath);
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
	void setSystem(File system) {
		this.system = system;
	}

	/**
	 * Returns the Project-File
	 *
	 * @return - Project-File or null if none
	 */
	public File getProjectFile() {
		return projectFile;
	}

	/**
	 * Sets the Project-File
	 *
	 * @param projectFile - Project-File
	 */
	public void setProjectFile(File projectFile) {
		this.projectFile = projectFile;
	}

	/**
	 * Returns the encrypted image file
	 *
	 * @return - encrypted image file or null if none found
	 */
	public File getEncryptedImgFile() {
		return encryptedImgFile;
	}

	/**
	 * Sets the encrypted image file
	 *
	 * @param encryptedImgFile - encrypted image file
	 */
	private void setEncryptedImgFile(File encryptedImgFile) {
		this.encryptedImgFile = encryptedImgFile;
	}

	/**
	 * Returns the EncryptionKeyName
	 *
	 * @return - EncryptionKeyName
	 */
	public String getEncryptionKeyName() {
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
	 * Returns if the Project is a MV Project
	 *
	 * @return - Is MV-Project
	 */
	boolean isMV() {
		return isMV;
	}

	/**
	 * Sets if the Project is an MV-Project
	 *
	 * @param isMV - Is MV-Project
	 */
	public void setMV(boolean isMV) {
		this.isMV = isMV;
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
	public ArrayList<File> getEncryptedFiles() {
		return encryptedFiles;
	}

	/**
	 * Returns the Resource-File List
	 *
	 * @return - Resource-File List
	 */
	public ArrayList<File> getResourceFiles() {
		return resourceFiles;
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
			d.detectEncryptionKeyFromJson(this.getSystem(), this.getEncryptionKeyName());
		} catch(JSONException e) {
			this.setEncrypted(false);
		} catch(Exception ex) {
			// VOID
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
	 * Finds an Encrypted image and assigns it to the class
	 */
	public void findEncryptedImg() {
		if(this.getEncryptedImgFile() != null)
			return;

		for(File file : this.getFiles()) {
			if(file.isFileEncryptedExt() && file.isImage()) {
				this.setEncryptedImgFile(file);
				return;
			}
		}
	}

	/**
	 * Find all Encrypted-Files of the Project and save them into an ArrayList
	 */
	private void findEncryptedFiles() {
		for(File file : this.getFiles()) {
			if(file.isFileEncryptedExt()) {
				this.getEncryptedFiles().add(file);

				if(this.getEncryptedImgFile() == null && file.isImage())
					this.setEncryptedImgFile(file);
			}
		}
	}

	/**
	 * Find all Resource-Files of the Project and save them into an ArrayList
	 */
	private void findResourceFiles() {
		for(File file : this.getFiles()) {
			if(file.canBeEncrypted())
				this.getResourceFiles().add(file);
		}
	}

	/**
	 * Finds the System-File and assign it
	 */
	private void findSystemFile() {
		this.setSystem(Finder.findSystemFile(this.getPath()));
	}

	/**
	 * Finds the Project-File and assigns it
	 */
	private void findProjectFile() {
		this.setProjectFile(Finder.findProjectFile(this.getPath()));
	}

	/**
	 * Encrypts all Resource-Files
	 *
	 * @param encrypter - Decrypter (aka Encrypter) Object
	 * @throws Exception - Key not Found Exception
	 */
	public void encryptFilesCmd(Decrypter encrypter) throws Exception {
		// Check if Output-Dir exists
		if(! File.existsDir(this.getOutputPath())) {
			App.showMessage("Output-dir \"" + this.getOutputPath() + "\" doesn't exists!", CMD.STATUS_ERROR);
			return;
		}

		if(encrypter.getDecryptCode() == null) {
			try {
				encrypter.detectEncryptionKeyFromJson(this.getSystem(), this.getEncryptionKeyName());
			} catch(Exception e) {
				throw new Exception(e);
			}
		}

		// Load resource files anyway
		if(this.isEncrypted())
			this.findResourceFiles();

		for(int i = 0; i < this.getResourceFiles().size(); i++) {
			File currentFile = this.getResourceFiles().get(i);

			try {
				App.showMessage("Encrypting: " + currentFile.getFilePath());

				encrypter.encryptFile(currentFile, this.isMV());
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				this.saveFile(currentFile);
			}
		}
	}

	/**
	 * Decrypts all Encrypted Files of the Project
	 *
	 * @param decrypter - Decrypter Object
	 * @throws Exception - Key not Found Exception
	 */
	public void decryptFilesCmd(Decrypter decrypter) throws Exception {
		decryptFilesCmd(decrypter, false);
	}

	/**
	 * Decrypts all Encrypted Files of the Project
	 *
	 * @param decrypter - Decrypter Object
	 * @param restoreImages - Restores images instead of decrypting
	 * @throws Exception - Key not Found Exception
	 */
	public void decryptFilesCmd(Decrypter decrypter, boolean restoreImages) throws Exception {
		// Check if Output-Dir exists
		if(! File.existsDir(this.getOutputPath())) {
			App.showMessage("Output-dir \"" + this.getOutputPath() + "\" doesn't exists!", CMD.STATUS_ERROR);
			return;
		}

		if(decrypter.getDecryptCode() == null) {
			try {
				decrypter.detectEncryptionKeyFromJson(this.getSystem(), this.getEncryptionKeyName());
			} catch(Exception e) {
				throw new Exception(e);
			}
		}

		// Ensure files are loaded
		if(! this.isEncrypted())
			this.findEncryptedFiles();

		for(int i = 0; i < this.getEncryptedFiles().size(); i++) {
			File currentFile = this.getEncryptedFiles().get(i);

			// Only images if restore images
			if(restoreImages && (! currentFile.isImage() || ! currentFile.isFileEncryptedExt()))
				continue;

			try {
				if(restoreImages)
					App.showMessage("Restoring Image" + currentFile.getFilePath());
				else
					App.showMessage("Decrypting: " + currentFile.getFilePath());

				decrypter.decryptFile(currentFile, restoreImages);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				this.saveFile(currentFile);
			}
		}
	}

	/**
	 * todo implement
	 *
	 * @return - todo
	 */
	boolean restoreProjectFile() {
		// todo implement create the project file and check if all is on the right spot and decrypted

		return false;
	}

	/**
	 * Changes the given (Project)Path of the File to output path of the File
	 *
	 * @param path - Path to Change
	 * @return - New Output Path
	 */
	private String projectPathToOutputPath(String path) {
		return this.getOutputPath() + path.substring(this.getPath().length());
	}

	/**
	 * Saves a File
	 *
	 * @param file - File to save to the Output Directory
	 * @param overwriteExisting - Overwrite existing Files
	 */
	public void saveFile(File file, boolean overwriteExisting) {
		String newPath = this.projectPathToOutputPath(file.getFileDirectoryPath());

		// Check if dir exists if not create it
		if(File.existsDir(newPath, true)) {
			file.changePathToFile(newPath);
			App.showMessage("Save File to: " + file.getFilePath(), CMD.STATUS_OK);
			file.save(overwriteExisting);
		} else
			App.showMessage("Can't create Directory for File: " + newPath + file.getFullFileName(), CMD.STATUS_ERROR);

		// Clean up Memory
		file.unloadContent();
	}

	/**
	 * Saves a File but doesn't overwrite existing
	 *
	 * @param file - File to save to the Output Directory
	 */
	void saveFile(File file) {
		saveFile(file, false);
	}

	/**
	 * Verify if the Directory IS a RPG-Directory
	 *
	 * @return - true if the Directory is a RPG-Maker-Project else false
	 */
	private boolean verifyDir() {
		return Finder.verifyRPGDir(File.ensureDSonEndOfPath(this.getPath()));
	}

	/**
	 * Returns the List of all relevant Project-Files
	 *
	 * @return - Project-Files Array
	 */
	public java.io.File[] getProjectFileList() {
		int projectFilesCount = getEncryptedFiles().size() + getResourceFiles().size() + (getSystem() == null ? 0 : 1) + (getProjectFile() == null ? 0 : 1);
		java.io.File[] fileList = new java.io.File[projectFilesCount];
		int i = 0;

		if(getSystem() != null) {
			fileList[i] = new java.io.File(getSystem().getFilePath());
			i++;
		}

		if(getProjectFile() != null) {
			fileList[i] = new java.io.File(getProjectFile().getFilePath());
			i++;
		}

		for(File file : getEncryptedFiles()) {
			fileList[i] = new java.io.File(file.getFilePath());
			i++;
		}

		for(File file : getResourceFiles()) {
			fileList[i] = new java.io.File(file.getFilePath());
			i++;
		}


		return fileList;
	}
}
