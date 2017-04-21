package org.petschko.lib.gui;

import com.sun.istack.internal.Nullable;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: http://petschko.org/
 * Date: 17.01.2017
 * Time: 19:05
 * Update: -
 * Version: 0.0.1
 *
 * Notes: JLabelExtra Class
 */
public class JLabelExtra extends JLabel {
	private String url = null;
	private MouseAdapter urlClickEvent = null;
	private boolean isBold = false;
	private boolean isUnderline = false;
	private boolean isItalic = false;

	/**
	 * Creates a <code>JLabel</code> instance with the specified
	 * text and horizontal alignment.
	 * The label is centered vertically in its display area.
	 *
	 * @param text The text to be displayed by the label.
	 * @param horizontalAlignment One of the following constants
	 * defined in <code>SwingConstants</code>:
	 * <code>LEFT</code>,
	 * <code>CENTER</code>,
	 * <code>RIGHT</code>,
	 * <code>LEADING</code> or
	 */
	public JLabelExtra(@Nullable String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	/**
	 * Creates a <code>JLabel</code> instance with the specified text.
	 * The label is aligned against the leading edge of its display area,
	 * and centered vertically.
	 *
	 * @param text The text to be displayed by the label.
	 */
	public JLabelExtra(@Nullable String text) {
		super(text);
	}

	/**
	 * Creates a <code>JLabel</code> instance with
	 * no image and with an empty string for the title.
	 * The label is centered vertically
	 * in its display area.
	 * The label's contents, once set, will be displayed on the leading edge
	 * of the label's display area.
	 */
	public JLabelExtra() {
		// Void
	}

	/**
	 * Sets the Text-Color of the JLabelExtra
	 *
	 * @param color - new Color for the Text or null for default
	 */
	public void setTextColor(@Nullable Color color) {
		if(color == null) {
			this.setForeground(new JLabel().getForeground());
			return;
		}

		this.setForeground(color);
	}

	/**
	 * Sets if the Text should be Bold
	 *
	 * @param bold - true if the text should be bold else false
	 */
	public void setBold(boolean bold) {
		if(bold && ! this.isBold) {
			// Set it Bold
			this.changeStyleAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
			this.isBold = true;
		} else if(! bold && this.isBold) {
			// Remove Bold
			this.changeStyleAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
			this.isBold = false;
		}
	}

	/**
	 * Sets if the Text should be Underlined
	 *
	 * @param underline - true if the text should be underlined else false
	 */
	public void setUnderline(boolean underline) {
		if(underline && ! this.isUnderline) {
			// Underline it
			this.changeStyleAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			this.isUnderline = true;
		} else if(! underline && this.isUnderline) {
			// Remove Underline
			this.changeStyleAttribute(TextAttribute.UNDERLINE, -1);
			this.isUnderline = false;
		}
	}

	/**
	 * Sets if the Text should be Italic
	 *
	 * @param italic - true if the text should be italic else false
	 */
	public void setItalic(boolean italic) {
		if(italic && ! this.isItalic) {
			// Set Italic
			this.changeStyleAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
			this.isItalic = true;
		} else if(! italic && this.isItalic) {
			// Remove Italic
			this.changeStyleAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
			this.isItalic = false;
		}
	}

	/**
	 * Sets the Font-Type of the Text
	 *
	 * @param fontType - Font-Type of the Text or null for default
	 */
	public void setFontType(@Nullable String fontType) {
		if(fontType == null) {
			this.changeStyleAttribute(TextAttribute.FONT, new JLabel().getFont().getFontName());
			return;
		}

		this.changeStyleAttribute(TextAttribute.FONT, fontType);
	}

	/**
	 * Sets the size of the Text
	 *
	 * @param fontSize - Font-Size of the Text or <=0 for default
	 */
	public void setFontSize(int fontSize) {
		if(fontSize < 1) {
			this.changeStyleAttribute(TextAttribute.SIZE, new JLabel().getFont().getSize());
			return;
		}

		this.changeStyleAttribute(TextAttribute.SIZE, fontSize);
	}

	/**
	 * Set a URL and let the User click on this
	 *
	 * @param url - URL to set or null if you want remove the URL
	 * @param defaultURLStyle - true Styles the URL for you Underlined-Blue else it don't style it for you
	 */
	public void setURL(@Nullable String url, boolean defaultURLStyle) {
		if(url == null) {
			this.removeURL(defaultURLStyle);
			return;
		}

		if(this.url != null) {
			// Don't change anything if the url is the same
			if(this.url.equals(url))
				return;

			// Remove old URL if url is different
			this.removeURL(defaultURLStyle);
		}

		// Styles
		if(defaultURLStyle) {
			this.setUnderline(true);
			this.setTextColor(Color.BLUE);
		}

		// Assign new URL
		this.url = url;
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.createMouseURLListener();
		this.addMouseListener(this.urlClickEvent);
	}

	/**
	 * Removes the URL Event and reset some styles back
	 *
	 * @param defaultURLStyle - true remove default URL-Styles else it don't remove styles
	 */
	private void removeURL(boolean defaultURLStyle) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		// Remove Styles
		if(defaultURLStyle) {
			this.setUnderline(false);
			this.setTextColor(null);
		}

		if(this.urlClickEvent != null) {
			this.removeMouseListener(this.urlClickEvent);
			this.urlClickEvent = null;
			this.url = null;
		}
	}

	/**
	 * Creates a MouseAdapter from the given URL
	 */
	private void createMouseURLListener() {
		if(this.url == null)
			return;

		this.urlClickEvent = new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 *
			 * @param e
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (URISyntaxException | IOException ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	/**
	 * Changes an Attribute of the Font
	 *
	 * @param attributeKey - Key of the Attribute
	 * @param value - New Value of the Attribute
	 */
	private void changeStyleAttribute(TextAttribute attributeKey, Object value) {
		Map<TextAttribute, Object> attributes = new HashMap<>(this.getFont().getAttributes());
		attributes.put(attributeKey, value);
		this.setFont(this.getFont().deriveFont(attributes));
	}
}
