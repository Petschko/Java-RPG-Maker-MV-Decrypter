package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Preferences;

import javax.swing.ProgressMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Peter Dragicevic
 */
class WorkerDecryption extends WorkerEncryption implements ActionListener {
	private boolean restoreImages = false;

	/**
	 * WorkerDecryption constructor
	 *
	 * @param gui - GUI-Object
	 * @param files - Files to Decrypt
	 */
	WorkerDecryption(GUI gui, ArrayList<File> files) {
		super(gui, files);
	}

	/**
	 * WorkerDecryption constructor
	 *
	 * @param gui - GUI-Object
	 * @param files - Files to Decrypt
	 * @param restoreImages - Restores Images without key
	 */
	WorkerDecryption(GUI gui, ArrayList<File> files, boolean restoreImages) {
		super(gui, files, false);
		this.restoreImages = restoreImages;
		this.init();
	}

	/**
	 * Inits
	 */
	@Override
	protected void init() {
		this.progressMonitor = new ProgressMonitor(gui.getMainWindow(), this.restoreImages ? "Restoring..." : "Decrypting...", "Preparing...", 0, this.files.size());
		this.progressMonitor.setProgress(0);
	}

	/**
	 * Decrypts all Files as long it's not canceled
	 */
	protected void decryptFiles() {
		int i = 0;
		for(File file : this.files) {
			// Check if cancel button was pressed
			if(this.progressMonitor.isCanceled()) {
				this.cancel(true);
				return;
			}

			this.progressMonitor.setNote("File: " + file.getFilePath());

			try {
				System.out.println("Decrypt: " + file.getFilePath());
				gui.getDecrypter().decryptFile(file, this.restoreImages);
			} catch(Exception e1) {
				e1.printStackTrace();
			} finally {
				if(! this.restoreImages || file.isImage()) {
					gui.getRpgProject().saveFile(
						file,
						Functions.strToBool(App.preferences.getConfig(Preferences.OVERWRITE_FILES, "false"))
					);
				}
			}

			// Add Progress to Progress-Monitor
			i++;
			this.progressMonitor.setProgress(i);
		}
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
		if(! this.initChecksOk())
			return null;

		this.prepareOutputDir();
		this.prepareDecrypter();

		// Check if Decrypter already has a Key
		if(! this.checkForKey(false))
			return null;

		// Decrypt and Save Files
		this.decryptFiles();

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
		if(this.closeProjectWhenDone) {
			gui.closeRPGProject();
		} else {
			gui.openRPGProject(gui.getRpgProject().getPath(), false);
		}

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
		this.execute();
	}
}
