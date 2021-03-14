package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.File;
import org.petschko.lib.exceptions.PathException;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 14.03.2021
 * Time: 19:48
 *
 * Notes: Class WorkerDirectoryClearing
 */
class WorkerDirectoryClearing extends SwingWorker<Void, Void> implements ActionListener {
	private GUI gui;
	private String directoryPath = null;
	private JDialog jDialog;

	/**
	 * GUI_DirectoryClearing constructor
	 *
	 * @param gui - GUI-Object
	 * @param directoryPath - Path to clear
	 */
	WorkerDirectoryClearing(GUI gui, String directoryPath) {
		if(directoryPath == null) {
			PathException pe = new PathException("directoryPath can't be null!", (String) null);
			pe.printStackTrace();
			return;
		}

		this.gui = gui;
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
		if(this.directoryPath == null)
			return null;

		if(File.clearDirectory(this.directoryPath)) {
			InfoWindow infoWindow = new InfoWindow("Output-Directory cleared!");
			infoWindow.show(gui.getMainWindow());
		} else {
			ErrorWindow errorWindow = new ErrorWindow(
					"Can't clear Directory... May an other Program has still Files open in there?",
					ErrorWindow.ERROR_LEVEL_WARNING,
					false
			);
			errorWindow.show(gui.getMainWindow());
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
		if(directoryPath != null)
			gui.setNewOutputDir(this.directoryPath);
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e - ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.directoryPath == null)
			return;

		this.jDialog = new JDialog();
		JLabel text = new JLabel("Please wait while clearing the Directory: " + this.directoryPath);
		text.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		this.jDialog.setTitle("Please wait...");
		this.jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.jDialog.add(text);
		this.jDialog.pack();
		this.jDialog.setLocationRelativeTo(gui.getMainMenu());
		this.jDialog.setVisible(true);

		this.execute();
	}
}
