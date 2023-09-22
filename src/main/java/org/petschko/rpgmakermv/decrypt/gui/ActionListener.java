package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.gui.JDirectoryChooser;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Preferences;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Peter Dragicevic
 */
class ActionListener {

	/**
	 * Close via Menu ActionListener
	 *
	 * @return - Close ActionListener
	 */
	static java.awt.event.ActionListener closeMenu() {
		return e -> App.closeGUI();
	}

	/**
	 * Close-Action
	 *
	 * @return - Close WindowAdapter
	 */
	static WindowAdapter closeButton() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				App.closeGUI();
			}
		};
	}

	/**
	 * Switches a Config-Value with the given name
	 *
	 * @param configName - Config-Name
	 * @return - Switch-Setting ActionListener
	 */
	static java.awt.event.ActionListener switchSetting(String configName) {
		if(configName == null) {
			Exception e = new Exception("configName can't be null!");
			e.printStackTrace();
		}

		return e -> App.preferences.switchBoolConfig(configName);
	}

	/**
	 * Open-Website ActionListener
	 *
	 * @param url - Target URL
	 * @return - Open-Website ActionListener
	 */
	static java.awt.event.ActionListener openWebsite(String url) {
		return e -> Functions.openWebsite(url);
	}

	/**
	 * Open an Explorer with the given Path
	 *
	 * @param directoryPath - Path to open
	 * @return Open-Explorer ActionListener
	 */
	static java.awt.event.ActionListener openExplorer(String directoryPath) {
		return e -> {
			Desktop desktop = Desktop.getDesktop();
			String path = File.ensureDSonEndOfPath(directoryPath);

			if(path == null) {
				ErrorWindow errorWindow = new ErrorWindow(
						"File-Explorer can't be opened due to: Directory can't be null!",
						ErrorWindow.ERROR_LEVEL_ERROR,
						false
				);

				errorWindow.show();
				return;
			}

			try {
				desktop.open(new java.io.File(path).getAbsoluteFile());
			} catch(Exception ex) {
				ex.printStackTrace();
				ErrorWindow errorWindow = new ErrorWindow(
						"Unable to open the File-Explorer with the Directory: " + directoryPath,
						ErrorWindow.ERROR_LEVEL_ERROR,
						false
				);

				errorWindow.show();
			}
		};
	}

	/**
	 * Opens the File-Dialog and selects the RPG-Maker MV/MZ Project directory
	 *
	 * @param gui - Main GUI Object
	 * @return - Select RPG-Maker Project-Dir ActionListener
	 */
	static java.awt.event.ActionListener selectRPGMDir(GUI gui) {
		return e -> {
			String openDir = App.preferences.getConfig(Preferences.LAST_RPG_DIR, ".");

			if(! File.existsDir(openDir))
				openDir = ".";

			UIManager.put("FileChooser.readOnly", Boolean.TRUE);
			JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
			int choose = dirChooser.showDialog(gui.getMainWindow(), null);

			if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
				App.preferences.setConfig(Preferences.LAST_RPG_DIR, dirChooser.getCurrentDirectory().getPath());

				gui.openRPGProject(dirChooser.getSelectedFile().getPath(), true);
			}
		};
	}

	/**
	 * Changes the Output dir with a Directory Chooser
	 *
	 * @param gui - Main GUI Object
	 * @return - Change output dir ActionListener
	 */
	static java.awt.event.ActionListener changeOutputDirectory(GUI gui) {
		return e -> {
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
				gui.getMainMenu().doClearOutputDir.setEnabled(true);
			}
		};
	}

	/**
	 * Changes the current output directory path to the executable directory path
	 *
	 * @param gui - Main GUI Object
	 * @return - Change output dir to current dir ActionListener
	 */
	static java.awt.event.ActionListener changeOutputDirToCurrentDir(GUI gui) {
		return e -> {
			// Warn the user that the selected directory will be cleared
			if(Boolean.parseBoolean(App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true")))
				new InfoWindow("You have chosen, that the selected Directory will be cleared.\nBeware that this Program clear the selected Directory (Deletes all Files within)! Don't select directories where you have important Files or Sub-Directories in!\n\n(Or turn off the clearing under Options)", "Important Info about your Files").show(gui.getMainWindow());

			int answer = JOptionPane.showOptionDialog(
					gui.getMainWindow(),
					"Do you really want to change the output directory to the Directory of this Program?",
					"Change Directory",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					1
			);

			if(answer == 1)
				return;

			final String newDir = ".";

			App.preferences.setConfig(Preferences.LAST_OUTPUT_PARENT_DIR, newDir);
			App.preferences.setConfig(Preferences.LAST_OUTPUT_DIR, newDir);
			gui.setNewOutputDir(newDir);
			gui.getMainMenu().doClearOutputDir.setEnabled(false);
		};
	}
}
