package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.AvanceRevisionView;
import com.dreamer8.yosimce.client.actividad.ui.AvanceRevisionView.AvanceRevisionPresenter;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AvanceRevisionActivity extends SimceActivity implements
		AvanceRevisionPresenter {

	
	private final AvanceRevisionView view;
	private int totalMaterial;
	private int materialProcesado;
	
	
	public AvanceRevisionActivity(ClientFactory factory, AvanceRevisionPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getAvanceRevisionView();
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.view.setPresenter(this);
		
	}

	@Override
	public void materialProcesado(final String codigo) {
		view.clearBox();
		getFactory().getActividadService().procesarMaterial(codigo, false, new SimceCallback<Void>(getFactory().getEventBus(),false) {

			@Override
			public void success(Void v) {
				materialProcesado+=1;
				getFactory().getEventBus().fireEvent(new MensajeEvent("Se ha procesado el código: <br />"+codigo+"<br />Exitosamente", MensajeEvent.MSG_OK, 5000));
				view.updateGraph(totalMaterial, materialProcesado);
			}
			
			@Override
			public void failure(Throwable caught) {
				getFactory().getEventBus().fireEvent(new MensajeEvent("Error al procesar el código: <br />"+codigo+"<br />Error:"+caught.getLocalizedMessage(), MensajeEvent.MSG_ERROR, false));
				super.failure(caught);
			}
		});
	}

	@Override
	public void materialNoProcesado(final String codigo) {
		view.clearBox();
		getFactory().getActividadService().procesarMaterial(codigo, false, new SimceCallback<Void>(getFactory().getEventBus(),false) {

			@Override
			public void success(Void v) {
				materialProcesado-=1;
				getFactory().getEventBus().fireEvent(new MensajeEvent("Se ha marcado como no procesado el código: <br />"+codigo+"<br />Exitosamente", MensajeEvent.MSG_OK, 5000));
				view.updateGraph(totalMaterial, materialProcesado);
			}
			
			@Override
			public void failure(Throwable caught) {
				getFactory().getEventBus().fireEvent(new MensajeEvent("Error al marcar como no procesado el código: <br />"+codigo+"<br />La operación no se llevo a cabo <br />Error:"+caught.getLocalizedMessage(), MensajeEvent.MSG_ERROR, false));
				super.failure(caught);
			}
		});
	}
	
	private void getTotalMaterial(){
		getFactory().getActividadService().getTotalMaterial(true, true, new SimceCallback<Integer>(getFactory().getEventBus(),false) {

			@Override
			public void success(Integer result) {
				totalMaterial = result;
				view.updateGraph(totalMaterial, 0);
			}
		});
	}
	
	private void getMaterialProcesado(){
		getFactory().getActividadService().getTotalMaterialProcesado(true, true, new SimceCallback<Integer>(getFactory().getEventBus(),false) {

			@Override
			public void success(Integer result) {
				materialProcesado = result;
				if(totalMaterial>materialProcesado){
					view.updateGraph(totalMaterial, materialProcesado);
				}
			}
		});
	}

	@Override
	public void onApiLoaded() {
		getTotalMaterial();
		getMaterialProcesado();
		Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
			
			@Override
			public boolean execute() {
				getTotalMaterial();
				getMaterialProcesado();
				return false;
			}
		}, 300000);
	}

}
