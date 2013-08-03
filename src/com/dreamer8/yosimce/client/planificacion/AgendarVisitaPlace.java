package com.dreamer8.yosimce.client.planificacion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AgendarVisitaPlace extends SimcePlace {

	private int cursoId;
	
	public AgendarVisitaPlace(){
		super();
		cursoId = -1;
	}
	
	public AgendarVisitaPlace(int aplicacionId, int nivelId, int tipoId,int cursoId) {
		super(aplicacionId, nivelId, tipoId);
		this.cursoId = cursoId;
	}

	public int getCursoId() {
		return cursoId;
	}

	public void setCursoId(int cursoId) {
		this.cursoId = cursoId;
	}
	
	@Prefix("agendarvisita")
	public static class Tokenizer implements PlaceTokenizer<AgendarVisitaPlace>{

		@Override
		public AgendarVisitaPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			AgendarVisitaPlace pp = new AgendarVisitaPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			pp.setCursoId((kvs.containsKey("c")) ? Integer.parseInt(kvs
					.get("c")) : -1);
			return pp;
		}

		@Override
		public String getToken(AgendarVisitaPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getCursoId()>=0){kvs.put("c", place.getCursoId() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
