package com.dreamer8.yosimce.client.actividad.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.HyperTextCell;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuItem;
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

	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarActividadesItem;
	@UiField MenuItem exportarAlumnosItem;
	@UiField MenuItem cursoItem;
	@UiField MenuItem formItem;
	@UiField MenuItem sincronizacionItem;
	@UiField MenuItem informacionItem;
	
	@UiField(provided = true) DataGrid<ActividadPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private HashMap<Integer,CheckBox> estadoCheckBoxs;
	
	private SingleSelectionModel<ActividadPreviewDTO> selectionModel;
	private ActividadPreviewDTO selectedItem;
	private ActividadesPresenter presenter;
	
	private DialogBox filtrosDialogBox;
	private FiltroActividadesPanelViewD filtrosPanel;
	
	private boolean sinc;
	private boolean form;
	private boolean info;
	
	public ActividadesViewD() {
		dataGrid = new DataGrid<ActividadPreviewDTO>(ActividadPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		filtrosPanel = new FiltroActividadesPanelViewD();
		filtrosDialogBox = new DialogBox(true, false);
		filtrosDialogBox.setWidget(filtrosPanel);
		estadoCheckBoxs = new HashMap<Integer,CheckBox>();
		
		menu.insertSeparator(1);
		menu.insertSeparator(5);
		
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		
		filtrosItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				filtrosDialogBox.showRelativeTo(filtrosItem);
			}
		});
		
		exportarActividadesItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onExportarClick();
			}
		});
		
		exportarAlumnosItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onExportarAlumnosClick();
			}
		});
		
		formItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				FormActividadPlace fap = new FormActividadPlace();
				if(selectedItem !=null)fap.setIdCurso(selectedItem.getCursoId());
				presenter.goTo(fap);
			}
		});
		
		sincronizacionItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				SincronizacionPlace place = new SincronizacionPlace();
				place.setIdCurso(selectedItem.getCursoId());
				presenter.goTo(place);
			}
		});
		
		informacionItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				DetalleCursoPlace dcp = new DetalleCursoPlace();
				if(selectedItem !=null)dcp.setCursoId(selectedItem.getCursoId());
				presenter.goTo(dcp);
			}
		});
		
		bind();
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@Override
	public void setExportarActividadesVisivility(boolean visible) {
		exportarActividadesItem.setVisible(visible);
	}
	
	@Override
	public void setExportarAlumnosVisivility(boolean visible) {
		exportarAlumnosItem.setVisible(visible);
	}
	
	@Override
	public void setSincronizacionVisivility(boolean visible) {
		this.sinc = visible;
	}
	
	@Override
	public void setFormularioVisivility(boolean visible) {
		this.form = visible;
	}
	
	@Override
	public void setInformacionVisivility(boolean visible) {
		this.info = visible;
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
	public void clear() {
		if(selectedItem !=null){
			selectionModel.setSelected(selectedItem, false);
			selectedItem = null;
		}
		sinc = false;
		form = false;
		info = false;
		cursoItem.setHTML("");
		estadoCheckBoxs.clear();
		filtrosPanel.estadosPanel.clear();
	}

	@Override
	public void setPresenter(ActividadesPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setActividadesMaterialContingencia(boolean value) {
		filtrosPanel.contingenciaBox.setValue(value);
	}
	
	@Override
	public void setActividadesContingencia(boolean value) {
		filtrosPanel.problemasBox.setValue(value);
	}

	@Override
	public void setActividadesContingenciaInhabilitante(boolean value) {
		filtrosPanel.problemasHinabilitantesBox.setValue(value);
	}
	
	@Override
	public void setActividadesSincronizadas(boolean value){
		filtrosPanel.sincronizacionTotalBox.setValue(value);
	}

	@Override
	public void setActividadesParcialementeSincronizadas(boolean value) {
		filtrosPanel.sincronizacionParcialBox.setValue(value);
	}

	@Override
	public void setActividadesNoSincronizadas(boolean value) {
		filtrosPanel.sincronizadasNulaBox.setValue(value);
	}

	@Override
	public void setSelectedEstados(ArrayList<Integer> estados) {
		for(Entry<Integer,CheckBox> e:estadoCheckBoxs.entrySet()){
			e.getValue().setValue(false);
		}
		for(Integer id:estados){
			if(estadoCheckBoxs.containsKey(id)){
				estadoCheckBoxs.get(id).setValue(true);
			}
		}
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
		sincronizacionItem.setVisible(false);
		informacionItem.setVisible(false);
		formItem.setVisible(false);
		
		pager.setDisplay(dataGrid);
		
		dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
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
				cursoItem.setHTML((selectionModel.getSelectedObject()!=null)?
						ViewUtils.limitarString(selectionModel.getSelectedObject().getNombreEstablecimiento(),40):"");
				sincronizacionItem.setVisible(selectionModel.getSelectedObject()!=null && sinc);
				informacionItem.setVisible(selectionModel.getSelectedObject()!=null && info);
				formItem.setVisible(selectionModel.getSelectedObject()!=null && form);
				cursoItem.setVisible(sincronizacionItem.isVisible() || informacionItem.isVisible() || formItem.isVisible());
				
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
				ap.setActividadesContintencia(filtrosPanel.problemasBox.getValue());
				ap.setActividadesContintenciaInhabilitante(filtrosPanel.problemasHinabilitantesBox.getValue());
				ap.setActividadesMaterialContintencia(filtrosPanel.contingenciaBox.getValue());
				ap.setActividadesSincronizadas(filtrosPanel.sincronizacionTotalBox.getValue());
				ap.setActividadesParcialmenteSincronizadas(filtrosPanel.sincronizacionParcialBox.getValue());
				ap.setActividadesNoSincronizadas(filtrosPanel.sincronizadasNulaBox.getValue());
				if(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())!="-1"){
					ap.setRegionId(Integer.parseInt(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())));
				}
				if(filtrosPanel.comunaBox.isVisible() && filtrosPanel.comunaBox.getValue(filtrosPanel.comunaBox.getSelectedIndex())!="-1"){
					ap.setComunaId(Integer.parseInt(filtrosPanel.comunaBox.getValue(filtrosPanel.comunaBox.getSelectedIndex())));
				}
				ArrayList<Integer> es = new ArrayList<Integer>();
				for(Entry<Integer,CheckBox> e:estadoCheckBoxs.entrySet()){
					if(e.getValue().getValue()){
						es.add(e.getKey());
					}
				}
				if(es.size()>0){
					ap.setEstadosSeleccionados(es);
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
		dataGrid.setColumnWidth(rbdColumn, 60, Unit.PX);
		
		Column<ActividadPreviewDTO,String> establecimientoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getNombreEstablecimiento();
			}
		};
		establecimientoColumn.setSortable(false);
		dataGrid.addColumn(establecimientoColumn,"Establecimiento");
		dataGrid.setColumnWidth(establecimientoColumn, 250, Unit.PX);
		
		Column<ActividadPreviewDTO,String> cursoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getCurso();
			}
		};
		cursoColumn.setSortable(false);
		dataGrid.addColumn(cursoColumn,"Curso");
		dataGrid.setColumnWidth(cursoColumn, 60, Unit.PX);
		
		Column<ActividadPreviewDTO,String> tipoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getTipoEstablecimiento();
			}
		};
		tipoColumn.setSortable(false);
		dataGrid.addColumn(tipoColumn,"Tipo");
		dataGrid.setColumnWidth(tipoColumn, 100, Unit.PX);
		
		Column<ActividadPreviewDTO,String> estadoColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getEstadoAgenda();
			}
		};
		estadoColumn.setSortable(false);
		dataGrid.addColumn(estadoColumn,"Estado");
		dataGrid.setColumnWidth(estadoColumn, 140, Unit.PX);
		
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
		b.appendHtmlConstant("% Cuestionarios");
		cuestionarioColumn.setSortable(false);
		dataGrid.addColumn(cuestionarioColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(cuestionarioColumn, 100, Unit.PX);
		
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
		b.appendHtmlConstant("% Asistencia");
		asistenciaColumn.setSortable(false);
		dataGrid.addColumn(asistenciaColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(asistenciaColumn, 100, Unit.PX);
		
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
		b.appendHtmlConstant("% Sincronizados");
		sincronizacionColumn.setSortable(false);
		dataGrid.addColumn(sincronizacionColumn,b.toSafeHtml());
		dataGrid.setColumnWidth(sincronizacionColumn, 100, Unit.PX);
		
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
		dataGrid.setColumnWidth(contingenciaColumn, 100, Unit.PX);
		
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
		dataGrid.setColumnWidth(contingenciaLimitanteColumn, 100, Unit.PX);
		
		Column<ActividadPreviewDTO,String> regionColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				if(o.getRegion().startsWith("Región")){
            		return o.getRegion().substring(6);
            	}
                return o.getRegion();
			}
		};
		regionColumn.setSortable(false);
		dataGrid.addColumn(regionColumn,"Región");
		dataGrid.setColumnWidth(regionColumn, 140, Unit.PX);
		
		Column<ActividadPreviewDTO,String> comunaColumn = new Column<ActividadPreviewDTO, String>(new TextCell()) {

			@Override
			public String getValue(ActividadPreviewDTO o) {
				return o.getComuna();
			}
		};
		comunaColumn.setSortable(false);
		dataGrid.addColumn(comunaColumn,"Comuna");
		dataGrid.setColumnWidth(comunaColumn, 120, Unit.PX);
		
		Column<ActividadPreviewDTO,DocumentoDTO> docColumn = new Column<ActividadPreviewDTO, DocumentoDTO>(new HyperTextCell()) {

			@Override
			public DocumentoDTO getValue(ActividadPreviewDTO o) {
				return o.getDocumento();
			}
		};
		docColumn.setSortable(false);
		dataGrid.addColumn(docColumn,"Documento");
		dataGrid.setColumnWidth(docColumn, 200, Unit.PX);
		
	}

	@Override
	public void setEstadosActividad(ArrayList<EstadoAgendaDTO> estados) {
		estadoCheckBoxs.clear();
		filtrosPanel.estadosPanel.clear();
		for(EstadoAgendaDTO ea:estados){
			CheckBox cb = new CheckBox(ea.getEstado());
			estadoCheckBoxs.put(ea.getId(), cb);
			filtrosPanel.estadosPanel.add(cb);
		}
	}
}
