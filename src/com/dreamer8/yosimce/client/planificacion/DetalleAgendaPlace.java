package com.dreamer8.yosimce.client.planificacion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class DetalleAgendaPlace extends SimcePlace {

	private int cursoId;
	
	public DetalleAgendaPlace(){
		super();
		cursoId = -1;
	}
	
	public DetalleAgendaPlace(int aplicacionId, int nivelId, int tipoId,int cursoId) {
		super(aplicacionId, nivelId, tipoId);
		this.cursoId = cursoId;
	}

	public int getCursoId() {
		return cursoId;
	}

	public void setCursoId(int cursoId) {
		this.cursoId = cursoId;
	}
	
	@Prefix("detalleagenda")
	public static class Tokenizer implements PlaceTokenizer<DetalleAgendaPlace>{

		@Override
		public DetalleAgendaPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			DetalleAgendaPlace pp = new DetalleAgendaPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			pp.setCursoId((kvs.containsKey("c")) ? Integer.parseInt(kvs
					.get("e")) : -1);
			return pp;
		}

		@Override
		public String getToken(DetalleAgendaPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getCursoId()>-1){kvs.put("c", place.getCursoId() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
