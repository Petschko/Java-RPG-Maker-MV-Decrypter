package org.petschko.rpgmakermv.decrypt.gui;

import org.petschko.lib.gui.JPanelLine;
import org.petschko.rpgmakermv.decrypt.Decrypter;
import org.petschko.rpgmakermv.decrypt.RPG_Project;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;

/**
 * @author Peter Dragicevic
 */
class ProjectInfo extends JPanel {
	private JPanel mainInfo = new JPanel(new GridLayout(2, 2));
	private JPanel keyInfo = new JPanel();
	private JPanel encryptedFiles = new JPanel(new GridLayout(1, 2));
	private JPanel resourceFiles = new JPanel(new GridLayout(1, 2));
	private JPanel projectFile = new JPanel(new GridLayout(1, 2));
	private JPanel systemFile = new JPanel(new GridLayout(1, 2));
	private JPanel jEncryptionKey = new JPanelLine();
	private JPanel jHeaderLen = new JPanelLine();
	private JPanel jSignature = new JPanelLine();
	private JPanel jVersion = new JPanelLine();
	private JPanel jRemain = new JPanelLine();

	// Info values
	private int encryptedFilesCount = 0;
	private int resourceFilesCount = 0;
	private boolean hasProjectFile = false;
	private boolean hasSystemFile = false;
	private String encryptionKey = null;
	private int headerLen = 0;
	private String signature = null;
	private String version = null;
	private String remain = null;

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 */
	ProjectInfo() {
		this.setBorder(BorderFactory.createTitledBorder("Project-Info"));
		this.setLayout(new GridLayout(2, 1));

		mainInfo.setBorder(BorderFactory.createEmptyBorder(5,7, 5, 7));
		keyInfo.setBorder(BorderFactory.createEmptyBorder(5,7, 5, 7));

		this.add(mainInfo);
		this.add(keyInfo);
	}

	/**
	 *Sets the Encrypted Files count
	 *
	 * @param encryptedFilesCount - Encrypted Files count
	 */
	void setEncryptedFilesCount(int encryptedFilesCount) {
		this.encryptedFilesCount = encryptedFilesCount;
	}

	/**
	 * Sets the Resource Files count
	 *
	 * @param resourceFilesCount - Resource Files count
	 */
	void setResourceFilesCount(int resourceFilesCount) {
		this.resourceFilesCount = resourceFilesCount;
	}

	/**
	 * Sets if it has the Project-File
	 *
	 * @param hasProjectFile - has it the Project-File
	 */
	void setHasProjectFile(boolean hasProjectFile) {
		this.hasProjectFile = hasProjectFile;
	}

	/**
	 * Sets if it has the System.json
	 *
	 * @param hasSystemFile - has it the System.json
	 */
	void setHasSystemFile(boolean hasSystemFile) {
		this.hasSystemFile = hasSystemFile;
	}

	/**
	 * Sets the Encryption-Key
	 *
	 * @param encryptionKey - Encryption-Key
	 */
	void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	/**
	 * Sets the Header-Len
	 *
	 * @param headerLen - Header-Len
	 */
	public void setHeaderLen(int headerLen) {
		this.headerLen = headerLen;
	}

	/**
	 * Sets the Header-Signature
	 *
	 * @param signature - Header-Signature
	 */
	void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * Sets the Header-Version
	 *
	 * @param version - Header-Version
	 */
	void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Sets the Header-Remain
	 *
	 * @param remain - Header-Remain
	 */
	void setRemain(String remain) {
		this.remain = remain;
	}

	/**
	 * Sets the Values from a RPG-Project
	 *
	 * @param rpgProject - RPG-Project
	 */
	void setValuesFromRPGProject(RPG_Project rpgProject) {
		if(rpgProject == null)
			return;

		this.encryptedFilesCount = rpgProject.getEncryptedFiles().size();
		this.resourceFilesCount = rpgProject.getResourceFiles().size();
		this.hasProjectFile = rpgProject.getProjectFile() != null;
		this.hasSystemFile = rpgProject.getSystem() != null;
	}

	/**
	 * Sets the Values from the Decrypter
	 *
	 * @param decrypter - Decrypter
	 */
	void setValuesFromDecrypter(Decrypter decrypter) {
		if(decrypter == null)
			return;

		this.encryptionKey = decrypter.getDecryptCode();
		this.headerLen = decrypter.getHeaderLen();
		this.signature = decrypter.getSignature();
		this.version = decrypter.getVersion();
		this.remain = decrypter.getRemain();
	}

	/**
	 * Removes all internal Layouts
	 */
	private void removeAllLayouts() {
		mainInfo.removeAll();
		keyInfo.removeAll();
		encryptedFiles.removeAll();
		resourceFiles.removeAll();
		projectFile.removeAll();
		systemFile.removeAll();
		jEncryptionKey.removeAll();
		jHeaderLen.removeAll();
		jSignature.removeAll();
		jVersion.removeAll();
		jRemain.removeAll();
	}

	/**
	 * Creates the No-Project Layout
	 */
	private void noProjectLayout() {
		removeAllLayouts();

		JLabel jLabel = new JLabel("No Project");
		jLabel.setForeground(Color.GRAY);
		mainInfo.add(jLabel);
	}

	/**
	 * Builds the content with all Info-Values
	 */
	private void buildContent() {
		removeAllLayouts();
		this.keyInfo.setLayout(new BoxLayout(this.keyInfo, BoxLayout.Y_AXIS));

		this.encryptedFiles.add(new JLabel("Encrypted Files:"));
		JLabel encryptedFilesCountLabel = new JLabel(String.valueOf(this.encryptedFilesCount));
		if(this.encryptedFilesCount == 0)
			encryptedFilesCountLabel.setForeground(Color.RED);
		this.encryptedFiles.add(encryptedFilesCountLabel);

		this.resourceFiles.add(new JLabel("Resource-Files:"));
		JLabel resourceFilesCountLabel = new JLabel(String.valueOf(this.resourceFilesCount));
		if(this.resourceFilesCount == 0)
			resourceFilesCountLabel.setForeground(Color.RED);
		this.resourceFiles.add(resourceFilesCountLabel);

		this.systemFile.add(new JLabel("System.json:"));
		JLabel hasSystemJsonLabel = new JLabel(this.hasSystemFile ? "Found" : "Not Found");
		hasSystemJsonLabel.setForeground(this.hasSystemFile ? new Color(6, 125, 23) : Color.RED);
		this.systemFile.add(hasSystemJsonLabel);

		this.projectFile.add(new JLabel("Project-File:"));
		JLabel hasProjectFile = new JLabel(this.hasProjectFile ? "Found" : "Not Found");
		hasProjectFile.setForeground(this.hasProjectFile ? new Color(6, 125, 23) : Color.RED);
		this.projectFile.add(hasProjectFile);

		this.jEncryptionKey.add(new JLabel("Key: "));
		JLabel keyLabel = new JLabel(this.encryptionKey == null ? "Not Found!" : this.encryptionKey);
		if(this.encryptionKey == null)
			keyLabel.setForeground(Color.RED);
		this.jEncryptionKey.add(keyLabel);

		this.jHeaderLen.add(new JLabel("Header-Length: "));
		JLabel headerLabel = new JLabel(String.valueOf(this.headerLen));
		if(this.headerLen != Decrypter.DEFAULT_HEADER_LEN && this.headerLen != 0) {
			headerLabel.setForeground(Color.BLUE);
			headerLabel.setText(headerLabel.getText() + " (Custom)");
		}
		if(this.headerLen == 0)
			headerLabel.setForeground(Color.RED);
		this.jHeaderLen.add(headerLabel);

		this.jSignature.add(new JLabel("Signature: "));
		JLabel signatureLabel = new JLabel(this.signature == null ? "-" : this.signature);
		if(this.signature == null)
			signatureLabel.setForeground(Color.RED);
		else if(! this.signature.equals(Decrypter.DEFAULT_SIGNATURE)) {
			signatureLabel.setForeground(Color.BLUE);
			signatureLabel.setText(signatureLabel.getText() + " (Custom)");
		}
		this.jSignature.add(signatureLabel);

		this.jVersion.add(new JLabel("Version: "));
		JLabel versionLabel = new JLabel(this.version == null ? "-" : this.version);
		if(this.version == null)
			versionLabel.setForeground(Color.RED);
		else if(! this.version.equals(Decrypter.DEFAULT_VERSION)) {
			versionLabel.setForeground(Color.BLUE);
			versionLabel.setText(versionLabel.getText() + " (Custom)");
		}
		this.jVersion.add(versionLabel);

		this.jRemain.add(new JLabel("Remain: "));
		JLabel remainLabel = new JLabel(this.remain == null ? "-" : this.remain);
		if(this.remain == null)
			remainLabel.setForeground(Color.RED);
		else if(! this.remain.equals(Decrypter.DEFAULT_REMAIN)) {
			remainLabel.setForeground(Color.BLUE);
			remainLabel.setText(remainLabel.getText() + " (Custom)");
		}
		this.jRemain.add(remainLabel);

		this.mainInfo.add(this.encryptedFiles);
		this.mainInfo.add(this.resourceFiles);
		this.mainInfo.add(this.systemFile);
		this.mainInfo.add(this.projectFile);
		this.mainInfo.validate();

		this.keyInfo.add(this.jEncryptionKey);
		this.keyInfo.add(this.jHeaderLen);
		this.keyInfo.add(this.jSignature);
		this.keyInfo.add(this.jVersion);
		this.keyInfo.add(this.jRemain);
		this.keyInfo.validate();
	}

	/**
	 * Refreshes the Layout & Shown Values
	 */
	void refresh() {
		buildContent();
		validate();
	}

	/**
	 * Resets the Layout to No-Project
	 */
	void reset() {
		encryptedFilesCount = 0;
		resourceFilesCount = 0;
		hasProjectFile = false;
		hasSystemFile = false;
		encryptionKey = null;
		headerLen = 0;
		signature = null;
		version = null;
		remain = null;

		noProjectLayout();
		validate();
	}
}
