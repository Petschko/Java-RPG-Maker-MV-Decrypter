package org.petschko.rpgmakermv.decrypt;

import org.json.JSONException;
import org.json.JSONObject;
import org.petschko.lib.File;
import org.petschko.rpgmakermv.decrypt.cmd.CMD;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

/**
 * @author Peter Dragicevic
 */
public class Decrypter {
	private static final String PNG_HEADER = "89504E470D0A1A0A0000000D49484452";
	public static final int DEFAULT_HEADER_LEN = 16;
	public static final String DEFAULT_SIGNATURE = "5250474d56000000";
	public static final String DEFAULT_VERSION = "000301";
	public static final String DEFAULT_REMAIN = "0000000000";

	private static byte[] pngHeaderBytes = null;

	private String decryptCode = null;
	private String[] realDecryptCode = null;
	private byte[] rpgHeaderBytes = null;
	private int headerLen;
	private String signature;
	private String version;
	private String remain;
	private boolean ignoreFakeHeader = false;

	/**
	 * Creates a new Decrypter instance
	 */
	public Decrypter() {
		this.setDefaultValues();
	}

	/**
	 * Creates a new Decrypter instance with a Decryption Code
	 *
	 * @param decryptCode - Decryption-Code
	 */
	public Decrypter(String decryptCode) {
		this.setDefaultValues();
		this.setDecryptCode(decryptCode);
	}

	/**
	 * Return the Decrypt-Code
	 *
	 * @return DecryptCode or null if not set
	 */
	public String getDecryptCode() {
		return decryptCode;
	}

	/**
	 * Sets the Decrypt-Code
	 *
	 * @param decryptCode - Decrypt-Code
	 */
	public void setDecryptCode(String decryptCode) {
		this.decryptCode = decryptCode;
	}

	/**
	 * Returns the Real Decrypt-Code as Array
	 *
	 * @return Real Decrypt-Code as Array
	 */
	private String[] getRealDecryptCode() {
		if(this.realDecryptCode == null)
			this.calcRealDecryptionCode();

		return realDecryptCode;
	}

	/**
	 * Sets the Real Decrypt-Code as Array
	 *
	 * @param realDecryptCode - Real Decrypt-Code as Array
	 */
	private void setRealDecryptCode(String[] realDecryptCode) {
		this.realDecryptCode = realDecryptCode;
	}

	/**
	 * Returns the RPG-Header-Bytes (aka fake header)
	 *
	 * @return - RPG-Header-Bytes
	 */
	private byte[] getRpgHeaderBytes() {
		if(this.rpgHeaderBytes == null)
			this.generateRpgHeaderBytes();

		return rpgHeaderBytes;
	}

	/**
	 * Sets RPG-Header Bytes
	 *
	 * @param rpgHeaderBytes - RPG-Header Bytes
	 */
	private void setRpgHeaderBytes(byte[] rpgHeaderBytes) {
		this.rpgHeaderBytes = rpgHeaderBytes;
	}

	/**
	 * Returns the Byte-Length of the File-Header
	 *
	 * @return - File-Header Length in Bytes
	 */
	public int getHeaderLen() {
		return headerLen;
	}

	/**
	 * Sets the File-Header Length in Bytes
	 *
	 * @param headerLen - File-Header Length in Bytes
	 */
	public void setHeaderLen(int headerLen) {
		this.headerLen = headerLen;
	}

	/**
	 * Returns the Signature
	 *
	 * @return - Signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Sets the Signature
	 *
	 * @param signature - Signature
	 */
	public void setSignature(String signature) {
		if(signature == null) {
			Exception e = new Exception("signature can't be null!");
			e.printStackTrace();
			return;
		}

		this.signature = signature;
	}

	/**
	 * Returns the Version
	 *
	 * @return - Version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the Version
	 *
	 * @param version - Version
	 */
	public void setVersion(String version) {
		if(version == null) {
			Exception e = new Exception("version can't be null!");
			e.printStackTrace();
			return;
		}

		this.version = version;
	}

	/**
	 * Returns Remain
	 *
	 * @return - Remain
	 */
	public String getRemain() {
		return remain;
	}

	/**
	 * Sets Remain
	 *
	 * @param remain - Remain
	 */
	public void setRemain(String remain) {
		if(remain == null) {
			Exception e = new Exception("remain can't be null!");
			e.printStackTrace();
			return;
		}

		this.remain = remain;
	}

	/**
	 * Returns if Fake-Header can be ignored
	 *
	 * @return - true if Fake-Header should be ignored else false
	 */
	boolean isIgnoreFakeHeader() {
		return ignoreFakeHeader;
	}

	/**
	 * Set if Fake-Header should be ignored
	 *
	 * @param ignoreFakeHeader - true if Fake-Header can be ignored else false
	 */
	public void setIgnoreFakeHeader(boolean ignoreFakeHeader) {
		this.ignoreFakeHeader = ignoreFakeHeader;
	}

	/**
	 * Sets default values
	 */
	private void setDefaultValues() {
		this.setHeaderLen(DEFAULT_HEADER_LEN);
		this.setSignature(DEFAULT_SIGNATURE);
		this.setVersion(DEFAULT_VERSION);
		this.setRemain(DEFAULT_REMAIN);
	}

	/**
	 * Reads the Decrypt-Code into an Array with 2 Paired Strings
	 *
	 * @throws NullPointerException - Decrypt-Code is null
	 */
	private void calcRealDecryptionCode() throws NullPointerException {
		if(this.getDecryptCode() == null)
			throw new NullPointerException("DecryptCode");

		String[] decryptArray = this.getDecryptCode().split("(?<=\\G.{2})");
		ArrayList<String> verifiedDecryptArray = new ArrayList<>();

		// Remove empty parts
		for(String aDecryptArray : decryptArray) {
			if(! aDecryptArray.equals(""))
				verifiedDecryptArray.add(aDecryptArray);
		}

		this.setRealDecryptCode(verifiedDecryptArray.toArray(new String[0]));
	}

	/**
	 * (Re-)Encrypts the File and adds the File-Header
	 *
	 * @param file - File which should be encrypted
	 * @param rpgMakerMv - Encrypt as RPG-Maker-MV File
	 * @throws Exception - Various Exceptions
	 */
	void encryptFile(File file, boolean rpgMakerMv) throws Exception {
		try {
			if(! file.load())
				throw new FileSystemException(file.getFilePath(), "", "Can't load File-Content...");
		} catch(Exception e) {
			e.printStackTrace();

			return;
		}

		// Check if all required external stuff is here
		if(this.getDecryptCode() == null)
			throw new NullPointerException("Encryption-Code is not set!");
		if(file.getContent() == null)
			throw new NullPointerException("File-Content is not loaded!");
		if(file.getContent().length < (this.getHeaderLen()))
			throw new Exception("File is to short (<" + (this.getHeaderLen()) + " Bytes)");

		// Get Content
		byte[] content = file.getContent();

		// Encrypt
		if(content.length > 0) {
			for(int i = 0; i < this.getHeaderLen(); i++) {
				content[i] = (byte) (content[i] ^ (byte) Integer.parseInt(this.getRealDecryptCode()[i], 16));
			}
		}

		// Add header and update File-Content
		file.setContent(this.addFileHeader(content));
		file.changeExtension(file.fakeExtByRealExt(rpgMakerMv));
	}

	/**
	 * Adds the RPG-MV/MZ File-Header to the content
	 *
	 * @param content - Content where the header should be added
	 * @return - Header before content
	 */
	private byte[] addFileHeader(byte[] content) {
		byte[] header = this.getRpgHeaderBytes();

		ByteBuffer buffer = ByteBuffer.wrap(new byte[header.length + content.length]);
		buffer.put(header);
		buffer.put(content);

		return buffer.array();
	}

	/**
	 * Decrypts the File (Header) and removes the Encryption-Header
	 *
	 * @param file - Encrypted File
	 * @param restorePictures - Restore Pictures without the Key
	 * @throws Exception - Various Exceptions
	 */
	public void decryptFile(File file, boolean restorePictures) throws Exception {
		if(restorePictures && (! file.isImage() || ! file.isFileEncryptedExt()))
			return;

		try {
			if(! file.load())
				throw new FileSystemException(file.getFilePath(), "", "Can't load File-Content...");
		} catch(Exception e) {
			e.printStackTrace();

			return;
		}

		// Check if all required external stuff is here
		if(this.getDecryptCode() == null && ! restorePictures)
			throw new NullPointerException("Decryption-Code is not set!");
		if(file.getContent() == null)
			throw new NullPointerException("File-Content is not loaded!");
		if(file.getContent().length < (this.getHeaderLen() * 2))
			throw new Exception("File is to short (<" + (this.getHeaderLen() * 2) + " Bytes)");

		// Get Content
		byte[] content = file.getContent();

		// Check Header
		if(! this.isIgnoreFakeHeader())
			if(! this.checkFakeHeader(content))
				throw new Exception("Header is Invalid!");

		// Remove Fake-Header from rest
		content = Decrypter.getByteArray(content, this.getHeaderLen());

		if(content.length > 0) {
			for(int i = 0; i < this.getHeaderLen(); i++) {
				if(restorePictures) // Restore Pictures
					content[i] = getPNGHeaderByteArray()[i];
				else // Decrypt Real-Header & First part of the Content
					content[i] = (byte) (content[i] ^ (byte) Integer.parseInt(this.getRealDecryptCode()[i], 16));
			}
		}

		// Update File-Content
		file.setContent(content);
		file.changeExtension(file.realExtByFakeExt());
	}

	/**
	 * Check if the Fake-Header is valid
	 *
	 * @param content - File-Content as Byte-Array
	 * @return - true if the header is valid else false
	 */
	boolean checkFakeHeader(byte[] content) {
		byte[] header = Decrypter.getByteArray(content, 0, this.getHeaderLen());
		byte[] refBytes = this.getRpgHeaderBytes();

		// Verify header (Check if its an encrypted file)
		for(int i = 0; i < this.getHeaderLen(); i++) {
			if(refBytes[i] != header[i])
				return false;
		}

		return true;
	}

	/**
	 * Detect the Decryption-Code from the given Json-File
	 *
	 * @param file - JSON-File with Decryption-Key
	 * @param keyName - Key-Name of the Decryption-Key
	 * @throws JSONException - Key not Found Exception
	 * @throws NullPointerException - System-File is null
	 */
	public void detectEncryptionKeyFromJson(File file, String keyName) throws JSONException, NullPointerException {
		try {
			if(! file.load())
				throw new FileSystemException(file.getFilePath(), "", "Can't load File-Content...");
		} catch(NullPointerException nullEx) {
			throw new NullPointerException("System-File is not set!");
		} catch(Exception e) {
			e.printStackTrace();

			return;
		}

		JSONObject jsonObj;
		String key;

		try {
			String fileContentAsString = new String(file.getContent(), StandardCharsets.UTF_8);
			jsonObj = new JSONObject(fileContentAsString);
		} catch(Exception e) {
			e.printStackTrace();

			return;
		}

		key = jsonObj.getString(keyName);

		App.showMessage("Key found :)!", CMD.STATUS_OK);
		this.setDecryptCode(key);
	}

	/**
	 * Detects the Key from the given Encrypted-Image-File
	 *
	 * @param file - Encrypted-Image-File
	 */
	public void detectEncryptionKeyFromImage(File file) throws Exception {
		// Only encrypted images
		if(! file.isImage() || ! file.isFileEncryptedExt())
			return;

		try {
			if(! file.load())
				throw new FileSystemException(file.getFilePath(), "", "Can't load File-Content...");
		} catch(Exception e) {
			e.printStackTrace();

			return;
		}

		// Check if all required external stuff is here
		if(file.getContent() == null)
			throw new NullPointerException("File-Content is not loaded!");
		if(file.getContent().length < (this.getHeaderLen() * 2))
			throw new Exception("File is to short (<" + (this.getHeaderLen() * 2) + " Bytes)");

		// Get Content
		byte[] content = file.getContent();
		byte[] keyBytes = new byte[this.getHeaderLen()];

		// Check Header
		if(! this.isIgnoreFakeHeader())
			if(! this.checkFakeHeader(content))
				throw new Exception("Header is Invalid!");

		// Remove Fake-Header from rest
		content = Decrypter.getByteArray(content, this.getHeaderLen());

		if(content.length > 0) {
			for(int i = 0; i < this.getHeaderLen(); i++) {
				keyBytes[i] = (byte) (content[i] ^ this.getPNGHeaderByteArray()[i]);
			}
		}

		App.showMessage("Key found :) - Inside Image!", CMD.STATUS_OK);
		this.setDecryptCode(bytesToHex(keyBytes));
	}

	/**
	 * Returns the PNG-Header Byte Array
	 *
	 * @return PNG-Header Byte Array
	 */
	private byte[] getPNGHeaderByteArray() {
		if(pngHeaderBytes != null)
			return pngHeaderBytes;

		String[] pngHeaderArr = PNG_HEADER.split("(?<=\\G.{2})");
		byte[] pngHeaderBytesArray = new byte[this.getHeaderLen()];

		for(int i = 0; i < this.getHeaderLen(); i++) {
			pngHeaderBytesArray[i] = (byte) Integer.parseInt(pngHeaderArr[i], 16);
		}
		pngHeaderBytes = pngHeaderBytesArray;

		return pngHeaderBytes;
	}

	/**
	 * Generates the RPG-Header byte-array (aka fake-Header)
	 */
	private void generateRpgHeaderBytes() {
		byte[] refBytes = new byte[this.getHeaderLen()];
		String refStr = this.getSignature() + this.getVersion() + this.getRemain();

		// Generate reference bytes
		for(int i = 0; i < this.getHeaderLen(); i++) {
			int subStrStart = i * 2;
			refBytes[i] = (byte) Integer.parseInt(refStr.substring(subStrStart, subStrStart + 2), 16);
		}

		this.rpgHeaderBytes = refBytes;
	}

	/**
	 * Get a new Byte-Array with given start pos and length
	 *
	 * @param byteArray - Byte-Array where to extract a new Byte-Array
	 * @param startPos - Start-Position on the Byte-Array (0 is first pos)
	 * @param length - Length of the new Array (Values below 0 means to Old-Array end)
	 * @return - New Byte-Array
	 */
	private static byte[] getByteArray(byte[] byteArray, int startPos, int length) {
		// Don't allow start-values below 0
		if(startPos < 0)
			startPos = 0;

		// Check if length is to below 0 (to end of array)
		if(length < 0)
			length = byteArray.length - startPos;

		byte[] newByteArray = new byte[length];
		int n = 0;

		for(int i = startPos; i < (startPos + length); i++) {
			// Check if byte array is on the last pos and return shorter byte array if
			if(byteArray.length <= i)
				return getByteArray(newByteArray, 0, n);

			newByteArray[n] = byteArray[i];
			n++;
		}

		return newByteArray;
	}

	/**
	 * Get a new Byte-Array from the given start pos to the end of the array
	 *
	 * @param byteArray - Byte-Array where to extract a new Byte-Array
	 * @param startPos - Start-Position on the Byte-Array (0 is first pos)
	 * @return - New Byte-Array
	 */
	private static byte[] getByteArray(byte[] byteArray, int startPos) {
		return getByteArray(byteArray, startPos, -1);
	}

	/**
	 * Converts bytes arrays to a hex string
	 *
	 * @param bytes - Byte-Array
	 * @return - Hex-String
	 */
	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();

		for(byte b : bytes)
			sb.append(String.format("%02x", b));

		return sb.toString();
	}
}
