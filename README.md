# Petschko's RPG-Maker-MV/MZ File-Decrypter (Java-Version)

## What's that?
This Project is used to decrypt RPG-Maker-MV/MZ-Resource-Files that are encrypted with the Build-In-Encryption of the RPG-Maker.

This Project is also able to decrypt whole RPG-Directories. It's similar to [my previous Project](https://github.com/Petschko/RPG-Maker-MV-Decrypter) but more comfortable.

### Which Files can be decrypted with this Program?
You can decrypt the Build-In-Encrypted Files from the RPG Maker MV or MZ. They usually have the extension `.rpgmvp`, `.rpgmvm`, `.rpgmvo`, `.png_`, `.m4a_` or `.ogg_`.

## Requirements
- Java 8 or higher
- Any OS (Linux, Windows, IOS etc)

## Installation
- [Download this ZIP (!!Alpha!!) - Version: 0.4.0](https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/releases/download/v.0.4.0-alpha/RPG_Maker_MV_Decrypter_jar_0.4.0.zip) or Clone/Download the Project and make yourself a JAR-File
- Put it where ever you want (Don't forget to extract, if you downloaded the ZIP xP)

## How to use
### (Normal) With Graphical interface
- Just double click on the JAR then the Program should start *(If not try the BAT-File - Windows only)*
- Click on the Menu "File" then "Open"
  - Browse to the RPG-Maker MV Project which you want decrypt
  - Select the Main-Directory of the Project (The Folder where the Game.exe is) on click on "Choose Directory"
- Wait for a short moment =)
- If there are Files listed and a Decryption-Key is in the Text-Box you're ready to Decrypt
  - Then go into the Menu and click "Decrypt" -> "All Files"
  - After a short moment you're done =)
  - You can find the Files now within the "Output"-Directory, which is in the same Directory as this Program. *(If you not changed it already via "File" -> "Change Output-Directory...")*

- You can also Decrypt single/multiple Files - Select them in the Project-Files Tab after that you go to "Decrypter" -> "Selected Files"
- You can also check out the "Option"-Menu and check if the settings fit to you =)
- You can find this "Manual" also within the "Info"-Menu

### Start with CMD - For automation / the guys who like it :3

In some case it can be useful to do stuff via the commandline. For example you want to mod a game and make it easy for the user to mod it (like they just need to start a .bat/.sh file which does the stuff)

- To see the global help (with all sub-commands) type in `java -jar "RPG Maker MV Decrypter.jar" help`
- To see the detailed help of a specific command type in `java -jar "RPG Maker MV Decrypter.jar" [command] help`
  - Example (display decrypt help): `java -jar "RPG Maker MV Decrypter.jar" decrypt help`

For details use and examples how to use it see [CMD Instructions](cmd-instructions.md)

## Motivation behind this
As Art-Creator for the RPG-Maker by myself, it is sometimes hard to figure out, if somebody is using Resources from you *(and may violate the licence like giving no credit or using a Non-Commercial-Resource in a Commercial Game for example)*.

I don't have time to play through all the games (even if I want^^). So I just quick look at the files, but its only possible if the files are not encrypted...

Sad for me, more People use the build in Encryption from the RPG-Maker-MV, so that's why I wrote this Program - To get a quick look at the Files without playing the whole Game =) May some other Artists will find this useful too.

It can be also useful for Translators, eg when you want to make a Game available for different Regions. *(IMO you should ask the Creator of the Game first! - Sometimes it's not possible...)* 

I'm also interested in Encryption in general, so this was a good base to learn, since the MV-Encryption is very weak - But it's great that's simple, because it will not slow down weak machines! *(See below)*

### Why is the encryption of my Game useless in this case?
Sometimes there is a nice Picture that you may save for yourself. You would even do with without this Decrypter by making a Screenshot (or record the Sound) >.<

So you see there is no need for encryption in RPG-Maker Games... If someone wants to get the Files, he will able to get them.
You can't encrypt your Files 100%, because the Game has to decrypt them by itself, to display them... (Or Play) - And since you are able to play the Game offline, you have to provide the Decryption Method and the Key. 

### But somebody will steal my assets
Yeah, that may happen but as said before, you can't stop them anyway (Even without Decrypter). Even if you use a more powerful Encryption - it will just slow down your Game on weak Machines.
Everybody is allowed to save anything for (!)personal use **only**(!) - But you're **not allowed** to create a Game with them. Except it's a free Resource, then you have to follow the Licence of the Resource-Creator! 
So please DON'T steal stuff, as Artist I know how much time such stuff take, so DON'T do it! 

### Why as Java-Project? | Advantages
This Java-Decrypter is better for whole Directories. It was easier to do it with Java, because you are very limited with JavaScript. (File-Access / Saving etc)
Why Java? - Because I know this Language xP

**Advantages using this in comparison to my previous Project:**
- Works with whole Directories
  - It detects Encrypted Files by itself
  - It Auto-Saves & Rename Decrypted-Files for you!
  - It keeps the Directory-Structure
  - You can specify an Output-Directory
- It's able to Detect the Encryption File & Key by its own
- Less buggy and faster than my JavaScript Version, because I'm independent from Browsers and Charset-Problems...
- You can use Command-Line to if you want
- Will add a restore Project Function in the future
- You can en/disable the verification of the 16Byte Fake-Header
  - If you disable the verification, it will just cut away the Fake-Header aka the first 16 Bytes of the File *(Useful when Decrypter-Signature changes)*
  - If you enable the verification it will check if the Files-Header is correct (aka if it's a real Encrypted File)

## Be Fair!
You are **not allowed** to use the Decrypted Files (**if its not allowed by the origin Resource-Licence**).
Please **don't steal, reuse or share stuff in Public**! That's not the idea of this Program!

You can save them for **Personal-Use only**. If the origin Licence allow use you can use them of course, but please follow the Licences!

**If that's your Project** and you simply lost your Origin-Files, **you have the same rights**, to do stuff with them, **as before** =)

## Donate

If you like this Project and want to thank me may consider to [Donate](https://www.paypal.me/petschko). I appreciate anything, which helps the Project (Pull-Requests, Bug Report etc), these are more worth than Donations but I'm happy for every amount as well. ^.^

## Contact
- E-Mail me if you have questions (no bug reporting please): peter@petschko.org
  - You can also tell me you like it or not >.< Of course you can make improvement suggestions if you want
- Please report Bugs here on github.com use the "[Issue](https://github.com/Petschko/Java-RPG-Maker-MV-Decrypter/issues)"-Section on this Project
