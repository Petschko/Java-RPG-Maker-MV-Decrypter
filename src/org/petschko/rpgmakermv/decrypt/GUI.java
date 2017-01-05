package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;
import org.petschko.lib.Functions;
import org.petschko.lib.gui.JLabelWrap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 28.12.2016
 * Time: 19:14
 * Update: -
 * Version: 0.0.1
 *
 * Notes: GUI Class
 */
class GUI {
	private JFrame mainWindow;
	private GUI_Menu mainMenu;
	private JPanel windowPanel = new JPanel(new GridLayout(1, 2));
	private JPanel projectFilesPanel = new JPanel();
	private JPanel fileList = new JPanel();
	private GUI_FileInfo fileInfo = new GUI_FileInfo();
	private RPGProject rpgProject;
	private String decryptKey = "";

	/**
	 * GUI Constructor
	 */
	GUI() {
		// Create and Setup components
		this.createMainWindow();
		this.createMainMenu();
		this.createWindowGUI();

		// Center Window and Display it
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		this.mainWindow.pack();

		// Assign Listener
		this.assignMainMenuListener();
		// todo implement
	}

	/**
	 * Dispose the GUI
	 */
	void dispose() {
		mainWindow.dispose();
	}

	/**
	 * Creates and assign the MainFrame
	 */
	private void createMainWindow() {
		this.mainWindow = new JFrame(Config.programName + " by " + Const.creator + " " + Config.version);

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
		if(Functions.strToBool(App.preferences.getConfig(Preferences.ignoreFakeHeader, "false")))
			this.mainMenu.ignoreFakeHeader.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.loadInvalidRPGDirs, "false")))
			this.mainMenu.loadInvalidRPGDirs.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.clearOutputDirBeforeDecrypt, "true")))
			this.mainMenu.clearOutputDir.setState(true);

		if(! this.mainMenu.clearOutputDir.isSelected()) {
			if(Functions.strToBool(App.preferences.getConfig(Preferences.overwriteFiles, "false")))
				this.mainMenu.overwriteExistingFiles.setState(true);
		} else
			this.mainMenu.overwriteExistingFiles.setEnabled(false);
		this.mainMenu.enableOnRPGProject(false);
	}

	/**
	 * Creates all Components for the Window
	 */
	private void createWindowGUI() {
		JLabelWrap filesListText = new JLabelWrap("Please open a RPG-Maker MV Project (File -> Open)");
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
		this.projectFilesPanel.add(filesListText, BorderLayout.NORTH);
		this.mainWindow.add(this.windowPanel, BorderLayout.CENTER);
		this.windowPanel.add(projectFilesPanel);
		this.windowPanel.add(this.fileInfo);
	}

	/**
	 * Assigns ActionListener to the Main-Menu parts
	 */
	private void assignMainMenuListener() {
		// -- File
		this.mainMenu.exit.addActionListener(GUI_ActionListener.closeMenu());
		// -- Options
		this.mainMenu.ignoreFakeHeader.addActionListener(GUI_ActionListener.switchSetting(Preferences.ignoreFakeHeader));
		this.mainMenu.loadInvalidRPGDirs.addActionListener(GUI_ActionListener.switchSetting(Preferences.loadInvalidRPGDirs));
		this.mainMenu.clearOutputDir.addActionListener(GUI_ActionListener.switchSetting(Preferences.clearOutputDirBeforeDecrypt));
		this.mainMenu.clearOutputDir.addActionListener(
				e -> this.mainMenu.overwriteExistingFiles.setEnabled(! this.mainMenu.overwriteExistingFiles.isEnabled())
		);
		this.mainMenu.overwriteExistingFiles.addActionListener(GUI_ActionListener.switchSetting(Preferences.overwriteFiles));
		// -- Decrypt
		// -- Info
		this.mainMenu.reportABug.addActionListener(GUI_ActionListener.openWebsite(Config.projectBugReportURL));
	}
}
