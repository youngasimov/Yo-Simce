package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.material.ui.BuscadorCodigoView;
import com.dreamer8.yosimce.client.material.ui.BuscadorCodigoView.BuscadorCodigoPresenter;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.DetallesMaterialDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class BuscadorCodigoActivity extends SimceActivity implements BuscadorCodigoPresenter {

	
	private BuscadorCodigoView view;
	private EventBus eventBus;
	private BuscadorCodigoPlace place;
	
	private MaterialDTO material;
	private DetallesMaterialDTO detalles;
	ArrayList<ActividadTipoDTO> tipos;
	
	
	
	
	public BuscadorCodigoActivity(ClientFactory factory, BuscadorCodigoPlace place,
			HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.place = place;
		view = getFactory().getBuscadorCodigoView();
		view.setPresenter(this);
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		this.eventBus = eventBus;
		clear();
		view.setFocusOnCodigoBox();
		
		getFactory().getLoginService().getActividadTipos(new SimceCallback<ArrayList<ActividadTipoDTO>>(eventBus,true) {

			@Override
			public void success(ArrayList<ActividadTipoDTO> result) {
				tipos = result;
				if(material!=null){
					for(ActividadTipoDTO at:tipos){
						if(at.getId()==material.getIdTipoActividad()){
							view.setTipoActividad(at.getNombre());
							break;
						}
					}
				}
			}
		});
		
		if(place.getCodigo()!=null && !place.getCodigo().isEmpty()){
			getFactory().getMaterialService().getMaterial(place.getCodigo(), new SimceCallback<MaterialDTO>(eventBus,true) {

				@Override
				public void success(MaterialDTO result) {
					material = result;
					view.setCodigo(material.getCodigo());
					view.setCurso(material.getCurso());
					view.setEstablecimiento(material.getEstablecimiento());
					view.setEtapa(material.getEtapa());
					view.setNivel(material.getNivel());
					view.setRbd(material.getRbd());
					view.setTipo(material.getTipo());
					
					
					if(tipos!=null){
						for(ActividadTipoDTO at:tipos){
							if(at.getId()==material.getIdTipoActividad()){
								view.setTipoActividad(at.getNombre());
								break;
							}
						}
					}
					
					getFactory().getMaterialService().getDetallesMaterial(material.getId(), new SimceCallback<DetallesMaterialDTO>(BuscadorCodigoActivity.this.eventBus,true) {

						@Override
						public void success(DetallesMaterialDTO result) {
							detalles = result;
							EmplazamientoDTO coa = detalles.getCentroOperacionAsignado();
							if(coa!=null){
								CentroOperacionPlace cop = new CentroOperacionPlace(place.getAplicacionId(), place.getNivelId(), place.getTipoId());
								cop.setCentroId(coa.getId());
								view.setCentroOperacionAsignado(coa.getNombre(), getFactory().getPlaceHistoryMapper().getToken(cop));
							}
							
							coa = result.getCentroOperacionIngresado();
							if(coa!=null){
								CentroOperacionPlace cop = new CentroOperacionPlace(place.getAplicacionId(), place.getNivelId(), place.getTipoId());
								cop.setCentroId(coa.getId());
								view.setCentroOperacionIngresado(coa.getNombre(), getFactory().getPlaceHistoryMapper().getToken(cop));
							}
							
							view.setHistorial(detalles.getHistorial());
						}
					});
				}
			});
			
		}
		
	}
	
	@Override
	public void getDetalleCodigoMaterial(String codigo) {
		BuscadorCodigoPlace bcp = new BuscadorCodigoPlace();
		bcp.setCodigo(codigo);
		goTo(bcp);
	}
	
	@Override
	public void onClear() {
		clear();
		view.setFocusOnCodigoBox();
	};
	
	private void clear(){
		view.setCodigo("");
		view.setCurso("");
		view.setEstablecimiento("");
		view.setEtapa("");
		view.setNivel("");
		view.setRbd("");
		view.setTipo("");
		view.setTipoActividad("");
		view.setCentroOperacionAsignado("", "");
		view.setCentroOperacionIngresado("", "");
		detalles = null;
		material = null;
		view.setHistorial(new ArrayList<HistorialMaterialItemDTO>());
	}
	

}
