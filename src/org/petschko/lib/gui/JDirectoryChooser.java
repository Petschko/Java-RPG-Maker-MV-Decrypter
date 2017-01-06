package org.petschko.lib.gui;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 06.01.2017
 * Time: 22:18
 * Update: -
 * Version: 0.0.1
 *
 * Notes: -
 */
public class JDirectoryChooser extends JFileChooser{
	/**
	 * Constructs a <code>JFileChooser</code> using the given path.
	 * Passing in a <code>null</code>
	 * string causes the file chooser to point to the user's default directory.
	 * This default depends on the operating system. It is
	 * typically the "My Documents" folder on Windows, and the user's
	 * home directory on Unix.
	 *
	 * @param currentDirectoryPath a <code>String</code> giving the path
	 * to a file or directory
	 */
	public JDirectoryChooser(String currentDirectoryPath) {
		super(currentDirectoryPath);

		this.setupDirChooser();
	}

	/**
	 * Constructs a <code>JFileChooser</code> using the given <code>File</code>
	 * as the path. Passing in a <code>null</code> file
	 * causes the file chooser to point to the user's default directory.
	 * This default depends on the operating system. It is
	 * typically the "My Documents" folder on Windows, and the user's
	 * home directory on Unix.
	 *
	 * @param currentDirectory a <code>File</code> object specifying
	 * the path to a file or directory
	 */
	public JDirectoryChooser(File currentDirectory) {
		super(currentDirectory);

		this.setupDirChooser();
	}

	/**
	 * Setup the Parameter so that it look & works like a DirectoryChooser
	 */
	private void setupDirChooser() {
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
	}
}
