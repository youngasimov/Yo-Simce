package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ActividadesPlace extends SimcePlace {

	private ArrayList<Integer> estadosSeleccionados;
	private boolean actividadesMaterialContintencia;
	private boolean actividadesContintencia;
	private boolean actividadesContintenciaInhabilitante;
	private boolean actividadesSincronizadas;
	private boolean actividadesParcialmenteSincronizadas;
	private boolean actividadesNoSincronizadas;
	
	private Integer regionId;
	private Integer comunaId;
	
	public ActividadesPlace(){
		super();
		estadosSeleccionados = new ArrayList<Integer>();
		actividadesMaterialContintencia = true;
		actividadesContintencia = true;
		actividadesContintenciaInhabilitante = true;
		actividadesSincronizadas = true;
		actividadesParcialmenteSincronizadas = true;
		actividadesNoSincronizadas = true;
		regionId = -1;
		comunaId = -1;
	}
	
	public ActividadesPlace(int aplicacionId, int nivelId, int tipoId){
		super(aplicacionId, nivelId, tipoId);
		estadosSeleccionados = new ArrayList<Integer>();
		actividadesMaterialContintencia = true;
		actividadesContintencia = true;
		actividadesContintenciaInhabilitante = true;
		actividadesSincronizadas = true;
		actividadesParcialmenteSincronizadas = true;
		actividadesNoSincronizadas = true;
		regionId = -1;
		comunaId = -1;
	}

	public ArrayList<Integer> getEstadosSeleccionados() {
		return estadosSeleccionados;
	}

	public void setEstadosSeleccionados(ArrayList<Integer> estadosSeleccionados) {
		this.estadosSeleccionados = estadosSeleccionados;
	}

	public boolean isActividadesMaterialContintencia() {
		return actividadesMaterialContintencia;
	}

	public void setActividadesMaterialContintencia(
			boolean actividadesMaterialContintencia) {
		this.actividadesMaterialContintencia = actividadesMaterialContintencia;
	}

	public boolean isActividadesContintencia() {
		return actividadesContintencia;
	}

	public void setActividadesContintencia(boolean actividadesContintencia) {
		this.actividadesContintencia = actividadesContintencia;
	}

	public boolean isActividadesContintenciaInhabilitante() {
		return actividadesContintenciaInhabilitante;
	}

	public void setActividadesContintenciaInhabilitante(
			boolean actividadesContintenciaInhabilitante) {
		this.actividadesContintenciaInhabilitante = actividadesContintenciaInhabilitante;
	}

	public boolean isActividadesSincronizadas() {
		return actividadesSincronizadas;
	}

	public void setActividadesSincronizadas(boolean actividadesSincronizadas) {
		this.actividadesSincronizadas = actividadesSincronizadas;
	}

	public boolean isActividadesParcialmenteSincronizadas() {
		return actividadesParcialmenteSincronizadas;
	}

	public void setActividadesParcialmenteSincronizadas(
			boolean actividadesParcialmenteSincronizadas) {
		this.actividadesParcialmenteSincronizadas = actividadesParcialmenteSincronizadas;
	}

	public boolean isActividadesNoSincronizadas() {
		return actividadesNoSincronizadas;
	}

	public void setActividadesNoSincronizadas(boolean actividadesNoSincronizadas) {
		this.actividadesNoSincronizadas = actividadesNoSincronizadas;
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
			aup.setActividadesContintencia((kvs.containsKey("ac") &&  kvs.get("ac").equals("0"))?false:true);
			aup.setActividadesContintenciaInhabilitante((kvs.containsKey("aci") &&  kvs.get("aci").equals("0"))?false:true);
			aup.setActividadesMaterialContintencia((kvs.containsKey("amc") &&  kvs.get("amc").equals("0"))?false:true);
			aup.setActividadesSincronizadas((kvs.containsKey("as") &&  kvs.get("as").equals("0"))?false:true);
			aup.setActividadesParcialmenteSincronizadas((kvs.containsKey("aps") &&  kvs.get("aps").equals("0"))?false:true);
			aup.setActividadesNoSincronizadas((kvs.containsKey("ans") &&  kvs.get("ans").equals("0"))?false:true);
			aup.setRegionId((kvs.containsKey("rid")) ? Integer.parseInt(kvs.get("rid")) : -1);
			aup.setComunaId((kvs.containsKey("rid") && kvs.containsKey("cid")) ? Integer.parseInt(kvs.get("cid")) : -1);
			if(kvs.containsKey("es")){
				
				String[] estados = kvs.get("es").split(":");
				ArrayList<Integer> e = new ArrayList<Integer>();
				for(String s:estados){
					e.add(Integer.parseInt(s));
				}
				aup.setEstadosSeleccionados(e);
			}
			return aup;
		}

		@Override
		public String getToken(ActividadesPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(!place.isActividadesContintencia()){kvs.put("ac", "0");}
			if(!place.isActividadesContintenciaInhabilitante()){kvs.put("aci", "0");}
			if(!place.isActividadesMaterialContintencia()){kvs.put("amc", "0");}
			if(!place.isActividadesSincronizadas()){kvs.put("as", "0");}
			if(!place.isActividadesParcialmenteSincronizadas()){kvs.put("aps", "0");}
			if(!place.isActividadesNoSincronizadas()){kvs.put("ans", "0");}
			if(place.getRegionId()!=-1){ kvs.put("rid", place.getRegionId()+""); }
			if(place.getComunaId()!=-1){ kvs.put("cid", place.getComunaId()+""); }
			if(!place.getEstadosSeleccionados().isEmpty()){	
				StringBuilder estados = new StringBuilder();
				for(Integer i:place.getEstadosSeleccionados()){
					estados.append(i);
					estados.append(":");
				}
				estados.deleteCharAt(estados.length()-1);
				kvs.put("es", estados.toString());
			}
			return TokenUtils.createKeyValuesToken(kvs);
		}
		
	}
}
