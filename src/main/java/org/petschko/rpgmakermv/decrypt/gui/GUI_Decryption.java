package org.petschko.rpgmakermv.decrypt.gui;

import org.json.JSONException;
import org.petschko.lib.Const;
import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Decrypter;
import org.petschko.rpgmakermv.decrypt.Preferences;

import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 14.03.2021
 * Time: 20:53
 *
 * Notes: Class GUI_Decryption
 */
class GUI_Decryption extends SwingWorker<Void, Void> implements ActionListener {
	private GUI gui;
	private ArrayList<File> files;
	private ProgressMonitor progressMonitor;
	private boolean restoreImages = false;

	/**
	 * GUI_Decryption constructor
	 *
	 * @param gui - GUI-Object
	 * @param files - Files to Decrypt
	 */
	GUI_Decryption(GUI gui, ArrayList<File> files) {
		this.gui = gui;
		this.files = files;
	}

	/**
	 * GUI_Decryption constructor
	 *
	 * @param gui - GUI-Object
	 * @param files - Files to Decrypt
	 * @param restoreImages - Restores Images without key
	 */
	GUI_Decryption(GUI gui, ArrayList<File> files, boolean restoreImages) {
		this.gui = gui;
		this.files = files;
		this.restoreImages = restoreImages;
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
		if(gui.getRpgProject() == null) {
			ErrorWindow errorWindow = new ErrorWindow(
					"RPG-Project can't be null!",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
			);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return null;
		}

		if(gui.getDecrypter() == null) {
			ErrorWindow errorWindow = new ErrorWindow(
					"Decrypter can't be null!",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
			);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return null;
		}

		// Clear Output-Dir if checked
		if(Functions.strToBool(App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true"))) {
			this.progressMonitor.setNote("Clearing Output-Directory...");
			File.clearDirectory(App.outputDir);
		}

		// Setup Decrypter
		this.progressMonitor.setNote("Configuring Decrypter...");
		gui.getDecrypter().setIgnoreFakeHeader(
				Functions.strToBool(App.preferences.getConfig(Preferences.IGNORE_FAKE_HEADER, "true"))
		);
		gui.getDecrypter().setRemain(App.preferences.getConfig(Preferences.DECRYPTER_REMAIN, Decrypter.DEFAULT_REMAIN));
		gui.getDecrypter().setSignature(App.preferences.getConfig(Preferences.DECRYPTER_SIGNATURE, Decrypter.DEFAULT_SIGNATURE));
		gui.getDecrypter().setVersion(App.preferences.getConfig(Preferences.DECRYPTER_VERSION, Decrypter.DEFAULT_VERSION));
		int headerLen = Decrypter.DEFAULT_HEADER_LEN;

		try {
			headerLen = Integer.parseInt(App.preferences.getConfig(Preferences.DECRYPTER_HEADER_LEN));
		} catch(NumberFormatException ex) {
			ErrorWindow errorWindow = new ErrorWindow(
					"Header-Length was not an Valid Number - Using Default-Length!",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
			);
			errorWindow.show(gui.getMainWindow());

			// Set default as new Len
			App.preferences.setConfig(Preferences.DECRYPTER_HEADER_LEN, Integer.toString(Decrypter.DEFAULT_HEADER_LEN));
		}
		gui.getDecrypter().setHeaderLen(headerLen);

		// Check if Decrypter already has a Key
		if(gui.getDecrypter().getDecryptCode() == null) {
			this.progressMonitor.setNote("Try to detect Encryption-Key...");
			try {
				gui.getDecrypter().detectEncryptionKeyFromJson(gui.getRpgProject().getSystem(), gui.getRpgProject().getEncryptionKeyName());
			} catch(NullPointerException decryNullEx) {
				// File-Null-Pointer
				ErrorWindow errorWindow = new ErrorWindow(
						"Can't find Decryption-Key-File!" + Const.NEW_LINE +
								"Make sure that the File is in the RPG-Directory..." + Const.NEW_LINE +
								"Or set the Key by yourself (Decrypter -> Set Encryption-Key)",
						ErrorWindow.ERROR_LEVEL_WARNING,
						false
				);
				errorWindow.show(gui.getMainWindow());

				// Halt task
				this.cancel(true);
				return null;
			} catch(JSONException e1) {
				// JSON-NotFound
				ErrorWindow errorWindow = new ErrorWindow("Can't find Decryption-Key in File!", ErrorWindow.ERROR_LEVEL_WARNING, false);
				errorWindow.show(gui.getMainWindow());

				// Halt task
				this.cancel(true);
				return null;
			}
		}

		// Decrypt and Save Files
		int i = 0;
		for(File file : this.files) {
			// Check if cancel button was pressed
			if(this.progressMonitor.isCanceled()) {
				this.cancel(true);
				return null;
			}

			this.progressMonitor.setNote("File: " + file.getFilePath());
			try {
				System.out.println("Decrypt: " + file.getFilePath());
				gui.getDecrypter().decryptFile(file, this.restoreImages);
			} catch(Exception e1) {
				e1.printStackTrace();
			} finally {
				if(! this.restoreImages || file.isImage())
					gui.getRpgProject().saveFile(file, Functions.strToBool(App.preferences.getConfig(Preferences.OVERWRITE_FILES, "false")));
			}
			// Add Progress to Progress-Monitor
			i++;
			this.progressMonitor.setProgress(i);
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
		this.progressMonitor.close();

		// Reset Files/ActionListener
		gui.openRPGProject(gui.getRpgProject().getPath(), false);

		if(this.isCancelled()) {
			System.out.println("Cancelled...");

			InfoWindow infoWindow;
			if(this.restoreImages)
				infoWindow = new InfoWindow("Restoring canceled!");
			else
				infoWindow = new InfoWindow("Decryption canceled!");

			infoWindow.show(gui.getMainWindow());
		} else {
			System.out.println("Done.");

			InfoWindow infoWindow;
			if(this.restoreImages)
				infoWindow = new InfoWindow("Images are restored! ^-^");
			else
				infoWindow = new InfoWindow("Decryption complete! =)");

			infoWindow.show(gui.getMainWindow());
		}
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e - ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.progressMonitor = new ProgressMonitor(gui.getMainWindow(), this.restoreImages ? "Restoring..." : "Decrypting...", "Preparing...", 0, this.files.size());
		this.progressMonitor.setProgress(0);

		this.execute();
	}
}
