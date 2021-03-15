package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.Const;
import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.exceptions.PathException;
import org.petschko.lib.gui.*;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.rpgmakermv.decrypt.*;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

/**
 * @author Peter Dragicevic
 */
public class GUI {
	private JFrame mainWindow;
	private Menu mainMenu;
	private JPanel windowPanel = new JPanel(new BorderLayout());
	JPanel projectFilesPanel = new JPanel();
	JList<java.io.File> fileList = new JList<>();
	private About guiAbout;
	private FileInfo fileInfo = new FileInfo();
	ProjectInfo projectInfo = new ProjectInfo();
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
		this.mainWindow.setPreferredSize(new Dimension(450, 500));

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
		JPanel middleStuffWrapper = new JPanel(new BorderLayout());
		JPanel middleFileContainer = new JPanel(new GridLayout(1, 2));

		// Design stuff
		this.projectFilesPanel.setLayout(new BorderLayout());
		this.projectFilesPanel.setBorder(BorderFactory.createTitledBorder("Project-Files"));
		this.windowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Assign to the main comps
		middleFileContainer.add(this.projectFilesPanel);
		middleFileContainer.add(this.fileInfo);

		this.projectInfo.reset();
		middleStuffWrapper.add(this.projectInfo, BorderLayout.NORTH);
		middleStuffWrapper.add(middleFileContainer, BorderLayout.CENTER);

		this.windowPanel.add(middleStuffWrapper, BorderLayout.CENTER);
		this.resetFileList();
		this.mainWindow.add(this.windowPanel, BorderLayout.CENTER);
	}

	/**
	 * Resets the File-List Panel
	 */
	void resetFileList() {
		JLabelWrap filesListText = new JLabelWrap("Please open a RPG-Maker MV Project (\"File\" -> \"Select RPG MV/MZ Project\")");
		filesListText.setColumns(20);
		filesListText.setForeground(Color.GRAY);
		filesListText.setBorder(BorderFactory.createEmptyBorder(5, 7, 5, 7));

		this.projectFilesPanel.removeAll();
		this.projectFilesPanel.add(filesListText, BorderLayout.NORTH);
		this.projectFilesPanel.validate();
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

	/**
	 * Removes everything from the open RPG-Project also removes UI-Changes
	 */
	void closeRPGProject() {
		setRpgProject(null);
		setDecrypter(null);

		mainMenu.enableOnRPGProject(false, this);
		mainMenu.deAssignRPGActionListener();

		resetFileList();
		projectInfo.reset();
	}

	/**
	 * Assigns the Decryption Code
	 */
	void assignDecryptKey() {
		String defaultValue = getDecrypter().getDecryptCode() == null ? "" : getDecrypter().getDecryptCode();
		String value = JOptionPane.showInputDialog(getMainWindow(), "Enter Decryption-Code:", defaultValue);

		if(value != null) {
			value = value.toLowerCase().trim();

			if(value.matches("[0-9a-f]+")) {
				getDecrypter().setDecryptCode(value);
				projectInfo.setEncryptionKey(value);
				projectInfo.refresh();
			} else {
				ErrorWindow ew = new ErrorWindow("Only 0-9 and A-F are allowed for the Code!", ErrorWindow.ERROR_LEVEL_WARNING, false);
				ew.show();
			}
		}
	}

	void changeHeaderSignature() {
		JTextField headerLen = new JTextField();
		headerLen.setText(String.valueOf(getDecrypter().getHeaderLen()));
		JTextField signature = new JTextField();
		signature.setText(getDecrypter().getSignature());
		JTextField version = new JTextField();
		version.setText(getDecrypter().getVersion());
		JTextField remain = new JTextField();
		remain.setText(getDecrypter().getRemain());

		Object[] fields = {
				"Header-Length:", headerLen,
				"Header-Signature:", signature,
				"Header-Version:", version,
				"Header-Remain:", remain
		};

		int option = JOptionPane.showConfirmDialog(getMainWindow(), fields, "Change-Header Values", JOptionPane.OK_CANCEL_OPTION);

		if(option == JOptionPane.OK_OPTION) {
			headerLen.setText(headerLen.getText().trim());
			signature.setText(signature.getText().toLowerCase().trim());
			version.setText(version.getText().toLowerCase().trim());
			remain.setText(remain.getText().toLowerCase().trim());
			ArrayList<String> errors = new ArrayList<>();
			boolean changes = false;

			if(headerLen.getText().matches("[0-9]+")) {
				getDecrypter().setHeaderLen(Integer.parseInt(headerLen.getText()));
				changes = true;
			} else
				errors.add("Header Length can be only numbers!");

			if(signature.getText().matches("[0-9a-f]+")) {
				getDecrypter().setSignature(signature.getText());
				projectInfo.setSignature(signature.getText());
				changes = true;
			} else
				errors.add("Only 0-9 and A-F are allowed for the Signature!");

			if(version.getText().matches("[0-9a-f]+")) {
				getDecrypter().setVersion(version.getText());
				projectInfo.setVersion(version.getText());
				changes = true;
			} else
				errors.add("Only 0-9 and A-F are allowed for the Version!");

			if(remain.getText().matches("[0-9a-f]+")) {
				getDecrypter().setRemain(remain.getText());
				projectInfo.setRemain(remain.getText());
				changes = true;
			} else
				errors.add("Only 0-9 and A-F are allowed for the Remain!");

			if(errors.size() > 0) {
				StringBuilder errorText = new StringBuilder();
				for(String e : errors)
					errorText.append(e).append(Const.NEW_LINE);

				ErrorWindow ew = new ErrorWindow(
						"There were some Errors in your inputs, these were are ignored:" + Const.NEW_LINE + Const.NEW_LINE + errorText,
						ErrorWindow.ERROR_LEVEL_WARNING,
						false
				);
				ew.show(getMainWindow());
			}

			if(changes)
				projectInfo.refresh();
		}
	}
}
