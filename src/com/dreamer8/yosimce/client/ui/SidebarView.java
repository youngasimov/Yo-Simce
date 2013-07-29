package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.user.client.ui.IsWidget;

public interface SidebarView extends IsWidget {
	
	void setGeneralVisivility(boolean visible);
	void setDetalleEstablecimientoViewItemVisivility(boolean visible);
	void setHistorialCambiosEstablecimientoViewItemVisivility(boolean visible);
	void setDetalleEstablecimientoViewItemSelected(boolean selected);
	void setHistorialCambiosEstablecimientoViewItemSelected(boolean selected);
	
	void setAgendamientoVisivility(boolean visible);
	void setAgendamientosViewItemVisivility(boolean visible);
	void setDetalleAgendaEstablecimientoViewItemVisivility(boolean visible);
	void setAgendarVisitaActionItemVisivility(boolean visible);
	void setAgendamientosViewItemSelected(boolean selected);
	void setDetalleAgendaEstablecimientoViewItemSelected(boolean selected);
	void setAgendarVisitaActionItemSelected(boolean selected);
	
	void setActividadVisivility(boolean visible);
	void setActividadesViewItemVisivility(boolean visible);
	void setFormularioActividadActionItemVisivility(boolean visible);
	void setDetalleActividadViewItemVisivility(boolean visible);
	void setSincronizacionActionItemVisivility(boolean visible);
	void setSincronizacionesViewItemVisivility(boolean visible);
	void setAprobarSupervisoresActionItemVisivility(boolean visible);
	void setActividadesViewItemSelected(boolean selected);
	void setFormularioActividadActionItemSelected(boolean selected);
	void setDetalleActividadViewItemSelected(boolean selected);
	void setSincronizacionActionItemSelected(boolean selected);
	void setSincronizacionesViewItemSelected(boolean selected);
	void setAprobarSupervisoresActionItemSelected(boolean selected);
	
	void setMaterialVisivility(boolean visible);
	void setIngresoMaterialActionItemVisivility(boolean visible);
	void setSalidaMaterialActionItemVisivility(boolean visible);
	void setHistorialMovimientosViewItemVisivility(boolean visible);
	void setMovimientosMaterialViewItemVisivility(boolean visible);
	void setIngresoMaterialActionItemSelected(boolean selected);
	void setSalidaMaterialActionItemSelected(boolean selected);
	void setHistorialMovimientosViewItemSelected(boolean selected);
	void setMovimientosMaterialViewItemSelected(boolean selected);
	
	void setAdministracionVisivility(boolean visible);
	void setAdministrarUsuariosActionItemVisivility(boolean visible);
	void setAdministrarEventosActionItemVisivility(boolean visible);
	void setAdministrarUsuariosActionItemItemSelected(boolean selected);
	void setAdministrarEventosActionItemItemSelected(boolean selected);
	
	void removeSeleccion();
	
	
	void setPresenter(SidebarPresenter presenter);
	
	
	interface SidebarPresenter extends Presenter{
		void goTo(SimcePlace place);
	}
}
