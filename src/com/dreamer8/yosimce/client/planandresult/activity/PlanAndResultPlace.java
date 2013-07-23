package com.dreamer8.yosimce.client.planandresult.activity;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.place.shared.Prefix;

public class PlanAndResultPlace extends SimcePlace{
	
	private String filters;
	private int tab;
	private int selectedCourse;
	
	
	public PlanAndResultPlace(){
		super();
		tab = -1;
		selectedCourse = -1;
		filters = "";
	}
	
	public PlanAndResultPlace(int tab, int selectedCourse,int aplicacionId, int nivelId, int tipoId, String filters){
		super(aplicacionId,nivelId,tipoId);
		this.tab = tab;
		this.selectedCourse = selectedCourse;
		this.filters = filters;
	}
	
	public String getFilters() {
		return filters;
	}

	public int getTab() {
		return tab;
	}

	public int getSelectedCourse() {
		return selectedCourse;
	}


	@Prefix("planificacion")
	public static class Tokenizer extends SimcePlace.Tokenizer<PlanAndResultPlace>{

		@Override
		public PlanAndResultPlace getPlace(String token) {
			if(!token.contains("?")){
				return new PlanAndResultPlace();
			}
			String x[] = token.split("&");
			String filters = "";
			String sTab = "";
			String sSelectedCourse = "";
			int tab = -1;
			int course = -1;
			
			for(String s:x){
				if(s.startsWith("filters=")){
					filters = s.substring(s.indexOf("="));
				}else if(s.startsWith("tab=")){
					sTab = s.substring(s.indexOf("="));
				}else if(s.startsWith("cour=")){
					sSelectedCourse = s.substring(s.indexOf("="));
				}
			}
			try{
				tab = Integer.parseInt(sTab);
			}catch(Exception e){
				tab = -1;
			}
			try{
				course = Integer.parseInt(sSelectedCourse);
			}catch(Exception e){
				course = -1;
			}
			
			return new PlanAndResultPlace(tab,course,getValueId(token, "a"),getValueId(token, "n"),getValueId(token, "t"),filters);
		}

		@Override
		public String getToken(PlanAndResultPlace place) {
			String token = addValuesToToken(place.getAplicacionId(), place.getNivelId(), place.getTipoId())+"&";
			if(place.getTab()>-1){
				token = token+"tab="+place.getTab()+"&";
			}
			if(place.getSelectedCourse()>-1){
				token = token+"cour="+place.getSelectedCourse()+"&";
			}
			if(place.getFilters()!= null && !place.getFilters().equals("")){
				token = token+"filters="+place.getFilters()+"&";
			}
			if(token.endsWith("&")){
				token = token.substring(0,token.length()-1);
			}
			return token;
		}
		
	}
}
