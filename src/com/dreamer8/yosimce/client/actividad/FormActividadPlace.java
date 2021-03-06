package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class FormActividadPlace extends SimcePlace {

	private int idCurso;
	
	public FormActividadPlace(){
		super();
		idCurso = -1;
	}
	
	public FormActividadPlace(int aplicacionId, int nivelId, int tipoId, int idActividad){
		super(aplicacionId, nivelId, tipoId);
		this.idCurso = idActividad;
	}
	
	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
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
			fap.setIdCurso((kvs.containsKey("c")) ? Integer.parseInt(kvs
					.get("c")) : -1);
			return fap;
		}

		@Override
		public String getToken(FormActividadPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getIdCurso() != -1){kvs.put("c", place.getIdCurso() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
