/* HashPassword.java
 * 
 */

package com.app.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class HashPassword {

	private static final Logger LOGGER = Logger.getLogger(HashPassword.class);
	
	/**
	 * Empty constructor
	 */
	public HashPassword() {
		super();
	}

	/**
	 * utilidad para generar hash
	 * 
	 * @author David
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Md5PasswordEncoder encoder;
		InputStreamReader stream;
		BufferedReader reader;
		String line, hash;
		
		encoder = new Md5PasswordEncoder();
		stream = new InputStreamReader(System.in);
		reader = new BufferedReader(stream);
		LOGGER.debug("Enter passwords to be hashed or <ENTER> to quit");
		LOGGER.debug("Enter passwords to be hashed or <ENTER> to quit");

		line = reader.readLine();
		if (line != null) {
			while (line != null && !line.isEmpty()) {
				hash = encoder.encodePassword(line, null);

				LOGGER.debug(hash);
				line = reader.readLine();
			}
		}
	}

}
