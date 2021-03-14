package org.petschko.lib.gui;

/**
 * @author Peter Dragicevic
 */
public class JOptionPane extends javax.swing.JOptionPane {
	/**
	 * Popups a yes/no question popup
	 *
	 * @param msg Message to display
	 * @param title Title of the Popup Window
	 * @param type Message type (Error, Warning etc)
	 * @param defaultYes true/false true means that "yes" is preselected
	 * @return JOptionPane dialog result
	 */
	public static int yesNoPopupQuestion(String msg, String title, int type, boolean defaultYes) {
		String def;
		if(defaultYes)
			def = "Yes";
		else
			def = "No";

		return JOptionPane.showOptionDialog(null, msg, title, JOptionPane.YES_NO_OPTION, type, null, new String[] {"Yes", "No"}, def);
	}
}
