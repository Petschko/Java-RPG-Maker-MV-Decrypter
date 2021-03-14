package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;
import org.petschko.rpgmakermv.decrypt.Decrypter;
import org.petschko.rpgmakermv.decrypt.RPG_Project;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:37
 *
 * Notes: Restore Class
 */
class Restore implements I_CMD {
	private String pathToProject;
	private String outputDir;

	// Decoder options
	private boolean verifyDir = false;
	private boolean ignoreFakeHeader = true;
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

		// Set Output-Dir
		try {
			outputDir = args[2];
		} catch(ArrayIndexOutOfBoundsException arEx) {
			outputDir = Config.DEFAULT_OUTPUT_DIR;
		}

		App.showMessage("Set Output-Dir to: \"" + outputDir + "\"");

		if(args.length >= 4)
			verifyDir = Boolean.parseBoolean(args[3]);
		if(args.length >= 5)
			ignoreFakeHeader = Boolean.parseBoolean(args[4]);
		if(args.length >= 6) {
			headerLen = Integer.parseInt(args[5]);

			// Ensure headerLen is at least 1 else default
			if(headerLen < 1)
				headerLen = Decrypter.DEFAULT_HEADER_LEN;
		}

		handleFiles();
	}

	/**
	 * Handles Files to restore
	 */
	private void handleFiles() {
		try {
			RPG_Project rpgProject = new RPG_Project(pathToProject, verifyDir);
			Decrypter decrypter = new Decrypter();

			rpgProject.setOutputPath(outputDir);
			decrypter.setIgnoreFakeHeader(ignoreFakeHeader);
			decrypter.setHeaderLen(headerLen);
			rpgProject.decryptFilesCmd(decrypter, true);
		} catch(Exception e) {
			e.printStackTrace();
			CMD.exitCMD(CMD.STATUS_ERROR);
		}
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("Restores images without needing the Key");
		App.showMessage("");
		App.showMessage("Usage: java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" restore [path to project] [(optional) output path] [(optional) verifyRpgDir - false|true] [(optional) ignoreFakeHeader - true|false] [(optional) headerLen]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Params: (Separate each param by a space, for paths use \"\" around the path)");
		App.showMessage(CMD.HELP_INDENT + "  [path to project]        - Path to the RPG-MV/MZ Project were you want to restore images");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [output path] - Path where the restored files go | Default: \"output\" (out dir in program dir)");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [verifyRpgDir (false|true)]     - Verifies if its a RPG-MV/MZ dir | Default: false");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [ignoreFakeHeader (true|false)] - Ignored the Files MV/MZ-Header | Default: true");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Header-Values: (usually REALLY not needed)");
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [headerLen] - Byte length of the Header | Default: " + this.headerLen);
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Examples of full commands with all params:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" restore \"C:\\my rpg mv game\\\" \"output\" false true");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" restore \"C:\\my rpg mv game\\\" \"C:\\my rpg mv game\\\" true false");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"" + Config.THIS_JAR_FILE_NAME + "\" restore \"C:\\my rpg mv game\\\" \"C:\\my rpg mv game\\\" true false 14");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "- The first command restores all files inside this programs output dir, dont check if its a");
		App.showMessage(CMD.HELP_INDENT + "  RPG-MV/MZ dir and ignores if the header of the files is the RPG-MV/MZ header");
		App.showMessage(CMD.HELP_INDENT + "- The second command restores all files inside the game directory, it checks if RPG-MV/MZ dir and");
		App.showMessage(CMD.HELP_INDENT + "  if Header is valid");
		App.showMessage(CMD.HELP_INDENT + "- The third command does the same as the 2nd, just shows how you can modify the header length");
		App.showMessage("");
	}
}
