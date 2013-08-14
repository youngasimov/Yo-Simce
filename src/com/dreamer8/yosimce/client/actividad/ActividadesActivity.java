package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView;
import com.dreamer8.yosimce.client.actividad.ui.ActividadesView.ActividadesPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.Range;

public class ActividadesActivity extends SimceActivity implements
		ActividadesPresenter {

	private final ActividadesPlace place;
	private final ActividadesView view;
	private EventBus eventBus;
	private HashMap<String,String> filtros;
	
	private Range range;
	
	public ActividadesActivity(ClientFactory factory, ActividadesPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		view = getFactory().getActividadesView();
		view.setPresenter(this);
		range = view.getDataDisplay().getVisibleRange();
	}

	@Override
	public void onExportarClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRangeChange(Range r) {
		this.range = r;
		getFactory().getActividadService().getPreviewActividades(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<ActividadPreviewDTO>>(eventBus) {

			@Override
			public void success(ArrayList<ActividadPreviewDTO> result) {
				view.getDataDisplay().setRowData(range.getStart(), result);
			}
		});
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		
		getFactory().getActividadService().getTotalPreviewActividades(filtros, new SimceCallback<Integer>(eventBus) {

			@Override
			public void success(Integer result) {
				view.getDataDisplay().setRowCount(result,true);
			}
		});
		
		getFactory().getActividadService().getPreviewActividades(range.getStart(), range.getLength(), filtros, new SimceCallback<ArrayList<ActividadPreviewDTO>>(eventBus) {

			@Override
			public void success(ArrayList<ActividadPreviewDTO> result) {
				view.getDataDisplay().setRowData(range.getStart(), result);
			}
		});
	}
	
	@Override
	public void onStop() {
		super.onStop();
		view.getDataDisplay().setRowCount(0);
		view.clearCursoSelection();
	}

}
