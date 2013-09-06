package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;

public class DetalleAgendaViewD extends Composite implements DetalleAgendaView{

	private static DetalleAgendaViewDUiBinder uiBinder = GWT
			.create(DetalleAgendaViewDUiBinder.class);

	interface DetalleAgendaViewDUiBinder extends
			UiBinder<Widget, DetalleAgendaViewD> {
	}
	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cursoItem;
	@UiField MenuItem cambiarItem;
	@UiField MenuItem informacionItem;
	@UiField(provided=true) CellList<AgendaItemDTO> agendaList;
	
	private int idCurso;
	private DetalleAgendaPresenter presenter;
	private AgendaCell cell;
	
	public DetalleAgendaViewD() {
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		initWidget(uiBinder.createAndBindUi(this));
		idCurso = -1;
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
				presenter.onCambiarCursoClick();
			}
		});
		
		informacionItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				DetalleCursoPlace dcp = new DetalleCursoPlace();
				dcp.setCursoId(idCurso);
				presenter.goTo(dcp);
			}
		});
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@Override
	public void setPresenter(DetalleAgendaPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<AgendaItemDTO> getDataDisplay() {
		return agendaList;
	}

	@Override
	public void setNombreEstablecimiento(String establecimiento) {
		this.cursoItem.setHTML(ViewUtils.limitarString(establecimiento,40));
	}

	@Override
	public UIObject getCambiarButton() {
		return cambiarItem;
	}

	@Override
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	
	@Override
	public void clear() {
		idCurso = -1;
		this.cursoItem.setHTML("");
		agendaList.setRowCount(0);
		agendaList.setRowData(new ArrayList<AgendaItemDTO>());
	}
}