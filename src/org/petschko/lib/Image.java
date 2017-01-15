package org.petschko.lib;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Author: Peter Dragicevic [peter-91@hotmail.de]
 * Authors-Website: http://petschko.org/
 * Date: 15.01.2017
 * Time: 21:52
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Image Class
 */
public class Image {
	private BufferedImage bufferedImage = null;

	/**
	 * Image constructor
	 *
	 * @param bufferedImage - Buffered Image
	 */
	public Image(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	/**
	 * Resize the Image
	 *
	 * @param newWidth - New Width
	 * @param newHeight - New Height
	 */
	public void resize(int newWidth, int newHeight) {
		if(this.bufferedImage == null)
			return;

		BufferedImage newImage = Image.drawNewPicture(newWidth, newHeight);
		Graphics g = newImage.getGraphics();

		g.drawImage(this.bufferedImage, 0, 0, newWidth, newHeight, null);

		this.bufferedImage = newImage;
	}

	/**
	 * Returns the width of the Image
	 *
	 * @return - Width of the Image or null if not set
	 */
	public int getWidth() {
		return bufferedImage.getWidth();
	}

	/**
	 * Returns the height of the Image
	 *
	 * @return - Height if the Image or null if not set
	 */
	public int getHeight() {
		return bufferedImage.getHeight();
	}

	/**
	 * Returns the BufferedImage
	 *
	 * @return - Buffered Image
	 */
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	/**
	 *
	 * @param x - width of the new picture
	 * @param y - height of the new picture
	 * @return - New Picture (transparency)
	 * @throws NumberFormatException - Invalid-Values
	 */
	public static BufferedImage drawNewPicture(int x, int y) throws NumberFormatException {
		if (x <= 0 || y <= 0)
			throw new NumberFormatException("drawNewPicture: x and y can't be <= 0!");

		BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();

		Color transparency = new Color(0, 0, 0, 0);
		g.setColor(transparency);
		g.fillRect(0, 0, x, y);

		return bi;
	}
}
