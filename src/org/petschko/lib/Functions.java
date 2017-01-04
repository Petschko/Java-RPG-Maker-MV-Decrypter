package org.petschko.lib;

import com.sun.istack.internal.Nullable;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 04.01.2017
 * Time: 14:28
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Functions Class
 */
public class Functions {
	/**
	 * Returns a boolean Value depending of the String-Content
	 *
	 * @param str - String to Check
	 * @return - true if string contains "true" or "1" else false
	 */
	public static boolean strToBool(@Nullable String str) {
		if(str == null)
			str = "";
		str = str.toLowerCase();

		return str.equals("1") || str.equals("true");
	}
}
