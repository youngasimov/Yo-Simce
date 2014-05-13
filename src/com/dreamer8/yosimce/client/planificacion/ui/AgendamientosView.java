package com.dreamer8.yosimce.client.planificacion.ui;

import java.util.ArrayList;
import java.util.Date;

import com.dreamer8.yosimce.client.SimcePresenter;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public interface AgendamientosView extends IsWidget {

	//Lista de cursos
	HasData<AgendaPreviewDTO> getDataDisplay();
    Column<AgendaPreviewDTO,?> getColumn(int index);
    int getColumnIndex(Column<AgendaPreviewDTO,?> column);
	void clear();
	void setEstados(ArrayList<EstadoAgendaDTO> estados);
	void setRegiones(ArrayList<SectorDTO> regiones);
	void setComunas(ArrayList<SectorDTO> comunas);
	void setDesde(Date date);
	void setHasta(Date date);
	void setSelectedEstados(ArrayList<Integer> estadosId);
	void setSelectedRegion(int regionId);
	void setSelectedComuna(int comunaId);
	//Fin Lista de cursos
	
	void setNombreEstablecimiento(String establecimiento);
	
	void setTiposActividad(ArrayList<ActividadTipoDTO> tipos);
	
	void selectTipoActividad(int id);
	
	int getSelectedTipoActividad();
	
	//Agenda
	HasData<AgendaItemDTO> getAgendaDataDisplay();
	void setUltimoEstado(AgendaItemDTO item);
	void setEstadosAgenda(ArrayList<EstadoAgendaDTO> estados);
	int getIdEstadoAgendaSeleccionado();
	Date getFechaHoraSeleccionada();
	String getComentario();
	void setEditarContactoVisivility(boolean visible);
	void setEditarDirectorVisivility(boolean visible);
	void setContacto(ContactoDTO contacto);
	void setDirector(ContactoDTO contacto);
	void setCargos(ArrayList<CargoDTO> cargos);
	//Fin Agenda
	
	//Detalles Curso
	void setRbd(String rbd);
	void setRegion(String region);
	void setComuna(String comuna);
	void setCurso(String curso);
	void setTipo(String tipo);
	void setSupervisor(UserDTO supervisor);
	void setExaminadores(ArrayList<UserDTO> examinadores);
	void setCentroOperacion(String co);
	void setAddress(String address);
	//Fin Detalles Curso
	
	void setExportarVisivility(boolean visible);
	void setModificarAgendaVisivility(boolean visible);
	void setDetallesAgendaVisivility(boolean visible);
	void setInformacionGeneralVisivility(boolean visible);
	void setAgendarBtnVisivility(boolean visible);
	void setSelectorTipoActividadVisivility(boolean visible);
	
	
	void setPresenter(AgendamientosPresenter presenter);
	
	public interface AgendamientosPresenter extends SimcePresenter{
		void onExportarClick();
		void onRegionChange(int regionId);
		void onCancelarFiltroClick();
		void onRangeChange(Range r);
		void onCursoClick(AgendaPreviewDTO agendaPreview);
		
		//Agenda
		void onModificarAgendaClick();
		void onEditarContacto(ContactoDTO contacto);
		void onEditarDirector(ContactoDTO contacto);
		void onFechaChange(Date d);
		//Fin Agenda
	}
}
