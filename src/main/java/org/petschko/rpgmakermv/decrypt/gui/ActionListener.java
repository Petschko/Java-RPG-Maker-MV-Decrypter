package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.rpgmakermv.decrypt.App;

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
}
