package org.petschko.rpgmakermv.decrypt.gui;

import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkerEncryption  extends SwingWorker<Void, Void> implements ActionListener {
	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * <p>
	 * Note that this method is executed only once.
	 *
	 * <p>
	 * Note: this method is executed in a background thread.
	 *
	 * @return the computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	protected Void doInBackground() throws Exception {
		return null;
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
