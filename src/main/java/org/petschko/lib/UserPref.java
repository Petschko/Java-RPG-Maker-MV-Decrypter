package org.petschko.lib;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * @author Peter Dragicevic
 */
public abstract class UserPref {
	private Properties properties = null;
	private String filePath;

	/**
	 * UserPrefs Constructor
	 *
	 * @param filePath - File-Path to UserPref-File
	 */
	public UserPref(String filePath) {
		this.setFilePath(filePath);

		if(filePath != null)
			this.load();
	}

	/**
	 * Returns the Properties Object
	 *
	 * @return - Properties Object
	 */
	private Properties getProperties() {
		return properties;
	}

	/**
	 * Sets the Properties Object
	 *
	 * @param properties - Properties Object
	 */
	protected void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Returns the File-Path of the Properties File
	 *
	 * @return - File-Path of the Properties File or null if not set
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the File-Path of the Properties File
	 *
	 * @param filePath - File-Path of the Properties File
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Loads the Properties of the User from a File
	 *
	 * @return - true of Properties where loaded else false
	 */
	public boolean load() {
		if(this.getFilePath() == null)
			return this.loadDefaults();

		if(! File.existsFile(this.getFilePath()))
			return this.loadDefaults();

		// Try to read the File
		Properties p = new Properties();
		Reader reader;
		try {
			reader = new FileReader(this.getFilePath());
			p.load(reader);
		} catch(Exception e) {
			e.printStackTrace();

			return loadDefaults();
		} finally {
			this.setProperties(p);
		}

		return true;
	}

	/**
	 * Loads the Default settings
	 *
	 * @return - true if the Properties where loaded correctly
	 */
	public abstract boolean loadDefaults();

	/**
	 * Saves the Properties of the User to a File
	 *
	 * @return - true if the Save was successful else false
	 */
	public boolean save() {
		if(this.getFilePath() == null || this.getProperties() == null)
			return false;

		// Check if File Exists if not try to create it
		if(! File.existsFile(this.getFilePath(), true))
			return false;

		try {
			FileWriter fileWriter = new FileWriter(this.getFilePath());
			this.getProperties().store(fileWriter, "User Config File");
		} catch(IOException e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Returns the requested Property-Value with a Fallback-Value
	 * @param configKey - Property-Key (Name)
	 * @return - Property-Value
	 */
	public String getConfig(String configKey) {
		return getConfig(configKey, null);
	}

	/**
	 * Returns the requested Property-Value or the Fallback-Value
	 *
	 * @param configKey - Property-Key (Name)
	 * @param fallbackValue - Fallback-Value if the Key is not set or null to ignore
	 * @return - Property-Value
	 */
	public String getConfig(String configKey, String fallbackValue) {
		if(this.getProperties() == null)
			this.load();

		if(fallbackValue == null)
			return this.getProperties().getProperty(configKey);

		// Try to add the Property if not exists
		if(this.getProperties().getProperty(configKey) == null)
			this.setConfig(configKey, fallbackValue);

		return this.getProperties().getProperty(configKey, fallbackValue);
	}

	/**
	 * Sets a Value to the Properties Object
	 *
	 * @param configKey - Property-Key (Name)
	 * @param newValue - New Value for this Properties-Key
	 */
	public void setConfig(String configKey, String newValue) {
		if(this.getProperties() == null)
			this.load();

		this.getProperties().setProperty(configKey, newValue);
	}

	/**
	 * Switches a Boolean "String" value to the opposite Boolean "String" Value if the value is not a Boolean String it doesn't change anything
	 *
	 * @param configKey - Config Key of the Value to switch
	 */
	public void switchBoolConfig(String configKey) {
		String config = this.getConfig(configKey);

		// Config doesn't exists
		if(config == null)
			config = "false";

		config = config.toLowerCase();

		// Only switch on boolean values
		if(config.equals("true") || config.equals("false") || config.equals("1") || config.equals("0")) {
			boolean newValue = ! Functions.strToBool(config);

			this.setConfig(configKey, Boolean.toString(newValue));
		}
	}
}
