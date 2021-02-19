package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:05
 *
 * Notes: CMD_Decrypt Class
 */
class CMD_Decrypt implements CMD_Command {
	private String pathToProject;
	private String outputDir;

	// Decrypter options
	private String key = null;
	private boolean verifyDir = false;
	private boolean ignoreFakeHeader = true;
	private int headerLen = Decrypter.defaultHeaderLen;
	private String signature = Decrypter.defaultSignature;
	private String version = Decrypter.defaultVersion;
	private String remain = Decrypter.defaultRemain;

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
			outputDir = Config.defaultOutputDir;
		}

		App.showMessage("Set Output-Dir to: \"" + outputDir + "\"");

		if(args.length >= 4)
			verifyDir = Boolean.parseBoolean(args[3]);
		if(args.length >= 5)
			ignoreFakeHeader = Boolean.parseBoolean(args[4]);
		if(args.length >= 6) {
			if(! args[5].toLowerCase().equals("auto")) {
				key = args[5].toLowerCase();
			}
		}
		if(args.length >= 7) {
			headerLen = Integer.parseInt(args[6]);

			// Ensure headerLen is at least 1 else default
			if(headerLen < 1)
				headerLen = Decrypter.defaultHeaderLen;
		}
		if(args.length >= 8)
			signature = args[7].trim().toLowerCase();
		if(args.length >= 9)
			version = args[8].trim().toLowerCase();
		if(args.length >= 10)
			remain = args[9].trim().toLowerCase();

		handleFiles();
	}

	/**
	 * Handles Files to decrypt
	 */
	private void handleFiles() {
		try {
			RPGProject rpgProject = new RPGProject(pathToProject, verifyDir);
			Decrypter decrypter;

			if(key == null)
				decrypter = new Decrypter();
			else
				decrypter = new Decrypter(key);

			rpgProject.setOutputPath(outputDir);
			decrypter.setIgnoreFakeHeader(ignoreFakeHeader);
			decrypter.setHeaderLen(headerLen);
			decrypter.setSignature(signature);
			decrypter.setVersion(version);
			decrypter.setRemain(remain);
			rpgProject.decryptFilesCmd(decrypter);
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
		App.showMessage("Decrypt Files");
		App.showMessage("");
		App.showMessage("Usage: java -jar \"RPG Maker MV Decrypter.jar\" decrypt [path to decrypt project] [(optional) output path] [(optional) verifyRpgDir - false|true] [(optional) ignoreFakeHeader - true|false] [(optional) key - auto|keyValue] [(optional) headerLen] [(optional) hsignature] [(optional) hversion] [(optional) hremain]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Params: (Separate each param by a space, for paths use \"\" around the path)");
		App.showMessage(CMD.HELP_INDENT + "  [path to decrypt project]        - Path to the RPG-MV/MZ Project you want to decrypt");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [output path]         - Path where the decrypted files go");
		App.showMessage(CMD.HELP_INDENT + "                                     Default: \"output\" (out dir in program dir)");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [verifyRpgDir (false|true)]");
		App.showMessage(CMD.HELP_INDENT + "                                   - Verifies if its a RPG-MV/MZ dir | Default: false");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [ignoreFakeHeader (true|false)] - Ignored the Files MV/MZ-Header | Default: true");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [key (auto|keyValue)] - Decryption key or auto detection | Default: auto");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Header-Values: (usually REALLY not needed)");
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [headerLen]  - Byte length of the Header | Default: " + this.headerLen);
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [hsignature] - Signature of the Header | Default: " + this.signature);
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [hversion]   - Version of the Header | Default: " + this.version);
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [hremain]    - Remain of the Header | Default: " + this.remain);
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Examples of full commands with all params:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" decrypt \"C:\\my rpg mv game\\\" \"output\" false true auto");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" decrypt \"C:\\my rpg mv game\\\" \"C:\\my rpg mv game\\\" true false d41d8cd98f00b204e9800998ecf8427e");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" decrypt \"C:\\my rpg mv game\\\" \"C:\\my rpg mv game\\\" true false d41d8cd98f00b204e9800998ecf8427e 14 5250474d56000000 000301 00000000");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "- The first command extracts all files inside this programs output dir, dont check if its a RPG-MV/MZ dir,");
		App.showMessage(CMD.HELP_INDENT + "  ignored if the header of the files is the RPG-MV/MZ header and auto-detects the key");
		App.showMessage(CMD.HELP_INDENT + "- The second command extract all files inside the game directory, checks if RPG-MV/MZ dir and Header is");
		App.showMessage(CMD.HELP_INDENT + "  valid with the key d41d8cd98f00b204e9800998ecf8427e");
		App.showMessage(CMD.HELP_INDENT + "- The third command does the same as the 2nd, just shows how you can modify the values of the header");
		App.showMessage("");
	}
}
