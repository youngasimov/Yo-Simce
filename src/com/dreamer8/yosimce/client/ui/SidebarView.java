package com.dreamer8.yosimce.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface SidebarView extends IsWidget {

	void setOpcionesGeneralesVisivility(boolean visible);
	void setInfoEstablecimientoMenuIntemVisivility(boolean visible);
	void setHistorialCambiosEstablecimientoMenuIntemVisivility(boolean visible);
	
	void setAgendamientoVisivility(boolean visible);
	void setAgendaEstablecimientosMenuItemVisivility(boolean visible);
	void setDetalleAgendaEstablecimientoMenuItemVisivility(boolean visible);
	void setAgendarVisitaMenuItemVisivility(boolean visible);
	
	void setActividadVisivility(boolean visible);
	void setActividadesMenuItemVisivility(boolean visible);
	void setFormularioActividadMenuItemVisivility(boolean visible);
	void setDetallesActividadMenuItemVisivility(boolean visible);
	void setSincronizacionMenuItemVisivility(boolean visible);
	void setEstadoSincronizacionesMenuItemVisivility(boolean visible);
	
	void setMaterialVisivility(boolean visible);
	void setIngresoMaterialMenuItemVisivility(boolean visible);
	void setSalidaMaterialMenuItemVisivility(boolean visible);
	void setHistorialMovimientosMenuItemVisivility(boolean visible);
	void setMovimientosMaterialMenuItemVisivility(boolean visible);
	
	void setAdministracionVisivility(boolean visible);
	void setUsuariosMenuItemVisivility(boolean visible);
	void setEventosMenuItemVisivility(boolean visible);
	
	interface SidebarPresenter extends Presenter{
		void onInfoEstablecimientosMenuItemClick();
		void onHistorialCambiosEstablecimientoMenuItemClick();
		
		void onAgendaEstablecimientosMenuItemClick();
		void onDetalleAgendaEstablecimientoMenuItemClick();
		void onAgendarVisitaMenuItemClick();
		
		void onActividadesMenuItemClick();
		void onFormularioActividadMenuItemClick();
		void onDetallesActividadMenuItemClick();
		void onSincronizacionMenuItemClick();
		void onEstadoSincronizacionesMenuItemClick();
		
		void onIngresoMaterialMenuItemClick();
		void onSalidaMaterialMenuItemClick();
		void onHistorialMovimientosMenuItemClick();
		void onMovimientosMaterialMenuItemClick();
		
		void onUsuariosMenuItemClick();
		void onEventosMenuItemClick();
	}
}
