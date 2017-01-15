package org.petschko.lib.gui;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 15.01.2017
 * Time: 17:45
 * Update: -
 * Version: 0.0.1
 *
 * Notes: GUI_About Class
 */
public abstract class GUI_About extends JFrame {
	protected JImagePanel imagePanel;
	protected Component parent;
	protected JButton closeButton;

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
	public GUI_About(@NotNull String title, @Nullable Component relativeTo) throws HeadlessException {
		super(title);
		this.parent = relativeTo;
		this.imagePanel = this.aboutIcon();
		this.setLocationRelativeTo(this.parent);

		this.setVisible(false);
		this.createCloseButton();
		this.add(this.imagePanel);
		this.constructAbout();
		//this.add(this.closeButton);
		this.windowCloseOperation();
	}

	/**
	 * Construct the Content of the About-Window
	 */
	protected abstract void constructAbout();

	/**
	 * Returns the text of the Close-Button
	 *
	 * @return - Button-Text
	 */
	protected abstract String closeButtonText();

	/**
	 * Creates the About-Icon
	 *
	 * @return - JImagePanel or null if not set
	 */
	protected abstract JImagePanel aboutIcon();

	/**
	 * Window Close Operation
	 */
	protected void windowCloseOperation() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				hideWindow();
			}
		});
	}

	/**
	 * Creates the close Button
	 */
	protected void createCloseButton() {
		this.closeButton = new JButton(this.closeButtonText());
		this.closeButton.addActionListener(this.closeButtonAction());
	}

	/**
	 * Close Action for the Button
	 *
	 * @return - ActionListener
	 */
	protected ActionListener closeButtonAction() {
		return e -> this.hideWindow();
	}

	/**
	 * Show this Window
	 */
	public void showWindow() {
		this.setLocationRelativeTo(this.parent);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}

	/**
	 * Hides the Window
	 */
	protected void hideWindow() {
		this.setAlwaysOnTop(false);
		this.setVisible(false);
	}
}
