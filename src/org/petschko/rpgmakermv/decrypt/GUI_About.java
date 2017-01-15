package org.petschko.rpgmakermv.decrypt;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.petschko.lib.gui.JImageLabel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
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
	GUI_About(@NotNull String title, @Nullable JFrame relativeTo) throws HeadlessException {
		super(title, relativeTo);
	}

	/**
	 * Construct the Content of the About-Window
	 */
	@Override
	protected void constructAbout() {
		// Initial Comps - Panels
		JPanel borderFrame = new JPanel();
		JPanel descriptionContainer = new JPanel();
		JPanel logoPanel = new JPanel();
		JPanel okButton = new JPanel();

		// Initial Comps - Labels

		// Set Layouts
		borderFrame.setLayout(new BorderLayout());
		descriptionContainer.setLayout(new BorderLayout());

		// Set Borders
		borderFrame.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//todo add info and design
		// Add stuff
		logoPanel.add(this.imagePanel);
		okButton.add(this.closeButton);

		borderFrame.add(logoPanel, BorderLayout.NORTH);
		borderFrame.add(okButton, BorderLayout.SOUTH);

		this.add(borderFrame);
		this.setResizable(false);
		this.pack();
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
	 * @return - JImageLabel or null if not set
	 */
	@Override
	protected JImageLabel aboutIcon() {
		JImageLabel imagePanel = new JImageLabel(Config.authorImage, true);

		imagePanel.setImageSize(333, 250);

		return imagePanel;
	}
}
