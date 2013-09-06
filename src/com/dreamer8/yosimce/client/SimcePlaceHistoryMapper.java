package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.client.actividad.ActividadPlace;
import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.administracion.AdminEventosPlace;
import com.dreamer8.yosimce.client.administracion.AdminPlace;
import com.dreamer8.yosimce.client.administracion.AdminUsuariosPlace;
import com.dreamer8.yosimce.client.administracion.PermisosPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.general.GeneralPlace;
import com.dreamer8.yosimce.client.material.MaterialPlace;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.dreamer8.yosimce.client.planificacion.PlanificacionPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({
		SimcePlace.Tokenizer.class,
		PlanificacionPlace.Tokenizer.class,
		AgendamientosPlace.Tokenizer.class,
		AgendarVisitaPlace.Tokenizer.class,
		DetalleAgendaPlace.Tokenizer.class,
		GeneralPlace.Tokenizer.class,
		DetalleCursoPlace.Tokenizer.class,
		ActividadPlace.Tokenizer.class,
		ActividadesPlace.Tokenizer.class,
		FormActividadPlace.Tokenizer.class,
		SincronizacionPlace.Tokenizer.class,
		MaterialDefectuosoPlace.Tokenizer.class,
		AprobarSupervisoresPlace.Tokenizer.class,
		MaterialPlace.Tokenizer.class,
		CentroOperacionPlace.Tokenizer.class,
		AdminPlace.Tokenizer.class,
		AdminUsuariosPlace.Tokenizer.class,
		AdminEventosPlace.Tokenizer.class,
		PermisosPlace.Tokenizer.class})
public interface SimcePlaceHistoryMapper extends PlaceHistoryMapper {
}
