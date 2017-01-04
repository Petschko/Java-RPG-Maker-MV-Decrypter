package org.petschko.rpgmakermv.decrypt;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 29.12.2016
 * Time: 17:07
 * Update: -
 * Version: 1.0.0
 *
 * Notes: GUI_Menu Class
 */
class GUI_Menu extends JMenuBar {
	// Main Menu-Points
	JMenu file;
	JMenu options;
	JMenu decrypt;
	JMenu tools;
	JMenu info;

	// File-Menu-Sub
	JMenuItem open;
	JMenuItem changeOutputDir;
	JMenuItem exit;

	// Options-Menu-Sub
	JCheckBoxMenuItem ignoreFakeHeader;
	JCheckBoxMenuItem loadInvalidRPGDirs;
	JCheckBoxMenuItem clearOutputDir;
	JCheckBoxMenuItem overwriteExistingFiles;

	// Decrypt-Menu-Sub
	JMenuItem selectedFiles;
	JMenuItem allFiles;
	JMenuItem setEncryptionKey;
	JMenuItem changeDecrypterSignature;

	// Tool-Menu-Sub
	JMenuItem restoreProject;
	JMenuItem doClearOutputDir;

	// Info-Menu-Sub
	JMenuItem help;
	JMenuItem reportABug;
	JMenuItem about;

	/**
	 * GUI_Menu Constructor
	 */
	GUI_Menu() {
		this.constructFileMenu();
		this.constructOptionsMenu();
		this.constructDecryptMenu();
		this.constructToolsMenu();
		this.constructInfoMenu();
		this.addAllMenus();
	}

	/**
	 * Creates the File-Menu
	 */
	private void constructFileMenu() {
		this.file = new JMenu("File");

		// Sub-Items
		this.open = new JMenuItem("Select RPG MV Project...");
		this.changeOutputDir = new JMenuItem("Change Output-Directory...");
		this.exit = new JMenuItem("Exit");
	}

	/**
	 * Creates the Options-Menu
	 */
	private void constructOptionsMenu() {
		this.options = new JMenu("Options");

		// Sub-Items
		this.ignoreFakeHeader = new JCheckBoxMenuItem("Ignore Fake-Header");
		this.loadInvalidRPGDirs = new JCheckBoxMenuItem("Load invalid RPG-MV-Dirs anyway");
		this.clearOutputDir = new JCheckBoxMenuItem("Clear output Dir before Decrypt");
		this.overwriteExistingFiles = new JCheckBoxMenuItem("Overwrite existing Files");
	}

	/**
	 * Creates the Decrypt-Menu
	 */
	private void constructDecryptMenu() {
		this.decrypt = new JMenu("Decrypt");

		// Sub-Items
		this.selectedFiles = new JMenuItem("Selected Files");
		this.allFiles = new JMenuItem("All Files");
		this.setEncryptionKey = new JMenuItem("Set Encryption-Key...");
		this.changeDecrypterSignature = new JMenuItem("Change Decrypter Signature...");
	}

	/**
	 * Creates the Tools-Menu
	 */
	private void constructToolsMenu() {
		this.tools = new JMenu("Tools");

		this.restoreProject = new JMenuItem("Restore-Project (Experimental)");
		this.doClearOutputDir = new JMenuItem("Clear Output-Dir now");
	}

	/**
	 * Creates the Info-Menu
	 */
	private void constructInfoMenu() {
		this.info = new JMenu("Info");

		this.help = new JMenuItem("Help");
		this.reportABug = new JMenuItem("Report a Bug...");
		this.about = new JMenuItem("About");
	}

	/**
	 * Puts all Menus and Menu-Items together
	 */
	private void addAllMenus() {
		this.add(this.file);
		this.file.add(this.open);
		this.file.add(this.changeOutputDir);
		this.file.addSeparator();
		this.file.add(this.exit);

		this.add(this.options);
		this.options.add(this.ignoreFakeHeader);
		this.options.add(this.loadInvalidRPGDirs);
		this.options.addSeparator();
		this.options.add(this.clearOutputDir);
		this.options.add(this.overwriteExistingFiles);

		this.add(this.decrypt);
		this.decrypt.add(this.selectedFiles);
		this.decrypt.add(this.allFiles);
		this.decrypt.addSeparator();
		this.decrypt.add(this.setEncryptionKey);
		this.decrypt.add(this.changeDecrypterSignature);

		this.add(this.tools);
		this.tools.add(this.restoreProject);
		this.tools.add(this.doClearOutputDir);

		this.add(this.info);
		this.info.add(this.help);
		this.info.add(this.reportABug);
		this.info.addSeparator();
		this.info.add(this.about);
	}

	/**
	 * Enable/Disable Menu-Items for RPGMaker Project-Operations
	 *
	 * @param enable - enable or disable Menu-Items
	 */
	void enableOnRPGProject(boolean enable) {
		this.selectedFiles.setEnabled(enable);
		this.allFiles.setEnabled(enable);
		this.restoreProject.setEnabled(enable);
	}
}
