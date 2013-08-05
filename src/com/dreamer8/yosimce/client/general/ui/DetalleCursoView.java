package com.dreamer8.yosimce.client.general.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;

public interface DetalleCursoView extends IsWidget {

	void setNombreEstablecimiento(String nombre);
	void setRbd(String rbd);
	void setRegion(String region);
	void setComuna(String comuna);
	void setCurso(String curso);
	void setTipo(String tipo);
	void setSupervisor(String supervisor);
	void setExaminador(String examinador);
	void setExaminador2(String examinador);
	void setDirector(String director);
	void setEmailContacto(String email);
	void setTelefonoContacto(String telefono);
	
	void clearAll();
	
	UIObject getCambiarButton();
	void setPresenter(DetalleCursoPresenter presenter);
	
	public interface DetalleCursoPresenter extends SimcePresenter{
		void onCambiarCursoClick();
	}
}