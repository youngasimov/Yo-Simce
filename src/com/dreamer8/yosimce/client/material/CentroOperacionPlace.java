package com.dreamer8.yosimce.client.material;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CentroOperacionPlace extends SimcePlace {

	public static final int PANEL_HIDDEN=0;
	public static final int PANEL_ENTRADA=1;
	public static final int PANEL_SALIDA=2;
	public static final int PANEL_PREDESPACHO=3;
	
	private int panel;
	private int centroId;
	
	public CentroOperacionPlace(){
		super(-1,-1,-1);
		panel = PANEL_HIDDEN;
		centroId = -1;
	}
	
	public CentroOperacionPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
		panel = PANEL_HIDDEN;
		centroId = -1;
	}
	
	public int getCentroId() {
		return centroId;
	}

	public void setCentroId(int centroId) {
		this.centroId = centroId;
	}

	public int getPanel() {
		return panel;
	}

	public void setPanel(int panel) {
		this.panel = panel;
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
			pp.setCentroId((kvs.containsKey("p")) ? Integer.parseInt(kvs
					.get("p")) : PANEL_HIDDEN);
			return pp;
		}

		@Override
		public String getToken(CentroOperacionPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			kvs.put("p", place.getPanel() + "");
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
