package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class SimceActivityMapper implements ActivityMapper {

	private ClientFactory factory;
	private UserDTO user;
	
	public SimceActivityMapper(ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.user = user;
	}
	
	@Override
	public Activity getActivity(Place place) {
		
		if(place instanceof ModuleSelectorPlace){
			return new ModuleSelectorActivity(factory, user);
		}else if(place instanceof NotLoggedPlace){
			return new NotLoggedActivity(factory);
		}else{
			return new AppActivity(factory, user);
		}
	}

	
	
}
