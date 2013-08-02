package com.dreamer8.yosimce.client.administracion;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AdminUsuariosPlace extends SimcePlace {

	private int idUser;
	
	public AdminUsuariosPlace(){
		super();
		idUser = -1;
	}
	
	public AdminUsuariosPlace(int aplicacionId, int nivelId, int tipoId, int idUser){
		super(aplicacionId, nivelId, tipoId);
		this.idUser = idUser;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	@Prefix("adminusuarios")
	public static class Tokenizer implements PlaceTokenizer<AdminUsuariosPlace>{

		@Override
		public AdminUsuariosPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			AdminUsuariosPlace aup = new AdminUsuariosPlace();
			aup.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			aup.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			aup.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			aup.setIdUser((kvs.containsKey("u")) ? Integer.parseInt(kvs
					.get("u")) : -1);
			return aup;
		}

		@Override
		public String getToken(AdminUsuariosPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getIdUser()>-1){kvs.put("u", place.getIdUser() + "");}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
	
}
