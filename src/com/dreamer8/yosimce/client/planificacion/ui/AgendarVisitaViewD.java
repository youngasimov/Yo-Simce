package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox;
import com.dreamer8.yosimce.client.ui.eureka.TimeBox.TIME_PRECISION;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
	
	@UiField HTML establecimiento;
	@UiField Button informacionButton;
	@UiField ImageButton cambiarButton;
	@UiField ImageButton editarContactoButton;
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
	private EditarContactoViewD editarContactoPanel;
	private ContactoDTO contacto;
	private ArrayList<CargoDTO> cargos;
	
	public AgendarVisitaViewD() {
		cell = new AgendaCell();
		agendaList = new CellList<AgendaItemDTO>(cell);
		timeBox = new TimeBox(new Date(), TIME_PRECISION.MINUTE, false);
		initWidget(uiBinder.createAndBindUi(this));
		format = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		idCurso = -1;
		
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
			}
		});
		editarContactoPanel.cancelarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editarContactoDialog.hide();
			}
		});
	}
	
	@UiHandler("modificarButton")
	void onModificarAgendaClick(ClickEvent event){
		presenter.onModificarAgendaClick();
	}
	
	@UiHandler("cambiarButton")
	void onCambiarClick(ClickEvent event){
		presenter.onCambiarCursoClick();
	}
	
	@UiHandler("informacionButton")
	void onInformacionClick(ClickEvent event){
		DetalleCursoPlace dcp = new DetalleCursoPlace();
		dcp.setCursoId(idCurso);
		presenter.goTo(dcp);
	}
	
	@UiHandler("editarContactoButton")
	void onEditarContactoClick(ClickEvent event){
		if(contacto != null){
			editarContactoPanel.nombreBox.setText(contacto.getContactoNombre());
			editarContactoPanel.fonoBox.setText(contacto.getContactoTelefono());
			editarContactoPanel.emailBox.setText(contacto.getContactoEmail());
			
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

	public void setIdCurso(int idCurso){
		this.idCurso = idCurso;
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
		this.establecimiento.setHTML(ViewUtils.limitarString(establecimiento,35));
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
		nombreContactoLabel.setText(contacto.getContactoNombre());
		fonoContactoLabel.setText(contacto.getContactoTelefono());
		emailContactoLabel.setText(contacto.getContactoEmail());
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
}
