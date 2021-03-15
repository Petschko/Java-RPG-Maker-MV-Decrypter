package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.File;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Peter Dragicevic
 */
class FileInfo extends JPanel {
	private File file = null;
	private JTextField filePath = new JTextField();
	private JTextField fileExt = new JTextField();
	private JPanel realExt = new JPanel();
	private JTextField realFileExt = new JTextField();


	/**
	 * FileInfo Constructor
	 */
	FileInfo() {
		this.setBorder(BorderFactory.createTitledBorder("File-Info"));
		// todo add comps together
	}

	void setFile(File file) {
		this.file = file;
		this.loadFileData();
	}

	private void loadFileData() {
		//todo load and set this element
	}
}
