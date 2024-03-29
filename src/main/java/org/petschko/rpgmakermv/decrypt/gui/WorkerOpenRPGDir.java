package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.CellRenderer;
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

import javax.swing.*;
import java.awt.BorderLayout;
import java.io.IOException;

/**
 * @author Peter Dragicevic
 */
class WorkerOpenRPGDir extends SwingWorker<Void, Void> {
	private static final int TO_MZ = 0;
	private static final int TO_MV = 1;

	private GUI gui;
	private String directoryPath;
	private boolean showInfoWindow = false;
	public boolean showImportantMessages = false;
	public boolean decryptWhenDone = false;
	public boolean encryptWhenDone = false;
	public boolean closeProjectWhenDone = false;

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
			this.directoryPath = File.convertRelativePathToAbsolute(this.directoryPath);

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
			e.printStackTrace(System.out);
			ErrorWindow errorWindow = new ErrorWindow(e.getMessage(), ErrorWindow.ERROR_LEVEL_ERROR, false);
			errorWindow.show(gui.getMainWindow());

			this.cancel(true);
			return null;
		}

		createFileList();
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
			boolean keyFound = false;

			// Set values for the GUI and the Decrypter
			gui.setDecrypter(new Decrypter());
			try {
				gui.getRpgProject().setOutputPath(File.convertRelativePathToAbsolute(App.outputDir));
			} catch (IOException e) {
				ErrorWindow errorWindow = new ErrorWindow(
						"Cant convert the relative output Path to an absolute Path...",
						ErrorWindow.ERROR_LEVEL_ERROR,
						false
				);
				errorWindow.show(gui.getMainWindow());
				gui.closeRPGProject();
				return;
			}
			gui.getMainMenu().enableOnRPGProject(true, gui);
			gui.getMainMenu().assignRPGActionListener(gui);

			// Load the Key
			try {
				gui.getDecrypter().detectEncryptionKeyFromJson(gui.getRpgProject().getSystem(), gui.getRpgProject().getEncryptionKeyName());
				keyFound = true;
			} catch(NullPointerException nullPointerException) {
				gui.getMainMenu().disableOnNoKey(false, gui);
			}

			// Load Info-Values to GUI
			gui.projectInfo.setValuesFromRPGProject(gui.getRpgProject());
			gui.projectInfo.setValuesFromDecrypter(gui.getDecrypter());
			gui.projectInfo.refresh();
			gui.getMainWindow().pack();

			// Done
			if(this.showInfoWindow || this.showImportantMessages) {
				if(keyFound && this.showInfoWindow) {
					InfoWindow infoWindow = new InfoWindow(
							"RPG-Maker Project loaded..." + Const.NEW_LINE + Const.NEW_LINE +
							"Please use one of these options:" + Const.NEW_LINE +
							"- \"Decrypt\" -> \"All Files\" to Decrypt." + Const.NEW_LINE +
							"- \"Decrypt\" -> \"Restore Images (No Key)\" for restoring."
					);
					infoWindow.show(gui.getMainWindow());
				} else if(! keyFound) {
					String text = "RPG-Maker Project loaded..." + Const.NEW_LINE + Const.NEW_LINE +
							"Key not Found... You can set it manually under:" + Const.NEW_LINE +
							"- \"Decrypt\" -> \"Set Encryption-Key...\" or " + Const.NEW_LINE +
							"- \"Encrypt\" -> \"Set Encryption-Key...\"" + Const.NEW_LINE + Const.NEW_LINE +
							"You can also still restore the images without the Key:" + Const.NEW_LINE +
							"- \"Decrypt\" -> \"Restore Images (No Key)\" for restoring.";

					if(gui.getRpgProject().getEncryptedFiles().size() > 0) {
						Object[] options = {
								"Detect from Image",
								"Ok"
						};
						int answer = JOptionPane.showOptionDialog(
								gui.getMainWindow(),
								text + Const.NEW_LINE + Const.NEW_LINE +
								"You can also Auto-Detect the Key from images.",
								"Project loaded without Key",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE,
								null,
								options,
								options[1]
						);

						if(answer == 0) {
							try {
								gui.getDecrypter().detectEncryptionKeyFromImage(gui.getRpgProject().getEncryptedImgFile());
							} catch(Exception e) {
								ErrorWindow errorWindow = new ErrorWindow(
										"Could not find the Key from Images...",
										ErrorWindow.ERROR_LEVEL_ERROR,
										false,
										e
								);
								errorWindow.show(gui.getMainWindow());
								return;
							}

							if(gui.getDecrypter().getDecryptCode() != null)
								keyFound = true;

							gui.setExtractedKey();
						}
					} else if(this.showInfoWindow || this.showImportantMessages) {
						ErrorWindow errorWindow = new ErrorWindow(
								text,
								ErrorWindow.ERROR_LEVEL_WARNING,
								false
						);
						errorWindow.show(gui.getMainWindow());
					}
				}
			}

			// Check if there are more jobs to run
			if(keyFound)
				runEndJobs();
		}

		// Close Project if its set
		if(this.closeProjectWhenDone)
			gui.closeRPGProject();
	}

	/**
	 * Creates the File-List
	 */
	void createFileList() {
		gui.fileList = new JList<>(gui.getRpgProject().getProjectFileList());
		gui.fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		CellRenderer cellRenderer = new CellRenderer();
		cellRenderer.setRelativePath(gui.getRpgProject().getPath());

		gui.fileList.setCellRenderer(cellRenderer);
		gui.fileList.setName("List");
		gui.fileList.setVisibleRowCount(-1);

		gui.projectFilesPanel.removeAll();
		gui.projectFilesPanel.add(new JScrollPane(gui.fileList), BorderLayout.CENTER);
		gui.projectFilesPanel.validate();
		gui.projectFilesPanel.setVisible(true);
	}

	/**
	 * Runs all end jobs like encryption or decryption
	 */
	private void runEndJobs() {
		if(this.decryptWhenDone) {
			WorkerDecryption decryptWorker = new WorkerDecryption(gui, gui.getRpgProject().getEncryptedFiles());
			decryptWorker.alternateOutPutDir = this.directoryPath;
			decryptWorker.closeProjectWhenDone = true;
			decryptWorker.ignoreClearing = true;

			decryptWorker.execute();
		} else if(this.encryptWhenDone) {
			WorkerEncryption encryptWorker = new WorkerEncryption(gui, gui.getRpgProject().getResourceFiles());
			encryptWorker.alternateOutPutDir = this.directoryPath;
			encryptWorker.closeProjectWhenDone = true;
			encryptWorker.ignoreClearing = true;

			// Ask if MV or MZ
			switch(this.dialogMvOrMZ()) {
				case TO_MZ:
					encryptWorker.toMv = false;
					break;
				case TO_MV:
					encryptWorker.toMv = true;
					break;
				default:
					gui.closeRPGProject();
					return;
			}

			encryptWorker.execute();
		}

		// Ensure the project will only be closed after these jobs are done
		if(this.decryptWhenDone || this.encryptWhenDone)
			this.closeProjectWhenDone = false;
	}

	/**
	 * Asks if the Encryption should be for RPG-Maker MV or MZ
	 *
	 * @return - Answer
	 */
	private int dialogMvOrMZ() {
		Object[] options = {
			"MZ",
			"MV",
			"Cancel"
		};
		return JOptionPane.showOptionDialog(
				gui.getMainWindow(),
				"Do you want to encrypt to RPG-Maker MZ or MV?",
				"Encrypt to...?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]
		);
	}
}
