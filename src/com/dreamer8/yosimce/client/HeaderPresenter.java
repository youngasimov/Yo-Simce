package com.dreamer8.yosimce.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ui.HeaderView;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HeaderPresenter implements HeaderView.HeaderPresenter{
	
	private ClientFactory factory;
	private HeaderView view;
	private UserDTO user;
	
	private int aplicacionId;
	private int nivelId;
	private int tipoId;
	
	private ArrayList<AplicacionDTO> aplicaciones;
	private HashMap<Integer,ArrayList<NivelDTO>> niveles;
	private HashMap<Integer,ArrayList<ActividadTipoDTO>> tipos;
	
	private HashMap<Integer,HashMap<String,ArrayList<String>>> permisos;
	
	private SimcePlace place;
	
	public HeaderPresenter(final ClientFactory factory, UserDTO user){
		this.factory = factory;
		this.view = factory.getHeaderView();
		this.user = user;
		aplicacionId = -1;
		nivelId = -1;
		tipoId=-1;
		aplicaciones = new ArrayList<AplicacionDTO>();
		niveles = new HashMap<Integer, ArrayList<NivelDTO>>();
		tipos = new HashMap<Integer, ArrayList<ActividadTipoDTO>>();
		permisos = new HashMap<Integer,HashMap<String,ArrayList<String>>>();
		bind();
	}

	@Override
	public void setDisplay(AcceptsOneWidget panel) {
		panel.setWidget(view.asWidget());
		
	}
	
	@Override
	public void onLoginClick() {
		Window.open("http://www.yosimce.cl", "_self", "");
	}

	@Override
	public void onAplicacionChange(int aplicacion) {
		factory.getPlaceController().goTo(new SimcePlace(aplicacion,-1,-1));
	}

	@Override
	public void onNivelChange(int nivel) {
		factory.getPlaceController().goTo(new SimcePlace(aplicacionId,nivel,-1));
	}

	@Override
	public void onTipoChange(int tipo) {
		factory.getPlaceController().goTo(new SimcePlace(aplicacionId,nivelId,tipo));
	}
	
	private void error(){
		
	}
	
	private void bind(){
		view.setPresenter(this);
		view.setAplicacionBoxVisivility(false);
		view.setNivelBoxVisivility(false);
		view.setTipoBoxVisivility(false);
		view.setLoginVisivibity(false);
		if(user == null){
			view.setUserName("No Registrado");
			view.setLoginVisivibity(true);
		}else{
			String username = user.getNombres()+" "+user.getApellidoPaterno()+" "+user.getApellidoMaterno();
			if(username.length()>50){
				username = username.substring(0,48)+"...";
			}
			view.setUserName(username);
			factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
				
				@Override
				public void onPlaceChange(PlaceChangeEvent event) {
					
					view.setAplicacionBoxVisivility(false);
					view.setNivelBoxVisivility(false);
					view.setTipoBoxVisivility(false);
					
					if(event.getNewPlace() instanceof SimcePlace){
						place = (SimcePlace)event.getNewPlace();
						aplicacionId = place.getAplicacionId();
						nivelId = place.getNivelId();
						tipoId = place.getTipoId();
						
						if(aplicaciones == null || aplicaciones.isEmpty()){
							//descarga la lista de aplicaciones
							factory.getLoginService().getAplicaciones(new SimceCallback<ArrayList<AplicacionDTO>>(factory.getEventBus()) {
	
								@Override
								public void success(ArrayList<AplicacionDTO> result) {
									aplicaciones = result;
									selectAplicacion();
									
								}
								
								@Override
								public void failure(Throwable caught) {
									super.failure(caught);
									if(factory.onTesting()){
										ArrayList<AplicacionDTO> result = new ArrayList<AplicacionDTO>();
										AplicacionDTO adto = new AplicacionDTO();
										adto.setNombre("Simce TIC");
										adto.setId(3);
										result.add(adto);
										adto = new AplicacionDTO();
										adto.setNombre("Simce");
										adto.setId(5);
										result.add(adto);
										success(result);
										
									}
								};
							});
						}else{
							//si ya esta descargada, la selecciona de ser nesesario
							selectAplicacion();
						}
					}else if(event.getNewPlace() instanceof NotLoggedPlace){
						view.setUserName("No registrado");
						view.setAplicacionBoxVisivility(false);
						view.setNivelBoxVisivility(false);
						view.setTipoBoxVisivility(false);
						view.setLoginVisivibity(true);
					}
				}
			});
		}
	}
	
	private void selectAplicacion(){
		
		if(aplicaciones == null || aplicaciones.isEmpty()){
			//no hay aplicaciones asignadas a este usuario
			aplicacionId = -1;
			error();
		}else if(aplicaciones.size()==1 && aplicacionId>-1 && aplicaciones.get(0).getId()!=aplicacionId){
			//hay una aplicacion, pero esta no coincide con la especificada en la url
			aplicacionId = -1;
			error();
		}else if(aplicaciones.size()==1 && aplicacionId==-1){
			//hay una aplicacion, pero no se ha especificado nada en la URL
			view.setAplicacionBoxVisivility(false);
			factory.getPlaceController().goTo(new SimcePlace(aplicaciones.get(0).getId(),-1,-1));
			return;
		}else if(aplicaciones.size()==1 && aplicacionId>-1 && aplicaciones.get(0).getId()==aplicacionId){
			//hay una aplicacion, y esta concide con la url
			view.setAplicacionBoxVisivility(false);
		}else{
			//hay mas de una aplicación
			view.setAplicacionList(aplicaciones);
			view.setAplicacionBoxVisivility(true);
		}
		if(aplicacionId>-1){
			view.selectAplicacion(aplicacionId);
			Cookies.setCookie("a", aplicacionId+"");
			if(niveles.containsKey(aplicacionId)){
				selectNivel();
			}else{
				factory.getLoginService().getNiveles(new SimceCallback<ArrayList<NivelDTO>>(factory.getEventBus()) {

					@Override
					public void success(ArrayList<NivelDTO> result) {
						niveles.put(aplicacionId, result);
						selectNivel();
					}
					
					@Override
					public void failure(Throwable caught) {
						super.failure(caught);
						if(factory.onTesting()){
							ArrayList<NivelDTO> result = new ArrayList<NivelDTO>();
							NivelDTO ndto = new NivelDTO();
							ndto.setNombre("5 básico");
							ndto.setId(7);
							result.add(ndto);
							ndto = new NivelDTO();
							ndto.setNombre("2 básico");
							ndto.setId(20);
							result.add(ndto);
							ndto = new NivelDTO();
							ndto.setNombre("2 medio");
							ndto.setId(34);
							result.add(ndto);
							success(result);
						}
					}
				});
			}
		}
	}
	
	
	private void selectNivel(){
		
		if(!niveles.containsKey(aplicacionId) || niveles.get(aplicacionId).isEmpty()){
			//no hay niveles para la aplicacion seleccionada
			nivelId = -1;
			error();
		}else if(niveles.get(aplicacionId).size() == 1 && nivelId>-1 && niveles.get(aplicacionId).get(0).getId()!=nivelId){
			//hay un nivel, pero este no coincide con el especificado en la url
			nivelId = -1;
			error();
		}else if(niveles.get(aplicacionId).size() == 1 && nivelId==-1){
			//hay un nivel, pero no se ha especificado nada en la URL
			view.setNivelBoxVisivility(false);
			factory.getPlaceController().goTo(new SimcePlace(aplicacionId,niveles.get(aplicacionId).get(0).getId(),-1));
			return;
		}else if(niveles.get(aplicacionId).size() == 1 && nivelId>-1 && niveles.get(aplicacionId).get(0).getId()==nivelId){
			//hay un nivel, y este concide con la url
			view.setNivelBoxVisivility(false);
		}else{
			//hay mas de un nivel
			view.setNivelList(niveles.get(aplicacionId));
			view.setNivelBoxVisivility(true);
		}
		if(nivelId>-1){
			view.selectNivel(nivelId);
			Cookies.setCookie("n", nivelId+"");
			if(tipos.containsKey(nivelId)){
				selectTipo();
			}else{
				factory.getLoginService().getActividadTipos(new SimceCallback<ArrayList<ActividadTipoDTO>>(factory.getEventBus()) {

					@Override
					public void success(ArrayList<ActividadTipoDTO> result) {
						tipos.put(nivelId, result);
						selectTipo();
					}
					
					@Override
					public void failure(Throwable caught) {
						super.failure(caught);
						if(factory.onTesting()){
							ArrayList<ActividadTipoDTO> result = new ArrayList<ActividadTipoDTO>();
							ActividadTipoDTO atdto = new ActividadTipoDTO();
							atdto.setNombre("visita previa");
							atdto.setId(8);
							result.add(atdto);
							atdto = new ActividadTipoDTO();
							atdto.setNombre("Prueba");
							atdto.setId(9);
							result.add(atdto);
							atdto = new ActividadTipoDTO();
							atdto.setNombre("Prueba 2");
							atdto.setId(10);
							result.add(atdto);
							success(result);
						}
					}
				});
			}
		}
	}
	
	private  void selectTipo(){
		
		if(!tipos.containsKey(nivelId) || tipos.get(nivelId).isEmpty()){
			//no hay tipos para el nivel seleccionado
			tipoId = -1;
			error();
		}else if(tipos.get(nivelId).size() == 1 && tipoId>-1 && tipos.get(nivelId).get(0).getId()!=tipoId){
			//hay un tipo, pero este no coincide con el especificado en la url
			tipoId = -1;
			error();
		}else if(tipos.get(nivelId).size() == 1 && tipoId==-1){
			//hay un tipo, pero no se ha especificado nada en la URL
			view.setTipoBoxVisivility(false);
			factory.getPlaceController().goTo(new SimcePlace(aplicacionId,nivelId,tipos.get(nivelId).get(0).getId()));
			return;
		}else if(tipos.get(nivelId).size() == 1 && tipoId>-1 && tipos.get(nivelId).get(0).getId()==tipoId){
			//hay un tipo, y este concide con la url
			view.setTipoBoxVisivility(false);
		}else{
			//hay mas de un tipo
			view.setTipoList(tipos.get(nivelId));
			view.setTipoBoxVisivility(true);
		}
		
		if(tipoId>-1){
			view.selectTipo(tipoId);
			Cookies.setCookie("t", tipoId+"");
			if(permisos.containsKey(tipoId)){
				factory.getEventBus().fireEvent(new PermisosEvent(permisos.get(tipoId)));
			}else{
				factory.getLoginService().getUsuarioPermisos(new SimceCallback<HashMap<String,ArrayList<String>>>(factory.getEventBus()) {
	
					@Override
					public void success(HashMap<String, ArrayList<String>> result) {
						permisos.put(tipoId, result);
						factory.getEventBus().fireEvent(new PermisosEvent(result));
					}
					
					@Override
					public void failure(Throwable caught) {
						super.failure(caught);
						if(factory.onTesting()){
							success(new HashMap<String, ArrayList<String>>());
						}
					}
				});
			}
		}
	}
	
}
