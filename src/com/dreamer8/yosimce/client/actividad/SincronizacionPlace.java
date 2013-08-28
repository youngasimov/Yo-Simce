package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SincronizacionPlace extends SimcePlace {

	private int idCurso;
	
	public SincronizacionPlace(){
		super();
		idCurso = -1;
	}
	
	public SincronizacionPlace(int aplicacionId, int nivelId, int tipoId, int idCurso){
		super(aplicacionId, nivelId, tipoId);
		this.idCurso = idCurso;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	
	@Prefix("sincronizacion")
	public static class Tokenizer implements PlaceTokenizer<SincronizacionPlace>{

		@Override
		public SincronizacionPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			SincronizacionPlace fap = new SincronizacionPlace();
			fap.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			fap.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			fap.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			fap.setIdCurso((kvs.containsKey("c")) ? Integer.parseInt(kvs
					.get("c")) : -1);
			return fap;
		}

		@Override
		public String getToken(SincronizacionPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getIdCurso() != -1){kvs.put("c", place.getIdCurso() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}	
	}
}
