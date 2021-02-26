package org.petschko.rpgmakermv.decrypt;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.petschko.lib.Const;
import org.petschko.lib.File;
import org.petschko.lib.Functions;
import org.petschko.lib.exceptions.PathException;
import org.petschko.lib.gui.*;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 28.12.2016
 * Time: 19:14
 * Update: 04.05.2019
 * Version: 0.1.3
 *
 * Notes: GUI Class
 */
class GUI {
	private JFrame mainWindow;
	private GUI_Menu mainMenu;
	private JPanel windowPanel = new JPanel(new BorderLayout());
	private JPanel projectFilesPanel = new JPanel();
	private JPanel fileList = new JPanel();
	private GUI_About guiAbout;
	private GUI_FileInfo fileInfo = new GUI_FileInfo();
	private RPGProject rpgProject;
	private Decrypter decrypter;

	/**
	 * GUI Constructor
	 */
	GUI() {
		// Create and Setup components
		this.createMainWindow();
		this.createMainMenu();
		this.guiAbout = new GUI_About("About " + Config.programName, this.mainWindow);
		this.createWindowGUI();

		// Center Window and Display it
		this.mainWindow.setLocationRelativeTo(null);
		this.mainWindow.setVisible(true);
		this.mainWindow.pack();

		// Assign Listener
		this.assignMainMenuListener();
		this.setNewOutputDir(App.outputDir);

		// Add Update-Check
		if(Functions.strToBool(App.preferences.getConfig(Preferences.autoCheckForUpdates, "true")))
			new GUI_Update(this, true);
	}

	/**
	 * Returns the Main-Window
	 *
	 * @return - Main-Window
	 */
	JFrame getMainWindow() {
		return mainWindow;
	}

	/**
	 * Returns the Main-Menu
	 *
	 * @return - Main-Menu
	 */
	GUI_Menu getMainMenu() {
		return mainMenu;
	}

	/**
	 * Dispose the GUI
	 */
	void dispose() {
		this.guiAbout.dispose();
		this.mainWindow.dispose();
	}

	/**
	 * Creates and assign the MainFrame
	 */
	private void createMainWindow() {
		this.mainWindow = new JFrame(Config.programName + " by " + Const.creator + " " + Config.version);

		// Change close Action
		this.mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.mainWindow.addWindowListener(GUI_ActionListener.closeButton());
	}

	/**
	 * Creates and assign the Main-Menu
	 */
	private void createMainMenu() {
		this.mainMenu = new GUI_Menu();
		this.mainWindow.add(this.mainMenu, BorderLayout.NORTH);

		// Set Menu-Settings
		if(Functions.strToBool(App.preferences.getConfig(Preferences.ignoreFakeHeader, "true")))
			this.mainMenu.ignoreFakeHeader.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.loadInvalidRPGDirs, "false")))
			this.mainMenu.loadInvalidRPGDirs.setState(true);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.clearOutputDirBeforeDecrypt, "true")))
			this.mainMenu.clearOutputDir.setState(true);

		if(! this.mainMenu.clearOutputDir.isSelected()) {
			if(Functions.strToBool(App.preferences.getConfig(Preferences.overwriteFiles, "false")))
				this.mainMenu.overwriteExistingFiles.setState(true);
		} else
			this.mainMenu.overwriteExistingFiles.setEnabled(false);

		if(Functions.strToBool(App.preferences.getConfig(Preferences.autoCheckForUpdates, "true")))
			this.mainMenu.checkForUpdates.setState(true);

		this.mainMenu.enableOnRPGProject(false);
	}

	/**
	 * Creates all Components for the Window
	 */
	private void createWindowGUI() {
		JPanel middleFileContainer = new JPanel(new GridLayout(1, 2));
		JPanel footContainer = new JPanel(new GridLayout(1, 3));
		JLabelWrap filesListText = new JLabelWrap("Please open a RPG-Maker MV Project (\"File\" -> \"Select RPG MV Project\")");
		filesListText.setColumns(20);

		/*JScrollPane scrollPane = new JScrollPane(
				this.fileList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);*/

		// Design stuff
		this.projectFilesPanel.setLayout(new BorderLayout());
		this.projectFilesPanel.setBorder(BorderFactory.createTitledBorder("Project-Files"));
		this.windowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Assign to the main comps
		middleFileContainer.add(this.projectFilesPanel);
		middleFileContainer.add(this.fileInfo);
		this.windowPanel.add(middleFileContainer, BorderLayout.CENTER);
		this.windowPanel.add(footContainer, BorderLayout.SOUTH);
		this.projectFilesPanel.add(filesListText, BorderLayout.NORTH);
		this.mainWindow.add(this.windowPanel, BorderLayout.CENTER);
	}

	/**
	 * Assigns ActionListener to the Main-Menu parts
	 */
	private void assignMainMenuListener() {
		// -- File
		this.mainMenu.open.addActionListener(
				e -> {
					String openDir = App.preferences.getConfig(Preferences.lastRPGDir, ".");

					if(! File.existsDir(openDir))
						openDir = ".";

					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
					int choose = dirChooser.showDialog(this.mainWindow, null);

					if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
						App.preferences.setConfig(Preferences.lastRPGDir, dirChooser.getCurrentDirectory().getPath());

						this.openRPGProject(dirChooser.getSelectedFile().getPath(), true);
					}
				}
		);
		this.mainMenu.changeOutputDir.addActionListener(
				e -> {
					// Warn the user that the selected directory will be cleared
					if(Boolean.parseBoolean(App.preferences.getConfig(Preferences.clearOutputDirBeforeDecrypt, "true")))
						new InfoWindow("You have chosen, that the selected Directory will be cleared.\nBeware that this Program clear the selected Directory (Deletes all Files within)! Don't select directories where you have important Files or Sub-Directories in!\n\n(Or turn off the clearing under Options)", "Important Info about your Files").show(this.mainWindow);

					String openDir = App.preferences.getConfig(Preferences.lastOutputParentDir, ".");

					if(! File.existsDir(openDir))
						openDir = ".";

					UIManager.put("FileChooser.readOnly", Boolean.TRUE);
					JDirectoryChooser dirChooser = new JDirectoryChooser(openDir);
					int choose = dirChooser.showDialog(this.mainWindow, null);

					if(dirChooser.getSelectedFile() != null && choose == JDirectoryChooser.APPROVE_OPTION) {
						App.preferences.setConfig(Preferences.lastOutputParentDir, dirChooser.getCurrentDirectory().getPath());
						this.setNewOutputDir(dirChooser.getSelectedFile().getPath());
					}
				}
		);
		this.mainMenu.exit.addActionListener(GUI_ActionListener.closeMenu());
		// -- Options
		this.mainMenu.ignoreFakeHeader.addActionListener(GUI_ActionListener.switchSetting(Preferences.ignoreFakeHeader));
		this.mainMenu.loadInvalidRPGDirs.addActionListener(GUI_ActionListener.switchSetting(Preferences.loadInvalidRPGDirs));
		this.mainMenu.clearOutputDir.addActionListener(GUI_ActionListener.switchSetting(Preferences.clearOutputDirBeforeDecrypt));
		this.mainMenu.clearOutputDir.addActionListener(
				e -> this.mainMenu.overwriteExistingFiles.setEnabled(! this.mainMenu.overwriteExistingFiles.isEnabled())
		);
		this.mainMenu.overwriteExistingFiles.addActionListener(GUI_ActionListener.switchSetting(Preferences.overwriteFiles));
		this.mainMenu.checkForUpdates.addActionListener(GUI_ActionListener.switchSetting(Preferences.autoCheckForUpdates));
		// -- Decrypt
		// -- Tools
		// -- Info
		this.mainMenu.updateProgram.addActionListener(
				e -> new GUI_Update(this)
		);
		this.mainMenu.reportABug.addActionListener(GUI_ActionListener.openWebsite(Config.projectBugReportURL));
		this.mainMenu.about.addActionListener(
				e -> this.guiAbout.showWindow()
		);
	}

	/**
	 * Assign RPG-Project ActionListeners
	 */
	private void assignRPGActionListener() {
		// Remove all Previous ActionListeners
		Functions.buttonRemoveAllActionListeners(this.mainMenu.openRPGDirExplorer);
		Functions.buttonRemoveAllActionListeners(this.mainMenu.allFiles);

		// Add new ActionListener
		this.mainMenu.openRPGDirExplorer.addActionListener(GUI_ActionListener.openExplorer(this.rpgProject.getPath()));
		//this.mainMenu.allFiles.addActionListener(this.decrypt(this.rpgProject.getEncryptedFiles()));
		this.mainMenu.allFiles.addActionListener(new GUI_Decryption(this.rpgProject.getEncryptedFiles()));
		this.mainMenu.restoreImages.addActionListener(new GUI_Decryption(this.rpgProject.getEncryptedFiles(), true));
	}

	/**
	 * Opens the RPG-MV-Project
	 *
	 * @param currentDirectory - Current RPG-Maker Directory
	 * @param showInfoWindow - Show Info-Window if done
	 */
	private void openRPGProject(@NotNull String currentDirectory, boolean showInfoWindow) {
		GUI_OpenRPGDir openRPG = new GUI_OpenRPGDir(currentDirectory, showInfoWindow);
		openRPG.execute();
	}

	/**
	 * Set the new Output dir & assign new ActionListeners
	 *
	 * @param newOutputDir - New Output-Directory
	 */
	private void setNewOutputDir(String newOutputDir) {
		App.outputDir = File.ensureDSonEndOfPath(newOutputDir);

		// Remove old ActionListener
		Functions.buttonRemoveAllActionListeners(this.mainMenu.openOutputDirExplorer);
		Functions.buttonRemoveAllActionListeners(this.mainMenu.doClearOutputDir);

		// New ActionListener
		this.mainMenu.openOutputDirExplorer.addActionListener(GUI_ActionListener.openExplorer(App.outputDir));
		this.mainMenu.doClearOutputDir.addActionListener(new GUI_DirectoryClearing(App.outputDir));
	}

	/**
	 * Class GUI_Decryption
	 */
	private class GUI_Decryption extends SwingWorker<Void, Void> implements ActionListener {
		private ArrayList<File> files;
		private ProgressMonitor progressMonitor;
		private boolean restoreImages = false;

		/**
		 * GUI_Decryption constructor
		 *
		 * @param files - Files to Decrypt
		 */
		GUI_Decryption(ArrayList<File> files) {
			this.files = files;
		}

		/**
		 * GUI_Decryption constructor
		 *
		 * @param files - Files to Decrypt
		 * @param restoreImages - Restores Images without key
		 */
		GUI_Decryption(ArrayList<File> files, boolean restoreImages) {
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
			// Clear Output-Dir if checked
			if(Functions.strToBool(App.preferences.getConfig(Preferences.clearOutputDirBeforeDecrypt, "true"))) {
				this.progressMonitor.setNote("Clearing Output-Directory...");
				File.clearDirectory(App.outputDir);
			}

			// Setup Decrypter
			this.progressMonitor.setNote("Configuring Decrypter...");
			decrypter.setIgnoreFakeHeader(
					Functions.strToBool(App.preferences.getConfig(Preferences.ignoreFakeHeader, "true"))
			);
			decrypter.setRemain(App.preferences.getConfig(Preferences.decrypterRemain, Decrypter.defaultRemain));
			decrypter.setSignature(App.preferences.getConfig(Preferences.decrypterSignature, Decrypter.defaultSignature));
			decrypter.setVersion(App.preferences.getConfig(Preferences.decrypterVersion, Decrypter.defaultVersion));
			int headerLen = Decrypter.defaultHeaderLen;

			try {
				headerLen = Integer.parseInt(App.preferences.getConfig(Preferences.decrypterHeaderLen));
			} catch(NumberFormatException ex) {
				ErrorWindow errorWindow = new ErrorWindow(
						"Header-Length was not an Valid Number - Using Default-Length!",
						ErrorWindow.ERROR_LEVEL_WARNING,
						false
				);
				errorWindow.show(mainWindow);

				// Set default as new Len
				App.preferences.setConfig(Preferences.decrypterHeaderLen, Integer.toString(Decrypter.defaultHeaderLen));
			}
			decrypter.setHeaderLen(headerLen);

			// Check if Decrypter already has a Key
			if(decrypter.getDecryptCode() == null) {
				this.progressMonitor.setNote("Try to detect Encryption-Key...");
				try {
					decrypter.detectEncryptionKeyFromJson(rpgProject.getSystem(), rpgProject.getEncryptionKeyName());
				} catch(NullPointerException decryNullEx) {
					// File-Null-Pointer
					ErrorWindow errorWindow = new ErrorWindow(
							"Can't find Decryption-Key-File!" + Const.newLine +
									"Make sure that the File is in the RPG-Directory..." + Const.newLine +
									"Or set the Key by yourself (Decrypter -> Set Encryption-Key)",
							ErrorWindow.ERROR_LEVEL_WARNING,
							false
					);
					errorWindow.show(mainWindow);

					// Halt task
					this.cancel(true);
					return null;
				} catch(JSONException e1) {
					// JSON-NotFound
					ErrorWindow errorWindow = new ErrorWindow("Can't find Decryption-Key in File!", ErrorWindow.ERROR_LEVEL_WARNING, false);
					errorWindow.show(mainWindow);

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
					decrypter.decryptFile(file, this.restoreImages);
				} catch(Exception e1) {
					e1.printStackTrace();
				} finally {
					if(! this.restoreImages || file.isImage())
						rpgProject.saveFile(file, Functions.strToBool(App.preferences.getConfig(Preferences.overwriteFiles, "false")));
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
			openRPGProject(rpgProject.getPath(), false);

			if(this.isCancelled()) {
				System.out.println("Cancelled...");

				InfoWindow infoWindow = new InfoWindow("Decryption canceled!");
				infoWindow.show(mainWindow);
			} else {
				System.out.println("Done.");

				InfoWindow infoWindow;
				if(this.restoreImages)
					infoWindow = new InfoWindow("Images are restored! ^-^");
				else
					infoWindow = new InfoWindow("Decryption complete! =)");

				infoWindow.show(mainWindow);
			}
		}

		/**
		 * Invoked when an action occurs.
		 *
		 * @param e - ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			this.progressMonitor = new ProgressMonitor(mainWindow, this.restoreImages ? "Restoring..." : "Decrypting...", "Preparing...", 0, this.files.size());
			this.progressMonitor.setProgress(0);

			this.execute();
		}
	}

	/**
	 * Class GUI_DirectoryClearing
	 */
	private class GUI_DirectoryClearing extends SwingWorker<Void, Void> implements ActionListener {
		private String directoryPath;
		private JDialog jDialog;

		/**
		 * GUI_DirectoryClearing constructor
		 *
		 * @param directoryPath - Path to clear
		 */
		GUI_DirectoryClearing(@NotNull String directoryPath) {
			this.directoryPath = File.ensureDSonEndOfPath(directoryPath);
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
			if(File.clearDirectory(this.directoryPath)) {
				InfoWindow infoWindow = new InfoWindow("Output-Directory cleared!");
				infoWindow.show(mainWindow);
			} else {
				ErrorWindow errorWindow = new ErrorWindow(
						"Can't clear Directory... May an other Program has still Files open in there?",
						ErrorWindow.ERROR_LEVEL_WARNING,
						false
				);
				errorWindow.show(mainWindow);
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
			this.jDialog.dispose();

			// Reset this ActionListener
			setNewOutputDir(this.directoryPath);
		}

		/**
		 * Invoked when an action occurs.
		 *
		 * @param e - ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			this.jDialog = new JDialog();
			JLabel text = new JLabel("Please wait while clearing the Directory: " + this.directoryPath);
			text.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
			this.jDialog.setTitle("Please wait...");
			this.jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			this.jDialog.add(text);
			this.jDialog.pack();
			this.jDialog.setLocationRelativeTo(mainWindow);
			this.jDialog.setVisible(true);

			this.execute();
		}
	}

	/**
	 * Class GUI_OpenRPGDir
	 */
	private class GUI_OpenRPGDir extends SwingWorker<Void, Void> {
		private String directoryPath;
		private boolean showInfoWindow;

		/**
		 * GUI_OpenRPGDir constructor
		 *
		 * @param directoryPath - Path of the Directory
		 */
		GUI_OpenRPGDir(String directoryPath) {
			this.directoryPath = directoryPath;
			this.showInfoWindow = false;
		}

		/**
		 * GUI_OpenRPGDir constructor
		 *
		 * @param directoryPath - Path of the Directory
		 * @param showInfoWindow - Show success Window after the Action
		 */
		GUI_OpenRPGDir(String directoryPath, boolean showInfoWindow) {
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
				rpgProject = new RPGProject(
						File.ensureDSonEndOfPath(this.directoryPath),
						! Functions.strToBool(App.preferences.getConfig(Preferences.loadInvalidRPGDirs, "false"))
				);
			} catch(PathException e) {
				ErrorWindow errorWindow = new ErrorWindow(
						e.getMessage() + Const.newLine +
								"You can turn on the Option \"Load invalid RPG-Dirs anyway\" if your Directory is a RPG-Dir but it not detect it correctly." + Const.newLine +
								"Warning: Turning on the Option may cause incorrect results.",
						ErrorWindow.ERROR_LEVEL_WARNING,
						false
				);
				errorWindow.show(mainWindow);

				this.cancel(true);
				return null;
			} catch(Exception e) {
				ErrorWindow errorWindow = new ErrorWindow(e.getMessage(), ErrorWindow.ERROR_LEVEL_ERROR, false);
				errorWindow.show(mainWindow);

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
				decrypter = new Decrypter();
				rpgProject.setOutputPath(App.outputDir);
				mainMenu.enableOnRPGProject(true);
				assignRPGActionListener();

				// Refresh Project-Files
				// todo (re)load file list

				// Done
				if(this.showInfoWindow) {
					InfoWindow infoWindow = new InfoWindow("RPG-Maker Project loaded..." + Const.newLine +
							"Please use \"Decrypt\" -> \"All\" Files to Decrypt.");
					infoWindow.show(mainWindow);
				}
			}
		}
	}
}
