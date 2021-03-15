package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.lib.update.UpdateException;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Peter Dragicevic
 */
class Update implements I_CMD {
	private static final String SUB_CMD_WHATS_NEW = "whatsnew";

	private org.petschko.lib.update.Update update = null;

	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	@Override
	public void run(String[] args) {
		this.update = checkForUpdates(false, true);

		if(this.update != null) {
			if(args.length >= 2) {
				String subCmd = args[1];

				// Handle sub CMDs
				if(subCmd.equals(SUB_CMD_WHATS_NEW)) {
					this.showWhatsNew();
				} else {
					App.showMessage("Sub-Command \"" + subCmd + "\" doesnt exists! See help", CMD.STATUS_WARNING);
					App.showMessage("");
					this.printHelp();
					CMD.exitCMD(CMD.STATUS_WARNING);
				}
				return;
			}

			this.runUpdate();
		}
	}

	/**
	 * Runs the Update
	 */
	private void runUpdate() {
		if(! this.update.isHasNewVersion()) {
			System.out.println("You are already using the newest Version!");
			System.out.println(CMD.LINE_CMD);
			return;
		}

		App.showMessage("Starting update...");
		try {
			update.runUpdate(Config.THIS_JAR_FILE_NAME, false);
		} catch(UpdateException e) {
			App.showMessage("Update Failed... Cause: " + e.getMessage());
		}
	}

	/**
	 * Opens the Link in whats new
	 */
	private void showWhatsNew() {
		if(! this.update.isHasNewVersion()) {
			System.out.println("You are already using the newest Version!");
			System.out.println(CMD.LINE_CMD);
		} else if(Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			if(desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					URI uri = new URI(this.update.getWhatsNewUrl().toString());
					desktop.browse(uri);
				} catch(IOException | URISyntaxException e) {
					App.showMessage("Can't open \"What's new...\"", CMD.STATUS_ERROR);
					CMD.exitCMD(CMD.STATUS_ERROR);
					return;
				}
			}
		} else {
			App.showMessage("Can't open \"What's new...\"...", CMD.STATUS_ERROR);
			App.showMessage("This operation isnt supported by your OS!", CMD.STATUS_ERROR);
			CMD.exitCMD(CMD.STATUS_ERROR);
			return;
		}

		CMD.exitCMD(CMD.STATUS_OK);
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("Usage: java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" update [(optional) Sub Command]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Sub-Commands:");
		App.showMessage(CMD.HELP_INDENT + "  " + SUB_CMD_WHATS_NEW + " - Opens \"Whats new\" in your Browser");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Updates the Program:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" update");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Show whats new in your Browser:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" update " + SUB_CMD_WHATS_NEW);
		App.showMessage("");
	}

	/**
	 * Check if there is an update and displays it
	 */
	static void checkForUpdates() {
		checkForUpdates(true, false);
	}

	/**
	 * Check if there is an update and may displays it
	 *
	 * @param output - Shows console output
	 * @param ignoreUpdateCache - Ignored the Update-Cache
	 * @return - Update Object
	 */
	private static org.petschko.lib.update.Update checkForUpdates(boolean output, boolean ignoreUpdateCache) {
		org.petschko.lib.update.Update update = null;

		try {
			if(ignoreUpdateCache)
				update = new org.petschko.lib.update.Update(Config.UPDATE_URL, Config.VERSION_NUMBER, true);
			else
				update = new org.petschko.lib.update.Update(Config.UPDATE_URL, Config.VERSION_NUMBER, Config.UPDATE_CHECK_EVERY_SECS);
		} catch(IOException e) {
			System.out.println("Update: Can't check for Updates...");
			System.out.println(CMD.LINE_CMD);
		}

		if(update != null) {
			if(update.isHasNewVersion() && output) {
				System.out.println("Update: There is a new Version available! New Version: " + update.getNewestVersion());
				System.out.println();
				System.out.println("For update run: java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" update");
				System.out.println("To see whats new (Opens Browser): java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" update "+ SUB_CMD_WHATS_NEW);
				System.out.println(CMD.LINE_CMD);
			}
		}

		return update;
	}
}
