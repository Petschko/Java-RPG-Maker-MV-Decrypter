package org.petschko.lib.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 17.01.2017
 * Time: 18:52
 * Update: -
 * Version: 0.0.1
 *
 * Notes: JPanelLine Class
 */
public class JPanelLine extends JPanel {
	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a box layout.
	 */
	public JPanelLine() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	/**
	 * Adds a Label with 1 empty space
	 */
	public void addSpaceLabel() {
		this.add(new JLabel(" "));
	}

	/**
	 * Adds a Space-Dimension
	 *
	 * @param len - Length of the Dimension
	 */
	public void addSpaceDimension(int len) {
		this.add(Box.createRigidArea(new Dimension(len, 0)));
	}
}
