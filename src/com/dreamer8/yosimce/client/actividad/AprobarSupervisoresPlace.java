package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AprobarSupervisoresPlace extends SimcePlace {

	public AprobarSupervisoresPlace(){
		super();
	}
	
	public AprobarSupervisoresPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
	}
	
	@Prefix("aprobarsupervisores")
	public static class Tokenizer implements PlaceTokenizer<AprobarSupervisoresPlace>{

		@Override
		public AprobarSupervisoresPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			AprobarSupervisoresPlace fap = new AprobarSupervisoresPlace();
			fap.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			fap.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			fap.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			return fap;
		}

		@Override
		public String getToken(AprobarSupervisoresPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
