package org.petschko.lib;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 26.12.2016
 * Time: 14:46
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Const Class
 */
public class Const {
	public static final String creator = "Petschko";
	private static final String creatorURL = "http://petschko.org/";

	// System Constance's
	public static final String ds = System.getProperty("file.separator");
	public static final int errorLevel_fatal = 4;
	public static final int errorLevel_error = 3;
	public static final int errorLevel_warning = 2;
	public static final int errorLevel_notice = 1;
	public static final int errorLevel_info = 0;

	/**
	 * Constructor
	 */
	private Const() {
		// VOID - This is a Static-Class
	}
}
