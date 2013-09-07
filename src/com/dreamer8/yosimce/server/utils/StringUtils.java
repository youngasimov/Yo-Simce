/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server.utils;

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
		rut = rut.replaceAll("[,.;_-]", "");
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
		rut = rut.replaceAll("[,.;_-]", "");
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
}
