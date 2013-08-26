package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.CursoSelector;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView;
import com.dreamer8.yosimce.client.general.ui.DetalleCursoView.DetalleCursoPresenter;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DetalleCursoActivity extends SimceActivity implements
		DetalleCursoPresenter {

	private final DetalleCursoPlace place;
	private final DetalleCursoView view;
	private CursoSelector selector;
	
	public DetalleCursoActivity(ClientFactory factory, DetalleCursoPlace place, HashMap<String, ArrayList<String>> permisos) {
		super(factory, place,permisos);
		this.place = place;
		this.view = getFactory().getDetalleCursoView();
		this.view.setPresenter(this);
	}
	
	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		selector = new CursoSelector(getFactory(),eventBus);
		selector.setOnCursoChangeAction(new Command() {
			
			@Override
			public void execute() {
				DetalleCursoPlace dcp = new DetalleCursoPlace();
				dcp.setCursoId(place.getCursoId());
				selector.hide();
				goTo(dcp);
			}
		});
		
		if(place.getCursoId()<0){
			selector.setOnCancelAction(new Command() {
				
				@Override
				public void execute() {
					GeneralPlace p =new GeneralPlace();
					goTo(p);
				}
			});
			selector.show();
		}else{
			getFactory().getGeneralService().getDetalleCurso(place.getCursoId(), new SimceCallback<DetalleCursoDTO>(eventBus) {

				@Override
				public void success(DetalleCursoDTO r) {
					view.setNombreEstablecimiento(r.getEstablecimiento());
					view.setRbd(r.getRbd());
					view.setRegion(r.getRegion());
					view.setComuna(r.getComuna());
					view.setCurso(r.getCurso());
					view.setTipo(r.getTipoEstablecimiento());
					if(r.getSupervisor()!=null){
						view.setSupervisor(r.getSupervisor().getNombres()+" "+r.getSupervisor().getApellidoPaterno()+" "+r.getSupervisor().getApellidoMaterno());
					}
					if (r.getExaminadores() != null && !r.getExaminadores().isEmpty()) {
						int ex = 0;
						for (UserDTO examinador : r.getExaminadores()) {
							if(ex == 0){
								view.setExaminador(examinador.getNombres()+" "+examinador.getApellidoPaterno()+" "+examinador.getApellidoMaterno());
								ex++;
							}else{
								view.setExaminador2(examinador.getNombres()+" "+examinador.getApellidoPaterno()+" "+examinador.getApellidoMaterno());
							}
						}
					}
					
					view.setDirector(r.getNombreContacto());
					view.setEmailContacto(r.getEmailContacto());
					view.setTelefonoContacto(r.getTelefonoContacto());
				}
				
			});
		}
	}
	
	@Override
	public void onCambiarCursoClick() {
		selector.show();
	}
	
	@Override
	public void onStop() {
		view.clearAll();
		super.onStop();
	}
}
