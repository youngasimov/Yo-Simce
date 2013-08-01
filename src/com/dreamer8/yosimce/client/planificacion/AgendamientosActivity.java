package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView;
import com.dreamer8.yosimce.client.planificacion.ui.AgendamientosView.AgendamientosPresenter;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.TipoEstablecimientoDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AgendamientosActivity extends SimceActivity implements
		AgendamientosPresenter {

	private final AgendamientosPlace place;
	private AgendamientosView view;
	
	public AgendamientosActivity(ClientFactory factory,AgendamientosPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory,permisos);
		this.place = place;
		view = factory.getAgendamientosView();
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel,eventBus);
		panel.setWidget(view.asWidget());
		
		if(getFactory().onTesting()){
			ArrayList<AgendaPreviewDTO> agendas = new ArrayList<AgendaPreviewDTO>();
			AgendaPreviewDTO ap = new AgendaPreviewDTO();
			ap.setEstablecimientoId(2);
			ap.setRegionName("RegionMetropolitana");
			ap.setComunaName("Providencia");
			ap.setEstablecimientoName("Carmilitas Descalsas");
			ap.setRbd("3895");
			TipoEstablecimientoDTO tipo = new TipoEstablecimientoDTO();
			tipo.setId(10);
			tipo.setTipo("Seleccionado");
			ap.setTipoEstablecimiento(tipo);
			AgendaItemDTO ai = new AgendaItemDTO();
			ai.setFecha(new Date());
			ai.setComentario("Quiere la boca exhausta vid, kiwi, piña y fugaz jamón. Fabio me exige, sin tapuj");
			EstadoAgendaDTO eai = new EstadoAgendaDTO();
			eai.setEstado("Por confirmar");
			eai.setId(3);			
			ai.setEstado(eai);
			ap.setAgendaItemActual(ai);
			agendas.add(ap);
			
			ap = new AgendaPreviewDTO();
			ap.setEstablecimientoId(5);
			ap.setRegionName("Region Metropolitana");
			ap.setComunaName("Providencia");
			ap.setEstablecimientoName("Liceo 34 de hombres");
			ap.setRbd("94854");
			tipo = new TipoEstablecimientoDTO();
			tipo.setId(11);
			tipo.setTipo("Reemplazo seleccionado");
			ap.setTipoEstablecimiento(tipo);
			ai = new AgendaItemDTO();
			ai.setFecha(new Date());
			ai.setComentario("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula.");
			eai = new EstadoAgendaDTO();
			eai.setEstado("Por confirmar");
			eai.setId(6);			
			ai.setEstado(eai);
			ap.setAgendaItemActual(ai);
			agendas.add(ap);
			view.getDataDisplay().setRowData(0, agendas);
		}
	}

	@Override
	public void onColumnSort(int columnIndex) {
		
	}

	@Override
	public void goTo(SimcePlace place) {
		place.setAplicacionId(this.place.getAplicacionId());
		place.setNivelId(this.place.getNivelId());
		place.setTipoId(this.place.getTipoId());
		getFactory().getPlaceController().goTo(place);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.clearCursoSelection();
	}

}
