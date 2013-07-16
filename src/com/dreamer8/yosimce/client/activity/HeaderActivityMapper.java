package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class HeaderActivityMapper implements ActivityMapper {

	private final ClientFactory factory;
	private UserDTO user;
	
	public HeaderActivityMapper(ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.user = user;
	}
	
	@Override
	public Activity getActivity(Place place) {
		// TODO Auto-generated method stub
		return null;
	}

}
