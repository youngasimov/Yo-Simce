package com.dreamer8.yosimce.client.administracion;

import java.util.ArrayList;
import java.util.HashMap;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.client.SimceActivity;
import com.dreamer8.yosimce.client.SimceCallback;
import com.dreamer8.yosimce.client.Utils;
import com.dreamer8.yosimce.client.administracion.ui.ReportesView;
import com.dreamer8.yosimce.client.administracion.ui.ReportesView.ReportesPresenter;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ReportesActivity extends SimceActivity implements
		ReportesPresenter {

	private final ReportesView view;
	
	private ArrayList<SectorDTO> regiones;
	private HashMap<Integer,ArrayList<SectorDTO>> comunas;
	private HashMap<Integer,String> reportes;
	private EventBus eventBus;
	
	public ReportesActivity(ClientFactory factory, ReportesPlace place,HashMap<String, ArrayList<String>> permisos) {
		super(factory, place, permisos);
		this.view = getFactory().getReportesView();
		this.view.setPresenter(this);
		comunas = new HashMap<Integer, ArrayList<SectorDTO>>();
		reportes = new HashMap<Integer, String>();
	}

	@Override
	public void init(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		
		reportes.clear();
		reportes.put(AdministracionService.MOVIMIENTO_MATERIAL, "Movimiento de material");
		reportes.put(AdministracionService.TRACKING_POR_CO, "Estado de material por Centro operación");
		
		view.setReportes(reportes);
		if(Utils.hasPermisos(getPermisos() , "GeneralService", "getComunas")){
			getFactory().getGeneralService().getRegiones(new SimceCallback<ArrayList<SectorDTO>>(eventBus,true) {
	
				@Override
				public void success(ArrayList<SectorDTO> result) {
					regiones = result;
					view.setRegiones(result);
				}
			});
		}
		
		
		
	}

	@Override
	public void onGenerarReporte() {
		
		int region = view.getSelectedRegion();
		int comuna = view.getSelectedComuna();
		int reporte = view.getSelectedReporte();
		String desde = view.getSelectedDate();
		
		if(reporte == -1){
			eventBus.fireEvent(new MensajeEvent("Seleccione el reporte a generar",MensajeEvent.MSG_WARNING,true));
			return;
		}
		
		if(desde  == null || desde.isEmpty()){
			desde = null;
		}
		//if(Utils.hasPermisos(eventBus, getPermisos() , "AdministracionService", "getReporte")){
			getFactory().getAdministracionService().getReporte(reporte, region, comuna, desde, new SimceCallback<DocumentoDTO>(eventBus,true) {
	
				@Override
				public void success(DocumentoDTO result) {
					Window.open(result.getUrl(), "_black", "");
				}
				
			});
		//}
		
	}

	@Override
	public void onRegionChange(final int region) {
		if(Utils.hasPermisos(eventBus, getPermisos() , "GeneralService", "getComunas")){
			if(comunas.containsKey(region)){
				view.setComunas(comunas.get(region));
			}else{
				for(SectorDTO s:regiones){
					if(s.getIdSector() == region){
						getFactory().getGeneralService().getComunas(s, new SimceCallback<ArrayList<SectorDTO>>(eventBus,false) {
	
							@Override
							public void success(ArrayList<SectorDTO> result) {
								comunas.put(region, result);
								view.setComunas(result);
							}
						});
						return;
					}
				}
				
			}
		}
	}

}
