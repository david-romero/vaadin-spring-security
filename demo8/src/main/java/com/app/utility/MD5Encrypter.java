/**
 * 
 */
package com.app.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;


/**
 * @author David Romero Alcaide
 * 
 */
public class MD5Encrypter {
	
	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(MD5Encrypter.class);
	
	public static String encrypterMD5Hash(String dataNotEncrypted) {
		String md5 = null;
		String dataNotEncryptedCopy = dataNotEncrypted;
		if (null == dataNotEncrypted) {
			return null;
		}

		String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";

		dataNotEncryptedCopy += salt;

		try {
			MessageDigest digest = createMessageDigedtObjectForMD5();

			updateInputStringInMessageDigest(dataNotEncryptedCopy, digest);

			md5 = convertsMessageDigestValueInBase16(digest);

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return md5;
	}

	/**
	 * @param digest
	 * @return
	 */
	private static String convertsMessageDigestValueInBase16(
			MessageDigest digest) {
		String md5;
		// Converts message digest value in base 16 (hex)
		md5 = new BigInteger(1, digest.digest()).toString(16);
		return md5;
	}

	/**
	 * @param dataNotEncrypted
	 * @param digest
	 */
	private static void updateInputStringInMessageDigest(
			String dataNotEncrypted, MessageDigest digest) {
		// Update input string in message digest
		digest.update(dataNotEncrypted.getBytes(), 0, dataNotEncrypted.length());
	}

	/**
	 * Create MessageDigest object for MD5
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static MessageDigest createMessageDigedtObjectForMD5()
			throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}

}
