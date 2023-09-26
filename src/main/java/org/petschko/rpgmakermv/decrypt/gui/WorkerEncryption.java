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

import javax.swing.SwingWorker;
import javax.swing.ProgressMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WorkerEncryption  extends SwingWorker<Void, Void> implements ActionListener {
	protected GUI gui;
	protected ArrayList<File> files;
	protected ProgressMonitor progressMonitor;
	public boolean closeProjectWhenDone = false;
	public boolean ignoreClearing = false;
	public String alternateOutPutDir = null;

	public boolean toMv = true;

	/**
	 * WorkerEncryption constructor
	 *
	 * @param gui - GUI-Object
	 * @param files - Files to Encrypt
	 */
	WorkerEncryption(GUI gui, ArrayList<File> files) {
		this(gui, files, true);
	}

	/**
	 * WorkerEncryption constructor
	 *
	 * @param gui - GUI-Object
	 * @param files - Files to Encrypt
	 * @param init - Call init method
	 */
	WorkerEncryption(GUI gui, ArrayList<File> files, boolean init) {
		this.gui = gui;
		this.files = files;

		if(init)
			this.init();
	}

	/**
	 * Inits
	 */
	protected void init() {
		this.progressMonitor = new ProgressMonitor(gui.getMainWindow(),  "Encrypting...", "Preparing...", 0, this.files.size());
		this.progressMonitor.setProgress(0);
	}

	/**
	 * Performs Pre-Checks before decrypting
	 *
	 * @return - Pre-Checks are okay
	 */
	protected boolean initChecksOk() {
		if(gui.getRpgProject() == null) {
			ErrorWindow errorWindow = new ErrorWindow(
				"RPG-Project can't be null!",
				ErrorWindow.ERROR_LEVEL_WARNING,
				false
			);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return false;
		}

		if(gui.getDecrypter() == null) {
			ErrorWindow errorWindow = new ErrorWindow(
				"Decrypter can't be null!",
				ErrorWindow.ERROR_LEVEL_WARNING,
				false
			);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return false;
		}

		return true;
	}

	/**
	 * Prepares the Output Dir by either setting the alternative dir or clearing it, if it's so desired by the User
	 */
	protected void prepareOutputDir() {
		// Set Alternative output dir
		if(this.alternateOutPutDir != null)
			gui.getRpgProject().setOutputPath(this.alternateOutPutDir);

		// Clear Output-Dir if checked but only if not alternate output dir or current dir
		if(!App.outputDir.equals(".") && this.alternateOutPutDir == null) {
			if(
				Functions.strToBool(
					App.preferences.getConfig(Preferences.CLEAR_OUTPUT_DIR_BEFORE_DECRYPT, "true")
				) && ! this.ignoreClearing
			) {
				this.progressMonitor.setNote("Clearing Output-Directory...");
				File.clearDirectory(App.outputDir);
			}
		}
	}

	/**
	 * Prepares the Decrypter Object
	 */
	protected void prepareDecrypter() {
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
	}

	/**
	 * Checks for the Decryption-Key
	 *
	 * @return - Has the Key
	 */
	protected boolean checkForKey(boolean encrypt) {
		if(gui.getDecrypter().getDecryptCode() == null) {
			this.progressMonitor.setNote("Try to detect " + (encrypt ? "Encrypt" : "Decrypt") + "ion-Key...");

			try {
				gui.getDecrypter().detectEncryptionKeyFromJson(
					gui.getRpgProject().getSystem(),
					gui.getRpgProject().getEncryptionKeyName()
				);
			} catch(NullPointerException decryNullEx) {
				// File-Null-Pointer
				ErrorWindow errorWindow = new ErrorWindow(
					"Can't find " + (encrypt ? "Encrypt" : "Decrypt") + "ion-Key-File!" + Const.NEW_LINE +
					"Make sure that the File is in the RPG-Directory..." + Const.NEW_LINE +
					"Or set the Key by yourself (" + (encrypt ? "Encrypt" : "Decrypt") + " -> Set Encryption-Key)",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
				);
				errorWindow.show(gui.getMainWindow());

				// Halt task
				this.cancel(true);
				return false;
			} catch(JSONException e1) {
				// JSON-NotFound
				ErrorWindow errorWindow = new ErrorWindow(
					"Can't find " + (encrypt ? "Encrypt" : "Decrypt") + "ion-Key in File!",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
				);
				errorWindow.show(gui.getMainWindow());

				// Halt task
				this.cancel(true);
				return false;
			}
		}

		return true;
	}

	/**
	 * Decrypts all Files as long it's not canceled
	 */
	protected void encryptFiles() {
		int i = 0;
		for(File file : this.files) {
			// Check if cancel button was pressed
			if(this.progressMonitor.isCanceled()) {
				this.cancel(true);
				return;
			}

			this.progressMonitor.setNote("File: " + file.getFilePath());
			boolean encrypted = false;

			try {
				System.out.println("Encrypt: " + file.getFilePath());
				encrypted = gui.getDecrypter().encryptFile(file, gui.getRpgProject().getPath(), toMv);
			} catch(Exception e1) {
				e1.printStackTrace();
			} finally {
				if(encrypted) {
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
		this.progressMonitor.setNote("Configuring Encrypter...");
		this.prepareDecrypter();

		// Check if Decrypter already has a Key
		if(! this.checkForKey(true))
			return null;

		// Encrypt and Save Files
		this.encryptFiles();

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

			InfoWindow infoWindow = new InfoWindow("Encryption canceled!");
			infoWindow.show(gui.getMainWindow());
		} else {
			System.out.println("Done.");

			InfoWindow infoWindow = new InfoWindow("Encryption complete!");
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
