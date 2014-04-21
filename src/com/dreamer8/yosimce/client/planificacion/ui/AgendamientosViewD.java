package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.ui.AgendarVisitaView.AgendarVisitaPresenter;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox.TIME_PRECISION;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
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
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
	
	interface Style extends CssResource {
		String line();
	}
	
	@UiField Style style;
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem buscarItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarItem;
	@UiField MenuItem cursoItem;
	@UiField(provided = true) DataGrid<AgendaPreviewDTO> dataGrid;
	@UiField(provided = true) SimplePager pager;
	@UiField Label colegioLabel;
	@UiField Label rbdLabel;
	@UiField Label regionLabel;
	@UiField Label comunaLabel;
	@UiField Label direccionLabel;
	@UiField Label cursoLabel;
	@UiField Label tipoLabel;
	@UiField Label centroOperacionLabel;
	@UiField FlexTable contactosTable;
	@UiField FlexTable personasTable;
	@UiField Button editContactoButton;
	@UiField Button editDirectorButton;
	@UiField Button agendarButton;
	@UiField ListBox tipoActividadBox;
	
	@UiField(provided=true) CellList<AgendaItemDTO> agendaList;
	
	
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
	
	private AgendaCell cell;
	
	
	private int idCurso;
	
	
	
	public AgendamientosViewD() {
		
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		dataGrid = new DataGrid<AgendaPreviewDTO>(AgendaPreviewDTO.KEY_PROVIDER);
		pager = new SimplePager(TextLocation.CENTER, false, false);
		initWidget(uiBinder.createAndBindUi(this));
		personasTable.getColumnFormatter().setWidth(0, "30%");
		personasTable.getColumnFormatter().setWidth(1, "70%");
		contactosTable.getColumnFormatter().setWidth(0, "30%");
		contactosTable.getColumnFormatter().setWidth(1, "70%");
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
		colegioLabel.setText("");
		rbdLabel.setText("");
		regionLabel.setText("");
		comunaLabel.setText("");
		direccionLabel.setText("");
		cursoLabel.setText("");
		tipoLabel.setText("");
		centroOperacionLabel.setText("");
		contactosTable.clear();
		personasTable.clear();
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
				
				boolean a = selectionModel.getSelectedObject()!=null && modificarAgendaVisible;
				boolean b = selectionModel.getSelectedObject()!=null && detalleVisible;
				boolean c = selectionModel.getSelectedObject()!=null && informacionVisible;
				cursoItem.setVisible(a || b || c);
				selectedItem = selectionModel.getSelectedObject();
				
				presenter.onCursoClick(selectedItem);
				
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
        
        /*
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
        */
        
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
        
        /*
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
        
        Column<AgendaPreviewDTO, String> nombreContactoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	return (object.getNombreContacto()!=null)?object.getNombreContacto():"";
            }
        };
        nombreContactoColumn.setSortable(false);
        dataGrid.addColumn(nombreContactoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Nombre contacto")));
        dataGrid.setColumnWidth(nombreContactoColumn, 200, Unit.PX);
        
        Column<AgendaPreviewDTO, String> telefonoContactoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	return (object.getTelefonoContacto()!=null)?object.getTelefonoContacto():"";
            }
        };
        telefonoContactoColumn.setSortable(false);
        dataGrid.addColumn(telefonoContactoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Teléfono contacto")));
        dataGrid.setColumnWidth(telefonoContactoColumn, 140, Unit.PX);
        
        Column<AgendaPreviewDTO, String> emailContactoColumn =new Column<AgendaPreviewDTO, String>(new TextCell()) {
            @Override
            public String getValue(AgendaPreviewDTO object) {
            	return (object.getMailContacto()!=null)?object.getMailContacto():"";
            }
        };
        emailContactoColumn.setSortable(false);
        dataGrid.addColumn(emailContactoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Email contacto")));
        dataGrid.setColumnWidth(emailContactoColumn, 200, Unit.PX);
        */
	}

	@Override
	public void setNombreEstablecimiento(String establecimiento) {
		cursoItem.setHTML(ViewUtils.limitarString(establecimiento, 40));
		colegioLabel.setText(establecimiento);
	}

	@Override
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	@Override
	public HasData<AgendaItemDTO> getAgendaDataDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUltimoEstado(AgendaItemDTO item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEstadosAgenda(ArrayList<EstadoAgendaDTO> estados) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIdEstadoAgendaSeleccionado() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getFechaHoraSeleccionada() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getComentario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEditarContactoVisivility(boolean visible) {
		editContactoButton.setVisible(visible);
	}

	@Override
	public void setEditarDirectorVisivility(boolean visible) {
		editDirectorButton.setVisible(visible);
	}

	@Override
	public void setRbd(String rbd) {
		rbdLabel.setText(rbd);
	}

	@Override
	public void setRegion(String region) {
		regionLabel.setText(region);
	}

	@Override
	public void setComuna(String comuna) {
		comunaLabel.setText(comuna);
	}

	@Override
	public void setCurso(String curso) {
		cursoLabel.setText(curso);
	}

	@Override
	public void setTipo(String tipo) {
		tipoLabel.setText(tipo);
	}

	@Override
	public void setSupervisor(UserDTO supervisor) {
		if(supervisor == null){
			personasTable.clear();
			return;
		}
		personasTable.setWidget(1, 0, new HTML("Supervisor"));
		personasTable.setWidget(1, 1, new HTML(supervisor.getNombres()+" "+supervisor.getApellidoPaterno()+" "+supervisor.getApellidoMaterno()));
		personasTable.setWidget(2, 0, new HTML(supervisor.getTelefono()));
		personasTable.setWidget(2, 1, new HTML(supervisor.getEmail()));
		personasTable.getFlexCellFormatter().addStyleName(2, 0, style.line());
		personasTable.getFlexCellFormatter().addStyleName(2, 1, style.line());
	}

	@Override
	public void setExaminadores(ArrayList<UserDTO> examinadores) {
		int i = 3;
		for(UserDTO examinador:examinadores){
			personasTable.setWidget(i, 0, new HTML("Examinador"));
			personasTable.setWidget(i, 1, new HTML(examinador.getNombres()+" "+examinador.getApellidoPaterno()+" "+examinador.getApellidoMaterno()));
			personasTable.setWidget(i+1, 0, new HTML(examinador.getTelefono()));
			personasTable.setWidget(i+1, 1, new HTML(examinador.getEmail()));
			personasTable.getFlexCellFormatter().addStyleName(i+1, 0, style.line());
			personasTable.getFlexCellFormatter().addStyleName(i+1, 1, style.line());
			i = i + 2;
		}
	}

	@Override
	public void setDirector(String director) {
		contactosTable.setWidget(1, 0, new HTML("Director"));
		contactosTable.setWidget(1, 1, new HTML(director));
	}

	@Override
	public void setEmailDirector(String email) {
		contactosTable.setWidget(2, 1, new HTML(email));
		contactosTable.getFlexCellFormatter().addStyleName(2, 1, style.line());
		contactosTable.getFlexCellFormatter().addStyleName(2, 0, style.line());
	}

	@Override
	public void setTelefonoDirector(String telefono) {
		contactosTable.setWidget(2, 0, new HTML(telefono));
		contactosTable.getFlexCellFormatter().addStyleName(2, 0, style.line());
		contactosTable.getFlexCellFormatter().addStyleName(2, 1, style.line());
	}

	@Override
	public void setContacto(String director) {
		contactosTable.setWidget(3, 1, new HTML(director));
	}

	@Override
	public void setCargoContacto(String cargo) {
		contactosTable.setWidget(3, 0, new HTML(cargo));
	}

	@Override
	public void setEmailContacto(String email) {
		contactosTable.setWidget(4, 1, new HTML(email));
	}

	@Override
	public void setTelefonoContacto(String telefono) {
		contactosTable.setWidget(4, 0, new HTML(telefono));
	}

	@Override
	public void setCentroOperacion(String co) {
		centroOperacionLabel.setText(co);
	}

	@Override
	public void setAddress(String address) {
		direccionLabel.setText(address);
	}

	@Override
	public void setBuscarVisivility(boolean visible) {
		buscarItem.setVisible(visible);
	}

	@Override
	public void setTiposActividad(ArrayList<ActividadTipoDTO> tipos) {
		tipoActividadBox.clear();
		for(ActividadTipoDTO tipo:tipos){
			tipoActividadBox.addItem(tipo.getNombre(), tipo.getId()+"");
		}
	}
	
	@Override
	public void selectTipoActividad(int id) {
		for(int i=0; i < tipoActividadBox.getItemCount();i++){
			int aux = Integer.parseInt(tipoActividadBox.getValue(i));
			if(aux == id){
				tipoActividadBox.setSelectedIndex(i);
				return;
			}
		}
	}
	
	public int getSelectedTipoActividad() {
		return Integer.parseInt(tipoActividadBox.getValue(tipoActividadBox.getSelectedIndex()));
	};
}