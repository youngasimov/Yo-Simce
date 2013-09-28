/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * http://comments.gmane.org/gmane.org.google.gwt/54307
 * http://ha.ckers.org/xss.html
 */
package com.dreamer8.yosimce.server.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class SecurityFilter {

    public SecurityFilter() {
    }

    public static String xssClean(String str) {
	if(str == null){
	    return null;
	}
	try {
	    str = removeInvisibleChar(str);
	    str = URLDecoder.decode(str, "UTF-8");
	    str = convertAttr(str);
	    str = escapeString(str);
	} catch (UnsupportedEncodingException ex) {
	    Logger.getLogger(SecurityFilter.class.getName()).log(Level.SEVERE, null, ex);
	}
	return str;
    }

    public static String escapeString(Object object) {
	if(object == null){
	    return null;
	}
	String str = object.toString();

	if(object.getClass() != String.class){
	    str = str.replaceAll("[^0-9.,-]", "");
	}
	
	return str.replaceAll("\\\'", "\\\'\\\'").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\\"", "\\\\\\\"");
    }

    public static String escapeLikeString(Object object,String escapeChar){
	if(object == null){
	    return null;
	}
	String str = SecurityFilter.escapeString(object);
	str = str.replaceAll("%", escapeChar + "%").replaceAll("_", escapeChar + "_");
	return str;
    }

    public static String stripEscapes(String str){
	if(str == null){
	    return null;
	}
	return str.replaceAll("\\\"", "\"").replaceAll("\\\'", "\'").replaceAll("\\\\\\\\", "\\\\");
    }

    public static String removeInvisibleChar(String str) {
	String[] invChars = {
	    "%0[0-8bcef]", // url encoded 00-08, 11, 12, 14, 15
	    "%1[0-9a-f]", // url encoded 16-31
	    "[\u0000-\u0008]", // 00-08
	    "\u000b",
	    "\u000c", // 11, 12
	    "[\u000e-\u001f]" // 14-31
	};
	for (String invC : invChars) {
	    str = str.replaceAll(invC, "");
	}
	return str;
    }

    public static String convertAttr(String str){
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	//str = str.replaceAll("\\", "\\\\");
	return str;
    }
}
