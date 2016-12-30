package org.petschko.lib;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 11.12.2016
 * Time: 20:23
 * Update: -
 * Version: 0.0.1
 *
 * Notes: File Class
 */
public class File {
	private String name;
	private String extension;
	private byte[] content = null;
	private String filePath;

	/**
	 * File Constructor
	 *
	 * @param filePath - Path of the File
	 * @throws Exception - Invalid File-Path
	 */
	public File(@NotNull String filePath) throws Exception {
		this.setFilePath(filePath);

		// Extract Name and Extension
		this.extractInfosFromPath();
	}

	/**
	 * Name getter
	 *
	 * @return - Name of the File without ext
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter
	 *
	 * @param name - New Name for the File without ext
	 */
	private void setName(@NotNull String name) {
		this.name = name;
	}

	/**
	 * Sets a new File-Name for the File (without ext)
	 *
	 * @param newName - New Name for the File (without ext)
	 */
	public void changeName(@NotNull String newName) {
		this.setFilePath(this.getFileDirectoryPath() + newName +
				((this.getExtension() != null) ? "." + this.getExtension() : ""));
		this.setName(newName);
	}

	/**
	 * Extension getter
	 *
	 * @return - File-Extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Extension setter
	 *
	 * @param extension - New Extension of the File
	 */
	private void setExtension(@Nullable String extension) {
		if(extension.equals(""))
			extension = null;

		this.extension = extension;
	}

	/**
	 * Sets a new Extension (May useful if you changed a File-type)
	 *
	 * @param newExtension - New Extension for the File
	 */
	public void changeExtension(@Nullable String newExtension) {
		if(newExtension.equals(""))
			newExtension = null;

		this.setFilePath(this.getFileDirectoryPath() + this.getName() +
				((newExtension != null) ? "." + newExtension : ""));
		this.setExtension(newExtension);
	}

	/**
	 * Content getter
	 *
	 * @return - File-Content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Content setter
	 *
	 * @param content - New File-Content
	 */
	public void setContent(@Nullable byte[] content) {
		this.content = content;
	}

	/**
	 * Filepath getter
	 *
	 * @return - File-Path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Filepath setter
	 *
	 * @param filePath - New path of the File
	 */
	public void setFilePath(@NotNull String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Get the Full-Filename
	 *
	 * @return - Full File-Name (with extension)
	 */
	public String getFullFileName() {
		return this.getName() + ((this.getExtension() != null) ? "." + this.getExtension() : "");
	}

	/**
	 * Get the Directory-Path of the File
	 *
	 * @return - File Directory-Path
	 */
	public String getFileDirectoryPath() {
		int lastDSChar = this.getFilePath().lastIndexOf(Const.ds);

		if(lastDSChar == -1)
			return "";

		return this.getFilePath().substring(0, lastDSChar + 1);
	}

	/**
	 * Changes the Directory of the File
	 *
	 * @param newPath - New Directory of the File
	 */
	public void changePathToFile(@Nullable String newPath) {
		if(newPath == null)
			newPath = "";

		this.setFilePath(File.ensureDSonEndOfPath(newPath) + this.getFullFileName());
	}

	/**
	 * Loads the File-Content as Byte-Array
	 *
	 * @return true if file was loaded false if not
	 * @throws FileSystemException - File to big Exception
	 */
	public boolean load() throws FileSystemException {
		FileInputStream fileIO;
		java.io.File file = new java.io.File(this.getFilePath());
		byte[] byteContent;

		try {
			// Check if file exists and if it can be read
			if(! file.exists())
				throw new FileNotFoundException();
			if(! file.canRead())
				throw new FileSystemException(this.getFilePath(), "", "Can't read File");
		} catch(Exception e) {
			e.printStackTrace();

			return false;
		}

		// Check size of the file
		if(file.length() > Integer.MAX_VALUE)
			throw new FileSystemException(
					this.getFilePath(),
					"",
					"File is to big... (> " + Integer.MAX_VALUE + " Bytes)!"
			);

		byteContent = new byte[(int) file.length()];

		// Read File into buffer
		try {
			fileIO = new FileInputStream(file);
			int readBytes = fileIO.read(byteContent, 0, byteContent.length);
			fileIO.close();

			if(readBytes != file.length())
				throw new IOException("Read bytes don't match File-Length!");
		} catch(Exception e) {
			e.printStackTrace();

			return false;
		}

		this.setContent(byteContent);

		return true;
	}

	/**
	 * Unload the content of the File
	 */
	public void unloadContent() {
		this.setContent(null);
	}

	/**
	 * Calls the save function with the default parameter for overwrite (false)
	 *
	 * @return - true if file was successfully saved else false
	 */
	public boolean save() {
		return this.save(false);
	}

	/**
	 * Save the File and overwrite existing files if allowed
	 *
	 * @param overwriteExisting - Is overwrite allowed
	 * @return - true if file was successfully saved else false
	 */
	public boolean save(boolean overwriteExisting) {
		java.io.FileOutputStream fileOS;
		java.io.File file;

		file = new java.io.File(this.getFilePath());

		// Check if file exists and if its allowed to overwrite
		if(file.exists() && ! overwriteExisting)
			return false;
		else if(! file.exists()) {
			try {
				if(! file.createNewFile())
					throw new Exception("Can't create File " + this.getFullFileName() + "!");
			} catch(Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		// Write Data to File
		try {
			if(file.canWrite()) {
				fileOS = new FileOutputStream(file);
				fileOS.write(this.getContent(), 0, this.getContent().length);
				fileOS.close();
			} else
				throw new FileSystemException(this.getFilePath(), "", "Can't write File!");
		} catch(Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Extract the File-Name and extension from the File-Path and set them
	 *
	 * @throws Exception - Invalid File-Path
	 */
	private void extractInfosFromPath() throws Exception {
		int filePathLen = this.getFilePath().length();
		int lastDSCharPos = this.getFilePath().lastIndexOf(Const.ds);
		int lastDotPos = this.getFilePath().lastIndexOf(".");
		String fileName = this.getFilePath();

		if(filePathLen - 1 == lastDSCharPos || filePathLen == 0)
			throw new Exception("File-Path invalid!");

		if(lastDSCharPos != -1)
			fileName = this.getFilePath().substring(lastDSCharPos + 1);

		if(lastDSCharPos >= lastDotPos || lastDotPos == 0 || (lastDotPos - 1) == lastDSCharPos || lastDotPos == filePathLen - 1)
			this.setExtension(null);
		else {
			this.setExtension(this.getFilePath().substring(lastDotPos + 1));
			fileName = this.getFilePath().substring(lastDSCharPos + 1, lastDotPos);
		}

		this.setName(fileName);
	}

	/**
	 * Reads all Files (Recursive) of a Directory to an Array
	 *
	 * @param dir - Directory to read
	 * @return - File-Array
	 */
	public static ArrayList<java.io.File> readDirFiles(java.io.File dir) {
		java.io.File[] files = dir.listFiles();
		ArrayList<java.io.File> fileList = new ArrayList<>();

		if(files == null)
			files = new java.io.File[0];

		for(java.io.File file : files) {
			if(file.isDirectory()) {
				ArrayList<java.io.File> dirContent = readDirFiles(file);

				// Process Directory-Content
				for(java.io.File aDirContent : dirContent)
					fileList.add(aDirContent);
			} else
				fileList.add(file);
		}

		return fileList;
	}

	/**
	 * Ensure that a Path has always a DIRECTORY_SEPARATOR on the end
	 *
	 * @param path - Unchecked Path-String
	 * @return - Checked and may corrected Path-String
	 */
	public static String ensureDSonEndOfPath(@NotNull String path) {
		if(! path.substring(path.length() - 1).equals(Const.ds))
			path = path + Const.ds;

		return path;
	}

	/**
	 * Checks if a Directory exists
	 *
	 * @param path - Path of the Directory
	 * @return - true if Directory exists else false
	 */
	public static boolean existsDir(@NotNull String path) {
		return existsDir(path, false);
	}

	/**
	 * Checks if a Directory exists and if not may create it if set
	 *
	 * @param path - Path of the Directory
	 * @param createMissing - true if the function should create missing Directories
	 * @return - true if the Directory exists (or was successfully created) else false
	 */
	public static boolean existsDir(@NotNull String path, boolean createMissing) {
		java.io.File dir = new java.io.File(path);

		if(! dir.exists()) {
			if(createMissing)
				if(dir.mkdirs())
					return true;

			return false;
		} else
			return true;
	}

	/**
	 * Check if a File exists
	 *
	 * @param filePath - Path of the File
	 * @return - true if File exists else false
	 */
	public static boolean existsFile(@NotNull String filePath) {
		// Call the same function like on check dir exists but never create "dirs"^^
		return existsDir(filePath, false);
	}
}
