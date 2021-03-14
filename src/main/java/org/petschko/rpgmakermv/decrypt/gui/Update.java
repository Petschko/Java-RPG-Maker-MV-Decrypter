package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.Const;
import org.petschko.lib.gui.JOptionPane;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.lib.update.UpdateException;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;
import org.petschko.rpgmakermv.decrypt.Preferences;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 20.02.2021
 * Time: 17:29
 *
 * Notes: Class Update
 */

class Update {
	private GUI gui;
	private org.petschko.lib.update.Update update = null;
	private String[] options;
	private boolean autoOptionExists = false;
	private boolean ranAutomatically = false;

	/**
	 * GUI_Update constructor
	 *
	 * @param gui - Main GUI-Object
	 */
	Update(GUI gui) {
		this.gui = gui;
		this.options = new String[] {"Update", "Show whats new", "Cancel"};

		this.init();
	}

	/**
	 * GUI_Update constructor
	 *
	 * @param gui - Main GUI-Object
	 * @param auto - This ran automatically
	 */
	Update(GUI gui, boolean auto) {
		this.gui = gui;
		this.options = new String[] {"Update", "Show whats new", "Disable update check", "Cancel"};
		this.autoOptionExists = true;
		this.ranAutomatically = auto;

		this.init();
	}

	/**
	 * Inits the Object
	 */
	private void init() {
		try {
			if(this.ranAutomatically)
				update = new org.petschko.lib.update.Update(Config.UPDATE_URL, Config.VERSION_NUMBER, Config.UPDATE_CHECK_EVERY_SECS);
			else
				update = new org.petschko.lib.update.Update(Config.UPDATE_URL, Config.VERSION_NUMBER, true);
		} catch (IOException e) {
			if(! this.ranAutomatically) {
				ErrorWindow ew = new ErrorWindow("Can't check for Updates...", ErrorWindow.ERROR_LEVEL_WARNING, false);
				ew.show(this.gui.getMainWindow());
			}
		}

		this.checkIfUpdate();
	}

	/**
	 * Checks if an update exists
	 */
	private void checkIfUpdate() {
		if(update != null) {
			if(update.isHasNewVersion()) {
				// Ask the user what to do
				int response = JOptionPane.showOptionDialog(
						this.gui.getMainWindow(),
						"Update found!" + Const.NEW_LINE +
								"Your Version: " + Config.VERSION_NUMBER + Const.NEW_LINE +
								"New Version: " + update.getNewestVersion() + Const.NEW_LINE + Const.NEW_LINE +
								"What do you want to do?",
						"Update found",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						this.options,
						this.options[0]
				);

				if(response == 0)
					this.runUpdate();
				else if(response == 1)
					this.showWhatsNew();
				else if(this.autoOptionExists && response == 2) {
					App.preferences.switchBoolConfig(Preferences.AUTO_CHECK_FOR_UPDATES);
					gui.getMainMenu().checkForUpdates.setState(false);
				}
			} else if(! this.ranAutomatically) {
				InfoWindow infoWindow = new InfoWindow("You're using the newest Version!", "No update found");
				infoWindow.show(this.gui.getMainWindow());
			}
		}
	}

	/**
	 * Runs the update
	 */
	private void runUpdate() {
		try {
			update.runUpdate(Config.THIS_JAR_FILE_NAME, true, true, null);
		} catch(UpdateException e) {
			ErrorWindow errorWindow = new ErrorWindow("Update Failed!", ErrorWindow.ERROR_LEVEL_ERROR, false, e);
			errorWindow.show(this.gui.getMainWindow());

			e.printStackTrace();
		}
	}

	/**
	 * Brings the user to the whats new url
	 */
	private void showWhatsNew() {
		if(Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			if(desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					URI uri = new URI(update.getWhatsNewUrl().toString());
					desktop.browse(uri);
				} catch(IOException | URISyntaxException e) {
					ErrorWindow errorWindow = new ErrorWindow("Can't open \"What's new...\"", ErrorWindow.ERROR_LEVEL_ERROR, false, e);
					errorWindow.show(this.gui.getMainWindow());

					e.printStackTrace();
				}
			}
		} else {
			ErrorWindow errorWindow = new ErrorWindow("Can't open \"What's new...\"..." + Const.NEW_LINE + "This operation isnt supported by your OS!", ErrorWindow.ERROR_LEVEL_ERROR, false);
			errorWindow.show(this.gui.getMainWindow());
		}
	}
}
