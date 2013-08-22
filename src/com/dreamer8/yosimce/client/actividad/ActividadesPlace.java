package com.dreamer8.yosimce.client.actividad;

import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ActividadesPlace extends SimcePlace {

	private boolean showActividadesNoInciadas;
	private boolean showActividadesTerminadas;
	private boolean showActividadesContingencia;
	private boolean showActividadesProblema;
	private boolean showActividadesSincronizadas;
	
	private Integer regionId;
	private Integer comunaId;
	
	public ActividadesPlace(){
		super();
		showActividadesNoInciadas = true;
		showActividadesTerminadas = true;
		showActividadesContingencia = true;
		showActividadesProblema = true;
		showActividadesSincronizadas = true;
		regionId = -1;
		comunaId = -1;
	}
	
	public ActividadesPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
		showActividadesNoInciadas = true;
		showActividadesTerminadas = true;
		showActividadesContingencia = true;
		showActividadesProblema = true;
		showActividadesSincronizadas = true;
		regionId = -1;
		comunaId = -1;
	}
	
	
	
	public boolean isShowActividadesNoInciadas() {
		return showActividadesNoInciadas;
	}

	public void setShowActividadesNoInciadas(boolean showActividadesNoInciadas) {
		this.showActividadesNoInciadas = showActividadesNoInciadas;
	}

	public boolean isShowActividadesTerminadas() {
		return showActividadesTerminadas;
	}

	public void setShowActividadesTerminadas(boolean showActividadesTerminadas) {
		this.showActividadesTerminadas = showActividadesTerminadas;
	}

	public boolean isShowActividadesContingencia() {
		return showActividadesContingencia;
	}

	public void setShowActividadesContingencia(boolean showActividadesContingencia) {
		this.showActividadesContingencia = showActividadesContingencia;
	}

	public boolean isShowActividadesProblema() {
		return showActividadesProblema;
	}

	public void setShowActividadesProblema(boolean showActividadesProblema) {
		this.showActividadesProblema = showActividadesProblema;
	}

	public boolean isShowActividadesSincronizadas() {
		return showActividadesSincronizadas;
	}

	public void setShowActividadesSincronizadas(boolean showActividadesSincronizadas) {
		this.showActividadesSincronizadas = showActividadesSincronizadas;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getComunaId() {
		return comunaId;
	}

	public void setComunaId(Integer comunaId) {
		this.comunaId = comunaId;
	}



	@Prefix("actividades")
	public static class Tokenizer implements PlaceTokenizer<ActividadesPlace>{

		@Override
		public ActividadesPlace getPlace(String token) {
			HashMap<String, String> kvs = TokenUtils.getTokenValues(token);
			ActividadesPlace aup = new ActividadesPlace();
			aup.setAplicacionId((kvs.containsKey(APPID)) ? Integer.parseInt(kvs
					.get(APPID)) : -1);
			aup.setNivelId((kvs.containsKey(NIVELID)) ? Integer.parseInt(kvs
					.get(NIVELID)) : -1);
			aup.setTipoId((kvs.containsKey(TIPOID)) ? Integer.parseInt(kvs
					.get(TIPOID)) : -1);
			aup.setShowActividadesNoInciadas((kvs.containsKey("sani") &&  kvs.get("sani").equals("0"))?false:true);
			aup.setShowActividadesTerminadas((kvs.containsKey("sat") &&  kvs.get("sat").equals("0"))?false:true);
			aup.setShowActividadesContingencia((kvs.containsKey("sac") &&  kvs.get("sac").equals("0"))?false:true);
			aup.setShowActividadesProblema((kvs.containsKey("sap") &&  kvs.get("sap").equals("0"))?false:true);
			aup.setShowActividadesSincronizadas((kvs.containsKey("sas") &&  kvs.get("sas").equals("0"))?false:true);
			aup.setRegionId((kvs.containsKey("rid")) ? Integer.parseInt(kvs
					.get("rid")) : -1);
			aup.setComunaId((kvs.containsKey("rid") && kvs.containsKey("cid")) ? Integer.parseInt(kvs
					.get("cid")) : -1);
			return aup;
		}

		@Override
		public String getToken(ActividadesPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(!place.isShowActividadesNoInciadas()){kvs.put("sani", "0");}
			if(!place.isShowActividadesTerminadas()){kvs.put("sat", "0");}
			if(!place.isShowActividadesContingencia()){kvs.put("sac", "0");}
			if(!place.isShowActividadesProblema()){kvs.put("sap", "0");}
			if(!place.isShowActividadesSincronizadas()){kvs.put("sas", "0");}
			if(place.getRegionId()!=-1){ kvs.put("rid", place.getRegionId()+""); }
			if(place.getComunaId()!=-1){ kvs.put("cid", place.getComunaId()+""); }
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
