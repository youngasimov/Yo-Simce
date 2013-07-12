package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class HeaderActivityMapper implements ActivityMapper {

	private final ClientFactory factory;
	
	public HeaderActivityMapper(ClientFactory factory){
		this.factory = factory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		// TODO Auto-generated method stub
		return null;
	}

}
