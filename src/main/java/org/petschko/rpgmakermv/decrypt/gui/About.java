package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.Const;
import org.petschko.lib.gui.JImageLabel;
import org.petschko.lib.gui.JLabelExtra;
import org.petschko.lib.gui.JPanelLine;
import org.petschko.rpgmakermv.decrypt.Config;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

/**
 * @author Peter Dragicevic
 */
class About extends org.petschko.lib.gui.GUI_About {
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
	About(String title, JFrame relativeTo) throws HeadlessException {
		super(title, relativeTo);
	}

	/**
	 * Construct the Content of the About-Window
	 */
	@Override
	protected void constructAbout() {
		int lineSpace = 4;

		// Initial Comps - Panels
		JPanel borderFrame = new JPanel();
		JPanel descriptionContainer = new JPanel();
		JPanel logoPanel = new JPanel();
		JPanel okButton = new JPanel();

		// Initial Comps - Labels
		JPanelLine versionLine = new JPanelLine();
		JLabelExtra versionHeading = new JLabelExtra("Version:");
		JLabel version = new JLabel(Config.VERSION);

		JPanelLine licenceLine = new JPanelLine();
		JLabelExtra licenceHeading = new JLabelExtra("Licence:");
		JLabelExtra licence = new JLabelExtra("MIT-Licence");
		licence.setURL(Config.PROJECT_LICENCE_URL, true);

		JPanelLine projectLine = new JPanelLine();
		JLabelExtra projectHpHeading = new JLabelExtra("Project-HP:");
		JLabelExtra projectHp = new JLabelExtra("Visit the Project-Page on Github");
		projectHp.setURL(Config.PROJECT_PAGE_URL, true);

		JPanelLine creditLine = new JPanelLine();
		JLabelExtra creditHeading = new JLabelExtra("Credits:");
		JPanelLine creatorLine = new JPanelLine();
		JLabel programmedBy = new JLabel(Const.CREATOR + " (Programmer) - ");
		JLabelExtra programmedByURL = new JLabelExtra("Website");
		programmedByURL.setURL(Const.CREATOR_URL, true);
		JLabelExtra programmedByDonate = new JLabelExtra("Donate");
		programmedByDonate.setURL(Const.CREATOR_DONATION_URL, true);

		// Set Layouts
		borderFrame.setLayout(new BorderLayout());
		descriptionContainer.setLayout(new BoxLayout(descriptionContainer, BoxLayout.Y_AXIS));

		// Style
		versionHeading.setUnderline(true);
		licenceHeading.setUnderline(true);
		projectHpHeading.setUnderline(true);
		creditHeading.setUnderline(true);
		borderFrame.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		descriptionContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		// Add stuff
		logoPanel.add(this.imagePanel);
		okButton.add(this.closeButton);

		versionLine.add(versionHeading);
		versionLine.addSpaceDimension(10);
		versionLine.add(version);
		licenceLine.add(licenceHeading);
		licenceLine.addSpaceDimension(10);
		licenceLine.add(licence);
		projectLine.add(projectHpHeading);
		projectLine.addSpaceDimension(10);
		projectLine.add(projectHp);
		creditLine.add(creditHeading);
		creatorLine.add(programmedBy);
		creatorLine.add(programmedByURL);
		creatorLine.add(new JLabel(" | "));
		creatorLine.add(programmedByDonate);

		descriptionContainer.add(versionLine);
		descriptionContainer.add(Box.createRigidArea(new Dimension(0, lineSpace)));
		descriptionContainer.add(licenceLine);
		descriptionContainer.add(Box.createRigidArea(new Dimension(0, lineSpace)));
		descriptionContainer.add(projectLine);
		descriptionContainer.add(Box.createRigidArea(new Dimension(0, lineSpace)));
		descriptionContainer.add(creditLine);
		descriptionContainer.add(creatorLine);

		borderFrame.add(logoPanel, BorderLayout.NORTH);
		borderFrame.add(descriptionContainer, BorderLayout.CENTER);
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
		JImageLabel imagePanel = new JImageLabel(Config.AUTHOR_IMAGE, true);

		// Ensure this size
		imagePanel.setImageSize(200, 200);

		return imagePanel;
	}
}
