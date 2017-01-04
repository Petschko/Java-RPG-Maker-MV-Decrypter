package org.petschko.rpgmakermv.decrypt;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 29.12.2016
 * Time: 17:59
 * Update: -
 * Version: 0.0.1
 *
 * Notes: GUI_ActionListener Class
 */
class GUI_ActionListener {

	static ActionListener closeMenu() {
		return e -> App.closeGUI();
	}

	static WindowAdapter closeButton() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				App.closeGUI();
			}
		};
	}
}
