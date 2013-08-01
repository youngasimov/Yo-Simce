package com.dreamer8.yosimce.client.general;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class DetalleEstablecimientoPlace extends SimcePlace {

	private int establecimientoId;
	
	public DetalleEstablecimientoPlace(){
		super();
		establecimientoId = -1;
	}
	
	public DetalleEstablecimientoPlace(int aplicacionId, int nivelId, int tipoId,int establecimientoId) {
		super(aplicacionId, nivelId, tipoId);
		this.establecimientoId = establecimientoId;
	}

	public int getEstablecimientoId() {
		return establecimientoId;
	}

	public void setEstablecimientoId(int establecimientoId) {
		this.establecimientoId = establecimientoId;
	}
	
	@Prefix("detalleestablecimiento")
	public static class Tokenizer implements PlaceTokenizer<DetalleEstablecimientoPlace>{

		@Override
		public DetalleEstablecimientoPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			DetalleEstablecimientoPlace pp = new DetalleEstablecimientoPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			pp.setEstablecimientoId((kvs.containsKey("e")) ? Integer.parseInt(kvs
					.get("e")) : -1);
			return pp;
		}

		@Override
		public String getToken(DetalleEstablecimientoPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getEstablecimientoId()>=0){kvs.put("e", place.getEstablecimientoId() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
