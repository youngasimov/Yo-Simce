package com.dreamer8.yosimce.client.general;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CentroControlPlace extends SimcePlace {

	public CentroControlPlace(){
		super();
	}
	
	public CentroControlPlace(int aplicacionId, int nivelId, int tipoId) {
		super(aplicacionId, nivelId, tipoId);
	}
	
	@Prefix("control")
	public static class Tokenizer implements PlaceTokenizer<CentroControlPlace>{

		@Override
		public CentroControlPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			CentroControlPlace pp = new CentroControlPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			return pp;
		}

		@Override
		public String getToken(CentroControlPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
