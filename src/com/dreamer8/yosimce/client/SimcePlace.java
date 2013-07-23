package com.dreamer8.yosimce.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SimcePlace extends Place {
	private int aplicacionId;
	private int nivelId;
	private int tipoId;
	
	public SimcePlace(){
		super();
		this.aplicacionId = -1;
		this.nivelId = -1;
		this.tipoId = -1;
	}
	
	public SimcePlace(int aplicacionId, int nivelId, int tipoId) {
		super();
		this.aplicacionId = aplicacionId;
		this.nivelId = nivelId;
		this.tipoId = tipoId;
	}

	public int getAplicacionId() {
		return aplicacionId;
	}

	public int getNivelId() {
		return nivelId;
	}

	public int getTipoId() {
		return tipoId;
	}
	
	public static abstract class Tokenizer<T extends SimcePlace> implements PlaceTokenizer<T>{

		public int getValueId(String token, String value){
			if(!token.contains("?")){
				return -1;
			}else{
				String x[] = token.substring(token.indexOf("?")).split("&");
				for(String s:x){
					if(s.startsWith(value+"=")){
						String sa = s.substring(s.indexOf(value+"="));
						try{
							return decode(Integer.parseInt(sa));
						}catch(Exception e){
							return -1;
						}
					}
				}
				return -1;
			}
		}
		
		public String addValuesToToken(int aplicacionId, int nivelId, int tipoId){
			String token = "";
			if(aplicacionId > -1){
				token = token+"a="+encode(aplicacionId)+"&";
			}
			if(nivelId > -1){
				token = token+"n="+encode(nivelId)+"&";
			}
			if(tipoId>-1){
				token = token+"t="+encode(tipoId)+"&";
			}
			return token.substring(0,token.length()-1);
		}
		
		private int encode(int id){
			return (id+5)*12-3;
		}
		
		private int decode(int value){
			return (value+3)/12-5;
		}
		
	}
}
