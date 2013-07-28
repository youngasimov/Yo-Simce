package com.dreamer8.yosimce.client.planificacion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AgendamientosPlace extends SimcePlace {

	private String filtros;

	public AgendamientosPlace() {
		super();
		filtros = "";
	}

	public AgendamientosPlace(int aplicacionId, int nivelId, int tipoId,
			String filtros) {
		super(aplicacionId, nivelId, tipoId);
		this.filtros = filtros;
	}

	public String getFiltros() {
		return filtros;
	}

	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}

	@Prefix("agendamientos")
	public static class Tokenizer implements PlaceTokenizer<AgendamientosPlace> {

		@Override
		public AgendamientosPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			AgendamientosPlace pp = new AgendamientosPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			pp.setFiltros((kvs.containsKey("f"))?kvs.get("f"):"");
			return pp;
		}

		@Override
		public String getToken(AgendamientosPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getFiltros()!= null && !place.getFiltros().equals("")){
				kvs.put("f", place.getFiltros());
			}
			return TokenUtils.createKeyValuesToken(kvs);
		}

	}

}
