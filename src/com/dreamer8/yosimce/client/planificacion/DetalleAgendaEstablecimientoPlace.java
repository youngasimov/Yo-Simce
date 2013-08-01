package com.dreamer8.yosimce.client.planificacion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class DetalleAgendaEstablecimientoPlace extends SimcePlace {

	private int establecimientoId;
	
	public DetalleAgendaEstablecimientoPlace(){
		super();
		establecimientoId = -1;
	}
	
	public DetalleAgendaEstablecimientoPlace(int aplicacionId, int nivelId, int tipoId,int establecimientoId) {
		super(aplicacionId, nivelId, tipoId);
		this.establecimientoId = establecimientoId;
	}

	public int getEstablecimientoId() {
		return establecimientoId;
	}

	public void setEstablecimientoId(int establecimientoId) {
		this.establecimientoId = establecimientoId;
	}
	
	@Prefix("detalleagenda")
	public static class Tokenizer implements PlaceTokenizer<DetalleAgendaEstablecimientoPlace>{

		@Override
		public DetalleAgendaEstablecimientoPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			DetalleAgendaEstablecimientoPlace pp = new DetalleAgendaEstablecimientoPlace();
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
		public String getToken(DetalleAgendaEstablecimientoPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getEstablecimientoId()>-1){kvs.put("e", place.getEstablecimientoId() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
