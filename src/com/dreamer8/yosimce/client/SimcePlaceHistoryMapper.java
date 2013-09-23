package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.administracion.PermisosPlace;
import com.dreamer8.yosimce.client.administracion.ReportesPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({
		SimcePlace.Tokenizer.class,
		AgendamientosPlace.Tokenizer.class,
		AgendarVisitaPlace.Tokenizer.class,
		DetalleAgendaPlace.Tokenizer.class,
		DetalleCursoPlace.Tokenizer.class,
		ActividadesPlace.Tokenizer.class,
		FormActividadPlace.Tokenizer.class,
		SincronizacionPlace.Tokenizer.class,
		MaterialDefectuosoPlace.Tokenizer.class,
		AprobarSupervisoresPlace.Tokenizer.class,
		CentroOperacionPlace.Tokenizer.class,
		ReportesPlace.Tokenizer.class,
		PermisosPlace.Tokenizer.class})
public interface SimcePlaceHistoryMapper extends PlaceHistoryMapper {
}
