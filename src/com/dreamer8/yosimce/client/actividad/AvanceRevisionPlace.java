package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AvanceRevisionPlace extends SimcePlace {

	public AvanceRevisionPlace() {
		super();
	}

	public AvanceRevisionPlace(int aplicacionId, int nivelId, int tipoId) {
		super(aplicacionId, nivelId, tipoId);
	}

	@Prefix("avance")
	public static class Tokenizer implements PlaceTokenizer<AvanceRevisionPlace>{

		@Override
		public AvanceRevisionPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			AvanceRevisionPlace fap = new AvanceRevisionPlace();
			fap.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			fap.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			fap.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			return fap;
		}

		@Override
		public String getToken(AvanceRevisionPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}

}
