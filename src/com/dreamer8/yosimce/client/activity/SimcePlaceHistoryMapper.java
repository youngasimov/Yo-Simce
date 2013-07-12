package com.dreamer8.yosimce.client.activity;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;


@WithTokenizers({NotLoggedPlace.Tokenizer.class, PlanAndResultPlace.Tokenizer.class})
public interface SimcePlaceHistoryMapper extends PlaceHistoryMapper {

}
