package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.NotNull;
import org.petschko.lib.File;
import org.petschko.lib.Functions;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 29.12.2016
 * Time: 17:59
 * Update: -
 * Version: 0.0.1
 *
 * Notes: GUI_ActionListener Class
 */
class GUI_ActionListener {

	/**
	 * Close via Menu ActionListener
	 *
	 * @return - Close ActionListener
	 */
	static ActionListener closeMenu() {
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
	static ActionListener switchSetting(@NotNull String configName) {
		boolean newValue = ! Functions.strToBool(App.preferences.getConfig(configName));

		return e -> App.preferences.setConfig(configName, Boolean.toString(newValue));
	}

	/**
	 * Open-Website ActionListener
	 *
	 * @param url - Target URL
	 * @return - Open-Website Action Listener
	 */
	static ActionListener openWebsite(String url) {
		return e -> Functions.openWebsite(url);
	}

	static ActionListener clearOutputDir(String outputDir) {
		return e -> File.clearDirectory(outputDir);
	}
}
