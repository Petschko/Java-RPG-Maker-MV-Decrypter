package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.Const;
import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.exceptions.PathException;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Decrypter;
import org.petschko.rpgmakermv.decrypt.Preferences;
import org.petschko.rpgmakermv.decrypt.RPG_Project;

import javax.swing.SwingWorker;

/**
 * @author Peter Dragicevic
 */
class WorkerOpenRPGDir extends SwingWorker<Void, Void> {
	private GUI gui;
	private String directoryPath;
	private boolean showInfoWindow = false;

	/**
	 * WorkerOpenRPGDir constructor
	 *
	 * @param gui - GUI-Object
	 * @param directoryPath - Path of the Directory
	 */
	WorkerOpenRPGDir(GUI gui, String directoryPath) {
		this.gui = gui;
		this.directoryPath = directoryPath;
	}

	/**
	 * WorkerOpenRPGDir constructor
	 *
	 * @param gui - GUI-Object
	 * @param directoryPath - Path of the Directory
	 * @param showInfoWindow - Show success Window after the Action
	 */
	WorkerOpenRPGDir(GUI gui, String directoryPath, boolean showInfoWindow) {
		this.gui = gui;
		this.directoryPath = directoryPath;
		this.showInfoWindow = showInfoWindow;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * Note that this method is executed only once.
	 *
	 * Note: this method is executed in a background thread.
	 *
	 * @return the computed result
	 */
	@Override
	protected Void doInBackground() {
		try {
			gui.setRpgProject(
				new RPG_Project(
					File.ensureDSonEndOfPath(this.directoryPath),
					! Functions.strToBool(App.preferences.getConfig(Preferences.LOAD_INVALID_RPG_DIRS, "false"))
				)
			);
		} catch(PathException e) {
			ErrorWindow errorWindow = new ErrorWindow(
					e.getMessage() + Const.NEW_LINE +
							"You can turn on the Option \"Load invalid RPG-Dirs anyway\" if your Directory is a RPG-Dir but it not detect it correctly." + Const.NEW_LINE +
							"Warning: Turning on the Option may cause incorrect results.",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
			);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return null;
		} catch(Exception e) {
			ErrorWindow errorWindow = new ErrorWindow(e.getMessage(), ErrorWindow.ERROR_LEVEL_ERROR, false);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return null;
		}

		return null;
	}

	/**
	 * Executed on the <i>Event Dispatch Thread</i> after the {@code doInBackground}
	 * method is finished. The default
	 * implementation does nothing. Subclasses may override this method to
	 * perform completion actions on the <i>Event Dispatch Thread</i>. Note
	 * that you can query status inside the implementation of this method to
	 * determine the result of this task or whether this task has been cancelled.
	 *
	 * @see #doInBackground
	 * @see #isCancelled()
	 * @see #get
	 */
	@Override
	protected void done() {
		if(! this.isCancelled()) {
			gui.setDecrypter(new Decrypter());
			gui.getRpgProject().setOutputPath(App.outputDir);
			gui.getMainMenu().enableOnRPGProject(true);
			gui.getMainMenu().assignRPGActionListener(gui);

			// Refresh Project-Files
			// todo (re)load file list

			// Done
			if(this.showInfoWindow) {
				InfoWindow infoWindow = new InfoWindow(
						"RPG-Maker Project loaded..." + Const.NEW_LINE +
						Const.NEW_LINE +
						"Please use one of these options:" + Const.NEW_LINE +
						"- \"Decrypt\" -> \"All Files\" to Decrypt." + Const.NEW_LINE +
						"- \"Decrypt\" -> \"Restore Images (No Key)\" for restoring."
				);
				infoWindow.show(gui.getMainWindow());
			}
		}
	}
}
