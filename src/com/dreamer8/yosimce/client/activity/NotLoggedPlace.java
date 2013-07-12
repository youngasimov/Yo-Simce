package com.dreamer8.yosimce.client.activity;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NotLoggedPlace extends Place {

	
	public static class Tokenizer implements PlaceTokenizer<NotLoggedPlace>{

		@Override
		public NotLoggedPlace getPlace(String token) {
			return new NotLoggedPlace();
		}

		@Override
		public String getToken(NotLoggedPlace place) {
			return "";
		}
		
	}
	
}
