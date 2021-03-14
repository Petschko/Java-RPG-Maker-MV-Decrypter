package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;
import org.petschko.rpgmakermv.decrypt.Decrypter;
import org.petschko.rpgmakermv.decrypt.RPG_Project;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Peter Dragicevic
 */
class DetectKey implements I_CMD {
	private String pathToProject;
	private boolean skipImageQuestion = false;

	// Decoder options
	private int headerLen = Decrypter.DEFAULT_HEADER_LEN;

	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	@Override
	public void run(String[] args) {
		if(args.length < 2) {
			App.showMessage("To less arguments, see help", CMD.STATUS_WARNING);
			App.showMessage("");
			this.printHelp();
			CMD.exitCMD(CMD.STATUS_WARNING);
			return;
		}

		// Set Path to Project
		pathToProject = args[1];
		App.showMessage("Set Project-Dir to: \"" + pathToProject + "\"");

		if(args.length >= 3)
			skipImageQuestion = Boolean.parseBoolean(args[2]);
		if(args.length >= 4) {
			headerLen = Integer.parseInt(args[3]);

			// Ensure headerLen is at least 1 else default
			if(headerLen < 1)
				headerLen = Decrypter.DEFAULT_HEADER_LEN;
		}

		detectsKey();
	}

	/**
	 * Detects the Key
	 */
	private void detectsKey() {
		try {
			RPG_Project rpgProject = new RPG_Project(pathToProject, false);
			Decrypter decrypter = new Decrypter();
			decrypter.setHeaderLen(headerLen);

			// Ensure we may have an encrypted image
			rpgProject.findEncryptedImg();

			// Try the detection from JSON-File if found
			if(rpgProject.getSystem() != null) {
				decrypter.detectEncryptionKeyFromJson(rpgProject.getSystem(), rpgProject.getEncryptionKeyName());

				if(decrypter.getDecryptCode() != null) {
					showKey(decrypter.getDecryptCode());
					return;
				}
			}

			// Try the detection from image but ask before
			if(rpgProject.getEncryptedImgFile() != null) {
				boolean doImgSearch = true;
				if(! skipImageQuestion) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					App.showMessage("");
					App.showMessage("Key not found so far (System.json)... Should I generate the Key from encrypted images?");
					App.showMessage("WARNING: Key from images may not work. Continue? (Y/n)");
					String s = reader.readLine().toLowerCase();

					if(! (s.equals("") || s.equals("y") || s.equals("yes") || s.equals("true") || s.equals("1")))
						doImgSearch = false;

					App.showMessage("Ok!");
				}

				if(doImgSearch) {
					App.showMessage("Searches for Key in Image...");
					decrypter.detectEncryptionKeyFromImage(rpgProject.getEncryptedImgFile());
				} else
					App.showMessage("Skips searching for Key in Image...");

				if(decrypter.getDecryptCode() != null) {
					showKey(decrypter.getDecryptCode());
					return;
				}
			}

			App.showMessage("Key not found...");
			CMD.exitCMD(CMD.STATUS_OK);
		} catch(Exception e) {
			e.printStackTrace();
			CMD.exitCMD(CMD.STATUS_ERROR);
		}
	}

	/**
	 * Shows the Key
	 *
	 * @param key - Key
	 */
	private void showKey(String key) {
		App.showMessage("---------------------------------------------------------", CMD.STATUS_OK);
		App.showMessage("> The Key is: " + key, CMD.STATUS_OK);
		App.showMessage("---------------------------------------------------------", CMD.STATUS_OK);
		CMD.exitCMD(CMD.STATUS_OK);
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("Detects the En-/Decryption-Key");
		App.showMessage("");
		App.showMessage("Usage: java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" key [path to project] [(optional) ask before image keysearch (true|false)] [(optional) headerLen]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Params: (Separate each param by a space, for paths use \"\" around the path)");
		App.showMessage(CMD.HELP_INDENT + "  [path to project] - Path to the RPG-MV/MZ Project were you want to get the key from");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [ask before image keysearch (true|false)] - Ask before search key in images | Default: true");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Header-Values: (usually REALLY not needed)");
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [headerLen] - Byte length of the Header | Default: " + this.headerLen);
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Examples of the full commands with all params:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" key \"C:\\my rpg mv game\\\" true");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" key \"C:\\my rpg mv game\\\" false");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" key \"C:\\my rpg mv game\\\" false 14");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "- The first command returns the key of the Project if found also asks before searching in images");
		App.showMessage(CMD.HELP_INDENT + "- The second command returns the key of the Project if found but skips the image question");
		App.showMessage(CMD.HELP_INDENT + "- The third command does the same as the 2nd, just shows how you can modify the header length");
		App.showMessage("");
	}
}
