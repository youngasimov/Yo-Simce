package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox.TIME_PRECISION;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.HasData;

public class AgendarVisitaViewD extends Composite implements AgendarVisitaView {

	private static AgendarVisitaViewDUiBinder uiBinder = GWT
			.create(AgendarVisitaViewDUiBinder.class);

	interface AgendarVisitaViewDUiBinder extends
			UiBinder<Widget, AgendarVisitaViewD> {
	}
	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cursoItem;
	@UiField MenuItem editarContactoItem;
	@UiField MenuItem editarDirectorItem;
	@UiField MenuItem cambiarItem;
	@UiField MenuItem informacionItem;
	
	@UiField ListBox estadoBox;
	@UiField DatePicker fechaPicker;
	@UiField Label fechaLabel;
	@UiField(provided=true) TimeBox timeBox;
	@UiField TextArea comentarioBox;
	@UiField Button modificarButton;
	
	@UiField Label nombreContactoLabel;
	@UiField Label fonoContactoLabel;
	@UiField Label emailContactoLabel;
	@UiField Label cargoContactoLabel;
	
	@UiField(provided=true) CellList<AgendaItemDTO> agendaList;
	
	
	private int idCurso;
	private AgendarVisitaPresenter presenter;
	private AgendaCell cell;
	private DateTimeFormat format;
	private DialogBox editarContactoDialog;
	private DialogBox editarDirectorDialog;
	private EditarContactoViewD editarContactoPanel;
	private EditarContactoViewD editarDirectorPanel;
	private ContactoDTO contacto;
	private ContactoDTO director;
	private ArrayList<CargoDTO> cargos;
	
	public AgendarVisitaViewD() {
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		timeBox = new TimeBox(new Date(), TIME_PRECISION.MINUTE, false);
		initWidget(uiBinder.createAndBindUi(this));
		format = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		idCurso = -1;
		
		menu.insertSeparator(2);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		editarContactoItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				if(contacto != null){
					editarContactoPanel.nombreBox.setText((contacto.getContactoNombre()!=null)?contacto.getContactoNombre():"");
					editarContactoPanel.fonoBox.setText((contacto.getContactoTelefono()!=null)?contacto.getContactoTelefono():"");
					editarContactoPanel.emailBox.setText((contacto.getContactoEmail()!=null)?contacto.getContactoEmail():"");
					
					if(contacto.getCargo()!=null){
						for(int i = 0; i < editarContactoPanel.cargoBox.getItemCount(); i++){
							if(contacto.getCargo().getId() == Integer.parseInt(editarContactoPanel.cargoBox.getValue(i))){
								editarContactoPanel.cargoBox.setItemSelected(i, true);
								break;
							}
						}
					}
				}
				editarContactoDialog.center();
			}
		});
		
		editarDirectorItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				if(director != null){
					editarDirectorPanel.fonoBox.setText((director.getContactoTelefono()!=null)?director.getContactoTelefono():"");
					editarDirectorPanel.nombreBox.setText((director.getContactoNombre()!=null)?director.getContactoNombre():"");
					editarDirectorPanel.emailBox.setText((director.getContactoEmail()!=null)?director.getContactoEmail():"");
					editarDirectorPanel.cargoBox.setVisible(true);
					editarDirectorPanel.cargoBox.clear();
					if(director.getCargo()!=null){
						editarDirectorPanel.cargoBox.addItem(director.getCargo().getCargo());
					}
				}
				editarDirectorDialog.center();
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
		
		
		editarContactoPanel = new EditarContactoViewD();
		editarContactoDialog = new DialogBox();
		editarContactoDialog.setAnimationEnabled(true);
		editarContactoDialog.setAutoHideEnabled(true);
		editarContactoDialog.setAutoHideOnHistoryEventsEnabled(true);
		editarContactoDialog.setGlassEnabled(true);
		editarContactoDialog.setModal(true);
		editarContactoDialog.setWidget(editarContactoPanel);
		editarContactoPanel.editarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ContactoDTO c = new ContactoDTO();
				c.setContactoNombre(editarContactoPanel.nombreBox.getText());
				c.setContactoTelefono(editarContactoPanel.fonoBox.getText());
				c.setContactoEmail(editarContactoPanel.emailBox.getText());
				if(contacto!=null){
					c.setCargo(contacto.getCargo());
				}
				int id = Integer.parseInt(editarContactoPanel.cargoBox.getValue(editarContactoPanel.cargoBox.getSelectedIndex()));
				for(CargoDTO cargo:cargos){
					if(cargo.getId() == id){
						c.setCargo(cargo);
						break;
					}
				}
				presenter.onEditarContacto(c);
				editarContactoDialog.hide();
				editarContactoPanel.nombreBox.setText("");
				editarContactoPanel.fonoBox.setText("");
				editarContactoPanel.emailBox.setText("");
				if(editarContactoPanel.cargoBox.getItemCount()>0){
					editarContactoPanel.cargoBox.setItemSelected(0, true);
				}
			}
		});
		editarContactoPanel.cancelarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editarContactoDialog.hide();
				editarContactoPanel.nombreBox.setText("");
				editarContactoPanel.fonoBox.setText("");
				editarContactoPanel.emailBox.setText("");
				if(editarContactoPanel.cargoBox.getItemCount()>0){
					editarContactoPanel.cargoBox.setItemSelected(0, true);
				}
			}
		});
		
		editarDirectorPanel = new EditarContactoViewD();
		editarDirectorDialog = new DialogBox();
		editarDirectorDialog.setAnimationEnabled(true);
		editarDirectorDialog.setAutoHideEnabled(true);
		editarDirectorDialog.setAutoHideOnHistoryEventsEnabled(true);
		editarDirectorDialog.setGlassEnabled(true);
		editarDirectorDialog.setModal(true);
		editarDirectorPanel.cargoBox.setVisible(false);
		editarDirectorDialog.setWidget(editarDirectorPanel);
		editarDirectorPanel.editarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ContactoDTO c = new ContactoDTO();
				c.setContactoNombre(editarDirectorPanel.nombreBox.getText());
				c.setContactoTelefono(editarDirectorPanel.fonoBox.getText());
				c.setContactoEmail(editarDirectorPanel.emailBox.getText());
				presenter.onEditarDirector(c);
				editarDirectorDialog.hide();
				editarDirectorPanel.nombreBox.setText("");
				editarDirectorPanel.fonoBox.setText("");
				editarDirectorPanel.emailBox.setText("");
			}
		});
		editarDirectorPanel.cancelarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editarDirectorDialog.hide();
				editarDirectorPanel.nombreBox.setText("");
				editarDirectorPanel.fonoBox.setText("");
				editarDirectorPanel.emailBox.setText("");
			}
		});
	}
	
	@UiHandler("modificarButton")
	void onModificarAgendaClick(ClickEvent event){
		presenter.onModificarAgendaClick();
	}
	
	
	
	@UiHandler("fechaPicker")
	void onFechaChange(ValueChangeEvent<Date> event){
		Date now = new Date();
		if(event.getValue().before(now) ){
			fechaPicker.setValue(now);
			fechaLabel.setText(format.format(now));
			timeBox.setValue(now.getTime());
		}else{
			fechaLabel.setText(format.format(event.getValue()));
			timeBox.setValue(event.getValue().getTime());
		}
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@Override
	public void setEditarContactoVisivility(boolean visible) {
		editarContactoItem.setVisible(visible);
	}
	
	@Override
	public void setEditarDirectorVisivility(boolean visible) {
		editarDirectorItem.setVisible(visible);
	}
	
	@Override
	public void setInformacionVisivility(boolean visible) {
		informacionItem.setVisible(visible);
	}

	@Override
	public void setIdCurso(int idCurso){
		this.idCurso = idCurso;
	}
	
	@Override
	public void setFocusOnComment() {
		this.comentarioBox.setFocus(true);
	}
	
	@Override
	public void setFocusOnEstado() {
		this.estadoBox.setFocus(true);
	}
	
	@Override
	public void setPresenter(AgendarVisitaPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<AgendaItemDTO> getDataDisplay() {
		return agendaList;
	}

	@Override
	public void setNombreEstablecimiento(String establecimiento) {
		this.cursoItem.setHTML(ViewUtils.limitarString(establecimiento,35));
	}

	@Override
	public void setEstadosAgenda(ArrayList<EstadoAgendaDTO> estados) {
		 estadoBox.clear();
		 for(EstadoAgendaDTO ea:estados){
			 estadoBox.addItem(ea.getEstado(),ea.getId()+"");
		 }
	}

	@Override
	public int getIdEstadoAgendaSeleccionado() {
		return Integer.parseInt(estadoBox.getValue(estadoBox.getSelectedIndex()));
	}

	@Override
	public Date getFechaHoraSeleccionada() {
		return new Date(timeBox.getValue());
	}

	@Override
	public String getComentario() {
		return comentarioBox.getText();
	}

	@Override
	public void setContacto(ContactoDTO contacto) {
		this.contacto = contacto;
		nombreContactoLabel.setText((contacto.getContactoNombre()!=null)?contacto.getContactoNombre():"");
		fonoContactoLabel.setText((contacto.getContactoTelefono()!=null)?contacto.getContactoTelefono():"");
		emailContactoLabel.setText((contacto.getContactoEmail()!=null)?contacto.getContactoEmail():"");
		if(contacto.getCargo()!=null){
			cargoContactoLabel.setText(contacto.getCargo().getCargo());
		}
	}

	@Override
	public void setCargos(ArrayList<CargoDTO> cargos) {
		this.cargos = cargos;
		editarContactoPanel.cargoBox.clear();
		for(CargoDTO cargo:cargos){
			editarContactoPanel.cargoBox.addItem(cargo.getCargo(),cargo.getId()+"");
		}
	}

	@Override
	public void setDirector(ContactoDTO contacto) {
		director = contacto;
	}
	
	@Override
	public void clear() {
		cursoItem.setHTML("");
		estadoBox.clear();
		fechaPicker.setValue(new Date());
		fechaLabel.setText("");
		timeBox.setValue(new Date().getTime());
		comentarioBox.setText("");
		nombreContactoLabel.setText("");
		fonoContactoLabel.setText("");
		emailContactoLabel.setText("");
		cargoContactoLabel.setText("");
		editarContactoPanel.nombreBox.setText("");
		editarContactoPanel.fonoBox.setText("");
		editarContactoPanel.emailBox.setText("");
		editarContactoPanel.cargoBox.clear();
		editarDirectorPanel.nombreBox.setText("");
		editarDirectorPanel.fonoBox.setText("");
		editarDirectorPanel.emailBox.setText("");
		editarDirectorPanel.cargoBox.clear();
		contacto = null;
		director = null;
		idCurso = -1;
		agendaList.setRowCount(0);
		agendaList.setRowData(new ArrayList<AgendaItemDTO>());
	}
}
