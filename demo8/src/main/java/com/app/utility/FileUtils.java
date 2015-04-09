/**
 * FileUtils.java
 * 24/06/2014 20:46:12
 * Copyright David Romero Alcaide
 * com.app.utility
 */
package com.app.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

/**
 * @author David Romero Alcaide
 * 
 */
public class FileUtils {
	public static List<String[]> convertCSVtoList(MultipartFile file)
			throws IOException {
		List<String[]> listaAlumnos = Lists.newArrayList();
		InputStream inputStream;

		inputStream = file.getInputStream();

		InputStreamReader iSR = new InputStreamReader(inputStream);

		BufferedReader bufferedReader = new BufferedReader(iSR);

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			String[] alumno = line.split(",");
			listaAlumnos.add(alumno);
		}
		iSR.close();
		bufferedReader.close();

		return listaAlumnos;
	}
}
