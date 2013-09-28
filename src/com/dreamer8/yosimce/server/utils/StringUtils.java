/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author jorge
 */
public class StringUtils {

	public static String getHourAndMinute(int hour, int minute, String separator) {
		return forceTwoDigits(hour) + separator + forceTwoDigits(minute);
	}

	public static String forceTwoDigits(int num) {
		String str = null;
		if (num < 10) {
			str = "0" + num;
		} else {
			str = "" + num;
		}
		return str;
	}

	public static String limpiarString(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceAll("[\\n\\r]", " ").replaceAll("[;,]", ".");
	}

	public static String getDateString(Date date) {
		if (date == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		return forceTwoDigits(day) + "/" + forceTwoDigits(month + 1) + "/"
				+ year + " " + forceTwoDigits(hour) + ":" + forceTwoDigits(min)
				+ ":" + forceTwoDigits(sec);
	}

	public static Date getDate(String dateString) {
		if (dateString == null
				|| !dateString
						.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4} [0-9]{1,2}:[0-9]{1,2}:*[0-9]{0,2}")) {
			return null;
		}
		String[] dateParts = dateString.split(" ");
		String[] date = dateParts[0].split("/");
		String[] time = dateParts[1].split(":");
		Calendar calendar = Calendar.getInstance();
		Integer sec = (time.length == 3) ? Integer.valueOf(time[2]) : 0;
		calendar.set(Integer.valueOf(date[2]), Integer.valueOf(date[1]) - 1,
				Integer.valueOf(date[0]), Integer.valueOf(time[0]),
				Integer.valueOf(time[1]), sec);
		return calendar.getTime();
	}

	public static Boolean isRut(String rut) {
		return rut
				.matches("([0-9]){1,3}[,.;_-]*([0-9]){1,3}[,.;_-]*([0-9]){0,3}[,.;_-]*([0-9kK]){0,1}");
	}

	public static String stripRut(String rut) {
		if (!isRut(rut)) {
			return rut;
		}
		rut = rut.replaceAll("[,.;_-][0-9kK]$", "").replaceAll("[,.;_-]", "");
		if (Integer.parseInt(rut) > 19999999) {
			rut = rut.replaceAll("[0-9kK]$", "");
		}
		return rut;
	}

	public static String formatRut(String rut) {
		if (!isRut(rut)) {
			return rut;
		}
		rut = rut.replaceAll("[,.;_-]", "").toUpperCase();
		String tmp = null;
		if (rut.length() < 8) {
			return rut;
		}
		if (rut.length() == 9) {
			tmp = rut.substring(0, 2) + ".";
			tmp += rut.substring(2, 5) + ".";
			tmp += rut.substring(5, 8) + "-";
			rut = tmp + rut.substring(8);
		} else if (rut.length() > 9) {
			tmp = rut.substring(0, 3) + ".";
			tmp += rut.substring(3, 6) + ".";
			tmp += rut.substring(6, 9) + "-";
			rut = tmp + rut.substring(9);
		} else {
			tmp = rut.substring(0, 1) + ".";
			tmp += rut.substring(1, 4) + ".";
			tmp += rut.substring(4, 7) + "-";
			rut = tmp + rut.substring(7);
		}
		return rut;
	}

	public static String formatRut(String rut, Boolean sobre10M) {
		if (!isRut(rut)) {
			return rut;
		}
		rut = rut.replaceAll("[,.;_-]", "").toUpperCase();
		String tmp = null;
		int end = 0;
		if (rut.length() >= 8) {
			return formatRut(rut);
		}
		if (sobre10M) {
			if (rut.length() > 2) {
				tmp = rut.substring(0, 2) + ".";
				end = 2;
				if (rut.length() > 5) {
					tmp += rut.substring(2, 5) + ".";
					end = 5;
					if (rut.length() > 8) {
						tmp += rut.substring(5, 8) + "-";
						end = 8;
					}
				}
				rut = (end != 0) ? tmp + rut.substring(end) : tmp;
			}
		} else {
			if (rut.length() > 1) {
				tmp = rut.substring(0, 1) + ".";
				end = 1;
				if (rut.length() > 4) {
					tmp += rut.substring(1, 4) + ".";
					end = 4;
					if (rut.length() > 7) {
						tmp += rut.substring(4, 7) + "-";
						end = 7;
					}
				}
				rut = (end != 0) ? tmp + rut.substring(end) : tmp;
			}
		}
		return rut;
	}

	public static Boolean isInt(String str) {
		return str.matches("^[0-9]+$");
	}

	public static String getExtension(String ruta) {
		Integer index = ruta.lastIndexOf(".");
		String extension = "";
		if (index > 0) {
			extension = ruta.substring(index);
		}
		return extension;
	}

	public static String getDatePathSafe(String date) {
		return date.replaceAll(" ", "_").replaceAll(":", "");
	}

	public static String formartFromUSAToISODate(String date) {
		if (!date.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,2}")) {
			return date;
		}
		String[] ds = date.split("/");
		String prefixYear = (Integer.valueOf(ds[2]) < 30) ? "20" : "19";
		return prefixYear + forceTwoDigits(Integer.valueOf(ds[2])) + "-"
				+ forceTwoDigits(Integer.valueOf(ds[0])) + "-"
				+ forceTwoDigits(Integer.valueOf(ds[1]));
	}

	public static String nombreInicialSegundo(String nombres) {
		if (nombres == null) {
			return "";
		}
		String[] noms = nombres.split(" ");
		if (noms.length < 2) {
			return nombres;
		}
		return noms[0] + " " + noms[1].substring(0, 1) + ".";
	}

	public static String getMes(int mes) {
		String[] meses = { "enero", "febrero", "marzo", "abril", "mayo",
				"junio", "julio", "agosto", "septiembre", "octubre",
				"noviembre", "diciembre" };
		return meses[mes % 12];
	}
}
