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

	public static String stripRut(String rut) {
        if (!rut.matches("([0-9]){1,3}[,.;_-]*([0-9]){1,3}[,.;_-]*([0-9]){1,3}([,.;_-][0-9kK]){0,1}")) {
            return rut;
        }
        return rut.replaceAll("[,.;_-][0-9kK]$", "").replaceAll("[,.;_-]", "");
    }
}
