package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.client.SimcePlace;
import com.google.gwt.user.client.ui.IsWidget;

public interface SidebarView extends IsWidget {
	
	void setGeneralVisivility(boolean visible);
	void setDetalleCursoViewItemVisivility(boolean visible);
	void setDetalleCursoViewItemSelected(boolean selected);
	
	void setAgendamientoVisivility(boolean visible);
	void setAgendamientosViewItemVisivility(boolean visible);
	void setDetalleAgendaViewItemVisivility(boolean visible);
	void setAgendarVisitaActionItemVisivility(boolean visible);
	void setAgendamientosViewItemSelected(boolean selected);
	void setDetalleAgendaViewItemSelected(boolean selected);
	void setAgendarVisitaActionItemSelected(boolean selected);
	
	void setActividadVisivility(boolean visible);
	void setActividadesViewItemVisivility(boolean visible);
	void setFormularioActividadActionItemVisivility(boolean visible);
	void setSincronizacionActionItemVisivility(boolean visible);
	void setMaterialDefectuosoActionItemVisivility(boolean visible);	
	void setAprobarSupervisoresActionItemVisivility(boolean visible);
	void setActividadesViewItemSelected(boolean selected);
	void setFormularioActividadActionItemSelected(boolean selected);
	void setSincronizacionActionItemSelected(boolean selected);
	void setMaterialDefectuosoActionItemSelected(boolean selected);
	void setAprobarSupervisoresActionItemSelected(boolean selected);
	
	void setMaterialVisivility(boolean visible);
	void setIngresoMaterialActionItemVisivility(boolean visible);
	void setSalidaMaterialActionItemVisivility(boolean visible);
	void setMovimientosMaterialViewItemVisivility(boolean visible);
	void setIngresoMaterialActionItemSelected(boolean selected);
	void setSalidaMaterialActionItemSelected(boolean selected);
	void setMovimientosMaterialViewItemSelected(boolean selected);
	
	void setAdministracionVisivility(boolean visible);
	void setAdministrarUsuariosActionItemVisivility(boolean visible);
	void setAdministrarEventosActionItemVisivility(boolean visible);
	void setAdministrarPermisosActionItemVisivility(boolean visible);
	void setAdministrarUsuariosActionItemItemSelected(boolean selected);
	void setAdministrarEventosActionItemItemSelected(boolean selected);
	void setAdministrarPermisosActionItemItemSelected(boolean selected);
	
	void removeSeleccion();
	
	
	void setPresenter(SidebarPresenter presenter);
	
	
	interface SidebarPresenter extends Presenter{
		void goTo(SimcePlace place);
	}
}
