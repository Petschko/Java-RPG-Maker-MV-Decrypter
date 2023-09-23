package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.Functions;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;
import org.petschko.rpgmakermv.decrypt.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	JMenuItem openCurrentDir;
	JMenuItem openCurrentDirDecrypt;
	JMenuItem openCurrentDirEncrypt;
	JMenuItem changeOutputDir;
	JMenuItem changeOutputDirToCurrent;
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
	JMenuItem resetHeaderToDefault;

	// Encrypt-Menu-Sub
	JMenuItem encryptSelectedFilesMV;
	JMenuItem encryptAllFilesMV;
	JMenuItem encryptSelectedFilesMZ;
	JMenuItem encryptAllFilesMZ;
	JMenuItem setEncryptionKeyE;
	JMenuItem setEncryptionFileE;
	JMenuItem changeDecrypterSignatureE;
	JMenuItem resetHeaderToDefaultE;

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

		this.defaultDisabled();
		this.disableUnimplemented();

		if(! Config.UPDATE_CHECK)
			this.disableUpdateCheck();
	}

	/**
	 * Creates the File-Menu
	 */
	private void constructFileMenu() {
		this.file = new JMenu("File");

		// Sub-Items
		this.open = new JMenuItem("Select RPG MV/MZ Project...");
		this.openCurrentDir = new JMenuItem("Select Project from current Directory");
		this.openCurrentDirDecrypt = new JMenuItem("Select Project from current Dir & Decrypt");
		this.openCurrentDirEncrypt = new JMenuItem("Select Project from current Dir & Encrypt...");
		this.changeOutputDir = new JMenuItem("Change Output-Directory...");
		this.changeOutputDirToCurrent = new JMenuItem("Change Output-Directory to current Directory");
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
		this.changeDecrypterSignature = new JMenuItem("Change Header-Values...");
		this.resetHeaderToDefault = new JMenuItem("Reset Header-Values");
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
		this.resetHeaderToDefaultE = new JMenuItem(this.resetHeaderToDefault.getName());
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
		this.file.addSeparator();
		this.file.add(this.openCurrentDir);
		this.file.add(this.openCurrentDirDecrypt);
		this.file.add(this.openCurrentDirEncrypt);
		this.file.addSeparator();
		this.file.add(this.changeOutputDir);
		this.file.add(this.changeOutputDirToCurrent);
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
		this.decrypt.add(this.resetHeaderToDefault);

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
		this.encrypt.add(this.resetHeaderToDefaultE);

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
	 * @param gui - GUI-Object
	 */
	void enableOnRPGProject(boolean enable, GUI gui) {
		boolean hasEncryptedFiles = false;
		boolean hasResourceFiles = false;
		if(gui != null) {
			if(gui.getRpgProject() != null) {
				hasEncryptedFiles = gui.getRpgProject().getEncryptedFiles().size() > 0 && enable;
				hasResourceFiles = gui.getRpgProject().getResourceFiles().size() > 0 && enable;
			}
		}

		this.openRPGDirExplorer.setEnabled(enable);
		this.closeRPGProject.setEnabled(enable);

		this.decrypt.setEnabled(hasEncryptedFiles);
		//this.selectedFiles.setEnabled(hasEncryptedFiles);
		this.allFiles.setEnabled(hasEncryptedFiles);
		this.restoreImages.setEnabled(hasEncryptedFiles);
		//this.setEncryptionFile.setEnabled(hasEncryptedFiles || hasResourceFiles);
		this.setEncryptionKey.setEnabled(hasEncryptedFiles || hasResourceFiles);
		this.changeDecrypterSignature.setEnabled(hasEncryptedFiles || hasResourceFiles);

		//this.encrypt.setEnabled(hasResourceFiles);
		//this.encryptAllFilesMV.setEnabled(hasResourceFiles);
		//this.encryptAllFilesMZ.setEnabled(hasResourceFiles);
		//this.encryptSelectedFilesMV.setEnabled(hasResourceFiles);
		//this.encryptSelectedFilesMZ.setEnabled(hasResourceFiles);
		//this.setEncryptionFileE.setEnabled(hasEncryptedFiles || hasResourceFiles);
		this.setEncryptionKeyE.setEnabled(hasEncryptedFiles || hasResourceFiles);
		this.changeDecrypterSignatureE.setEnabled(hasEncryptedFiles || hasResourceFiles);

		//this.detectKeyFromEncryptedImg.setEnabled(enable);
		//this.restoreProjectMV.setEnabled(enable);
		//this.restoreProjectMZ.setEnabled(enable);
	}

	/**
	 * Disable when no Key is Found on a Project
	 */
	public void disableOnNoKey(boolean enable, GUI gui) {
		boolean hasEncryptedFiles = false;
		boolean hasResourceFiles = false;
		if(gui != null) {
			if(gui.getRpgProject() != null) {
				hasEncryptedFiles = gui.getRpgProject().getEncryptedFiles().size() > 0 && enable;
				hasResourceFiles = gui.getRpgProject().getResourceFiles().size() > 0 && enable;
			}
		}

		//this.selectedFiles.setEnabled(hasEncryptedFiles);
		this.allFiles.setEnabled(hasEncryptedFiles);

		//this.encryptAllFilesMV.setEnabled(hasResourceFiles);
		//this.encryptAllFilesMZ.setEnabled(hasResourceFiles);
		//this.encryptSelectedFilesMV.setEnabled(hasResourceFiles);
		//this.encryptSelectedFilesMZ.setEnabled(hasResourceFiles);

		//this.restoreProjectMV.setEnabled(enable);
		//this.restoreProjectMZ.setEnabled(enable);
	}

	/**
	 * Disable currently unimplemented Menus
	 */
	private void disableUnimplemented() {
		this.selectedFiles.setEnabled(false);
		this.setEncryptionFile.setEnabled(false);
		this.encrypt.setEnabled(false);
		this.detectKeyFromEncryptedImg.setEnabled(false);
		this.restoreProjectMV.setEnabled(false);
		this.restoreProjectMZ.setEnabled(false);
		this.help.setEnabled(false);
	}

	/**
	 * Disables the Update-Function in the Menu
	 */
	private void disableUpdateCheck() {
		this.checkForUpdates.setState(false);
		this.checkForUpdates.setEnabled(false);
		this.updateProgram.setEnabled(false);
	}

	/**
	 * Disables Menu points which are always disabled on startup
	 */
	private void defaultDisabled() {
		this.resetHeaderToDefault.setEnabled(false);
		this.resetHeaderToDefaultE.setEnabled(false);
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

		this.doClearOutputDir.setEnabled(!App.preferences.getConfig(Preferences.LAST_OUTPUT_DIR, "").equals("."));

		if(Config.UPDATE_CHECK) {
			if(Functions.strToBool(App.preferences.getConfig(Preferences.AUTO_CHECK_FOR_UPDATES, "true")))
				this.checkForUpdates.setState(true);
		}

		this.enableOnRPGProject(false, null);
	}

	/**
	 * Assigns all Action-Listeners to the Menu-Items
	 *
	 * @param gui - GUI-Object
	 */
	void assignActionListeners(GUI gui) {
		// -- File
		this.open.addActionListener(ActionListener.selectRPGMDir(gui));
		this.openCurrentDir.addActionListener(e -> { gui.openRPGProject(".", true);});
		this.openCurrentDirDecrypt.addActionListener(e -> {gui.openRPGProjectDecrypt(".");});
		this.openCurrentDirEncrypt.addActionListener(e -> {gui.openRPGProjectEncrypt(".");});
		this.changeOutputDir.addActionListener(ActionListener.changeOutputDirectory(gui));
		this.changeOutputDirToCurrent.addActionListener(ActionListener.changeOutputDirToCurrentDir(gui));
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
		this.changeDecrypterSignature.addActionListener(
				e -> gui.changeHeaderSignature()
		);
		this.resetHeaderToDefault.addActionListener(
				e -> gui.resetHeaderValues()
		);

		// -- Encrypt
		this.setEncryptionKeyE.addActionListener(
				e -> gui.assignDecryptKey()
		);
		this.changeDecrypterSignatureE.addActionListener(
				e -> gui.changeHeaderSignature()
		);
		this.resetHeaderToDefaultE.addActionListener(
				e -> gui.resetHeaderValues()
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
		this.restoreImages.addActionListener(new WorkerDecryption(gui, gui.getRpgProject().getEncryptedImages(), true));
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
