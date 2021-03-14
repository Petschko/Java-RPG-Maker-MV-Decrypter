package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.Const;
import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.exceptions.PathException;
import org.petschko.lib.gui.*;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.rpgmakermv.decrypt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 28.12.2016
 * Time: 19:14
 * Update: 04.05.2019
 * Version: 0.1.3
 *
 * Notes: GUI Class
 */
public class GUI {
	private JFrame mainWindow;
	private GUI_Menu mainMenu;
	private JPanel windowPanel = new JPanel(new BorderLayout());
	private JPanel projectFilesPanel = new JPanel();
	private JPanel fileList = new JPanel();
	private GUI_About guiAbout;
	private GUI_FileInfo fileInfo = new GUI_FileInfo();
	private RPGProject rpgProject = null;
	private Decrypter decrypter = null;

	/**
	 * GUI Constructor
	 */
	public GUI() {
		// Create and Setup components
		this.createMainWindow();
		this.createMainMenu();
		this.guiAbout = new GUI_About("About " + Config.PROGRAM_NAME, this.mainWindow);
		this.createWindowGUI();

		// Center Window and Display it
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		this.mainWindow.pack();

		// Assign Listener
		this.assignMainMenuListener();
		this.setNewOutputDir(App.outputDir);

		// Add Update-Check
		if(Functions.strToBool(App.preferences.getConfig(Preferences.AUTO_CHECK_FOR_UPDATES, "true")))
			new GUI_Update(this, true);
	}

	/**
	 * Returns the Main-Window
	 *
	 * @return - Main-Window
	 */
	JFrame getMainWindow() {
		return mainWindow;
	}

	/**
	 * Returns the Main-Menu
	 *
	 * @return - Main-Menu
	 */
	GUI_Menu getMainMenu() {
		return mainMenu;
	}

	/**
	 * Returns the RPG-Project
	 *
	 * @return - RPG-Project
	 */
	RPGProject getRpgProject() {
		return rpgProject;
	}

	/**
	 * Sets the RPG-Project
	 *
	 * @param rpgProject - RPG-Project
	 */
	void setRpgProject(RPGProject rpgProject) {
		this.rpgProject = rpgProject;
	}

	/**
	 * Returns the Decrypter Object
	 *
	 * @return - Decrypter-Object
	 */
	Decrypter getDecrypter() {
		return decrypter;
	}

	/**
	 * Sets the Decrypter Object
	 *
	 * @param decrypter - Decrypter Object
	 */
	void setDecrypter(Decrypter decrypter) {
		this.decrypter = decrypter;
	}

	/**
	 * Dispose the GUI
	 */
	public void dispose() {
		this.guiAbout.dispose();
		this.mainWindow.dispose();
	}

	/**
	 * Creates and assign the MainFrame
	 */
	private void createMainWindow() {
		this.mainWindow = new JFrame(Config.PROGRAM_NAME + " by " + Const.CREATOR + " " + Config.VERSION);

		// Change close Action
		this.mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.mainWindow.addWindowListener(GUI_ActionListener.closeButton());
	}

	/**
	 * Creates and assign the Main-Menu
	 */
	private void createMainMenu() {
		this.mainMenu = new GUI_Menu();
		this.mainWindow.add(this.mainMenu, BorderLayout.NORTH);

		// Set Menu-Settings
		if(Functions.strToBool(App.preferences.getConfig(Preferences.IGNORE_FAKE_HEADER, "true")))
			this.mainMenu.ignoreFakeHeader.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.LOAD_INVALID_RPG_DIRS, "false")))
			this.mainMenu.loadInvalidRPGDirs.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true")))
			this.mainMenu.clearOutputDir.setState(true);

		if(! this.mainMenu.clearOutputDir.isSelected()) {
			if(Functions.strToBool(App.preferences.getConfig(Preferences.OVERWRITE_FILES, "false")))
				this.mainMenu.overwriteExistingFiles.setState(true);
		} else
			this.mainMenu.overwriteExistingFiles.setEnabled(false);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.AUTO_CHECK_FOR_UPDATES, "true")))
			this.mainMenu.checkForUpdates.setState(true);

		this.mainMenu.enableOnRPGProject(false);
	}

	/**
	 * Creates all Components for the Window
	 */
	private void createWindowGUI() {
		JPanel middleFileContainer = new JPanel(new GridLayout(1, 2));
		JPanel footContainer = new JPanel(new GridLayout(1, 3));
		JLabelWrap filesListText = new JLabelWrap("Please open a RPG-Maker MV Project (\"File\" -> \"Select RPG MV Project\")");
		filesListText.setColumns(20);

		/*JScrollPane scrollPane = new JScrollPane(
				this.fileList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);*/

		// Design stuff
		this.projectFilesPanel.setLayout(new BorderLayout());
		this.projectFilesPanel.setBorder(BorderFactory.createTitledBorder("Project-Files"));
		this.windowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Assign to the main comps
		middleFileContainer.add(this.projectFilesPanel);
		middleFileContainer.add(this.fileInfo);
		this.windowPanel.add(middleFileContainer, BorderLayout.CENTER);
		this.windowPanel.add(footContainer, BorderLayout.SOUTH);
		this.projectFilesPanel.add(filesListText, BorderLayout.NORTH);
		this.mainWindow.add(this.windowPanel, BorderLayout.CENTER);
	}

	/**
	 * Assigns ActionListener to the Main-Menu parts
	 */
	private void assignMainMenuListener() {
		// -- File
		this.mainMenu.open.addActionListener(
				e -> {
					String openDir = App.preferences.getConfig(Preferences.LAST_RPG_DIR, ".");

					if(! File.existsDir(openDir))
						openDir = ".";

					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
					int choose = dirChooser.showDialog(this.mainWindow, null);

					if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
						App.preferences.setConfig(Preferences.LAST_RPG_DIR, dirChooser.getCurrentDirectory().getPath());

						this.openRPGProject(dirChooser.getSelectedFile().getPath(), true);
					}
				}
		);
		this.mainMenu.changeOutputDir.addActionListener(
				e -> {
					// Warn the user that the selected directory will be cleared
					if(Boolean.parseBoolean(App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true")))
						new InfoWindow("You have chosen, that the selected Directory will be cleared.\nBeware that this Program clear the selected Directory (Deletes all Files within)! Don't select directories where you have important Files or Sub-Directories in!\n\n(Or turn off the clearing under Options)", "Important Info about your Files").show(this.mainWindow);

					String openDir = App.preferences.getConfig(Preferences.LAST_OUTPUT_PARENT_DIR, ".");

					if(! File.existsDir(openDir))
						openDir = ".";

					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
					int choose = dirChooser.showDialog(this.mainWindow, null);

					if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
						App.preferences.setConfig(Preferences.LAST_OUTPUT_PARENT_DIR, dirChooser.getCurrentDirectory().getPath());
						App.preferences.setConfig(Preferences.LAST_OUTPUT_DIR, dirChooser.getSelectedFile().getPath());
						this.setNewOutputDir(dirChooser.getSelectedFile().getPath());
					}
				}
		);
		this.mainMenu.exit.addActionListener(GUI_ActionListener.closeMenu());
		// -- Options
		this.mainMenu.ignoreFakeHeader.addActionListener(GUI_ActionListener.switchSetting(Preferences.IGNORE_FAKE_HEADER));
		this.mainMenu.loadInvalidRPGDirs.addActionListener(GUI_ActionListener.switchSetting(Preferences.LOAD_INVALID_RPG_DIRS));
		this.mainMenu.clearOutputDir.addActionListener(GUI_ActionListener.switchSetting(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT));
		this.mainMenu.clearOutputDir.addActionListener(
				e -> this.mainMenu.overwriteExistingFiles.setEnabled(! this.mainMenu.overwriteExistingFiles.isEnabled())
		);
		this.mainMenu.overwriteExistingFiles.addActionListener(GUI_ActionListener.switchSetting(Preferences.OVERWRITE_FILES));
		this.mainMenu.checkForUpdates.addActionListener(GUI_ActionListener.switchSetting(Preferences.AUTO_CHECK_FOR_UPDATES));
		// -- Decrypt
		// -- Tools
		// -- Info
		this.mainMenu.updateProgram.addActionListener(
				e -> new GUI_Update(this)
		);
		this.mainMenu.reportABug.addActionListener(GUI_ActionListener.openWebsite(Config.PROJECT_BUG_REPORT_URL));
		this.mainMenu.about.addActionListener(
				e -> this.guiAbout.showWindow()
		);
	}

	/**
	 * Assign RPG-Project ActionListeners
	 */
	void assignRPGActionListener() {
		// Remove all Previous ActionListeners
		Functions.buttonRemoveAllActionListeners(this.mainMenu.openRPGDirExplorer);
		Functions.buttonRemoveAllActionListeners(this.mainMenu.allFiles);
		Functions.buttonRemoveAllActionListeners(this.mainMenu.restoreImages);

		// Add new ActionListener
		this.mainMenu.openRPGDirExplorer.addActionListener(GUI_ActionListener.openExplorer(this.rpgProject.getPath()));

		this.mainMenu.allFiles.addActionListener(new GUI_Decryption(this, this.rpgProject.getEncryptedFiles()));
		this.mainMenu.restoreImages.addActionListener(new GUI_Decryption(this, this.rpgProject.getEncryptedFiles(), true));
	}

	/**
	 * Opens the RPG-MV-Project
	 *
	 * @param currentDirectory - Current RPG-Maker Directory
	 * @param showInfoWindow - Show Info-Window if done
	 */
	void openRPGProject(String currentDirectory, boolean showInfoWindow) {
		if(currentDirectory == null) {
			PathException pe = new PathException("currentDirectory can't be null!", (String) null);
			pe.printStackTrace();
			return;
		}

		GUI_OpenRPGDir openRPG = new GUI_OpenRPGDir(this, currentDirectory, showInfoWindow);
		openRPG.execute();
	}

	/**
	 * Set the new Output dir & assign new ActionListeners
	 *
	 * @param newOutputDir - New Output-Directory
	 */
	void setNewOutputDir(String newOutputDir) {
		App.outputDir = File.ensureDSonEndOfPath(newOutputDir);

		// Remove old ActionListener
		Functions.buttonRemoveAllActionListeners(this.mainMenu.openOutputDirExplorer);
		Functions.buttonRemoveAllActionListeners(this.mainMenu.doClearOutputDir);

		// New ActionListener
		this.mainMenu.openOutputDirExplorer.addActionListener(GUI_ActionListener.openExplorer(App.outputDir));
		this.mainMenu.doClearOutputDir.addActionListener(new GUI_DirectoryClearing(this, App.outputDir));
	}
}
