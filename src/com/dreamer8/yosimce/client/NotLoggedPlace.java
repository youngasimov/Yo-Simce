package com.dreamer8.yosimce.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class NotLoggedPlace extends Place {

	
	@Prefix("login")
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
