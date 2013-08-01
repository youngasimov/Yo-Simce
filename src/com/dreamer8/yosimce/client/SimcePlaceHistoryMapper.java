package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.general.DetalleEstablecimientoPlace;
import com.dreamer8.yosimce.client.general.GeneralPlace;
import com.dreamer8.yosimce.client.general.HistorialEstablecimientoPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.dreamer8.yosimce.client.planificacion.PlanificacionPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ NotLoggedPlace.Tokenizer.class,
		SimcePlace.Tokenizer.class,
		PlanificacionPlace.Tokenizer.class,
		AgendamientosPlace.Tokenizer.class,
		AgendarVisitaPlace.Tokenizer.class,
		DetalleAgendaPlace.Tokenizer.class,
		GeneralPlace.Tokenizer.class,
		DetalleEstablecimientoPlace.Tokenizer.class,
		HistorialEstablecimientoPlace.Tokenizer.class})
public interface SimcePlaceHistoryMapper extends PlaceHistoryMapper {
}