package com.dreamer8.yosimce.client.administracion.ui;

import java.util.HashMap;
import java.util.Map.Entry;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ReportesViewD extends Composite implements ReportesView {

	private static ReportesViewDUiBinder uiBinder = GWT
			.create(ReportesViewDUiBinder.class);

	interface ReportesViewDUiBinder extends UiBinder<Widget, ReportesViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField DecoratorPanel reportesPanel;
	//@UiField ListBox regionBox;
	//@UiField ListBox comunaBox;
	@UiField TextBox codigoBox;
	@UiField ListBox reporteBox;
	@UiField Button generarButton;
	
	private ReportesPresenter presenter;
	
	//private DateTimeFormat format;
	
	public ReportesViewD() {
		initWidget(uiBinder.createAndBindUi(this));
		//desdeBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_MEDIUM)));
		//format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
	}
	
	@UiHandler("generarButton")
	void onGenerarReporteClick(ClickEvent event){
		presenter.onGenerarReporte();
	}
	
	/*
	@UiHandler("regionBox")
	void onRegionChange(ChangeEvent event){
		if(comunaBox.getItemCount()>0){
			comunaBox.setSelectedIndex(0);
		}
		presenter.onRegionChange(Integer.parseInt(regionBox.getValue(regionBox.getSelectedIndex())));
	}*/

	@Override
	public void setPresenter(ReportesPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setReportesVisibility(boolean visible) {
		reportesPanel.setVisible(visible);
	}
	
	@Override
	public String getCodigo() {
		return codigoBox.getValue();
	}

	/*
	@Override
	public void setRegiones(ArrayList<SectorDTO> regiones) {
		regionBox.clear();
		regionBox.addItem("Todas","-1");
		for(SectorDTO s:regiones){
			regionBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}*/
/*
	@Override
	public void setComunas(ArrayList<SectorDTO> comunas) {
		comunaBox.clear();
		comunaBox.addItem("Todas","-1");
		for(SectorDTO s:comunas){
			comunaBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}

	@Override
	public int getSelectedRegion() {
		return Integer.parseInt(regionBox.getValue(regionBox.getSelectedIndex()));
	}

	@Override
	public int getSelectedComuna() {
		return Integer.parseInt(comunaBox.getValue(comunaBox.getSelectedIndex()));
	}

	@Override
	public String getSelectedDate() {
		Date d = desdeBox.getValue();
		if(d == null){
			return "";
		}else{
			return format.format(d);
		}
	}
*/
	@Override
	public void setReportes(HashMap<Integer, String> reportes) {
		reporteBox.clear();
		reporteBox.addItem("Seleccione reporte","-1");
		for(Entry<Integer,String> entry:reportes.entrySet()){
			reporteBox.addItem(entry.getValue(),entry.getKey()+"");
		}
	}

	@Override
	public int getSelectedReporte() {
		return Integer.parseInt(reporteBox.getValue(reporteBox.getSelectedIndex()));
	}
	
	@Override
	public void clear() {
		/*if(regionBox.getItemCount()>0){
			regionBox.setSelectedIndex(0);
		}
		if(comunaBox.getItemCount()>0){
			comunaBox.setSelectedIndex(0);
		}
		desdeBox.setValue(null);
		*/
		if(reporteBox.getItemCount()>0){
			reporteBox.setSelectedIndex(0);
		}
	}

}
