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

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;

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
			gui.setDecrypter(new Decrypter());
			gui.getRpgProject().setOutputPath(App.outputDir);
			gui.getMainMenu().enableOnRPGProject(true);
			gui.getMainMenu().assignRPGActionListener(gui);

			// Load the Key
			gui.getDecrypter().detectEncryptionKeyFromJson(gui.getRpgProject().getSystem(), gui.getRpgProject().getEncryptionKeyName());

			// Load Info-Values to GUI
			gui.projectInfo.setValuesFromRPGProject(gui.getRpgProject());
			gui.projectInfo.setValuesFromDecrypter(gui.getDecrypter());
			gui.projectInfo.refresh();
			gui.getMainWindow().pack();

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

	/**
	 * Creates the File-List
	 */
	void createFileList() {
		gui.fileList = new JList<>(gui.getRpgProject().getProjectFileList());
		gui.fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		CellRenderer cellRenderer = new CellRenderer();
		cellRenderer.setRelativePath(gui.getRpgProject().getPath());

		gui.fileList.setCellRenderer(cellRenderer);
		gui.fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		gui.fileList.setName("List");
		gui.fileList.setVisibleRowCount(-1);

		gui.projectFilesPanel.removeAll();
		gui.projectFilesPanel.add(new JScrollPane(gui.fileList), BorderLayout.CENTER);
		gui.projectFilesPanel.validate();
		gui.projectFilesPanel.setVisible(true);
	}
}
