package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.administracion.AdminEventosPlace;
import com.dreamer8.yosimce.client.administracion.AdminPlace;
import com.dreamer8.yosimce.client.administracion.AdminUsuariosPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.general.GeneralPlace;
import com.dreamer8.yosimce.client.general.HistorialCursoPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.dreamer8.yosimce.client.planificacion.PlanificacionPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;

public class SidebarViewD extends Composite implements SidebarView{

	private static SidebarViewDUiBinder uiBinder = GWT
			.create(SidebarViewDUiBinder.class);

	interface SidebarViewDUiBinder extends UiBinder<Widget, SidebarViewD> {
	}
	
	interface Style extends CssResource {
	  String hide();
	  String selected();
	}
	
	@UiField Style style;
	@UiField LIElement general;
	@UiField LIElement agendamiento;
	@UiField LIElement actividad;
	@UiField LIElement material;
	@UiField LIElement administracion;
	@UiField Anchor generalButton;
	@UiField Anchor agendamientoButton;
	@UiField Anchor actividadButton;
	@UiField Anchor materialButton;
	@UiField Anchor administracionButton;
	
	@UiField Anchor detalleCursoViewItem;
	@UiField Anchor historialCambiosCursoViewItem;
	@UiField Anchor agendamientosViewItem;
	@UiField Anchor detalleAgendaViewItem;
	@UiField Anchor agendarVisitaActionItem;
	@UiField Anchor actividadesViewItem;
	@UiField Anchor formularioActividadActionItem;
	@UiField Anchor detalleActividadViewItem;
	@UiField Anchor sincronizacionActionItem;
	@UiField Anchor sincronizacionesViewItem;
	@UiField Anchor aprobarSupervisoresActionItem;
	@UiField Anchor ingresoMaterialActionItem;
	@UiField Anchor salidaMaterialActionItem;
	@UiField Anchor historialMovimientosViewItem;
	@UiField Anchor movimientosMaterialViewItem;
	@UiField Anchor administrarUsuariosActionItem;
	@UiField Anchor administrarEventosActionItem;
	
	private SidebarPresenter presenter;
	
	public SidebarViewD() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("generalButton")
	void onGeneralButtonClick(ClickEvent event){
		presenter.goTo(new GeneralPlace());
	}
	
	@UiHandler("agendamientoButton")
	void onAgendamientoButtonClick(ClickEvent event){
		presenter.goTo(new PlanificacionPlace());
	}
	
	@UiHandler("actividadButton")
	void onActividadButtonClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("materialButton")
	void onMaterialButtonClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("administracionButton")
	void onAdministracionButtonClick(ClickEvent event){
		presenter.goTo(new AdminPlace());
	}
	
	
	@UiHandler("detalleCursoViewItem")
	void onDetalleCursoViewItemClick(ClickEvent event){
		presenter.goTo(new DetalleCursoPlace());
	}
	
	@UiHandler("historialCambiosCursoViewItem")
	void onHistorialCambiosCursoViewItemClick(ClickEvent event){
		presenter.goTo(new HistorialCursoPlace());
	}
	
	@UiHandler("agendamientosViewItem")
	void onAgendamientosViewItemClick(ClickEvent event){
		presenter.goTo(new AgendamientosPlace());
	}
	
	@UiHandler("detalleAgendaViewItem")
	void onDetalleAgendaViewItemClick(ClickEvent event){
		presenter.goTo(new DetalleAgendaPlace());
	}
	
	@UiHandler("agendarVisitaActionItem")
	void onAgendarVisitaActionItemClick(ClickEvent event){
		presenter.goTo(new AgendarVisitaPlace());
	}
	
	@UiHandler("actividadesViewItem")
	void onActividadesViewItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("formularioActividadActionItem")
	void onFormularioActividadActionItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("detalleActividadViewItem")
	void onDetalleActividadViewItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("sincronizacionActionItem")
	void onSincronizacionActionItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("aprobarSupervisoresActionItem")
	void onAprobarSupervisoresActionItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("ingresoMaterialActionItem")
	void onIngresoMaterialActionItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("salidaMaterialActionItem")
	void onSalidaMaterialActionItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("historialMovimientosViewItem")
	void onHistorialMovimientosViewItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("movimientosMaterialViewItem")
	void onMovimientosMaterialViewItemClick(ClickEvent event){
		//presenter.goTo(place);
	}
	
	@UiHandler("administrarUsuariosActionItem")
	void onAdministrarUsuariosActionItemClick(ClickEvent event){
		presenter.goTo(new AdminUsuariosPlace());
	}
	
	@UiHandler("administrarEventosActionItem")
	void onAdministrarEventosActionItemClick(ClickEvent event){
		presenter.goTo(new AdminEventosPlace());
	}
	

	@Override
	public void setPresenter(SidebarPresenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public void setGeneralVisivility(boolean visible) {
		if(visible){
			general.removeClassName(style.hide());
		}else{
			general.addClassName(style.hide());
		}
	}


	@Override
	public void setDetalleCursoViewItemVisivility(boolean visible) {
		detalleCursoViewItem.setVisible(visible);
	}


	@Override
	public void setHistorialCambiosCursoViewItemVisivility(
			boolean visible) {
		historialCambiosCursoViewItem.setVisible(visible);
	}


	@Override
	public void setAgendamientoVisivility(boolean visible) {
		if(visible){
			agendamiento.removeClassName(style.hide());
		}else{
			agendamiento.addClassName(style.hide());
		}
	}


	@Override
	public void setAgendamientosViewItemVisivility(boolean visible) {
		agendamientosViewItem.setVisible(visible);
	}


	@Override
	public void setDetalleAgendaViewItemVisivility(
			boolean visible) {
		detalleAgendaViewItem.setVisible(visible);
	}


	@Override
	public void setAgendarVisitaActionItemVisivility(boolean visible) {
		agendarVisitaActionItem.setVisible(visible);
	}


	@Override
	public void setActividadVisivility(boolean visible) {
		if(visible){
			actividad.removeClassName(style.hide());
		}else{
			actividad.addClassName(style.hide());
		}
	}


	@Override
	public void setActividadesViewItemVisivility(boolean visible) {
		actividadesViewItem.setVisible(visible);
	}


	@Override
	public void setFormularioActividadActionItemVisivility(boolean visible) {
		formularioActividadActionItem.setVisible(visible);
	}


	@Override
	public void setDetalleActividadViewItemVisivility(boolean visible) {
		detalleActividadViewItem.setVisible(visible);
	}


	@Override
	public void setSincronizacionActionItemVisivility(boolean visible) {
		sincronizacionActionItem.setVisible(visible);
	}


	@Override
	public void setSincronizacionesViewItemVisivility(boolean visible) {
		sincronizacionesViewItem.setVisible(visible);
	}


	@Override
	public void setAprobarSupervisoresActionItemVisivility(boolean visible) {
		aprobarSupervisoresActionItem.setVisible(visible);
	}


	@Override
	public void setMaterialVisivility(boolean visible) {
		if(visible){
			material.removeClassName(style.hide());
		}else{
			material.addClassName(style.hide());
		}
	}


	@Override
	public void setIngresoMaterialActionItemVisivility(boolean visible) {
		ingresoMaterialActionItem.setVisible(visible);
	}


	@Override
	public void setSalidaMaterialActionItemVisivility(boolean visible) {
		salidaMaterialActionItem.setVisible(visible);
	}


	@Override
	public void setHistorialMovimientosViewItemVisivility(boolean visible) {
		historialMovimientosViewItem.setVisible(visible);
	}


	@Override
	public void setMovimientosMaterialViewItemVisivility(boolean visible) {
		movimientosMaterialViewItem.setVisible(visible);
	}


	@Override
	public void setAdministracionVisivility(boolean visible) {
		if(visible){
			administracion.removeClassName(style.hide());
		}else{
			administracion.addClassName(style.hide());
		}
	}


	@Override
	public void setAdministrarUsuariosActionItemVisivility(boolean visible) {
		administrarUsuariosActionItem.setVisible(visible);
	}


	@Override
	public void setAdministrarEventosActionItemVisivility(boolean visible) {
		administrarEventosActionItem.setVisible(visible);
	}

	@Override
	public void setDetalleCursoViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			detalleCursoViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setHistorialCambiosCursoViewItemSelected(
			boolean selected) {
		removeSeleccion();
		if(selected){
			historialCambiosCursoViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setAgendamientosViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			agendamientosViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setDetalleAgendaViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			detalleAgendaViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setAgendarVisitaActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			agendarVisitaActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setActividadesViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			actividadesViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setFormularioActividadActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			formularioActividadActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setDetalleActividadViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			detalleActividadViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setSincronizacionActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			sincronizacionActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setSincronizacionesViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			sincronizacionesViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setAprobarSupervisoresActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			aprobarSupervisoresActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setIngresoMaterialActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			ingresoMaterialActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setSalidaMaterialActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			salidaMaterialActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setHistorialMovimientosViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			historialMovimientosViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setMovimientosMaterialViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			movimientosMaterialViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setAdministrarUsuariosActionItemItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			administrarUsuariosActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setAdministrarEventosActionItemItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			administrarEventosActionItem.addStyleName(style.selected());
		}
	}
	
	@Override
	public void removeSeleccion(){
		detalleCursoViewItem.removeStyleName(style.selected());
		historialCambiosCursoViewItem.removeStyleName(style.selected());
		agendamientosViewItem.removeStyleName(style.selected());
		detalleAgendaViewItem.removeStyleName(style.selected());
		agendarVisitaActionItem.removeStyleName(style.selected());
		actividadesViewItem.removeStyleName(style.selected());
		formularioActividadActionItem.removeStyleName(style.selected());
		detalleActividadViewItem.removeStyleName(style.selected());
		sincronizacionActionItem.removeStyleName(style.selected());
		sincronizacionesViewItem.removeStyleName(style.selected());
		aprobarSupervisoresActionItem.removeStyleName(style.selected());
		ingresoMaterialActionItem.removeStyleName(style.selected());
		salidaMaterialActionItem.removeStyleName(style.selected());
		historialMovimientosViewItem.removeStyleName(style.selected());
		movimientosMaterialViewItem.removeStyleName(style.selected());
		administrarUsuariosActionItem.removeStyleName(style.selected());
		administrarEventosActionItem.removeStyleName(style.selected());
	}

}
