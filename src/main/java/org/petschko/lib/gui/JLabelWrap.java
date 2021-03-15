package org.petschko.lib.gui;

import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * @author Peter Dragicevic
 */
public class JLabelWrap extends JTextArea {
	/**
	 * JLabelWrap Constructor
	 */
	public JLabelWrap() {
		this.styleItAsLabel();
	}

	/**
	 * JLabelWrap Constructor
	 *
	 * @param text - Label-Text
	 */
	public JLabelWrap(String text) {
		super(text);

		this.styleItAsLabel();
	}

	/**
	 * JLabelWrap Constructor
	 *
	 * @param rows - Rows of the Element
	 * @param columns - Columns of the Element
	 */
	public JLabelWrap(int rows, int columns) {
		super(rows, columns);

		this.styleItAsLabel();
	}

	/**
	 * JLabelWrap Constructor
	 *
	 * @param text - Label-Text
	 * @param rows - Rows of the Element
	 * @param columns - Columns of the Element
	 */
	public JLabelWrap(String text, int rows, int columns) {
		super(text, rows, columns);

		this.styleItAsLabel();
	}

	/**
	 * Styles the Text-Area to look like a Label
	 */
	private void styleItAsLabel() {
		this.setFont(UIManager.getFont("Label.font"));
		this.setBackground(null);
		this.setBorder(null);
		this.setOpaque(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setEditable(false);
		this.setFocusable(false);
	}
}
