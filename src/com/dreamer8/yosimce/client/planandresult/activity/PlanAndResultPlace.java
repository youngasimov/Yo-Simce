package com.dreamer8.yosimce.client.planandresult.activity;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PlanAndResultPlace extends Place {
	
	private String filters;
	private int tab;
	private int selectedCourse;
	
	
	public PlanAndResultPlace(){
		tab = -1;
		selectedCourse = -1;
		filters = "";
	}
	
	public PlanAndResultPlace(int tab, int selectedCourse, String filters){
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
	public static class Tokenizer implements PlaceTokenizer<PlanAndResultPlace>{

		@Override
		public PlanAndResultPlace getPlace(String token) {
			if(!token.contains("?")){
				return new PlanAndResultPlace();
			}
			String x[] = token.substring(token.indexOf("?")).split("&");
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
			return new PlanAndResultPlace(tab,course,filters);
		}

		@Override
		public String getToken(PlanAndResultPlace place) {
			String token = "?";
			if(place.getTab()>-1){
				token = token+"tab="+place.getTab()+"&";
			}
			if(place.getSelectedCourse()>-1){
				token = token+"cour="+place.getSelectedCourse()+"&";
			}
			if(place.getFilters()!= null && !place.getFilters().equals("")){
				token = token+"filters="+place.getFilters()+"&";
			}
			
			token = token.substring(0,token.length()-1);
			if(token.endsWith("?")){
				token = token.substring(0,token.length()-1);
			}
			return token;
		}
		
	}
}
