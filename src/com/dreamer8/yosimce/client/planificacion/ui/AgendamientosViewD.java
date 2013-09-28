package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
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

public class AgendamientosViewD extends Composite implements AgendamientosView {

	private static AgendamientosViewDUiBinder uiBinder = GWT
			.create(AgendamientosViewDUiBinder.class);

	interface AgendamientosViewDUiBinder extends
			UiBinder<Widget, AgendamientosViewD> {
	}
	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarItem;
	@UiField MenuItem cursoItem;
	@UiField MenuItem modificarItem;
	@UiField MenuItem detallesItem;
	@UiField MenuItem informacionItem;
	@UiField(provided = true) DataGrid<AgendaPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	
	private HashMap<Integer,CheckBox> estadoCheckBoxs;
	
	private AgendamientosPresenter presenter;
	private SingleSelectionModel<AgendaPreviewDTO> selectionModel;
	private AgendaPreviewDTO selectedItem;
	
	private DialogBox filtrosDialogBox;
	private FiltroAgendamientosPanelViewD filtrosPanel;
	
	private boolean modificarAgendaVisible;
	private boolean detalleVisible;
	private boolean informacionVisible;
	private DateTimeFormat format;
	
	
	public AgendamientosViewD() {
		dataGrid = new DataGrid<AgendaPreviewDTO>(AgendaPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		filtrosDialogBox = new DialogBox(true, false);
		filtrosPanel = new FiltroAgendamientosPanelViewD();
		filtrosDialogBox.setWidget(filtrosPanel);
		estadoCheckBoxs = new HashMap<Integer,CheckBox>();
		format = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		menu.insertSeparator(1);
		menu.insertSeparator(4);
		
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
		
		exportarItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onExportarClick();
			}
		});
		
		modificarItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				AgendarVisitaPlace avp = new AgendarVisitaPlace();
				if(selectedItem !=null)avp.setCursoId(selectedItem.getCursoId());
				presenter.goTo(avp);
			}
		});
		
		detallesItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				DetalleAgendaPlace daep = new DetalleAgendaPlace();
				if(selectedItem !=null)daep.setCursoId(selectedItem.getCursoId());
				presenter.goTo(daep);
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
	public void setPresenter(AgendamientosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<AgendaPreviewDTO> getDataDisplay() {
		return dataGrid;
	}

	@Override
	public Column<AgendaPreviewDTO,?> getColumn(int index) {
		return dataGrid.getColumn(index);
	}

	@Override
	public int getColumnIndex(Column<AgendaPreviewDTO,?> column) {
		return dataGrid.getColumnIndex(column);
	}
	
	@Override
	public void clear() {
		if(selectedItem !=null){
			selectionModel.setSelected(selectedItem, false);
			selectedItem = null;
		}
		cursoItem.setText("");
		modificarAgendaVisible = false;
		detalleVisible = false;
		informacionVisible = false;
		estadoCheckBoxs.clear();
		filtrosPanel.estadosPanel.clear();
	}
	
	@Override
	public void setExportarVisivility(boolean visible) {
		exportarItem.setVisible(visible);
	}
	
	@Override
	public void setModificarAgendaVisivility(boolean visible) {
		modificarAgendaVisible = visible;
	}
	
	@Override
	public void setDetallesAgendaVisivility(boolean visible) {
		detalleVisible = visible;
	}
	
	@Override
	public void setInformacionGeneralVisivility(boolean visible) {
		informacionVisible = visible;
	}

	@Override
	public void setEstados(ArrayList<EstadoAgendaDTO> estados) {
		estadoCheckBoxs.clear();
		filtrosPanel.estadosPanel.clear();
		for(EstadoAgendaDTO ea:estados){
			CheckBox cb = new CheckBox(ea.getEstado());
			estadoCheckBoxs.put(ea.getId(), cb);
			filtrosPanel.estadosPanel.add(cb);
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
	public void setDesde(Date date) {
		filtrosPanel.desdeBox.setValue(date);
	}

	@Override
	public void setHasta(Date date) {
		filtrosPanel.hastaBox.setValue(date);
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
		modificarItem.setVisible(false);
		detallesItem.setVisible(false);
		informacionItem.setVisible(false);
		cursoItem.setVisible(false);
		
		pager.setDisplay(dataGrid);
		
		dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
		selectionModel = new SingleSelectionModel<AgendaPreviewDTO>(AgendaPreviewDTO.KEY_PROVIDER);
		
		dataGrid.setSelectionModel(selectionModel,new CellPreviewEvent.Handler<AgendaPreviewDTO>(){

			@Override
			public void onCellPreview(CellPreviewEvent<AgendaPreviewDTO> event) {
				if(!event.getNativeEvent().getType().contains("click")){
					return;
				}
				selectionModel.setSelected(event.getValue(), !selectionModel.isSelected(event.getValue()));
			}});
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				cursoItem.setText((selectionModel.getSelectedObject()!=null)?
						ViewUtils.limitarString(selectionModel.getSelectedObject().getEstablecimientoName(),40):"");
				
				modificarItem.setVisible(selectionModel.getSelectedObject()!=null && modificarAgendaVisible);
				detallesItem.setVisible(selectionModel.getSelectedObject()!=null && detalleVisible);
				informacionItem.setVisible(selectionModel.getSelectedObject()!=null && informacionVisible);
				cursoItem.setVisible(modificarItem.isVisible() || detallesItem.isVisible() || informacionItem.isVisible());
				selectedItem = selectionModel.getSelectedObject();
			}
		});
		
		dataGrid.addRangeChangeHandler(new RangeChangeEvent.Handler() {
			
			@Override
			public void onRangeChange(RangeChangeEvent event) {
				presenter.onRangeChange(event.getNewRange());
			}
		});
		
		filtrosPanel.regionBox.addChangeHandler(new ChangeHandler() {
			
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
				AgendamientosPlace p = new AgendamientosPlace();
				if(filtrosPanel.desdeBox.getValue()!=null){
					p.setDesdeTimestamp(filtrosPanel.desdeBox.getValue().getTime());
				}
				if(filtrosPanel.hastaBox.getValue()!=null){
					p.setHastaTimestamp(filtrosPanel.hastaBox.getValue().getTime());
				}
				if(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())!="-1"){
					p.setRegionId(Integer.parseInt(filtrosPanel.regionBox.getValue(filtrosPanel.regionBox.getSelectedIndex())));
				}
				if(filtrosPanel.comunaBox.isVisible() && filtrosPanel.comunaBox.getValue(filtrosPanel.comunaBox.getSelectedIndex())!="-1"){
					p.setComunaId(Integer.parseInt(filtrosPanel.comunaBox.getValue(filtrosPanel.comunaBox.getSelectedIndex())));
				}
				ArrayList<Integer> es = new ArrayList<Integer>();
				for(Entry<Integer,CheckBox> e:estadoCheckBoxs.entrySet()){
					if(e.getValue().getValue()){
						es.add(e.getKey());
					}
				}
				if(es.size()>0){
					p.setEstadosSeleccionados(es);
				}
				filtrosDialogBox.hide();
				presenter.goTo(p);
			}
		});
	}

	
	private void buildGrid(){
		Column<AgendaPreviewDTO, String> rbdColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getRbd();
            }
        };
        rbdColumn.setSortable(false);
        dataGrid.addColumn(rbdColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("RBD")));
        dataGrid.setColumnWidth(rbdColumn, 60, Unit.PX);
        
        Column<AgendaPreviewDTO, String> establecimientoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getEstablecimientoName();
            }
        };
        establecimientoColumn.setSortable(false);
        dataGrid.addColumn(establecimientoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Establecimiento")));
        dataGrid.setColumnWidth(establecimientoColumn, 250, Unit.PX);
        
        Column<AgendaPreviewDTO, String> cursoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getCurso();
            }
        };
        cursoColumn.setSortable(false);
        dataGrid.addColumn(cursoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Curso")));
        dataGrid.setColumnWidth(cursoColumn, 60, Unit.PX);
        
        Column<AgendaPreviewDTO, String> tipoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getTipoEstablecimiento().getTipo();
            }
        };
        tipoColumn.setSortable(false);
        dataGrid.addColumn(tipoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Tipo")));
        dataGrid.setColumnWidth(tipoColumn, 100, Unit.PX);
        
        Column<AgendaPreviewDTO, String> estadoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getAgendaItemActual().getEstado().getEstado();
            }
        };
        estadoColumn.setSortable(false);
        dataGrid.addColumn(estadoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Estado actual")));
        dataGrid.setColumnWidth(estadoColumn, 140, Unit.PX);
        
        Column<AgendaPreviewDTO, Date> dateColumn =new Column<AgendaPreviewDTO, Date>(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM))) {
            @Override
            public Date getValue(AgendaPreviewDTO object) {
                return (object.getAgendaItemActual()==null || object.getAgendaItemActual().getFecha() == null || object.getAgendaItemActual().getFecha().isEmpty())?null:format.parse(object.getAgendaItemActual().getFecha());
            }
        };
        dateColumn.setSortable(false);
        dataGrid.addColumn(dateColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Fecha")));
        dataGrid.setColumnWidth(dateColumn, 160, Unit.PX);
        
        Column<AgendaPreviewDTO, String> regionColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	if(object.getRegionName().startsWith("Región")){
            		return object.getRegionName().substring(6);
            	}
                return object.getRegionName();
            }
        };
        regionColumn.setSortable(false);
        dataGrid.addColumn(regionColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Región")));
        dataGrid.setColumnWidth(regionColumn, 140, Unit.PX);
        
        Column<AgendaPreviewDTO, String> comunaColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
                return object.getComunaName();
            }
        };
        comunaColumn.setSortable(false);
        dataGrid.addColumn(comunaColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Comuna")));
        dataGrid.setColumnWidth(comunaColumn, 120, Unit.PX);
        
        Column<AgendaPreviewDTO, String> comentarioColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	if(object.getAgendaItemActual().getComentario()!=null){
            		return ViewUtils.limitarString(object.getAgendaItemActual().getComentario(), 30);
            	}else{
            		return "";
            	}
            	
            }
        };
        comentarioColumn.setSortable(false);
        dataGrid.addColumn(comentarioColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Comentario")));
        dataGrid.setColumnWidth(comentarioColumn, 250, Unit.PX);
        
        Column<AgendaPreviewDTO, String> examinadorColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	return (object.getExaminador()!=null &&
            			object.getExaminador().getNombres()!= null &&
            			object.getExaminador().getApellidoPaterno() != null)?object.getExaminador().getNombres() +" "+object.getExaminador().getApellidoPaterno() :"";
            }
        };
        examinadorColumn.setSortable(false);
        dataGrid.addColumn(examinadorColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Examinador")));
        dataGrid.setColumnWidth(examinadorColumn, 220, Unit.PX);
        
        Column<AgendaPreviewDTO, String> supervisorColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	return (object.getSupervisor()!=null &&
            			object.getSupervisor().getNombres()!= null &&
            			object.getSupervisor().getApellidoPaterno() != null)?object.getSupervisor().getNombres() +" "+object.getSupervisor().getApellidoPaterno() :"";
            }
        };
        supervisorColumn.setSortable(false);
        dataGrid.addColumn(supervisorColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Supervisor")));
        dataGrid.setColumnWidth(supervisorColumn, 220, Unit.PX);
        
        Column<AgendaPreviewDTO, String> totalAlumnosColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	return (object.getTotalAlumnos()!=null)?object.getTotalAlumnos()+"":"0";
            }
        };
        totalAlumnosColumn.setSortable(false);
        dataGrid.addColumn(totalAlumnosColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("T. alumnos")));
        dataGrid.setColumnWidth(totalAlumnosColumn, 100, Unit.PX);
	}
}
