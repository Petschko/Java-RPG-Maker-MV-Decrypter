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
 * @author Peter Dragicevic
 */
public class GUI {
	private JFrame mainWindow;
	private Menu mainMenu;
	private JPanel windowPanel = new JPanel(new BorderLayout());
	private JPanel projectFilesPanel = new JPanel();
	private JPanel fileList = new JPanel();
	private About guiAbout;
	private FileInfo fileInfo = new FileInfo();
	private RPG_Project rpgProject = null;
	private Decrypter decrypter = null;

	/**
	 * GUI Constructor
	 */
	public GUI() {
		// Create and Setup components
		this.createMainWindow();
		this.createMainMenu();
		this.guiAbout = new About("About " + Config.PROGRAM_NAME, this.mainWindow);
		this.createWindowGUI();

		// Center Window and Display it
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		this.mainWindow.pack();

		// Assign Listener
		this.mainMenu.assignActionListeners(this);
		this.setNewOutputDir(App.outputDir);

		// Add Update-Check
		if(Functions.strToBool(App.preferences.getConfig(Preferences.AUTO_CHECK_FOR_UPDATES, "true")))
			new Update(this, true);
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
	Menu getMainMenu() {
		return mainMenu;
	}

	/**
	 * Returns the RPG-Project
	 *
	 * @return - RPG-Project
	 */
	RPG_Project getRpgProject() {
		return rpgProject;
	}

	/**
	 * Sets the RPG-Project
	 *
	 * @param rpgProject - RPG-Project
	 */
	void setRpgProject(RPG_Project rpgProject) {
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
	 * Returns the GUI-About Object
	 *
	 * @return - GUI-About Object
	 */
	About getGuiAbout() {
		return guiAbout;
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
		this.mainWindow.addWindowListener(ActionListener.closeButton());
	}

	/**
	 * Creates and assign the Main-Menu
	 */
	private void createMainMenu() {
		this.mainMenu = new Menu();
		this.mainWindow.add(this.mainMenu, BorderLayout.NORTH);

		// Set Menu-Settings
		this.mainMenu.loadSettings();
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

		WorkerOpenRPGDir openRPG = new WorkerOpenRPGDir(this, currentDirectory, showInfoWindow);
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
		this.mainMenu.openOutputDirExplorer.addActionListener(ActionListener.openExplorer(App.outputDir));
		this.mainMenu.doClearOutputDir.addActionListener(new WorkerDirectoryClearing(this, App.outputDir));
	}
}
