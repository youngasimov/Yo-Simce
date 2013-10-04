package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.GATracker;
import com.dreamer8.yosimce.client.SimcePlace;
import com.dreamer8.yosimce.client.actividad.ActividadesPlace;
import com.dreamer8.yosimce.client.actividad.AprobarSupervisoresPlace;
import com.dreamer8.yosimce.client.actividad.FormActividadPlace;
import com.dreamer8.yosimce.client.actividad.MaterialDefectuosoPlace;
import com.dreamer8.yosimce.client.actividad.SincronizacionPlace;
import com.dreamer8.yosimce.client.administracion.PermisosPlace;
import com.dreamer8.yosimce.client.administracion.ReportesPlace;
import com.dreamer8.yosimce.client.general.CentroControlPlace;
import com.dreamer8.yosimce.client.general.DetalleCursoPlace;
import com.dreamer8.yosimce.client.material.CentroOperacionPlace;
import com.dreamer8.yosimce.client.planificacion.AgendamientosPlace;
import com.dreamer8.yosimce.client.planificacion.AgendarVisitaPlace;
import com.dreamer8.yosimce.client.planificacion.DetalleAgendaPlace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ScrollPanel;
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
	@UiField ScrollPanel scroll;
	@UiField UListElement general;
	@UiField UListElement agendamiento;
	@UiField UListElement actividad;
	@UiField UListElement material;
	@UiField UListElement administracion;
	
	@UiField Anchor menuItem;
	
	@UiField Anchor detalleCursoViewItem;
	@UiField Anchor centroControlViewItem;
	
	@UiField Anchor agendamientosViewItem;
	@UiField Anchor detalleAgendaViewItem;
	@UiField Anchor agendarVisitaActionItem;
	
	@UiField Anchor actividadesViewItem;
	@UiField Anchor formularioActividadActionItem;
	@UiField Anchor sincronizacionActionItem;
	@UiField Anchor materialDefectuosoActionItem;
	@UiField Anchor aprobarSupervisoresActionItem;
	
	@UiField Anchor centroOperacionViewItem;
	
	@UiField Anchor reportesActionItem;
	@UiField Anchor administrarPermisosActionItem;
	
	private SidebarPresenter presenter;
	
	public SidebarViewD() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("menuItem")
	void onMenuCLick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Menu");
		presenter.goTo(new SimcePlace());
	}
	
	@UiHandler("centroControlViewItem")
	void onCentroControlViewItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Centro control");
		presenter.goTo(new CentroControlPlace());
	}
	
	@UiHandler("detalleCursoViewItem")
	void onDetalleCursoViewItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Detalle curso");
		presenter.goTo(new DetalleCursoPlace());
	}
	
	@UiHandler("agendamientosViewItem")
	void onAgendamientosViewItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Lista agendamientos");
		presenter.goTo(new AgendamientosPlace());
	}
	
	@UiHandler("detalleAgendaViewItem")
	void onDetalleAgendaViewItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Detalles de agenda");
		presenter.goTo(new DetalleAgendaPlace());
	}
	
	@UiHandler("agendarVisitaActionItem")
	void onAgendarVisitaActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Agendar visita");
		presenter.goTo(new AgendarVisitaPlace());
	}
	
	@UiHandler("actividadesViewItem")
	void onActividadesViewItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Lista de actividades");
		presenter.goTo(new ActividadesPlace());
	}
	
	@UiHandler("formularioActividadActionItem")
	void onFormularioActividadActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Formulario actividad");
		presenter.goTo(new FormActividadPlace());
	}
	
	@UiHandler("sincronizacionActionItem")
	void onSincronizacionActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Sincronización");
		presenter.goTo(new SincronizacionPlace());
	}
	
	@UiHandler("materialDefectuosoActionItem")
	void onMaterialDefectousoActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Material defectuoso");
		presenter.goTo(new MaterialDefectuosoPlace());
	}
	
	@UiHandler("aprobarSupervisoresActionItem")
	void onAprobarSupervisoresActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Evaluación supervisores");
		presenter.goTo(new AprobarSupervisoresPlace());
	}
	
	@UiHandler("centroOperacionViewItem")
	void onCentroOperacionViewItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Centro operación");
		presenter.goTo(new CentroOperacionPlace());
	}
	
	@UiHandler("reportesActionItem")
	void onReportesActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Reportes");
		presenter.goTo(new ReportesPlace());
	}
	
	@UiHandler("administrarPermisosActionItem")
	void onAdministrarPermisosActionItemClick(ClickEvent event){
		GATracker.trackEvent("sidebar menu","Administrar permisos");
		presenter.goTo(new PermisosPlace());
	}
	
	@Override
	public void setScrollOnTop() {
		scroll.setVerticalScrollPosition(0);
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
	public void setCentroControlViewItemVisivility(boolean visible){
		centroControlViewItem.setVisible(visible);
	}
	
	@Override
	public void setCentroControlViewItemSelected(boolean selected){
		removeSeleccion();
		if(selected){
			centroControlViewItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setDetalleCursoViewItemVisivility(boolean visible) {
		detalleCursoViewItem.setVisible(visible);
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
	public void setSincronizacionActionItemVisivility(boolean visible) {
		sincronizacionActionItem.setVisible(visible);
	}
	
	@Override
	public void setMaterialDefectuosoActionItemVisivility(boolean visible) {
		materialDefectuosoActionItem.setVisible(visible);
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
	public void setCentroOperacionViewItemVisivility(boolean visible) {
		centroOperacionViewItem.setVisible(visible);
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
	public void setReportesActionItemVisivility(boolean visible) {
		reportesActionItem.setVisible(visible);
	}
	
	@Override
	public void setAdministrarPermisosActionItemVisivility(boolean visible) {
		administrarPermisosActionItem.setVisible(visible);
	}
	
	

	@Override
	public void setDetalleCursoViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			detalleCursoViewItem.addStyleName(style.selected());
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
	public void setSincronizacionActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			sincronizacionActionItem.addStyleName(style.selected());
		}
	}

	@Override
	public void setMaterialDefectuosoActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			materialDefectuosoActionItem.addStyleName(style.selected());
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
	public void setCentroOperacionViewItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			centroOperacionViewItem.addStyleName(style.selected());
		}
	}
	
	@Override
	public void setReportesActionItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			reportesActionItem.addStyleName(style.selected());
		}
	}
	
	@Override
	public void setAdministrarPermisosActionItemItemSelected(boolean selected) {
		removeSeleccion();
		if(selected){
			administrarPermisosActionItem.addStyleName(style.selected());
		}
	}
	
	@Override
	public void removeSeleccion(){
		centroControlViewItem.removeStyleName(style.selected());
		detalleCursoViewItem.removeStyleName(style.selected());
		agendamientosViewItem.removeStyleName(style.selected());
		detalleAgendaViewItem.removeStyleName(style.selected());
		agendarVisitaActionItem.removeStyleName(style.selected());
		actividadesViewItem.removeStyleName(style.selected());
		formularioActividadActionItem.removeStyleName(style.selected());
		sincronizacionActionItem.removeStyleName(style.selected());
		materialDefectuosoActionItem.removeStyleName(style.selected());
		aprobarSupervisoresActionItem.removeStyleName(style.selected());
		centroOperacionViewItem.removeStyleName(style.selected());
		administrarPermisosActionItem.removeStyleName(style.selected());
	}
}
