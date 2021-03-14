package org.petschko.rpgmakermv.decrypt.cmd;

import org.petschko.rpgmakermv.decrypt.App;
import org.petschko.rpgmakermv.decrypt.Config;
import org.petschko.rpgmakermv.decrypt.Decrypter;
import org.petschko.rpgmakermv.decrypt.RPGProject;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:35
 *
 * Notes: CMD_Encrypt Class
 */
class CMD_Encrypt implements CMD_Command {
	private String pathToProject;
	private String outputDir;

	// Encrypter options
	private boolean toMV = true;
	private String key = null;
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
			toMV = Boolean.parseBoolean(args[3]);
		if(args.length >= 5) {
			if(! args[4].toLowerCase().equals("auto")) {
				key = args[4].toLowerCase();
			}
		}
		if(args.length >= 6) {
			headerLen = Integer.parseInt(args[5]);

			// Ensure headerLen is at least 1 else default
			if(headerLen < 1)
				headerLen = Decrypter.defaultHeaderLen;
		}
		if(args.length >= 7)
			signature = args[6].trim().toLowerCase();
		if(args.length >= 8)
			version = args[7].trim().toLowerCase();
		if(args.length >= 9)
			remain = args[8].trim().toLowerCase();

		handleFiles();
	}

	/**
	 * Handles Files to encrypt
	 */
	private void handleFiles() {
		try {
			RPGProject rpgProject = new RPGProject(pathToProject, false);
			Decrypter encrypter;

			if(key == null)
				encrypter = new Decrypter();
			else
				encrypter = new Decrypter(key);

			rpgProject.setOutputPath(outputDir);
			rpgProject.setMV(toMV);
			encrypter.setHeaderLen(headerLen);
			encrypter.setSignature(signature);
			encrypter.setVersion(version);
			encrypter.setRemain(remain);
			rpgProject.encryptFilesCmd(encrypter);
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
		App.showMessage("Encrypts files back to MV/MZ");
		App.showMessage("");
		App.showMessage("Usage: java -jar \"RPG Maker MV Decrypter.jar\" encrypt [path to encrypt files dir] [(optional) output path] [(optional) to MV - true|false] [(optional) key - auto|keyValue] [(optional) headerLen] [(optional) hsignature] [(optional) hversion] [(optional) hremain]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Params: (Separate each param by a space, for paths use \"\" around the path)");
		App.showMessage(CMD.HELP_INDENT + "  [path to encrypt files dir]      - Path to the File-Dir/Project you want to encrypt");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [output path]         - Path where the encrypted files go");
		App.showMessage(CMD.HELP_INDENT + "                                     Default: \"output\" (out dir in program dir)");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [to MV (true|false)]  - Encrypts files for MV (MZ on false) | Default: true");
		App.showMessage(CMD.HELP_INDENT + "  (optional) [key (auto|keyValue)] - Encryption key or auto detection | Default: auto");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Header-Values: (usually REALLY not needed)");
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [headerLen]  - Byte length of the Header | Default: " + this.headerLen);
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [hsignature] - Signature of the Header | Default: " + this.signature);
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [hversion]   - Version of the Header | Default: " + this.version);
		App.showMessage(CMD.HELP_INDENT + "  (very optional) [hremain]    - Remain of the Header | Default: " + this.remain);
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Examples of full commands with all params:");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" encrypt \"C:\\my rpg mv game\\\" \"output\" true auto");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" encrypt \"C:\\my rpg mv game\\\" \"C:\\my rpg mv game\\\" true d41d8cd98f00b204e9800998ecf8427e");
		App.showMessage(CMD.HELP_INDENT + "  java -jar \"RPG Maker MV Decrypter.jar\" encrypt \"C:\\my rpg mv game\\\" \"C:\\my rpg mv game\\\" true d41d8cd98f00b204e9800998ecf8427e 14 5250474d56000000 000301 00000000");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "- The first command encrypts all files to this programs output dir, as a MV-Files and auto-detects the key");
		App.showMessage(CMD.HELP_INDENT + "- The second command encrypts all files inside the game directory, as MV-Files");
		App.showMessage(CMD.HELP_INDENT + "  with the key d41d8cd98f00b204e9800998ecf8427e");
		App.showMessage(CMD.HELP_INDENT + "- The third command does the same as the 2nd, just shows how you can modify the values of the header");
		App.showMessage("");
	}
}
