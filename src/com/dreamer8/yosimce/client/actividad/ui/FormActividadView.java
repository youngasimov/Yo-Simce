package com.dreamer8.yosimce.client.actividad.ui;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface FormActividadView extends IsWidget {
	
	//void setEstadosAplicacion();
	
	void setNombreEstablecimiento(String establecimiento);
	void setRbd(String rbd);
	void setCurso(String curso);
	void setTipoCurso(String tipo);
	void setRegion(String region);
	void setComuna(String comuna);
	
	void setMotivoEstado(String motivo);
	String getMotivoEstado();
	
	void setExaminador(UserDTO user);
	UserDTO getExaminador();
	
	
	
	
	
	void setPresenter(FormActividadPresenter presenter);
	
	public interface FormActividadPresenter extends SimcePresenter{
		void onCambiarCursoClick();
		void onCambiarExaminadorClick();
		void guardarFormulario();
	}
}
