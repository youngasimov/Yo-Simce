package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.ui.IsWidget;

public interface FormActividadView extends IsWidget {
	
	void setNombreEstablecimiento(String establecimiento);
	void setRbd(String rbd);
	void setCurso(String curso);
	void setTipoCurso(String tipo);
	void setRegion(String region);
	void setComuna(String comuna);
	
	void setTiposContingencia(ArrayList<TipoContingenciaDTO> tipos);
	void setContingencias(ArrayList<ContingenciaDTO> contingencias);
	
	void setExaminador(UserDTO user);
	void setExaminadoresSuplentes(ArrayList<UserDTO> examinadores);
	UserDTO getExaminador();
	
	
	
	
	void enableAddContingencia(boolean enable);
	void showSeccionExaminador(boolean show);
	void showForm(boolean show);
	void setPresenter(FormActividadPresenter presenter);
	
	public interface FormActividadPresenter extends SimcePresenter{
		void onCambiarCursoClick();
		void onCambiarExaminador(UserDTO nuevoExaminador);
		void guardarFormulario();
		void onAgregarContingencia(ContingenciaDTO contingencia);
		void onRemoveContingecia(ContingenciaDTO contingencia);
		void onEstadoCompletadoSelected();
		void onEstadoAnuladoSelected();
		void getExaminadoresSuplentes(String Search);
	}
}
