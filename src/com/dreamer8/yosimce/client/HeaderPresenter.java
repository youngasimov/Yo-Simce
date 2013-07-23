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
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HeaderPresenter implements HeaderView.HeaderPresenter{

	
	private ClientFactory factory;
	private HeaderView view;
	private UserDTO user;
	
	private AplicacionDTO selectedAplicacion;
	private NivelDTO selectedNivel;
	private ActividadTipoDTO selectedTipo;
	
	private int aplicacionId;
	private int nivelId;
	private int tipoId;
	
	private ArrayList<AplicacionDTO> aplicaciones;
	private HashMap<Integer,ArrayList<NivelDTO>> niveles;
	private HashMap<Integer,ArrayList<ActividadTipoDTO>> tipos;
	
	private HashMap<String,ArrayList<String>> permisos;
	
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
		bind();
		
		
		
	}

	@Override
	public void setDisplay(AcceptsOneWidget panel) {
		panel.setWidget(view.asWidget());
		
	}

	@Override
	public void onAplicacionChange(AplicacionDTO aplicacion) {
		selectedAplicacion = null;
		selectedNivel = null;
		selectedTipo = null;
		Cookies.removeCookie("a");
		Cookies.removeCookie("n");
		Cookies.removeCookie("t");
		aplicacionId = aplicacion.getId();
		nivelId = -1;
		tipoId = -1;
		selectAplicacion();
	}

	@Override
	public void onNivelChange(NivelDTO nivel) {
		selectedNivel = null;
		selectedTipo = null;
		Cookies.removeCookie("n");
		Cookies.removeCookie("t");
		nivelId = nivel.getId();
		tipoId = -1;
		selectNivel();
	}

	@Override
	public void onTipoChange(ActividadTipoDTO tipo) {
		selectedTipo = null;
		Cookies.removeCookie("t");
		tipoId = tipo.getId();
		selectTipo();
	}
	
	private void bind(){
		view.setPresenter(this);
		view.setAplicacionBoxVisivility(false);
		view.setNivelBoxVisivility(false);
		view.setTipoBoxVisivility(false);
		view.error(false);
		if(user != null){
			String username = user.getNombres()+" "+user.getApellidoPaterno()+" "+user.getApellidoMaterno();
			if(username.length()>50){
				username = username.substring(0,48)+"...";
			}
			view.setUserName(username);
		}
		
		
		
		factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
			
			@Override
			public void onPlaceChange(PlaceChangeEvent event) {
				if(event.getNewPlace() instanceof SimcePlace){
					SimcePlace sp = (SimcePlace)event.getNewPlace();
					aplicacionId = sp.getAplicacionId();
					nivelId = sp.getNivelId();
					tipoId = sp.getTipoId();
					
					if(aplicaciones == null || aplicaciones.isEmpty()){
						factory.getLoginService().getAplicaciones(new SimceCallback<ArrayList<AplicacionDTO>>(factory.getEventBus()) {

							@Override
							public void success(ArrayList<AplicacionDTO> result) {
								if(result.isEmpty()){
									view.error(true);
								}else{
									view.setAplicacionBoxVisivility(result.size()>1);
								}
								view.setAplicacionList(result);
								aplicaciones = result;
								selectAplicacion();
							}
						});
					}else{
						selectAplicacion();
					}
				}else if(event.getNewPlace() instanceof NotLoggedPlace){
					
				}
			}
		});
	}
	
	private void selectAplicacion(){
		if(aplicaciones!=null && aplicacionId>=0 && selectedAplicacion == null){
			for(AplicacionDTO aplicacion:aplicaciones){
				if(aplicacion.getId() == aplicacionId){
					selectedAplicacion = aplicacion;
					view.selectAplicacion(aplicacion);
					Cookies.setCookie("a", aplicacionId+"");
					if(niveles.containsKey(aplicacionId)){
						selectNivel();
					}else if(aplicacionId>=0){
						factory.getLoginService().getNiveles(new SimceCallback<ArrayList<NivelDTO>>(factory.getEventBus()) {

							@Override
							public void success(ArrayList<NivelDTO> result) {
								if(result.isEmpty()){
									view.error(true);
								}else{
									view.setNivelBoxVisivility(result.size()>1);
								}
								view.setNivelList(result);
								niveles.put(aplicacionId, result);
								selectNivel();
							}
						});
					}
					return;
				}
			}
		}
	}
	
	
	private void selectNivel(){	
		if(niveles.containsKey(aplicacionId) && nivelId>=0 && selectedNivel == null){
			ArrayList<NivelDTO> ns = niveles.get(aplicacionId); 
			for(NivelDTO nivel:ns){
				if(nivel.getId() == nivelId){
					selectedNivel = nivel;
					view.selectNivel(nivel);
					Cookies.setCookie("n", nivelId+"");
					if(tipos.containsKey(nivelId)){
						selectTipo();
					}else{
						factory.getLoginService().getActividadTipos(new SimceCallback<ArrayList<ActividadTipoDTO>>(factory.getEventBus()) {

							@Override
							public void success(ArrayList<ActividadTipoDTO> result) {
								if(result.isEmpty()){
									view.error(true);
								}else{
									view.setTipoBoxVisivility(result.size()>1);
								}
								view.setTipoList(result);
								tipos.put(nivelId, result);
								selectTipo();
							}
						});
					}
					return;
				}
			}
		}
	}
	
	private  void selectTipo(){
		if(tipos.containsKey(nivelId) && tipoId>=0 && selectedTipo == null){
			ArrayList<ActividadTipoDTO> ts = tipos.get(nivelId);
			for(ActividadTipoDTO tipo:ts){
				if(tipo.getId() == tipoId){
					selectedTipo = tipo;
					view.selectTipo(tipo);
					Cookies.setCookie("t", tipoId+"");
					factory.getLoginService().getUsuarioPermisos(new SimceCallback<HashMap<String,ArrayList<String>>>(factory.getEventBus()) {

						@Override
						public void success(HashMap<String, ArrayList<String>> result) {
							permisos = result;
							factory.getEventBus().fireEvent(new PermisosEvent(result));
						}
					});
					return;
				}
			}
		}
	}
	
}
