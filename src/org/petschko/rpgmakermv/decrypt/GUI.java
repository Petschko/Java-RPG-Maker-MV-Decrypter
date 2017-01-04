package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.petschko.lib.Const;
import org.petschko.lib.Functions;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.io.StringWriter;

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
	private RPGProject rpgProject;

	/**
	 * GUI Constructor
	 */
	GUI() {
		// Create and Setup components
		this.createMainWindow();
		this.createMainMenu();

		// Center Window and Display it
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		this.mainWindow.pack();

		// Assign Listener
		// todo implement

	}

	/**
	 * Dispose the GUI
	 */
	void dispose() {
		mainWindow.dispose();
	}

	private void createMainWindow() {
		this.mainWindow = new JFrame(Config.programName + " by " + Const.creator + " " + Config.version);

		// Change close Action
		this.mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.mainWindow.addWindowListener(GUI_ActionListener.closeButton());
	}

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

		if(! this.mainMenu.clearOutputDir.isSelected())
			if(Functions.strToBool(App.preferences.getConfig(Preferences.overwriteFiles, "false")))
				this.mainMenu.overwriteExistingFiles.setState(true);
		else
			this.mainMenu.overwriteExistingFiles.setEnabled(false);
		this.mainMenu.enableOnRPGProject(false);

		// Assign listener
		this.mainMenu.exit.addActionListener(GUI_ActionListener.closeMenu());
	}

	/**
	 * Shows an Error/Info Window and may Exit the Program
	 *
	 * @param message - Message to Display
	 * @param errorLevel - Error-Level
	 * @param stopProgram - true if Program should exit on this Error else false
	 * @param e - Error-Exception
	 */
	void showError(@NotNull String message, int errorLevel, boolean stopProgram, @Nullable Exception e) {
		String msg = message;
		String title;
		int type;

		if(e != null) {
			StringWriter stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			msg = msg + " | Exception-Trace: " + stringWriter.toString();
		}

		switch(errorLevel) {
			case Const.errorLevel_fatal:
				title = "Fatal-Error";
				type = JOptionPane.ERROR_MESSAGE;
				break;
			case Const.errorLevel_error:
				title = "Error";
				type = JOptionPane.ERROR_MESSAGE;
				break;
			case Const.errorLevel_warning:
				title = "Warning";
				type = JOptionPane.WARNING_MESSAGE;
				break;
			case Const.errorLevel_notice:
				title = "Notice";
				type = JOptionPane.INFORMATION_MESSAGE;
				break;
			case Const.errorLevel_info:
			default:
				title = "Info";
				type = JOptionPane.INFORMATION_MESSAGE;
		}

		JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.DEFAULT_OPTION, type);

		if(stopProgram)
			System.exit(type);
	}

	/**
	 * Shows an Info-Window
	 *
	 * @param message - Info-Message
	 */
	void showInfo(@NotNull String message) {
		this.showError(message, Const.errorLevel_info, false, null);
	}
}
