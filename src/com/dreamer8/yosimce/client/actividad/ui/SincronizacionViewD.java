package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class SincronizacionViewD extends Composite implements
		SincronizacionView {

	private static SincronizacionViewDUiBinder uiBinder = GWT
			.create(SincronizacionViewDUiBinder.class);

	interface SincronizacionViewDUiBinder extends
			UiBinder<Widget, SincronizacionViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cursoItem;
	@UiField MenuItem cambiarItem;
	@UiField MenuItem guardarItem;
	@UiField MenuItem materialDefectuosoItem;
	@UiField(provided = true) DataGrid<SincAlumnoDTO> dataGrid;
	@UiField HTML alumnosHtml;

	
	private ArrayList<SincAlumnoDTO> alumnos;
	private ArrayList<EstadoSincronizacionDTO> estados;
	private EstadoSincronizacionDTO sinInfo;
	private SincronizacionPresenter presenter;
	private Column<SincAlumnoDTO, String> materialColumn;
	private Column<SincAlumnoDTO, String> estadoColumn;
	private Column<SincAlumnoDTO, String> comentarioColumn;
	private Column<SincAlumnoDTO, Boolean> formColumn;
	
	private FieldUpdater<SincAlumnoDTO, String> materialUpdater;
	private FieldUpdater<SincAlumnoDTO, String> estadoUpdater;
	private FieldUpdater<SincAlumnoDTO, String> comentarioUpdater;
	private FieldUpdater<SincAlumnoDTO, Boolean> formUpdater;
	
	
	private CursoDTO curso;

	public SincronizacionViewD() {
		dataGrid = new DataGrid<SincAlumnoDTO>(200,SincAlumnoDTO.KEY_PROVIDER);
		initWidget(uiBinder.createAndBindUi(this));
		dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CURRENT_PAGE);
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		estados = new ArrayList<EstadoSincronizacionDTO>();
		menu.insertSeparator(2);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		
		cambiarItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onCambiarCursoButtonClick();
			}
		});
		
		guardarItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onGuardarTodoButtonClick();
			}
		});
		
		materialDefectuosoItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				MaterialDefectuosoPlace mdp = new MaterialDefectuosoPlace();
				mdp.setIdCurso(curso.getId());
				presenter.goTo(mdp);
			}
		});
		
		buildTable();
	}

	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@Override
	public void setMaterialDefectusoVisivility(boolean visible) {
		materialDefectuosoItem.setVisible(visible);
	}
	
	@Override
	public void setEstadosSincronizacion(
			ArrayList<EstadoSincronizacionDTO> estados) {
		this.estados.clear();
		this.estados.addAll(estados);
		
		insertEstadoColumn();
		
		dataGrid.redraw();
		if(alumnos!= null){
			setAlumnos(alumnos);
		}
	}
	
	@Override
	public void setGuardarButtonEnabled(boolean enabled) {
		guardarItem.setEnabled(enabled);
	}
	
	@Override
	public void updateTableRow(SincAlumnoDTO alumno) {
		int index = dataGrid.getVisibleItems().indexOf(alumno);
		dataGrid.redrawRow(index);
	}
	
	@Override
	public void setCurso(CursoDTO curso) {
		this.curso = curso;
		cursoItem.setHTML(ViewUtils.limitarString(curso.getNombreEstablecimiento()+" - "+curso.getNombre(),40));
	}
	
	@Override
	public void setTotalALumnos(int total){
		alumnosHtml.setHTML(total+"");
	}
	
	@Override
	public void setIdMaterialFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		materialUpdater = updater;
		if(materialColumn!=null){
			materialColumn.setFieldUpdater(updater);
		}
	}

	@Override
	public void setEstadoFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		estadoUpdater = updater;
		if(estadoColumn != null){
			estadoColumn.setFieldUpdater(updater);
		}
	}
	
	@Override
	public void setFormFieldUpdater(FieldUpdater<SincAlumnoDTO, Boolean> updater) {
		formUpdater = updater;
		if(formColumn!=null){
			formColumn.setFieldUpdater(updater);
		}
	}

	@Override
	public void setComentarioFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		comentarioUpdater = updater;
		if(comentarioColumn!=null){
			comentarioColumn.setFieldUpdater(updater);
		}
	}

	@Override
	public void setAlumnos(ArrayList<SincAlumnoDTO> alumnos) {
		this.alumnos = alumnos;
		if(!estados.isEmpty()){
			dataGrid.setPageSize(alumnos.size()+1);
			dataGrid.setPageStart(0);
			dataGrid.setRowCount(alumnos.size());
			dataGrid.setVisibleRange(0, alumnos.size());
			dataGrid.setRowData(alumnos);
		}
	}

	@Override
	public void setPresenter(SincronizacionPresenter presenter) {
		this.presenter = presenter;
	}

	private void buildTable() {
		
		Column<SincAlumnoDTO, String> sincColumn = new Column<SincAlumnoDTO, String>(new ImageCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				if (o.getSinc() == SincAlumnoDTO.SINC_SIN_INFORMACION) {
					return "";
				} else if (o.getSinc() == SincAlumnoDTO.SINC_EN_PROCESO) {
					return "/images/update.gif";
				} else if (o.getSinc() == SincAlumnoDTO.SINC_EXITOSA) {
					return "/images/ok.png";
				} else {
					return "/images/error.png";
				}
			}
		};
		dataGrid.addColumn(sincColumn,"");
		dataGrid.setColumnWidth(sincColumn, 50,Unit.PX);
		
		Column<SincAlumnoDTO, String> nombreColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return ViewUtils.limitarString(o.getNombres(),20);
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.addColumn(nombreColumn, "Nombres");
		dataGrid.setColumnWidth(nombreColumn, 160,Unit.PX);

		Column<SincAlumnoDTO, String> paternoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return (o.getApellidoPaterno()!=null)?o.getApellidoPaterno():"";
			}
		};
		paternoColumn.setSortable(false);
		dataGrid.addColumn(paternoColumn, "A. Paterno");
		dataGrid.setColumnWidth(paternoColumn,130,Unit.PX);

		Column<SincAlumnoDTO, String> maternoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return (o.getApellidoMaterno()!=null)?o.getApellidoMaterno():"";
			}
		};
		maternoColumn.setSortable(false);
		dataGrid.addColumn(maternoColumn, "A. Materno");
		dataGrid.setColumnWidth(maternoColumn,130,Unit.PX);

		Column<SincAlumnoDTO, String> rutColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return (o.getRut()!=null)?o.getRut():"";
			}
		};
		rutColumn.setSortable(false);
		dataGrid.addColumn(rutColumn, "RUT");
		dataGrid.setColumnWidth(rutColumn,110,Unit.PX);
		
		Column<SincAlumnoDTO, String> tipoColumn = new Column<SincAlumnoDTO, String>(new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return (o.getTipoAlumno()!=null)?o.getTipoAlumno():"";
			}
		};
		tipoColumn.setSortable(false);
		dataGrid.addColumn(tipoColumn, "Tipo alumno");
		dataGrid.setColumnWidth(tipoColumn,130,Unit.PX);

		
		materialColumn = new Column<SincAlumnoDTO, String>(new TextInputCell()) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				return (o.getIdPendrive()!=null)?o.getIdPendrive():"";
			}
		};
		dataGrid.addColumn(materialColumn, "id dispositivo");
		dataGrid.setColumnWidth(materialColumn,154,Unit.PX);
		if(materialUpdater!=null){
			materialColumn.setFieldUpdater(materialUpdater);
		}
		
		formColumn = new Column<SincAlumnoDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(SincAlumnoDTO o) {
				return (o.getEntregoFormulario()!=null)?o.getEntregoFormulario():false;
			}
		};
		dataGrid.addColumn(formColumn, "Form. P. y A.");
		dataGrid.setColumnWidth(formColumn,110,Unit.PX);
		if(formUpdater!=null){
			formColumn.setFieldUpdater(formUpdater);
		}
		
		comentarioColumn = new Column<SincAlumnoDTO, String>(new TextInputCell()) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				return ViewUtils.limitarString(o.getComentario(),35);
			}
		};
		dataGrid.addColumn(comentarioColumn, "Comentario");
		dataGrid.setColumnWidth(comentarioColumn,200,Unit.PX);
		if(comentarioUpdater!=null){
			comentarioColumn.setFieldUpdater(comentarioUpdater);
		}
	}
	
	private void insertEstadoColumn(){
		
		ArrayList<String> selection = new ArrayList<String>();
		sinInfo = null;
		for(EstadoSincronizacionDTO e:estados){
			selection.add(e.getNombreEstado());
			if(e.getNombreEstado().contains("Sin Información")){
				sinInfo = e;
			}
		}
		
		estadoColumn = new Column<SincAlumnoDTO, String>(new SelectionCell(selection)) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				if(o.getEstado() == null || o.getEstado().getIdEstadoSincronizacion() == null || o.getEstado().getNombreEstado() == null){
					o.setEstado(sinInfo);
				}
				return (o.getEstado()!=null)?o.getEstado().getNombreEstado():"";
			}
		};
		dataGrid.insertColumn(7,estadoColumn, "Estado");
		dataGrid.setColumnWidth(estadoColumn,140,Unit.PX);
		if(estadoUpdater!=null){
			estadoColumn.setFieldUpdater(estadoUpdater);
		}
	}

	@Override
	public void clear() {
		cursoItem.setText("");
		estados.clear();
		dataGrid.setRowCount(0);
		alumnos = null;
		if(estadoColumn!=null){
			dataGrid.removeColumn(estadoColumn);
			estadoColumn = null;
		}
	}
}
