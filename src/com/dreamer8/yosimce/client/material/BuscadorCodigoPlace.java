package com.dreamer8.yosimce.client.material;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class BuscadorCodigoPlace extends SimcePlace  {

	private String codigo; 
	
	public BuscadorCodigoPlace(){
		this(-1,-1,-1);
	}
	
	public BuscadorCodigoPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
		this.codigo = "";
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	@Prefix("codigos")
	public static class Tokenizer implements PlaceTokenizer<BuscadorCodigoPlace>{

		
		
		@Override
		public BuscadorCodigoPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			BuscadorCodigoPlace pp = new BuscadorCodigoPlace();
			pp.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			pp.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			pp.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			pp.setCodigo((kvs.containsKey("c")) ? kvs.get("c") : "");
			return pp;
		}

		@Override
		public String getToken(BuscadorCodigoPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			kvs.put("c", place.getCodigo());
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
