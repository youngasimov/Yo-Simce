package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class FormActividadPlace extends SimcePlace {

	private int idActividad;
	
	public FormActividadPlace(){
		super();
	}
	
	public FormActividadPlace(int aplicacionId, int nivelId, int tipoId, int idActividad){
		super(aplicacionId, nivelId, tipoId);
		this.idActividad = idActividad;
	}
	
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	@Prefix("formularioactividad")
	public static class Tokenizer implements PlaceTokenizer<FormActividadPlace>{

		@Override
		public FormActividadPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			FormActividadPlace fap = new FormActividadPlace();
			fap.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			fap.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			fap.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			fap.setIdActividad((kvs.containsKey("act")) ? Integer.parseInt(kvs
					.get("act")) : -1);
			return fap;
		}

		@Override
		public String getToken(FormActividadPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getIdActividad() != -1){kvs.put("act", place.getIdActividad() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
