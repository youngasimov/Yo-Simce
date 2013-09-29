package com.dreamer8.yosimce.client.administracion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ReportesPlace extends SimcePlace {

	public ReportesPlace(){
		super();
	}
	
	public ReportesPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
	}
	
	
	
	@Prefix("reportes")
	public static class Tokenizer implements PlaceTokenizer<ReportesPlace>{

		@Override
		public ReportesPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			ReportesPlace aup = new ReportesPlace();
			aup.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			aup.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			aup.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			return aup;
		}

		@Override
		public String getToken(ReportesPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}