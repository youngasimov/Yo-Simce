package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface DetalleCursoView extends IsWidget {

	void setNombreEstablecimiento(String nombre);
	void setRbd(String rbd);
	void setRegion(String region);
	void setComuna(String comuna);
	void setCurso(String curso);
	void setTipo(String tipo);
	void setSupervisor(UserDTO supervisor);
	void setExaminadores(ArrayList<UserDTO> examinadores);
	void setDirector(String director);
	void setEmailDirector(String email);
	void setTelefonoDirector(String telefono);
	void setContacto(String director);
	void setCargoContacto(String cargo);
	void setEmailContacto(String email);
	void setTelefonoContacto(String telefono);
	void setCentroOperacion(String co);
	void clearAll();
	
	void setPresenter(DetalleCursoPresenter presenter);
	
	public interface DetalleCursoPresenter extends SimcePresenter{
		void onCambiarCursoClick();
	}
}