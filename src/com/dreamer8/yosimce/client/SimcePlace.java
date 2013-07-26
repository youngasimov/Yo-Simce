package com.dreamer8.yosimce.client;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SimcePlace extends Place {
	
	public static final String APPID = "a";
	public static final String NIVELID = "n";
	public static final String TIPOID = "t";
	
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
	
	public void setAplicacionId(int aplicacionId) {
		this.aplicacionId = aplicacionId;
	}

	public void setNivelId(int nivelId) {
		this.nivelId = nivelId;
	}

	public void setTipoId(int tipoId) {
		this.tipoId = tipoId;
	}

	@Prefix("app")
	public static class Tokenizer implements PlaceTokenizer<SimcePlace>{

		@Override
		public SimcePlace getPlace(String token) {
			HashMap<String,String> t = TokenUtils.getTokenValues(token);
			SimcePlace sp = new SimcePlace();
			if(t.containsKey(APPID)){
				sp.setAplicacionId(Integer.parseInt(t.get(APPID)));
			}
			if(t.containsKey(NIVELID)){
				sp.setNivelId(Integer.parseInt(t.get(NIVELID)));
			}
			if(t.containsKey(TIPOID)){
				sp.setTipoId(Integer.parseInt(t.get(TIPOID)));
			}
			return sp;
		}

		@Override
		public String getToken(SimcePlace place) {
			HashMap<String,String> t = new HashMap<String,String>();
			t.put(APPID, place.getAplicacionId()+"");
			t.put(NIVELID, place.getNivelId()+"");
			t.put(TIPOID, place.getTipoId()+"");
			return TokenUtils.createKeyValuesToken(t);
		}
	}
	
	public static class TokenUtils{
		public static HashMap<String,String> getTokenValues(String token){
			HashMap<String,String> values = new HashMap<String,String>(); 
			String keyValues[] = token.split("&");
			for(String keyValue:keyValues){
				String[] x = keyValue.split("=");
				if(x.length == 2){
					values.put(x[0], x[1]);
				}
			}
			return values;
		}
		
		public static String createKeyValuesToken(HashMap<String,String> keyValues){
			StringBuilder sb = new StringBuilder();
			
			for(Entry<String,String> keyValue:keyValues.entrySet()){
				sb.append(keyValue.getKey());
				sb.append("=");
				sb.append(keyValue.getValue());
				sb.append("&");
			}
			sb.deleteCharAt(sb.lastIndexOf("&"));
			return sb.toString();
		}
	}
}
