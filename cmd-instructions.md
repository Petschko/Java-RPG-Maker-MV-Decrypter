I put the detailed Command-Line explanation here since not many people use it and its pretty "long"

Everything what's explained here is also explained in the help-commands within the Program.

# Command explanation

Before we start:
- You only need the params which are marked as __(Req.)__. All other params are optional.
- If a path contains a space use the quotations "" around: `"D:\games\Project with Space\"`
- Remember that IOS & Linux not have a Drive-Letter nor using the `\ ` as Directory-Separator; use `/` instead when you're using Linux/IOS
- Important: If you just Type `java -jar "RPG Maker MV Decrypter.jar"` the Program will start with GUI
- You can also use the Help-Command with `java -jar "RPG Maker MV Decrypter.jar" help`
- The Script ends with "Done." Then you're done.

There exists commands you can use, these are explained here:
- __decrypt__ - Decrypts all Files (Help: `java -jar "RPG Maker MV Decrypter.jar" decrypt help`)
  - The Script will decrypt all Files in the target dir
  - Simple-Syntax: `java -jar "RPG Maker MV Decrypter.jar" decrypt [(Req.) target path] [output path] [verifyRpgDir (false|true)] [ignoreFakeHeader (true|false)]`
  - Full-Syntax: `java -jar "RPG Maker MV Decrypter.jar" decrypt [(Req.) target path] [output path] [verifyRpgDir (false|true)] [ignoreFakeHeader (true|false)] [headerLen (number)] [hsignature] [hversion] [hremain]`
  - Example 1: `java -jar "RPG Maker MV Decrypter.jar" decrypt D:\games\Project1\`
    - Decrypts the game located in `D:\games\Project1\` and saves files to the output dir of this program
  - Example 2: `java -jar "RPG Maker MV Decrypter.jar" decrypt D:\games\Project1\ D:\games\Project1\`
    - Decrypts the game located in `D:\games\Project1\` and saves files in the game dir _(preserves structure)_
  - Example 3: `java -jar "RPG Maker MV Decrypter.jar" decrypt D:\games\Project1\ D:\games\Project1\ false true d41d8cd98f00b204e9800998ecf8427e`
    - Like example 2, just using the Decryption key `d41d8cd98f00b204e9800998ecf8427e`
  - You can do more things with `decrypt` please use the help to see more details
- __restore__ - Restores all PNG-Files without a Key (Help: `java -jar "RPG Maker MV Decrypter.jar" restore help`)
  - The Script will restore all Files in the target dir _(no key needed)_
  - Simple-Syntax: `java -jar "RPG Maker MV Decrypter.jar"  restore [(Req.) target path] [output path] [verifyRpgDir (false|true)] [ignoreFakeHeader (true|false)]`
  - Full-Syntax: `java -jar "RPG Maker MV Decrypter.jar"  restore [(Req.) target path] [output path] [verifyRpgDir (false|true)] [ignoreFakeHeader (true|false)] [headerLen (number)]`
  - Example 1: `java -jar "RPG Maker MV Decrypter.jar" restore "C:\my rpg mv game\"`
    - Restores all Images in `C:\my rpg mv game\` and saves files to the output dir of this program
  - Example 2: `java -jar "RPG Maker MV Decrypter.jar" restore "C:\my rpg mv game\" "C:\my rpg mv game\"`
    - Restores all Images in `C:\my rpg mv game\` and saves the restored images also to the game _(preserves structure)_
  - Example 3: `java -jar "RPG Maker MV Decrypter.jar" restore "C:\my rpg mv game\" "C:\my rpg mv game\" true false`
    - Like Example 2, just adding that the Script should verify it's a RPG-MV/MZ dir and the MV/MZ-Header on the files is correct
- __encrypt__ - Encrypts all Files (Help: `java -jar "RPG Maker MV Decrypter.jar" ecrypt help`)
  - This Script (Re-)Encrypts all Files in the target dir
  - This Script NEEDs a Key (so it must be either in the Target-dir - `System.json`-File) or given as Parameter
  - Simple-Syntax: `java -jar "RPG Maker MV Decrypter.jar" encrypt [(Req.) target path] [output path] [to MV (true|false)] [key (auto|keyValue)]`
  - Full-Syntax: `java -jar "RPG Maker MV Decrypter.jar" encrypt [(Req.) target path] [output path] [to MV (true|false)] [key (auto|keyValue)] [headerLen (number)] [hsignature] [hversion] [hremain]`
  - Example 1: `java -jar "RPG Maker MV Decrypter.jar" encrypt "C:\my rpg mv game\"`
    - Encrypts all resource Files in `C:\my rpg mv game\` to the MV-Format with the detected Key
  - Example 2: `java -jar "RPG Maker MV Decrypter.jar" encrypt "C:\my rpg mz game\" output false`
    - Encrypts all resource File in `C:\my rpg mz game\` to the output dir of this program in the MZ-Format
  - Example 3: `java -jar "RPG Maker MV Decrypter.jar" encrypt "C:\my rpg mz game\" output false d41d8cd98f00b204e9800998ecf8427e`
    - Same as Example 2, just using a manual given Key: `d41d8cd98f00b204e9800998ecf8427e`
  - You can do more things with `encrypt` please use the help to see more details
- __key__ - Detects and displays the Key (Help: `java -jar "RPG Maker MV Decrypter.jar" key help`)
  - This Script shows the Key of the given directory/project
  - Simple-Syntax: `java -jar "RPG Maker MV Decrypter.jar" key [(Req.) target path] [ask before image keysearch (true|false)]`
  - Full-Syntax: `java -jar "RPG Maker MV Decrypter.jar" key [(Req.) target path] [ask before image keysearch (true|false)] [headerLen (number)]`
  - Example 1: `java -jar "RPG Maker MV Decrypter.jar" key "C:\my rpg mv game\"`
    - Will do a Key-Search in `C:\my rpg mv game\` and shows the Key if found, if not found it will ask if you want to generate it out of encrypted images
  - Example 2: `java -jar "RPG Maker MV Decrypter.jar" key "C:\my rpg mv game\" false`
    - Same as Example 1, just don't ask if search on images, it will always do automatically
- __update__ - Updates the Program (Help: `java -jar "RPG Maker MV Decrypter.jar" update help`)
  - This Script updates the Program
  - Syntax: `java -jar "RPG Maker MV Decrypter.jar" update [(Optional) Sub-Command]`
  - Program Update Command: `java -jar "RPG Maker MV Decrypter.jar" update`
  - Open "What's new" in your Default-Browser: `java -jar "RPG Maker MV Decrypter.jar" update whatsnew` 
