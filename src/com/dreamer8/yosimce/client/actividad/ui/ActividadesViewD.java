package com.dreamer8.yosimce.client.actividad.ui;


import java.util.ArrayList;

import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.HyperTextCell;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ActividadesViewD extends Composite implements ActividadesView {

	private static ActividadesViewDUiBinder uiBinder = GWT
			.create(ActividadesViewDUiBinder.class);

	interface ActividadesViewDUiBinder extends
			UiBinder<Widget, ActividadesViewD> {
	}

	@UiField ImageButton filtrosButton;
	@UiField ImageButton exportarButton;
	@UiField Button sincronizacionButton;
	@UiField Button informacionButton;
	@UiField Button formularioButton;
	@UiField  HTML establecimientoSeleccionado;
	@UiField(provided = true) DataGrid<ActividadPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private SingleSelectionModel<ActividadPreviewDTO> selectionModel;
	private ActividadPreviewDTO selectedItem;
	private ActividadesPresenter presenter;
	
	private DialogBox filtrosDialogBox;
	private FiltroActividadesPanelViewD filtrosPanel;
	
	public ActividadesViewD() {
		dataGrid = new DataGrid<ActividadPreviewDTO>(ActividadPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		filtrosPanel = new FiltroActividadesPanelViewD();
		filtrosDialogBox = new DialogBox(true, false);
		filtrosDialogBox.setWidget(filtrosPanel);
		bind();
	}
	
	@UiHandler("filtrosButton")
	void onFiltrosClick(ClickEvent event){
		filtrosDialogBox.showRelativeTo(filtrosButton);
	}
	
	@UiHandler("exportarButton")
	void onExportarClick(ClickEvent event){
		presenter.onExportarClick();
	}
	
	@UiHandler("sincronizacionButton")
	void onDetallesClick(ClickEvent event){
		SincronizacionPlace place = new SincronizacionPlace();
		place.setIdCurso(selectedItem.getCursoId());
		presenter.goTo(place);
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		if(selectedItem !=null)dcp.setCursoId(selectedItem.getCursoId());
		presenter.goTo(dcp);
	}
	
	@UiHandler("formularioButton")
	void onFormularioClick(ClickEvent event){
		FormActividadPlace fap = new FormActividadPlace();
		if(selectedItem !=null)fap.setIdCurso(selectedItem.getCursoId());
		presenter.goTo(fap);
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}

	@Override
	public HasData<ActividadPreviewDTO> getDataDisplay() {
		return dataGrid;
	}

	@Override
	public Column<ActividadPreviewDTO, ?> getColumn(int index) {
		return dataGrid.getColumn(index);
	}

	@Override
	public int getColumnIndex(Column<ActividadPreviewDTO, ?> column) {
		return dataGrid.getColumnIndex(column);
	}

	@Override
	public void clearCursoSelection() {
		if(selectedItem !=null){
			selectionModel.setSelected(selectedItem, false);
			selectedItem = null;
		}
	}

	@Override
	public void setPresenter(ActividadesPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setActividadesNoIniciadas(boolean value) {
		//filtrosPanel.noIniciadasBox.setValue(value);
	}

	@Override
	public void setActividadesTerminadas(boolean value) {
		//filtrosPanel.terminadasBox.setValue(value);
	}

	@Override
	public void setActividadesContingencia(boolean value) {
		filtrosPanel.contingenciaBox.setValue(value);
	}

	@Override
	public void setActividadesProblema(boolean value) {
		filtrosPanel.problemasBox.setValue(value);
	}
	
	@Override
	public void setActividadesSincronizadas(boolean value){
		//filtrosPanel.sincronizadasBox.setValue(value);
	}

	@Override
	public void setRegiones(ArrayList<SectorDTO> regiones) {
		filtrosPanel.regionBox.setVisible(true);
		filtrosPanel.regionBox.clear();
		filtrosPanel.regionBox.addItem("todas",-1+"");
		filtrosPanel.comunaBox.setVisible(false);
		for(SectorDTO s:regiones){
			filtrosPanel.regionBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}

	@Override
	public void setComunas(ArrayList<SectorDTO> comunas) {
		filtrosPanel.comunaBox.setVisible(true);
		filtrosPanel.comunaBox.clear();
		filtrosPanel.comunaBox.addItem("todas","-1");
		for(SectorDTO s:comunas){
			filtrosPanel.comunaBox.addItem(s.getSector(),s.getIdSector()+"");
		}
	}

	@Override
	public void setSelectedRegion(int regionId) {
		for(int i=0;i<filtrosPanel.regionBox.getItemCount();i++){
			if(Integer.parseInt(filtrosPanel.regionBox.getValue(i)) == regionId){
				filtrosPanel.regionBox.setItemSelected(i, true);
			}
		}
	}

	@Override
	public void setSelectedComuna(int comunaId) {
		for(int i=0;i<filtrosPanel.comunaBox.getItemCount();i++){
			if(Integer.parseInt(filtrosPanel.comunaBox.getValue(i)) == comunaId){
				filtrosPanel.comunaBox.setItemSelected(i, true);
			}
		}
	}
	
	private void bind(){
		dataGrid.setWidth("100%");
		buildGrid();
		sincronizacionButton.setVisible(false);
		informacionButton.setVisible(false);
		formularioButton.setVisible(false);
		
		pager.setDisplay(dataGrid);
		
		selectionModel = new SingleSelectionModel<ActividadPreviewDTO>(ActividadPreviewDTO.KEY_PROVIDER);
		
		dataGrid.setSelectionModel(selectionModel,new CellPreviewEvent.Handler<ActividadPreviewDTO>(){

			@Override
			public void onCellPreview(CellPreviewEvent<ActividadPreviewDTO> event) {
				if(!event.getNativeEvent().getType().contains("click")){
					return;
				}
				selectionModel.setSelected(event.getValue(), !selectionModel.isSelected(event.getValue()));
			}});
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				establecimientoSeleccionado.setHTML((selectionModel.getSelectedObject()!=null)?
						ViewUtils.limitarString(selectionModel.getSelectedObject().getNombreEstablecimiento(),27)+" >":"");
				sincronizacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				informacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				formularioButton.setVisible(selectionModel.getSelectedObject()!=null);
				selectedItem = selectionModel.getSelectedObject();
			}
		});
		
		dataGrid.addRangeChangeHandler(new RangeChangeEvent.Handler() {
			
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				presenter.onRangeChange(event.getNewRange());
			}
		});
		
		filtrosPanel.regionBox.addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent event) {
				presenter.onRegionChange(Integer.parseInt(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())));
			}
		});
		
		filtrosPanel.cancelarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				presenter.onCancelarFiltroClick();
				filtrosDialogBox.hide();
			}
		});
		
		filtrosPanel.aplicarButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ActividadesPlace ap = new ActividadesPlace();
				//ap.setShowActividadesNoInciadas(filtrosPanel.noIniciadasBox.getValue());
				//ap.setShowActividadesTerminadas(filtrosPanel.terminadasBox.getValue());
				ap.setShowActividadesContingencia(filtrosPanel.contingenciaBox.getValue());
				ap.setShowActividadesProblema(filtrosPanel.problemasBox.getValue());
				//ap.setShowActividadesSincronizadas(filtrosPanel.sincronizadasBox.getValue());
				if(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())!="-1"){
					ap.setRegionId(Integer.parseInt(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())));
				}
				if(filtrosPanel.comunaBox.getValue(filtrosPanel.comunaBox.getSelectedIndex())!="-1"){
					ap.setComunaId(Integer.parseInt(filtrosPanel.comunaBox.getValue(filtrosPanel.comunaBox.getSelectedIndex())));
				}
				filtrosDialogBox.hide();
				presenter.goTo(ap);
			}
		});
	}

	
	private void buildGrid(){
		
		Column<ActividadPreviewDTO,String> rbdColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getRbd();
			}
		};
		rbdColumn.setSortable(false);
		dataGrid.addColumn(rbdColumn,"RBD");
		dataGrid.setColumnWidth(rbdColumn, "60px");
		
		Column<ActividadPreviewDTO,String> nombreColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getNombreEstablecimiento();
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.addColumn(nombreColumn,"Establecimiento");
		dataGrid.setColumnWidth(nombreColumn, "220px");
		
		Column<ActividadPreviewDTO,String> cursoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getCurso();
			}
		};
		cursoColumn.setSortable(false);
		dataGrid.addColumn(cursoColumn,"Curso");
		dataGrid.setColumnWidth(cursoColumn, "50px");
		
		Column<ActividadPreviewDTO,String> tipoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getTipoEstablecimiento();
			}
		};
		tipoColumn.setSortable(false);
		dataGrid.addColumn(tipoColumn,"Tipo");
		dataGrid.setColumnWidth(tipoColumn, "100px");
		
		Column<ActividadPreviewDTO,String> estadoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getEstadoAgenda();
			}
		};
		estadoColumn.setSortable(false);
		dataGrid.addColumn(estadoColumn,"Estado");
		dataGrid.setColumnWidth(estadoColumn, "120px");
		
		Column<ActividadPreviewDTO,Number> cuestionarioColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				if(o.getCuestionariosPadresApoderadosEntregados() <= 0){
					return 0;
				}
				float value =100 * ((float)o.getCuestionariosPadresApoderadosRecibidos())/((float)o.getCuestionariosPadresApoderadosEntregados());
				return value;
			}
		};
		SafeHtmlBuilder b = new SafeHtmlBuilder();
		b.appendHtmlConstant("Cuestionarios recibidos %");
		cuestionarioColumn.setSortable(false);
		dataGrid.addColumn(cuestionarioColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(cuestionarioColumn, "150px");
		
		Column<ActividadPreviewDTO,Number> asistenciaColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				if(o.getAlumnosTotales() <= 0){
					return 0;
				}
				float value =100 * ((float)o.getAlumnosEvaluados())/((float)o.getAlumnosTotales());
				return value;
			}
		};
		b = new SafeHtmlBuilder();
		b.appendHtmlConstant("Asistencia %");
		asistenciaColumn.setSortable(false);
		dataGrid.addColumn(asistenciaColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(asistenciaColumn, "150px");
		
		Column<ActividadPreviewDTO,Number> sincronizacionColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				if(o.getAlumnosEvaluados()<=0){
					return 0;
				}
				float value = 100 * ((float)o.getAlumnosSincronizados())/((float)o.getAlumnosEvaluados());
				return value;
			}
		};
		b = new SafeHtmlBuilder();
		b.appendHtmlConstant("Sincronizados %");
		sincronizacionColumn.setSortable(false);
		dataGrid.addColumn(sincronizacionColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(sincronizacionColumn, "150px");
		
		Column<ActividadPreviewDTO,String> contingenciaColumn = new Column<ActividadPreviewDTO, String>(new ImageCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getContingencia()){
					return "/images/warning.png";
				}else{
					return "";
				}
			}
		};
		contingenciaColumn.setSortable(false);
		dataGrid.addColumn(contingenciaColumn,"Contingencia");
		dataGrid.setColumnWidth(contingenciaColumn, "100px");
		
		Column<ActividadPreviewDTO,String> contingenciaLimitanteColumn = new Column<ActividadPreviewDTO, String>(new ImageCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getContingenciaLimitante()){
					return "/images/warning.png";
				}else{
					return "";
				}
			}
		};
		contingenciaLimitanteColumn.setSortable(false);
		dataGrid.addColumn(contingenciaLimitanteColumn,"Limitante");
		dataGrid.setColumnWidth(contingenciaLimitanteColumn, "100px");
		
		Column<ActividadPreviewDTO,String> regionColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getRegion();
			}
		};
		regionColumn.setSortable(false);
		dataGrid.addColumn(regionColumn,"Región");
		dataGrid.setColumnWidth(regionColumn, "150px");
		
		Column<ActividadPreviewDTO,String> comunaColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getComuna();
			}
		};
		comunaColumn.setSortable(false);
		dataGrid.addColumn(comunaColumn,"Comuna");
		dataGrid.setColumnWidth(comunaColumn, "150px");
		
		Column<ActividadPreviewDTO,DocumentoDTO> docColumn = new Column<ActividadPreviewDTO, DocumentoDTO>(new HyperTextCell()) {

			@Override
			public DocumentoDTO getValue(ActividadPreviewDTO o) {
				return o.getDocumento();
			}
		};
		docColumn.setSortable(false);
		dataGrid.addColumn(docColumn,"Documento");
		dataGrid.setColumnWidth(docColumn, "150px");
		
	}
}
