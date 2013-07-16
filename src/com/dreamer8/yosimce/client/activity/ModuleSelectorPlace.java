package com.dreamer8.yosimce.client.activity;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ModuleSelectorPlace extends Place {

	
	@Prefix("modulos")
	public static class Tokenizer implements PlaceTokenizer<ModuleSelectorPlace>{

		@Override
		public ModuleSelectorPlace getPlace(String token) {
			return new ModuleSelectorPlace();
		}

		@Override
		public String getToken(ModuleSelectorPlace place) {
			return "";
		}
		
	}
}
