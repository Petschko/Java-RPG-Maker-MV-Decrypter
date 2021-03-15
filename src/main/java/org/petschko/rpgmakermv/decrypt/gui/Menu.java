package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.gui.JDirectoryChooser;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;
import org.petschko.rpgmakermv.decrypt.Preferences;

import javax.swing.*;

/**
 * @author Peter Dragicevic
 */
class Menu extends JMenuBar {
	// Main Menu-Points
	JMenu file;
	JMenu options;
	JMenu decrypt;
	JMenu encrypt;
	JMenu tools;
	JMenu info;

	// File-Menu-Sub
	JMenuItem open;
	JMenuItem changeOutputDir;
	JMenuItem openRPGDirExplorer;
	JMenuItem openOutputDirExplorer;
	JMenuItem closeRPGProject;
	JMenuItem exit;

	// Options-Menu-Sub
	JCheckBoxMenuItem ignoreFakeHeader;
	JCheckBoxMenuItem loadInvalidRPGDirs;
	JCheckBoxMenuItem clearOutputDir;
	JCheckBoxMenuItem overwriteExistingFiles;
	JCheckBoxMenuItem checkForUpdates;

	// Decrypt-Menu-Sub
	JMenuItem selectedFiles;
	JMenuItem allFiles;
	JMenuItem restoreImages;
	JMenuItem setEncryptionKey;
	JMenuItem setEncryptionFile;
	JMenuItem changeDecrypterSignature;

	// Encrypt-Menu-Sub
	JMenuItem encryptSelectedFilesMV;
	JMenuItem encryptAllFilesMV;
	JMenuItem encryptSelectedFilesMZ;
	JMenuItem encryptAllFilesMZ;
	JMenuItem setEncryptionKeyE;
	JMenuItem setEncryptionFileE;
	JMenuItem changeDecrypterSignatureE;

	// Tool-Menu-Sub
	JMenuItem detectKeyFromEncryptedImg;
	JMenuItem restoreProjectMV;
	JMenuItem restoreProjectMZ;
	JMenuItem doClearOutputDir;

	// Info-Menu-Sub
	JMenuItem help;
	JMenuItem updateProgram;
	JMenuItem reportABug;
	JMenuItem about;

	/**
	 * Menu Constructor
	 */
	Menu() {
		this.constructFileMenu();
		this.constructOptionsMenu();
		this.constructDecryptMenu();
		this.constructEncryptMenu();
		this.constructToolsMenu();
		this.constructInfoMenu();
		this.addAllMenus();

		this.disableUnimplemented();
	}

	/**
	 * Creates the File-Menu
	 */
	private void constructFileMenu() {
		this.file = new JMenu("File");

		// Sub-Items
		this.open = new JMenuItem("Select RPG MV/MZ Project...");
		this.changeOutputDir = new JMenuItem("Change Output-Directory...");
		this.openRPGDirExplorer = new JMenuItem("Show RPG-Dir in Explorer");
		this.openOutputDirExplorer = new JMenuItem("Show Output-Dir in Explorer");
		this.closeRPGProject = new JMenuItem("Close RPG MV/MZ Project");
		this.exit = new JMenuItem("Exit");
	}

	/**
	 * Creates the Options-Menu
	 */
	private void constructOptionsMenu() {
		this.options = new JMenu("Options");

		// Sub-Items
		this.ignoreFakeHeader = new JCheckBoxMenuItem("Ignore Fake-Header (Faster)");
		this.loadInvalidRPGDirs = new JCheckBoxMenuItem("Load invalid RPG-MV-Dirs anyway");
		this.clearOutputDir = new JCheckBoxMenuItem("Clear output Dir before Decrypt");
		this.overwriteExistingFiles = new JCheckBoxMenuItem("Overwrite existing Files");
		this.checkForUpdates = new JCheckBoxMenuItem("Auto check for Updates");
	}

	/**
	 * Creates the Decrypt-Menu
	 */
	private void constructDecryptMenu() {
		this.decrypt = new JMenu("Decrypt");

		// Sub-Items
		this.selectedFiles = new JMenuItem("Selected Files");
		this.allFiles = new JMenuItem("All Files");
		this.restoreImages = new JMenuItem("Restore Images (No Key)");
		this.setEncryptionKey = new JMenuItem("Set Encryption-Key...");
		this.setEncryptionFile = new JMenuItem("Key from Encrypted-PNG-File...");
		this.changeDecrypterSignature = new JMenuItem("Change Decrypter Signature...");
	}

	/**
	 * Creates the Encrypt-Menu
	 */
	private void constructEncryptMenu() {
		this.encrypt = new JMenu("Encrypt");

		// Sub-Items
		this.encryptSelectedFilesMV = new JMenuItem("Selected Files (to MV)");
		this.encryptAllFilesMV = new JMenuItem("All Files (to MV)");
		this.encryptSelectedFilesMZ = new JMenuItem("Selected Files (to MZ)");
		this.encryptAllFilesMZ = new JMenuItem("All Files (to MZ)");
		this.setEncryptionKeyE = new JMenuItem(this.setEncryptionKey.getName());
		this.setEncryptionFileE = new JMenuItem(this.setEncryptionFile.getName());
		this.changeDecrypterSignatureE = new JMenuItem(this.changeDecrypterSignature.getName());
	}

	/**
	 * Creates the Tools-Menu
	 */
	private void constructToolsMenu() {
		this.tools = new JMenu("Tools");

		this.detectKeyFromEncryptedImg = new JMenuItem("Extract Key from .rpgmvp/.png_ File");
		this.restoreProjectMV = new JMenuItem("Restore-Project (MV) (Experimental)");
		this.restoreProjectMZ = new JMenuItem("Restore-Project (MZ) (Experimental)");
		this.doClearOutputDir = new JMenuItem("Clear Output-Dir now");
	}

	/**
	 * Creates the Info-Menu
	 */
	private void constructInfoMenu() {
		this.info = new JMenu("Info");

		this.help = new JMenuItem("Help");
		this.updateProgram = new JMenuItem("Check for Updates");
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
		this.file.add(this.openRPGDirExplorer);
		this.file.add(this.openOutputDirExplorer);
		this.file.addSeparator();
		this.file.add(this.closeRPGProject);
		this.file.addSeparator();
		this.file.add(this.exit);

		this.add(this.options);
		this.options.add(this.ignoreFakeHeader);
		this.options.add(this.loadInvalidRPGDirs);
		this.options.addSeparator();
		this.options.add(this.clearOutputDir);
		this.options.add(this.overwriteExistingFiles);
		this.options.addSeparator();
		this.options.add(this.checkForUpdates);

		this.add(this.decrypt);
		this.decrypt.add(this.selectedFiles);
		this.decrypt.add(this.allFiles);
		this.decrypt.add(this.restoreImages);
		this.decrypt.addSeparator();
		this.decrypt.add(this.setEncryptionFile);
		this.decrypt.add(this.setEncryptionKey);
		this.decrypt.add(this.changeDecrypterSignature);

		this.add(this.encrypt);
		this.encrypt.add(this.encryptSelectedFilesMV);
		this.encrypt.add(this.encryptAllFilesMV);
		this.encrypt.addSeparator();
		this.encrypt.add(this.encryptSelectedFilesMZ);
		this.encrypt.add(this.encryptAllFilesMZ);
		this.encrypt.addSeparator();
		this.encrypt.add(this.setEncryptionKeyE);
		this.encrypt.add(this.setEncryptionFileE);
		this.encrypt.add(this.changeDecrypterSignatureE);

		this.add(this.tools);
		this.tools.add(this.detectKeyFromEncryptedImg);
		this.tools.add(this.restoreProjectMV);
		this.tools.add(this.restoreProjectMZ);
		this.tools.addSeparator();
		this.tools.add(this.doClearOutputDir);

		this.add(this.info);
		this.info.add(this.help);
		this.info.add(this.updateProgram);
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
		this.openRPGDirExplorer.setEnabled(enable);
		this.closeRPGProject.setEnabled(enable);
		this.decrypt.setEnabled(enable);
		//this.selectedFiles.setEnabled(enable);
		this.allFiles.setEnabled(enable);
		this.restoreImages.setEnabled(enable);
		//this.setEncryptionFile.setEnabled(enable);
		this.setEncryptionKey.setEnabled(enable);
		//this.restoreProject.setEnabled(enable);
	}

	/**
	 * Disable currently unimplemented Menus
	 */
	private void disableUnimplemented() {
		this.selectedFiles.setEnabled(false);
		this.setEncryptionFile.setEnabled(false);
		this.changeDecrypterSignature.setEnabled(false);
		this.encrypt.setEnabled(false);
		this.detectKeyFromEncryptedImg.setEnabled(false);
		this.restoreProjectMV.setEnabled(false);
		this.restoreProjectMZ.setEnabled(false);
		this.help.setEnabled(false);
	}

	/**
	 * Loads the settings from Preferences for the Menu
	 */
	void loadSettings() {
		if(Functions.strToBool(App.preferences.getConfig(Preferences.IGNORE_FAKE_HEADER, "true")))
			this.ignoreFakeHeader.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.LOAD_INVALID_RPG_DIRS, "false")))
			this.loadInvalidRPGDirs.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true")))
			this.clearOutputDir.setState(true);

		if(! this.clearOutputDir.isSelected()) {
			if(Functions.strToBool(App.preferences.getConfig(Preferences.OVERWRITE_FILES, "false")))
				this.overwriteExistingFiles.setState(true);
		} else
			this.overwriteExistingFiles.setEnabled(false);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.AUTO_CHECK_FOR_UPDATES, "true")))
			this.checkForUpdates.setState(true);

		this.enableOnRPGProject(false);
	}

	/**
	 * Assigns all Action-Listeners to the Menu-Items
	 *
	 * @param gui - GUI-Object
	 */
	void assignActionListeners(GUI gui) {
		// -- File
		this.open.addActionListener(
				e -> {
					String openDir = App.preferences.getConfig(Preferences.LAST_RPG_DIR, ".");

					if(! File.existsDir(openDir))
						openDir = ".";

					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
					int choose = dirChooser.showDialog(gui.getMainMenu(), null);

					if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
						App.preferences.setConfig(Preferences.LAST_RPG_DIR, dirChooser.getCurrentDirectory().getPath());

						gui.openRPGProject(dirChooser.getSelectedFile().getPath(), true);
					}
				}
		);
		this.changeOutputDir.addActionListener(
				e -> {
					// Warn the user that the selected directory will be cleared
					if(Boolean.parseBoolean(App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true")))
						new InfoWindow("You have chosen, that the selected Directory will be cleared.\nBeware that this Program clear the selected Directory (Deletes all Files within)! Don't select directories where you have important Files or Sub-Directories in!\n\n(Or turn off the clearing under Options)", "Important Info about your Files").show(gui.getMainWindow());

					String openDir = App.preferences.getConfig(Preferences.LAST_OUTPUT_PARENT_DIR, ".");

					if(! File.existsDir(openDir))
						openDir = ".";

					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
					int choose = dirChooser.showDialog(gui.getMainMenu(), null);

					if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
						App.preferences.setConfig(Preferences.LAST_OUTPUT_PARENT_DIR, dirChooser.getCurrentDirectory().getPath());
						App.preferences.setConfig(Preferences.LAST_OUTPUT_DIR, dirChooser.getSelectedFile().getPath());
						gui.setNewOutputDir(dirChooser.getSelectedFile().getPath());
					}
				}
		);
		this.exit.addActionListener(ActionListener.closeMenu());
		// -- Options
		this.ignoreFakeHeader.addActionListener(ActionListener.switchSetting(Preferences.IGNORE_FAKE_HEADER));
		this.loadInvalidRPGDirs.addActionListener(ActionListener.switchSetting(Preferences.LOAD_INVALID_RPG_DIRS));
		this.clearOutputDir.addActionListener(ActionListener.switchSetting(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT));
		this.clearOutputDir.addActionListener(
				e -> this.overwriteExistingFiles.setEnabled(! this.overwriteExistingFiles.isEnabled())
		);
		this.overwriteExistingFiles.addActionListener(ActionListener.switchSetting(Preferences.OVERWRITE_FILES));
		this.checkForUpdates.addActionListener(ActionListener.switchSetting(Preferences.AUTO_CHECK_FOR_UPDATES));
		// -- Decrypt
		this.setEncryptionKey.addActionListener(
				e -> gui.assignDecryptKey()
		);
		// -- Encrypt
		this.setEncryptionKeyE.addActionListener(
				e -> gui.assignDecryptKey()
		);
		// -- Tools
		// -- Info
		this.updateProgram.addActionListener(
				e -> new Update(gui)
		);
		this.reportABug.addActionListener(ActionListener.openWebsite(Config.PROJECT_BUG_REPORT_URL));
		this.about.addActionListener(
				e -> gui.getGuiAbout().showWindow()
		);
	}

	/**
	 * Assign RPG-Project ActionListeners
	 *
	 * @param gui - GUI-Object
	 */
	void assignRPGActionListener(GUI gui) {
		// Remove all Previous ActionListeners
		Functions.buttonRemoveAllActionListeners(this.openRPGDirExplorer);
		Functions.buttonRemoveAllActionListeners(this.closeRPGProject);
		Functions.buttonRemoveAllActionListeners(this.allFiles);
		Functions.buttonRemoveAllActionListeners(this.restoreImages);

		// Add new ActionListener
		this.openRPGDirExplorer.addActionListener(ActionListener.openExplorer(gui.getRpgProject().getPath()));
		this.closeRPGProject.addActionListener(
				e -> gui.closeRPGProject()
		);

		this.allFiles.addActionListener(new WorkerDecryption(gui, gui.getRpgProject().getEncryptedFiles()));
		this.restoreImages.addActionListener(new WorkerDecryption(gui, gui.getRpgProject().getEncryptedFiles(), true));
	}

	/**
	 * De-Assigns Action-Listeners for RPG-Project
	 */
	void deAssignRPGActionListener() {
		Functions.buttonRemoveAllActionListeners(openRPGDirExplorer);
		Functions.buttonRemoveAllActionListeners(closeRPGProject);
		Functions.buttonRemoveAllActionListeners(allFiles);
		Functions.buttonRemoveAllActionListeners(restoreImages);
	}
}
