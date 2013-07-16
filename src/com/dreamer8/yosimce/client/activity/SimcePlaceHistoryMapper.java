package com.dreamer8.yosimce.client.activity;

import com.dreamer8.yosimce.client.planandresult.activity.PlanAndResultPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;


@WithTokenizers({ModuleSelectorPlace.Tokenizer.class,NotLoggedPlace.Tokenizer.class, PlanAndResultPlace.Tokenizer.class})
public interface SimcePlaceHistoryMapper extends PlaceHistoryMapper {

}
