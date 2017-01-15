package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.petschko.lib.gui.JImagePanel;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 15.01.2017
 * Time: 19:42
 * Update: -
 * Version: 0.0.1
 *
 * Notes: GUI_About Class
 */
class GUI_About extends org.petschko.lib.gui.GUI_About {
	/**
	 * Creates a new, initially invisible <code>Frame</code> with the
	 * specified title.
	 * <p>
	 * This constructor sets the component's locale property to the value
	 * returned by <code>JComponent.getDefaultLocale</code>.
	 *
	 * @param title the title for the frame
	 * @param relativeTo relative to which parent component
	 * @throws HeadlessException if GraphicsEnvironment.isHeadless()
	 * returns true.
	 * @see GraphicsEnvironment#isHeadless
	 * @see Component#setSize
	 * @see Component#setVisible
	 * @see JComponent#getDefaultLocale
	 */
	GUI_About(@NotNull String title, @Nullable Component relativeTo) throws HeadlessException {
		super(title, relativeTo);
	}

	/**
	 * Construct the Content of the About-Window
	 */
	@Override
	protected void constructAbout() {

	}

	/**
	 * Returns the text of the Close-Button
	 *
	 * @return - Button-Text
	 */
	@Override
	protected String closeButtonText() {
		return "Ok";
	}

	/**
	 * Creates the About-Icon
	 *
	 * @return - JImagePanel or null if not set
	 */
	@Override
	protected JImagePanel aboutIcon() {
		JImagePanel imagePanel = new JImagePanel("/org/petschko/icons/petschko_icon.png", true);

		return imagePanel;
	}
}
