package com.dreamer8.yosimce.client.administracion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AdminEventosPlace extends SimcePlace {

	private int idEvento;
	
	public AdminEventosPlace(){
		super();
		idEvento = -1;
	}
	
	public AdminEventosPlace(int aplicacionId, int nivelId, int tipoId, int idEvento){
		super(aplicacionId, nivelId, tipoId);
		this.idEvento = idEvento;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	
	@Prefix("admineventos")
	public static class Tokenizer implements PlaceTokenizer<AdminEventosPlace>{

		@Override
		public AdminEventosPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			AdminEventosPlace aup = new AdminEventosPlace();
			aup.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			aup.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			aup.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			aup.setIdEvento((kvs.containsKey("e")) ? Integer.parseInt(kvs
					.get("e")) : -1);
			return aup;
		}

		@Override
		public String getToken(AdminEventosPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getIdEvento()>-1){kvs.put("e", place.getIdEvento() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
