package org.petschko.lib.gui;

import org.petschko.lib.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 15.01.2017
 * Time: 19:47
 * Update: -
 * Version: 0.0.1
 *
 * Notes: JImageLabel Class
 */
public class JImageLabel extends JLabel {
	private BufferedImage image = null;

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 *
	 * @param imagePath - Path of the Image
	 */
	public JImageLabel(String imagePath) {
		this.setImage(imagePath, false);
	}

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 *
	 * @param image - File Pointing to the Image
	 */
	public JImageLabel(java.io.File image) {
		this.setImage(image);
	}

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 *
	 * @param imagePath - Path or ClassPath to the Image
	 * @param useURL - true if use class path else false
	 */
	public JImageLabel(String imagePath, boolean useURL) {
		this.setImage(imagePath, useURL);
	}

	/**
	 * Returns the Image as BufferedImage
	 *
	 * @return - Buffered Image or null if not set
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Set this Image direct as Buffered Image
	 *
	 * @param image - new BufferedImage
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		this.createImagePanel();
	}

	/**
	 * Set a new Image as File for the Panel
	 *
	 * @param file - File Pointing to the Image
	 */
	public void setImage(java.io.File file) {
		this.setBufferedImage(file);
	}

	/**
	 * Set a new Image as String (Path or Class-Path) for the Panel
	 *
	 * @param imagePath - Path or Class-Path
	 * @param useURL - true uses Class-Path instead of a Path
	 */
	public void setImage(String imagePath, boolean useURL) {
		if(imagePath == null) {
			Exception e = new Exception("Image Path can't be null");
			e.printStackTrace();
			return;
		}

		if(useURL) {
			this.setBufferedImage(getClass().getResource(imagePath));
		} else {
			java.io.File imageFile;

			try {
				imageFile = new java.io.File(imagePath);
			} catch(Exception e) {
				e.printStackTrace();
				this.image = null;

				return;
			}

			this.setBufferedImage(imageFile);
		}
	}

	/**
	 * Reads the imageURL
	 *
	 * @param imageURL - URL of the Image
	 */
	private void setBufferedImage(URL imageURL) {
		if(imageURL == null) {
			this.image = null;

			return;
		}

		try {
			this.image = ImageIO.read(imageURL);
		} catch(IOException e) {
			e.printStackTrace();
			this.image = null;

			return;
		}

		// Try to create the new Panel after reading
		this.createImagePanel();
	}

	/**
	 * Reads an Image-File
	 *
	 * @param imageFile - Image-File
	 */
	private void setBufferedImage(java.io.File imageFile) {
		try {
			this.image = ImageIO.read(imageFile);
		} catch(IOException e) {
			e.printStackTrace();
			this.image = null;

			return;
		}

		// Try to create the new Panel after reading
		this.createImagePanel();
	}

	/**
	 * Assign the Buffered Image to the Panel
	 */
	private void createImagePanel() {
		if(this.image != null)
			this.setIcon(new ImageIcon(this.image));
	}

	/**
	 * Resize the Element
	 *
	 * @param width - New Width
	 * @param height - New Height
	 */
	public void setImageSize(int width, int height) {
		Image img = new Image(this.image);

		try {
			img.resize(width, height);
		} catch(Exception e) {
			e.printStackTrace();

			return;
		}

		this.setImage(img.getBufferedImage());
	}
}
