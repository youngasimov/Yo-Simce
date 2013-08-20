package com.dreamer8.yosimce.client.actividad.ui;


import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
	@UiField  HTML establecimientoSeleccionado;
	@UiField(provided = true) DataGrid<ActividadPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private SingleSelectionModel<ActividadPreviewDTO> selectionModel;
	private ActividadPreviewDTO selectedItem;
	private ActividadesPresenter presenter;
	
	public ActividadesViewD() {
		dataGrid = new DataGrid<ActividadPreviewDTO>(ActividadPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		bind();
	}
	
	@UiHandler("filtrosButton")
	void onFiltrosClick(ClickEvent event){
		
	}
	
	@UiHandler("exportarButton")
	void onExportarClick(ClickEvent event){
		presenter.onExportarClick();
	}
	
	@UiHandler("sincronizacionButton")
	void onDetallesClick(ClickEvent event){
		SincronizacionPlace place = new SincronizacionPlace();
		place.setIdSincronizacion(selectedItem.getCursoId());
		presenter.goTo(place);
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		if(selectedItem !=null)dcp.setCursoId(selectedItem.getCursoId());
		presenter.goTo(dcp);
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
	
	private void bind(){
		dataGrid.setWidth("100%");
		buildGrid();
		sincronizacionButton.setVisible(false);
		informacionButton.setVisible(false);
		
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
				establecimientoSeleccionado.setHTML((selectionModel.getSelectedObject()!=null)?selectionModel.getSelectedObject().getNombreEstablecimiento()+" >":"");
				sincronizacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				informacionButton.setVisible(selectionModel.getSelectedObject()!=null);
				selectedItem = selectionModel.getSelectedObject();
			}
		});
		
		dataGrid.addRangeChangeHandler(new RangeChangeEvent.Handler() {
			
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				presenter.onRangeChange(event.getNewRange());
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
		dataGrid.setColumnWidth(rbdColumn, "50px");
		
		Column<ActividadPreviewDTO,String> nombreColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getNombreEstablecimiento().length()>20){
					return o.getNombreEstablecimiento().substring(0,20)+"...";
				}else{
					return o.getNombreEstablecimiento();
				}
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.addColumn(nombreColumn,"Establecimiento");
		dataGrid.setColumnWidth(nombreColumn, "200px");
		
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
		
		Column<ActividadPreviewDTO,Number> cuestionarioColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				float value =100 * ((float)o.getCuestionariosPadresApoderadosRecibidos())/((float)o.getCuestionariosPadresApoderadosEntregados());
				return value;
			}
		};
		SafeHtmlBuilder b = new SafeHtmlBuilder();
		b.appendHtmlConstant("Cuestionarios<br />Recibidos vs Entregados");
		cuestionarioColumn.setSortable(false);
		dataGrid.addColumn(cuestionarioColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(cuestionarioColumn, "150px");
		
		Column<ActividadPreviewDTO,Number> asistenciaColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				float value =100 * ((float)o.getAlumnosEvaluados())/((float)o.getAlumnosTotales());
				return value;
			}
		};
		b = new SafeHtmlBuilder();
		b.appendHtmlConstant("Asistencia<br />Evaluados vs Total");
		asistenciaColumn.setSortable(false);
		dataGrid.addColumn(asistenciaColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(asistenciaColumn, "150px");
		
		Column<ActividadPreviewDTO,Number> sincronizacionColumn = new Column<ActividadPreviewDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(ActividadPreviewDTO o) {
				float value = 100 * ((float)o.getAlumnosSincronizados())/((float)o.getAlumnosEvaluados());
				return value;
			}
		};
		b = new SafeHtmlBuilder();
		b.appendHtmlConstant("Sinc<br />Sincronizados vs Evaluados");
		sincronizacionColumn.setSortable(false);
		dataGrid.addColumn(sincronizacionColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(sincronizacionColumn, "150px");
		
		Column<ActividadPreviewDTO,String> contingenciaColumn = new Column<ActividadPreviewDTO, String>(new ImageCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getContingencia()){
					return SimceResources.INSTANCE.ok().getSafeUri().asString();
				}else{
					return "";
				}
			}
		};
		contingenciaColumn.setSortable(false);
		dataGrid.addColumn(contingenciaColumn,"Contingencia");
		dataGrid.setColumnWidth(contingenciaColumn, "150px");
		
		Column<ActividadPreviewDTO,String> contingenciaLimitanteColumn = new Column<ActividadPreviewDTO, String>(new ImageCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getContingenciaLimitante()){
					return SimceResources.INSTANCE.ok().getSafeUri().asString();
				}else{
					return "";
				}
			}
		};
		contingenciaLimitanteColumn.setSortable(false);
		dataGrid.addColumn(contingenciaLimitanteColumn,"Limitante");
		dataGrid.setColumnWidth(contingenciaLimitanteColumn, "150px");
		
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
		dataGrid.addColumn(comunaColumn,"Región");
		dataGrid.setColumnWidth(comunaColumn, "150px");
		
	}
}
