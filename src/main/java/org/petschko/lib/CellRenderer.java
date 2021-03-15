package org.petschko.lib;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author trenton-telge
 */
public class CellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;
	private String relativePath = null;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof File) {
			File file = (File) value;
			String fileText = file.getName();

			if(relativePath != null)
				fileText = file.getPath().replace(relativePath, "");

			setText(fileText);

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
		}

		return this;
	}

	/**
	 * Sets the Relative Path of the Project
	 *
	 * @param path - Path of the Project
	 */
	public void setRelativePath(String path) {
		if(path != null)
			path = org.petschko.lib.File.ensureDSonEndOfPath(path);

		this.relativePath = path;
	}
}
