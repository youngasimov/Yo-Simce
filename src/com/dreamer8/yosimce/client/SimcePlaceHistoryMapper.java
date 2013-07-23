package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.planandresult.activity.PlanAndResultPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;


@WithTokenizers({NotLoggedPlace.Tokenizer.class, PlanAndResultPlace.Tokenizer.class})
public interface SimcePlaceHistoryMapper extends PlaceHistoryMapper {

}
