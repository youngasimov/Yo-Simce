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
}
