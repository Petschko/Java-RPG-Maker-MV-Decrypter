package org.petschko.lib;

import org.petschko.lib.exceptions.PathException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
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
	public File(String filePath) throws Exception {
		if(filePath == null) {
			PathException pe = new PathException("filePath can't be null!", (String) null);
			pe.printStackTrace();
		}

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
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets a new File-Name for the File (without ext)
	 *
	 * @param newName - New Name for the File (without ext)
	 */
	public void changeName(String newName) {
		if(newName == null) {
			Exception e = new Exception("newName can't be null!");
			e.printStackTrace();
			return;
		}

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
	private void setExtension(String extension) {
		if(extension != null) {
			if (extension.equals(""))
				extension = null;
			else
				extension = extension.toLowerCase();
		}

		this.extension = extension;
	}

	/**
	 * Sets a new Extension (May useful if you changed a File-type)
	 *
	 * @param newExtension - New Extension for the File
	 */
	public void changeExtension(String newExtension) {
		if(newExtension != null) {
			if (newExtension.equals(""))
				newExtension = null;
		}

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
	public void setContent(byte[] content) {
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
	public void setFilePath(String filePath) {
		if(filePath == null) {
			PathException pe = new PathException("filePath can't be null!", (String) null);
			pe.printStackTrace();
			return;
		}

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
	public void changePathToFile(String newPath) {
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
	 * Reads all Files Recursive of a Directory to an Array
	 *
	 * @param dir - Directory to read
	 * @return - File-Array
	 */
	public static ArrayList<java.io.File> readDirFiles(java.io.File dir) {
		return readDirFiles(dir, true);
	}

	/**
	 * Reads all Files (Recursive) of a Directory to an Array
	 *
	 * @param dir - Directory to read
	 * @param recursive - Read Files in Sub-Directories
	 * @return - File-Array
	 */
	public static ArrayList<java.io.File> readDirFiles(java.io.File dir, boolean recursive) {
		java.io.File[] files = dir.listFiles();
		ArrayList<java.io.File> fileList = new ArrayList<>();

		if(files == null)
			files = new java.io.File[0];

		for(java.io.File file : files) {
			if(file.isDirectory()) {
				if(recursive) {
					ArrayList<java.io.File> dirContent = readDirFiles(file);

					// Process Directory-Content
					fileList.addAll(dirContent);
				}
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
	public static String ensureDSonEndOfPath(String path) {
		if(path == null) {
			PathException pe = new PathException("path can't be null!", (String) null);
			pe.printStackTrace();
			return null;
		}

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
	public static boolean existsDir(String path) {
		if(path == null) {
			PathException pe = new PathException("path can't be null!", (String) null);
			pe.printStackTrace();
			return false;
		}

		return existsDir(path, false);
	}

	/**
	 * Checks if a Directory exists and if not may create it if set
	 *
	 * @param path - Path of the Directory
	 * @param createMissing - true if the function should create missing Directories
	 * @return - true if the Directory exists (or was successfully created) else false
	 */
	public static boolean existsDir(String path, boolean createMissing) {
		if(path == null) {
			PathException pe = new PathException("path can't be null!", (String) null);
			pe.printStackTrace();
			return false;
		}

		java.io.File dir = new java.io.File(path);

		if(! dir.exists()) {
			if(createMissing)
				return dir.mkdirs();

			return false;
		} else
			return true;
	}

	/**
	 * Checks if a File exists
	 *
	 * @param filePath - Path of the File
	 * @return - true if File exists else false
	 */
	public static boolean existsFile(String filePath) {
		if(filePath == null) {
			PathException pe = new PathException("filePath can't be null!", (String) null);
			pe.printStackTrace();
			return false;
		}

		return existsFile(filePath, false);
	}

	/**
	 * Checks if a File exists
	 *
	 * @param filePath - Path of the File
	 * @param createMissing - true if the function should create missing Files
	 * @return - true if the File exists (or was successfully created) else false
	 */
	public static boolean existsFile(String filePath, boolean createMissing) {
		if(filePath == null) {
			PathException pe = new PathException("filePath can't be null!", (String) null);
			pe.printStackTrace();
			return false;
		}

		java.io.File file = new java.io.File(filePath);

		if(! file.exists()) {
			if(createMissing) {
				try {
					// Create new File
					new FileWriter(file).close();
				} catch(IOException e) {
					e.printStackTrace();

					return false;
				}

				// Check if File exists now
				return file.exists();
			}

			return false;
		} else
			return true;
	}

	/**
	 * Deletes/Clears a Directory
	 *
	 * @param directoryPath - Path to the Directory
	 * @param recursive - Specify if it should delete recursively
	 * @param deleteOwn - Specify if it should delete itself too or just clearing
	 * @return - true on success else false
	 */
	private static boolean deleteDirectoryOperation(String directoryPath, boolean recursive, boolean deleteOwn) {
		if(directoryPath == null) {
			PathException pe = new PathException("directoryPath can't be null!", (String) null);
			pe.printStackTrace();
			return false;
		}

		java.io.File dir = new java.io.File(directoryPath);
		java.io.File[] dirContent;

		// Ensure that dir Exists
		if(!File.existsDir(directoryPath))
			return false;

		dirContent = dir.listFiles();

		// Empty Directory
		if(dirContent == null)
			return !deleteOwn || dir.delete();

		for(java.io.File item : dirContent) {
			if(item.isDirectory() && recursive) {
				if(!File.deleteDirectory(item.getPath()))
					return false;
			} else {
				if(!item.delete())
					return false;
			}
		}

		return !deleteOwn || dir.delete();

	}

	/**
	 * Deletes a whole Directory recursively
	 *
	 * @param directoryPath - Path to the Directory
	 * @return - true on success else false
	 */
	public static boolean deleteDirectory(String directoryPath) {
		return File.deleteDirectoryOperation(directoryPath, true, true);
	}

	/**
	 * Deletes the Files in the current dir but not recursively
	 *
	 * @param directoryPath - Path to the Directory
	 * @return - true on success else false
	 */
	public static boolean deleteFilesInDir(String directoryPath) {
		return File.deleteDirectoryOperation(directoryPath, false, false);
	}

	/**
	 * Try to create a Directory on the given Position
	 *
	 * @param directoryPath - Path of the new Directory
	 * @return - true if a Directory was created else false
	 */
	public static boolean createDirectory(String directoryPath) {
		if(directoryPath == null) {
			PathException pe = new PathException("directoryPath can't be null!", (String) null);
			pe.printStackTrace();
			return false;
		}

		java.io.File dir = new java.io.File(directoryPath);

		return ! dir.exists() && dir.mkdirs();
	}

	/**
	 * Empty (Clears) a Directory
	 *
	 * @param directoryPath - Path to the Directory
	 * @return - true if Directory was cleared else false
	 */
	public static boolean clearDirectory(String directoryPath) {
		return File.deleteDirectoryOperation(directoryPath, true, false);
	}

	/**
	 * Shows if this file can be encrypted
	 *
	 * @return - Can file be encrypted
	 */
	public boolean canBeEncrypted() {
		switch(this.extension) {
			case "png":
			case "m4a":
			case "ogg":
				return true;
			default:
				return false;
		}
	}

	/**
	 * Returns the real Extension of the current fake extension
	 *
	 * @return - Real File-Extension
	 */
	public String realExtByFakeExt() {
		switch(this.extension) {
			case "rpgmvp":
			case "png_":
				return "png";
			case "rpgmvm":
			case "m4a_":
				return "m4a";
			case "rpgmvo":
			case "ogg_":
				return "ogg";
			default:
				return "unknown";
		}
	}

	/**
	 * Returns the RPG-MV/MZ Extension of the current extension
	 *
	 * @return - Fake File-Extension
	 */
	public String fakeExtByRealExt(boolean isMV) {
		switch(this.extension) {
			case "png":
				return isMV ? "rpgmvp" : "png_";
			case "m4a":
				return isMV ? "rpgmvm" : "m4a_";
			case "ogg":
				return isMV ? "rpgmvo" : "ogg_";
			default:
				return this.extension;
		}
	}

	/**
	 * Shows if this file is an Image
	 *
	 * @return - true if the file is an image
	 */
	public boolean isImage() {
		switch(this.extension) {
			case "rpgmvp":
			case "png_":
			case "jpg":
			case "jpeg":
			case "png":
			case "gif":
			case "bmp":
			case "ico":
				return true;
			default:
				return false;
		}
	}

	/**
	 * Check if the given Extension is an Encrypted Extension
	 *
	 * @return - true if the Extension is an encrypted File-extension else false
	 */
	public boolean isFileEncryptedExt() {
		if(this.extension == null)
			return false;

		switch(this.extension) {
			case "rpgmvp":
			case "rpgmvm":
			case "rpgmvo":
			case "png_":
			case "m4a_":
			case "ogg_":
				return true;
			default:
				return false;
		}
	}
}
