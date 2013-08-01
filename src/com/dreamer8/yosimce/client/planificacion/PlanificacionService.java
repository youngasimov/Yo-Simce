package com.dreamer8.yosimce.client.planificacion;

import java.util.ArrayList;
import java.util.Map;

import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.ExaminadorDTO;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("planificacionservice")
public interface PlanificacionService extends RemoteService {
	
	public ArrayList<AgendaPreviewDTO> getPreviewAgendamientos(Integer offset, Integer length, Map<String, String> filtros) throws NoAllowedException, NoLoggedException, DBException;
	
	public AgendaDTO getAgendaCurso(Integer idCurso) throws NoAllowedException, NoLoggedException, DBException;
	
	public Void AgendarVisita(Integer idCurso, AgendaItemDTO itemAgenda) throws NoAllowedException, NoLoggedException, DBException;
	
	public ArrayList<EstadoAgendaDTO> getEstadosAgenda() throws NoAllowedException, NoLoggedException, DBException;
	
	public ArrayList<EstablecimientoDTO> getEstablecimientos(Integer offset,
			Integer lenght, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException;

	public EstablecimientoDTO getEstablecimiento(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException;

	public ArrayList<ExaminadorDTO> getExaminadores(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException;

	public SupervisorDTO getSupervisor(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException;

	public ContactoDTO getContacto(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException;
}
