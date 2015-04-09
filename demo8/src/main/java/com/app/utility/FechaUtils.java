/**
 * FechaUtils.java
 * 20/01/2014 19:25:50
 * Copyright David Romero Alcaide
 * com.app.utility
 */
package com.app.utility;

import java.util.Calendar;
import java.util.Date;

/**
 * @author David Romero Alcaide
 * 
 */
public class FechaUtils {

	public static int[] getFecha(Date fechaDate) {
		int[] fecha = new int[3];
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaDate);
		fecha[0] = cal.get(Calendar.DAY_OF_MONTH);
		// Los meses empiezan por 0
		fecha[1] = cal.get(Calendar.MONTH) + 1; 
		fecha[2] = cal.get(Calendar.YEAR);
		return fecha;
	}

}
