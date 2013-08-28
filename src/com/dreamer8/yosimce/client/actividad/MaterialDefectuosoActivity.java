package com.dreamer8.yosimce.client.actividad;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.actividad.ui.MaterialDefectuosoView;
import com.dreamer8.yosimce.client.actividad.ui.MaterialDefectuosoView.MaterialDefectuosoPresenter;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MaterialDefectuosoActivity extends SimceActivity implements
		MaterialDefectuosoPresenter {

	private final MaterialDefectuosoPlace place;
	private final MaterialDefectuosoView view;
	
	private EventBus eventBus;
	private CursoSelector selector;
	
	private ArrayList<MaterialDefectuosoDTO> material;
	
	public MaterialDefectuosoActivity(ClientFactory factory, MaterialDefectuosoPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		this.view = getFactory().getMaterialDefectuosoView();
		this.view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		panel.setWidget(view.asWidget());
		
		selector = new CursoSelector(getFactory(),eventBus);
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				MaterialDefectuosoPlace sp = new MaterialDefectuosoPlace();
				sp.setIdCurso(selector.getSelectedCurso().getId());
				selector.hide();
				goTo(sp);
			}
		});
		
		if(place.getIdCurso() < 0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					goTo(new ActividadPlace());
				}
			});
			selector.show();
		}else{
			
			getFactory().getGeneralService().getCurso(place.getIdCurso(), new SimceCallback<CursoDTO>(eventBus) {

				@Override
				public void success(CursoDTO result) {
					view.setCurso(result);
				}
			});
			
			getFactory().getActividadService().getEstadosSincronizacionFallida(new SimceCallback<ArrayList<EstadoSincronizacionDTO>>(eventBus) {

				@Override
				public void success(ArrayList<EstadoSincronizacionDTO> result) {
					view.setEstadosSincronizacion(result);
				}
			});
			
			getFactory().getActividadService().getMaterialDefectuoso(place.getIdCurso(), new SimceCallback<ArrayList<MaterialDefectuosoDTO>>(eventBus) {

				@Override
				public void success(ArrayList<MaterialDefectuosoDTO> result) {
					if(MaterialDefectuosoActivity.this.material == null){
						MaterialDefectuosoActivity.this.material = new ArrayList<MaterialDefectuosoDTO>();
					}
					material.addAll(result);
					view.setMaterialDefectuoso(result);
				}
				
				@Override
				public void failure(Throwable caught) {
					super.failure(caught);
					if(MaterialDefectuosoActivity.this.material == null){
						MaterialDefectuosoActivity.this.material = new ArrayList<MaterialDefectuosoDTO>();
					}
					view.setMaterialDefectuoso(MaterialDefectuosoActivity.this.material);
				}
			});
			
		}
	}

	@Override
	public void onCambiarCursoClick() {
		selector.show();
	}
	
	@Override
	public void onRemoveMaterialDefectuoso(final MaterialDefectuosoDTO material) {
		getFactory().getActividadService().removeMaterialDefectuoso(place.getIdCurso(), material.getIdMaterial(), new SimceCallback<Boolean>(eventBus) {

			@Override
			public void success(Boolean result) {
				MaterialDefectuosoActivity.this.material.remove(material);
				view.setMaterialDefectuoso(MaterialDefectuosoActivity.this.material);
			}
		});
	}

	@Override
	public void onAddMaterialDefectuoso(final MaterialDefectuosoDTO material) {
		getFactory().getActividadService().addOrUpdateMaterialDefectuoso(place.getIdCurso(), material, new SimceCallback<Boolean>(eventBus) {

			@Override
			public void success(Boolean result) {
				if(MaterialDefectuosoActivity.this.material == null){
					MaterialDefectuosoActivity.this.material = new ArrayList<MaterialDefectuosoDTO>();
				}
				MaterialDefectuosoActivity.this.material.add(material);
				view.setMaterialDefectuoso(MaterialDefectuosoActivity.this.material);
			}
		});
	}

}
