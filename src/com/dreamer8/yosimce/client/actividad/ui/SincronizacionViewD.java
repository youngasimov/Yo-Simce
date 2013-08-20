package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
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
import com.google.gwt.view.client.HasData;

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

	private SincronizacionPresenter presenter;
	private Column<SincAlumnoDTO, String> materialColumn;
	private Column<SincAlumnoDTO, Boolean> estadoColumn;
	private Column<SincAlumnoDTO, String> comentarioColumn;
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
		establecimientoSeleccionado.setHTML(curso.getNombreEstablecimiento()+"-"+curso.getNivel());
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
			FieldUpdater<SincAlumnoDTO, Boolean> updater) {
		estadoColumn.setFieldUpdater(updater);
	}

	@Override
	public void setComentarioFieldUpdater(
			FieldUpdater<SincAlumnoDTO, String> updater) {
		comentarioColumn.setFieldUpdater(updater);
	}

	@Override
	public HasData<SincAlumnoDTO> getDataDisplay() {
		return dataGrid;
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
		nombreColumn.setSortable(true);
		dataGrid.addColumn(nombreColumn, "Nombres");

		Column<SincAlumnoDTO, String> paternoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getApellidoPaterno();
			}
		};
		paternoColumn.setSortable(true);
		dataGrid.addColumn(paternoColumn, "A. Paterno");

		Column<SincAlumnoDTO, String> maternoColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getApellidoMaterno();
			}
		};
		maternoColumn.setSortable(true);
		dataGrid.addColumn(maternoColumn, "A. Materno");

		Column<SincAlumnoDTO, String> rutColumn = new Column<SincAlumnoDTO, String>(
				new TextCell()) {

			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getRut();
			}
		};
		rutColumn.setSortable(true);
		dataGrid.addColumn(rutColumn, "RUT");

		
		materialColumn = new Column<SincAlumnoDTO, String>(new TextInputCell()) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getIdPendrive();
			}
		};
		dataGrid.addColumn(materialColumn, "id dispositivo");
		
		estadoColumn = new Column<SincAlumnoDTO, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(SincAlumnoDTO o) {
				return o.getSincronizado();
			}
		};
		dataGrid.addColumn(estadoColumn, "Sincronizado");
		
		comentarioColumn = new Column<SincAlumnoDTO, String>(new TextInputCell()) {
			@Override
			public String getValue(SincAlumnoDTO o) {
				return o.getIdPendrive();
			}
		};
		dataGrid.addColumn(comentarioColumn, "Comentario");
	}
}
