package com.dreamer8.yosimce.client.ui;

public class ViewUtils {
	
	public static String limitarString(String t, int caracteres){
		if(t == null){
			return "";
		}else if(t.length()>caracteres){
			StringBuilder b = new StringBuilder();
			b.append(t.substring(0,(caracteres-3)/2));
			b.append("...");
			b.append(t.substring(t.length()-(caracteres-3)/2));
			return b.toString();
		}else{
			return t;
		}
	}

}
