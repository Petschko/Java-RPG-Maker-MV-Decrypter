package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.File;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 05.01.2017
 * Time: 12:07
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Class GUI_FileInfo
 */
class GUI_FileInfo extends JPanel {
	private File file = null;
	private JTextField filePath = new JTextField();
	private JTextField fileExt = new JTextField();
	private JPanel realExt = new JPanel();
	private JTextField realFileExt = new JTextField();


	/**
	 * GUI_FileInfo Constructor
	 */
	GUI_FileInfo() {
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
