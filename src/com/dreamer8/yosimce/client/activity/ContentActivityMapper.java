package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.planandresult.activity.PlanAndResultContentActivity;
import com.dreamer8.yosimce.client.planandresult.activity.PlanAndResultPlace;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ContentActivityMapper implements ActivityMapper {

	
	private ClientFactory factory;
	private UserDTO user;
	
	public ContentActivityMapper(ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.user = user;
	}
	
	@Override
	public Activity getActivity(Place place) {
		if(place instanceof PlanAndResultPlace){
			return new PlanAndResultContentActivity((PlanAndResultPlace)place, factory, user);
		}
		return null;
	}

}