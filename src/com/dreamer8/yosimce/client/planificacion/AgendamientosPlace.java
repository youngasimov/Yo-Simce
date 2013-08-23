package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AgendamientosPlace extends SimcePlace {
	
	private ArrayList<Integer> estadosSeleccionados;
	
	private long desdeTimestamp;
	private long hastaTimestamp;
	
	private Integer regionId;
	private Integer comunaId;

	public AgendamientosPlace() {
		super();
		estadosSeleccionados = new ArrayList<Integer>();
		desdeTimestamp = -1;
		hastaTimestamp = -1;
		regionId = -1;
		comunaId = -1;
		
	}

	public AgendamientosPlace(int aplicacionId, int nivelId, int tipoId) {
		super(aplicacionId, nivelId, tipoId);
		estadosSeleccionados = new ArrayList<Integer>();
		desdeTimestamp = -1;
		hastaTimestamp = -1;
		regionId = -1;
		comunaId = -1;
	}

	public ArrayList<Integer> getEstadosSeleccionados() {
		return estadosSeleccionados;
	}

	public void setEstadosSeleccionados(ArrayList<Integer> estadosSeleccionados) {
		this.estadosSeleccionados = estadosSeleccionados;
	}

	public long getDesdeTimestamp() {
		return desdeTimestamp;
	}

	public void setDesdeTimestamp(long desdeTimestamp) {
		this.desdeTimestamp = desdeTimestamp;
	}

	public long getHastaTimestamp() {
		return hastaTimestamp;
	}

	public void setHastaTimestamp(long hastaTimestamp) {
		this.hastaTimestamp = hastaTimestamp;
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
			pp.setRegionId((kvs.containsKey("rid")) ? Integer.parseInt(kvs
					.get("rid")) : -1);
			pp.setComunaId((kvs.containsKey("rid") && kvs.containsKey("cid")) ? Integer.parseInt(kvs
					.get("cid")) : -1);
			pp.setDesdeTimestamp((kvs.containsKey("dts")) ? Long.parseLong(kvs
					.get("dts")) : -1);
			pp.setHastaTimestamp((kvs.containsKey("hts")) ? Long.parseLong(kvs
					.get("hts")) : -1);
			
			if(kvs.containsKey("es")){
				
				String[] estados = kvs.get("es").split(":");
				ArrayList<Integer> e = new ArrayList<Integer>();
				for(String s:estados){
					e.add(Integer.parseInt(s));
				}
				pp.setEstadosSeleccionados(e);
			}
			return pp;
		}

		@Override
		public String getToken(AgendamientosPlace place) {
			HashMap<String, String> kvs = new HashMap<String, String>();
			kvs.put(APPID, place.getAplicacionId() + "");
			kvs.put(NIVELID, place.getNivelId() + "");
			kvs.put(TIPOID, place.getTipoId() + "");
			if(place.getDesdeTimestamp()!=-1){ kvs.put("dts", place.getDesdeTimestamp()+""); }
			if(place.getHastaTimestamp()!=-1){ kvs.put("hts", place.getHastaTimestamp()+""); }
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
