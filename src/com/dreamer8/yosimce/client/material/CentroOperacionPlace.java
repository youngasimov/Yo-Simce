package com.dreamer8.yosimce.client.material;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CentroOperacionPlace extends SimcePlace {
	
	private int centroId;
	
	public CentroOperacionPlace(){
		super(-1,-1,-1);
		centroId = -1;
	}
	
	public CentroOperacionPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
		centroId = -1;
	}
	
	public int getCentroId() {
		return centroId;
	}

	public void setCentroId(int centroId) {
		this.centroId = centroId;
	}



	@Prefix("centrooperacion")
	public static class Tokenizer implements PlaceTokenizer<CentroOperacionPlace>{

		@Override
		public CentroOperacionPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			CentroOperacionPlace pp = new CentroOperacionPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			pp.setCentroId((kvs.containsKey("co")) ? Integer.parseInt(kvs
					.get("co")) : -1);
			return pp;
		}

		@Override
		public String getToken(CentroOperacionPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			kvs.put("co", place.getCentroId() + "");
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
