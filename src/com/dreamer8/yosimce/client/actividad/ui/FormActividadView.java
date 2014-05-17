package com.dreamer8.yosimce.client.actividad.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
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
	
	void setEstados(ArrayList<EstadoAgendaDTO> estados);
	void selectEstado(EstadoAgendaDTO estado);
	
	void setTiposContingencia(ArrayList<TipoContingenciaDTO> tipos);
	void setContingencias(ArrayList<ContingenciaDTO> contingencias);
	
	void setExaminadores(ArrayList<EvaluacionUsuarioDTO> user);
	ArrayList<EvaluacionUsuarioDTO> getExaminadores();
	void setExaminadoresSuplentes(ArrayList<UserDTO> examinadores);
	
	
	
	void setFechaActividad(Date date);
	Date getFechaActividad();
	
	void setInicioActividad(Date hora);
	Date getInicioActividad();
	void setInicioPrueba(Date hora);
	Date getInicioPrueba();
	void setTerminoPrueba(Date hora);
	Date getTerminoPrueba();
	
	void setTotalAlumnos(int total);
	int getTotalAlumnos();
	void setAlumnosAusentes(int ausentes);
	int getAlumnosAusentes();
	void setAlumnosDS(int ds);
	int getAlumnosDS();
	
	void setPcsTotales(int total);
	int getPcsTotales();
	
	void setCuestionariosTotales(int total);
	int getCuestionariosTotales();
	void setCuestionariosEntregados(int entregados);
	int getCuestionariosEntregados();
	void setCuestionariosRecibidos(int recibidos);
	int getCuestionariosRecibidos();
	
	void setUsoMaterialContingencia(boolean contingencia);
	boolean getUsoMaterialContingencia();
	void setDetalleUsoMaterialContingencia(String detalle);
	String getDetalleUsoMaterialContingencia();
	
	void setEvaluacionGeneral(Integer evaluacion);
	Integer getEvaluacionGeneral();
	
	String getUploadFile();
	void setHyperlink(DocumentoDTO documento);
	
	boolean isUploading();
	boolean isFileUploaded();;
	
	void enableAddContingencia(boolean enable);
	void showSeccionExaminador(boolean show);
	void showForm(boolean show);
	void setPresenter(FormActividadPresenter presenter);
	
	void setSaveVisibility(boolean visible);
	
	void setTotalAlumnosEnabled(boolean enabled);
	void setPcsTotalEnabled(boolean enabled);
	void setAlumnosDSEnabled(boolean enabled);
	void setCuestionariosTotalesEnabled(boolean enabled);
	void setCuestionariosEntregadosEnabled(boolean enabled);
	void showUsoMaterialComplementarioPanel(boolean visible);
	
	void setNombreExaminadorSelected(String nombre);
	void setRealizadaPorSupervisor(boolean realizada);
	void setEvaluacion(int pp, int pu, int lf, int ge);
	int getEvaluacionPresentacionPersonal();
	int getEvaluacionPuntualidad();
	int getEvaluacionLlenadoFormulario();
	int getEvaluacionGeneralExaminador();
	void showEvaluacionExaminadores(boolean show);
	void enableExaminadorActions(boolean enable);
	void updateEvaluacionExaminador(EvaluacionUsuarioDTO evaluacion);
	
	void clear();
	
	void enableAddExaminador(boolean enabled);
	void enableRemoveExaminador(boolean enabled);
	
	public interface FormActividadPresenter extends SimcePresenter{
		void onCambiarCursoClick();
		void guardarFormulario();
		void onAgregarContingencia(ContingenciaDTO contingencia);
		void onRemoveContingecia(ContingenciaDTO contingencia);
		void onEstadoChange(Integer estadoId);
		void getExaminadoresSuplentes(String Search);
		void onDocumentoUploaded(String documento);
		void onExaminadorSelected(EvaluacionUsuarioDTO eval);
		void setSelectedExaminadorAusente();
		//void updateEvaluacionExaminador();
		void onActividadRealizadaPorSupervisor(boolean realizadaPorSupervisor);
		void onAddExaminador(EvaluacionUsuarioDTO examinador);
		void onEvaluacionPresentacionPersonalChange(int value);
		void onEvaluacionPuntualidadChange(int value);
		void onEvaluacionFormularioChange(int value);
		void onEvaluacionGeneralChange(int value);
		
	}
}
