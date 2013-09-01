package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.ui.ImageButton;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SincronizacionViewD extends Composite implements
		SincronizacionView {

	private static SincronizacionViewDUiBinder uiBinder = GWT
			.create(SincronizacionViewDUiBinder.class);

	interface SincronizacionViewDUiBinder extends
			UiBinder<Widget, SincronizacionViewD> {
	}

	@UiField HTML establecimientoSeleccionado;
	@UiField ImageButton cambiarButton;
	//@UiField ImageButton agregarButton;
	@UiField ImageButton guardarButton;
	@UiField Button conProblemasButton;
	@UiField(provided = true) DataGrid<SincAlumnoDTO> dataGrid;
	@UiField HTML alumnosHtml;

	
	private ArrayList<SincAlumnoDTO> alumnos;
	private ArrayList<EstadoSincronizacionDTO> estados;
	private SincronizacionPresenter presenter;
	private Column<SincAlumnoDTO, String> materialColumn;
	private Column<SincAlumnoDTO, String> estadoColumn;
	private Column<SincAlumnoDTO, String> comentarioColumn;
	private Column<SincAlumnoDTO, Boolean> formColumn;
	private CursoDTO curso;

	public SincronizacionViewD() {
		dataGrid = new DataGrid<SincAlumnoDTO>(100,SincAlumnoDTO.KEY_PROVIDER);
		initWidget(uiBinder.createAndBindUi(this));
		
		dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CURRENT_PAGE);
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
		buildTable();
	}

	@UiHandler("cambiarButton")
	public void onCambiarClick(ClickEvent event) {
		presenter.onCambiarCursoButtonClick();
	}

	/*
	@UiHandler("agregarButton")
	public void onAgregarClick(ClickEvent event) {
		presenter.onAgregarAlumnoButtonClick();
	}
	*/

	@UiHandler("guardarButton")
	public void onGuardarClick(ClickEvent event) {
		presenter.onGuardarTodoButtonClick();
	}
	
	@UiHandler("conProblemasButton")
	public void onConProblemasClick(ClickEvent event) {
		MaterialDefectuosoPlace mdp = new MaterialDefectuosoPlace();
		mdp.setIdCurso(curso.getId());
		presenter.goTo(mdp);
	}

	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@Override
	public void setMaterialDefectusoVisivility(boolean visible) {
		conProblemasButton.setVisible(visible);
	}
	
	@Override
	public void setEstadosSincronizacion(
			ArrayList<EstadoSincronizacionDTO> estados) {
		this.estados = estados;
		buildTable();
		if(alumnos!= null){
			setAlumnos(alumnos);
		}
	}
	
	@Override
	public void setGuardarButtonEnabled(boolean enabled) {
		guardarButton.setEnabled(enabled);
	}
	
	@Override
	public void updateTable() {
		dataGrid.redraw();
	}
	
	@Override
	public void setCurso(CursoDTO curso) {
		this.curso = curso;
		establecimientoSeleccionado.setHTML(ViewUtils.limitarString(curso.getNombreEstablecimiento()+"-"+curso.getNombre(),35));
	}
	
	@Override
	public void setTotalALumnos(int total){
		alumnosHtml.setHTML(total+"");
	}
	
	@Override
	public void setIdMaterialFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		materialColumn.setFieldUpdater(updater);
	}

	@Override
	public void setEstadoFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		
		
		estadoColumn.setFieldUpdater(updater);
	}
	
	@Override
	public void setFormFieldUpdater(FieldUpdater<SincAlumnoDTO, Boolean> updater) {
		formColumn.setFieldUpdater(updater);
	}

	@Override
	public void setComentarioFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		comentarioColumn.setFieldUpdater(updater);
	}

	@Override
	public void setAlumnos(ArrayList<SincAlumnoDTO> alumnos) {
		this.alumnos = alumnos;
		if(estados == null){
			return;
		}
		dataGrid.setPageSize(alumnos.size()+1);
		dataGrid.setPageStart(0);
		dataGrid.setRowCount(alumnos.size());
		dataGrid.setVisibleRange(0, alumnos.size());
		dataGrid.setRowData(alumnos);
	}

	@Override
	public void setPresenter(SincronizacionPresenter presenter) {
		this.presenter = presenter;
	}

	private void buildTable() {
		dataGrid.addColumn(new Column<SincAlumnoDTO, String>(new ImageCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				if (o.getSinc() == SincAlumnoDTO.SINC_SIN_INFORMACION) {
					return "";
				} else if (o.getSinc() == SincAlumnoDTO.SINC_EN_PROCESO) {
					return SimceResources.INSTANCE.update().getSafeUri()
							.asString();
				} else if (o.getSinc() == SincAlumnoDTO.SINC_EXITOSA) {
					return SimceResources.INSTANCE.ok().getSafeUri().asString();
				} else {
					return SimceResources.INSTANCE.error().getSafeUri()
							.asString();
				}
			}
		});

		Column<SincAlumnoDTO, String> nombreColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getNombres();
			}
		};
		nombreColumn.setSortable(false);
		dataGrid.addColumn(nombreColumn, "Nombres");

		Column<SincAlumnoDTO, String> paternoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getApellidoPaterno();
			}
		};
		paternoColumn.setSortable(false);
		dataGrid.addColumn(paternoColumn, "A. Paterno");

		Column<SincAlumnoDTO, String> maternoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getApellidoMaterno();
			}
		};
		maternoColumn.setSortable(false);
		dataGrid.addColumn(maternoColumn, "A. Materno");

		Column<SincAlumnoDTO, String> rutColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getRut();
			}
		};
		rutColumn.setSortable(false);
		dataGrid.addColumn(rutColumn, "RUT");
		
		Column<SincAlumnoDTO, String> tipoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getTipoAlumno();
			}
		};
		tipoColumn.setSortable(false);
		dataGrid.addColumn(tipoColumn, "Tipo alumno");

		
		materialColumn = new Column<SincAlumnoDTO, String>(new TextInputCell()) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getIdPendrive();
			}
		};
		dataGrid.addColumn(materialColumn, "id dispositivo");
		
		if(estados != null){
			ArrayList<String> selection = new ArrayList<String>();
			for(EstadoSincronizacionDTO e:estados){
				selection.add(e.getNombreEstado());
			}
			
			estadoColumn = new Column<SincAlumnoDTO, String>(new SelectionCell(selection)) {
				@Override
				public String getValue(SincAlumnoDTO o) {
					return o.getEstado().getNombreEstado();
				}
			};
			dataGrid.addColumn(estadoColumn, "Estado");
		}
		
		formColumn = new Column<SincAlumnoDTO, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(SincAlumnoDTO o) {
				return o.getEntregoFormulario();
			}
		};
		dataGrid.addColumn(formColumn, "Formulario P. y A.");
		
		comentarioColumn = new Column<SincAlumnoDTO, String>(new TextInputCell()) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getComentario();
			}
		};
		dataGrid.addColumn(comentarioColumn, "Comentario");
	}

	@Override
	public void clear() {
		establecimientoSeleccionado.setText("");
		dataGrid.setRowCount(0);
		alumnos = null;
	}
}
