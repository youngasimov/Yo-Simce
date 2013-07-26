package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.user.client.ui.IsWidget;

public interface SidebarView extends IsWidget {
	
	void setGeneralVisivility(boolean visible);
	void setDetalleEstablecimientoViewItemVisivility(boolean visible);
	void setHistorialCambiosEstablecimientoViewItemVisivility(boolean visible);
	
	void setAgendamientoVisivility(boolean visible);
	void setAgendamientosViewItemVisivility(boolean visible);
	void setDetalleAgendaEstablecimientoViewItemVisivility(boolean visible);
	void setAgendarVisitaActionItemVisivility(boolean visible);
	
	void setActividadVisivility(boolean visible);
	void setActividadesViewItemVisivility(boolean visible);
	void setFormularioActividadActionItemVisivility(boolean visible);
	void setDetalleActividadViewItemVisivility(boolean visible);
	void setSincronizacionActionItemVisivility(boolean visible);
	void setSincronizacionesViewItemVisivility(boolean visible);
	void setAprobarSupervisoresActionVisivility(boolean visible);
	
	void setMaterialVisivility(boolean visible);
	void setIngresoMaterialActionItemVisivility(boolean visible);
	void setSalidaMaterialActionItemVisivility(boolean visible);
	void setHistorialMovimientosViewItemVisivility(boolean visible);
	void setMovimientosMaterialViewItemVisivility(boolean visible);
	
	void setAdministracionVisivility(boolean visible);
	void setdministrarUsuariosActionItemVisivility(boolean visible);
	void setAdministrarEventosActionItemVisivility(boolean visible);
	
	void setPresenter(SidebarPresenter presenter);
	
	
	interface SidebarPresenter extends Presenter{
		void goTo(SimcePlace place);
	}
}
