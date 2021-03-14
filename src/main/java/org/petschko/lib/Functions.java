package org.petschko.lib;

import javax.swing.AbstractButton;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Peter Dragicevic
 */
public class Functions {
	/**
	 * Returns a boolean Value depending of the String-Content
	 *
	 * @param str - String to Check
	 * @return - true if string contains "true" or "1" else false
	 */
	public static boolean strToBool(String str) {
		if(str == null)
			str = "";
		str = str.toLowerCase();

		return str.equals("1") || str.equals("true");
	}

	/**
	 * Opens a Website in the Browser
	 *
	 * @param uri - Target URI
	 */
	public static void openWebsite(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

		if(desktop == null)
			return;

		if(desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Opens a Website in the Browser
	 *
	 * @param url - Target URL
	 */
	public static void openWebsite(URL url) {
		try {
			Functions.openWebsite(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a Website in the Browser
	 *
	 * @param url - Target URI as String
	 */
	public static void openWebsite(String url) {
		try {
			Functions.openWebsite(new URI(url));
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes all Action-Listeners from Buttons
	 *
	 * @param abstractButton - Button where to remove all ActionListeners
	 */
	public static void buttonRemoveAllActionListeners(AbstractButton abstractButton) {
		ActionListener[] actionListeners = abstractButton.getActionListeners();

		for(ActionListener al : actionListeners) {
			abstractButton.removeActionListener(al);
		}
	}
}
