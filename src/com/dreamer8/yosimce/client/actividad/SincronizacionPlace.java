package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SincronizacionPlace extends SimcePlace {

	private int idSincronizacion;
	
	public SincronizacionPlace(){
		super();
	}
	
	public SincronizacionPlace(int aplicacionId, int nivelId, int tipoId, int idSincronizacion){
		super(aplicacionId, nivelId, tipoId);
		this.idSincronizacion = idSincronizacion;
	}

	public int getIdSincronizacion() {
		return idSincronizacion;
	}

	public void setIdSincronizacion(int idSincronizacion) {
		this.idSincronizacion = idSincronizacion;
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
			fap.setIdSincronizacion((kvs.containsKey("sinc")) ? Integer.parseInt(kvs
					.get("sinc")) : -1);
			return fap;
		}

		@Override
		public String getToken(SincronizacionPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getIdSincronizacion() != -1){kvs.put("sinc", place.getIdSincronizacion() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}	
	}
}
