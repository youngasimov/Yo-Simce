package com.dreamer8.yosimce.client.material.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.PlaceHolderTextBox;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class BuscadorCodigoViewD extends Composite implements BuscadorCodigoView {

	private static BuscadorCodigoViewDUiBinder uiBinder = GWT
			.create(BuscadorCodigoViewDUiBinder.class);

	interface BuscadorCodigoViewDUiBinder extends
			UiBinder<Widget, BuscadorCodigoViewD> {
	}
	
	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField PlaceHolderTextBox codigoBox;
	@UiField Button searchButton;
	@UiField Button cleanButton;
	@UiField Label codigoLabel;
	@UiField Hyperlink coAsignadoLink;
	@UiField Hyperlink coIngresadoLink;
	@UiField Label tipoLabel;
	@UiField Label etapaLabel;
	@UiField Label rbdLabel;
	@UiField Label establecimientoLabel;
	@UiField Label nivelLabel;
	@UiField Label cursoLabel;
	@UiField Label tipoActividadLabel;
	@UiField(provided=true) DataGrid<HistorialMaterialItemDTO> historialGrid;
	
	private BuscadorCodigoPresenter presenter;
	private ListDataProvider<HistorialMaterialItemDTO> dataProvider;
	
	public BuscadorCodigoViewD() {
		
		historialGrid = new DataGrid<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		historialGrid.setPageSize(10);
		dataProvider = new ListDataProvider<HistorialMaterialItemDTO>();
		initWidget(uiBinder.createAndBindUi(this));
		dataProvider.addDataDisplay(historialGrid);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		
		buildHistorialTable();
	}
	
	@UiHandler("codigoBox")
	void onCodigoBoxKeyPress(KeyUpEvent event){
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			presenter.getDetalleCodigoMaterial(codigoBox.getValue());
		}
	}
	
	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event){
		presenter.getDetalleCodigoMaterial(codigoBox.getValue());
	}
	
	@UiHandler("cleanButton")
	void onCleanButtonClick(ClickEvent event){
		presenter.onClear();
	}
	
	@Override
	public void setPresenter(BuscadorCodigoPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setFocusOnCodigoBox() {
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				codigoBox.setFocus(true);
			}
		});
	}

	@Override
	public void setCodigo(String codigo) {
		codigoLabel.setText(codigo);
		codigoBox.setValue(codigo);
	}

	@Override
	public void setTipo(String tipo) {
		tipoLabel.setText(tipo);
	}

	@Override
	public void setEtapa(String etapa) {
		etapaLabel.setText(etapa);
	}

	@Override
	public void setRbd(String rbd) {
		rbdLabel.setText(rbd);
	}

	@Override
	public void setEstablecimiento(String establecimiento) {
		establecimientoLabel.setText(establecimiento);
	}

	@Override
	public void setNivel(String nivel) {
		nivelLabel.setText(nivel);
	}

	@Override
	public void setCurso(String curso) {
		cursoLabel.setText(curso);
	}

	@Override
	public void setTipoActividad(String tipoActividad) {
		tipoActividadLabel.setText(tipoActividad);
	}

	@Override
	public void setHistorial(ArrayList<HistorialMaterialItemDTO> historial) {
		dataProvider.setList(historial);
	}

	@Override
	public void setCentroOperacionAsignado(String nombre, String token) {
		coAsignadoLink.setText(nombre);
		coAsignadoLink.setTargetHistoryToken(token);
	}

	@Override
	public void setCentroOperacionIngresado(String nombre, String token) {
		coIngresadoLink.setText(nombre);
		coIngresadoLink.setTargetHistoryToken(token);
	}
	
	private void buildHistorialTable(){
		
		historialGrid.setAutoHeaderRefreshDisabled(true);		
		historialGrid.setAutoFooterRefreshDisabled(true);
		historialGrid.setPageSize(50);
		historialGrid.setPageStart(0);
		historialGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		
		DateTimeFormat dtf = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		Column<HistorialMaterialItemDTO,Date> dateColumn = new Column<HistorialMaterialItemDTO,Date>(new DateCell(dtf)){

			@Override
			public Date getValue(HistorialMaterialItemDTO object) {
				return object.getFecha();
			}
		};
		dateColumn.setSortable(true);
		historialGrid.addColumn(dateColumn,"Fecha");
		
		Column<HistorialMaterialItemDTO,String> desdeColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return object.getDesde();
			}
		};
		historialGrid.addColumn(desdeColumn,"Desde");
		
		Column<HistorialMaterialItemDTO,String> haciaColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return object.getHacia();
			}
		};
		historialGrid.addColumn(haciaColumn,"Hacia");
		
		Column<HistorialMaterialItemDTO,String> usuarioColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return (object.getReceptor()!=null)?object.getReceptor().getNombres()+" "+object.getReceptor().getApellidoPaterno()+" "+object.getReceptor().getApellidoMaterno():"";
			}
		};
		historialGrid.addColumn(usuarioColumn,"Usuario receptor");
	}

}
