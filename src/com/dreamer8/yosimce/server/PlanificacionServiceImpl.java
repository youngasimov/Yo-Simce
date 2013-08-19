package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.ExaminadorDTO;
import com.dreamer8.yosimce.shared.dto.SupervisorDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class PlanificacionServiceImpl extends CustomRemoteServiceServlet
		implements PlanificacionService {

	private String className = "PlanificacionService";

	/**
	 * @permiso getEstablecimientos
	 */
	@Override
	public ArrayList<EstablecimientoDTO> getEstablecimientos(Integer offset,
			Integer lenght, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstablecimientoDTO> edtos = new ArrayList<EstablecimientoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstablecimientos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				s.beginTransaction();

				Usuario u = getUsuarioActual();
				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				UsuarioTipo ut = utdao
						.findByIdAplicacionANDIdNivelANDIdUsuario(idAplicacion,
								idNivel, u.getId());

				if (ut == null) {
					throw new NullPointerException(
							"El usuario no está habilitado para obtener establecimientos para la aplicación y nivel especificados.");
				}

				EstablecimientoDAO edao = new EstablecimientoDAO();
				List<Establecimiento> es = edao
						.findEstablecimientosByActividadWithFilters(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), ut.getNombre(), offset, lenght,
								filtros);

				if (es != null && !es.isEmpty()) {
					for (Establecimiento establecimiento : es) {
						edtos.add(establecimiento.getEstablecimientoDTO());
					}
				}

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return edtos;
	}

	/**
	 * @permiso getEstablecimiento
	 */
	@Override
	public EstablecimientoDTO getEstablecimiento(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException {

		EstablecimientoDTO edto = new EstablecimientoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstablecimiento")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				if (idEstablecimiento == null) {
					throw new NullPointerException(
							"No se ha especificado el establecimiento.");
				}

				s.beginTransaction();

				EstablecimientoDAO edao = new EstablecimientoDAO();
				Establecimiento e = edao.getById(idEstablecimiento);
				edto = e.getEstablecimientoDTO();

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return edto;
	}

	/**
	 * @permiso getPreviewAgendamientos
	 */
	@Override
	public ArrayList<AgendaPreviewDTO> getPreviewAgendamientos(Integer offset,
			Integer length, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getAgendaCurso
	 */
	@Override
	public AgendaDTO getAgendaCurso(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {
		AgendaDTO adto = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getAgendaCurso")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				if (idCurso == null) {
					throw new NullPointerException(
							"No se ha especificado el curso.");
				}

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a == null || a.getId() == null) {
					throw new NullPointerException(
							"La actividad especificada no existe.");
				}

				adto = a.getAgendaDTO();

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return adto;
	}

	/**
	 * @permiso AgendarVisita
	 */
	@Override
	public AgendaItemDTO AgendarVisita(Integer idCurso, AgendaItemDTO itemAgenda)
			throws NoAllowedException, NoLoggedException, DBException {

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "AgendarVisita")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				if (idCurso == null) {
					throw new NullPointerException(
							"No se ha especificado un curso");
				}

				if (itemAgenda == null || itemAgenda.getFecha() == null
						|| itemAgenda.getEstado() == null
						|| itemAgenda.getEstado().getId() == null) {
					throw new NullPointerException(
							"Faltan datos para realizar el agendamiento");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a == null) {
					throw new NullPointerException(
							"No existe una actividad para este curso en el nivel seleccionado");
				}
				ActividadEstadoDAO aedao = new ActividadEstadoDAO();
				ActividadEstado ae = aedao.getById(itemAgenda.getEstado()
						.getId());
				if (ae == null) {
					throw new NullPointerException(
							"El estado especificado no existe");
				}
				a.setFechaInicio(itemAgenda.getFecha());
				a.setUsuario(u);
				a.setComentario(itemAgenda.getComentario());
				a.setActividadEstado(ae);
				adao.update(a);

				itemAgenda.setCreador(u.getUserDTO());

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return itemAgenda;
	}

	/**
	 * @permiso getEstadosAgenda
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosAgenda()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstadosAgenda")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadEstadoDAO aedao = new ActividadEstadoDAO();
				List<ActividadEstado> aes = aedao.findAllByAgendamiento();
				for (ActividadEstado actividadEstado : aes) {
					eadtos.add(actividadEstado.getEstadoAgendaDTO());
				}

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return eadtos;
	}

	/**
	 * @permiso getExaminadores
	 */
	@Override
	public ArrayList<ExaminadorDTO> getExaminadores(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getSupervisor
	 */
	@Override
	public SupervisorDTO getSupervisor(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException {

		SupervisorDTO sdto = new SupervisorDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getSupervisor")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				s.beginTransaction();

				UsuarioDAO udao = new UsuarioDAO();
				Usuario u = udao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDUsuarioTipo(
								idAplicacion, idNivel, idActividadTipo,
								idEstablecimiento, UsuarioTipo.SUPERVISOR);
				if (u != null) {
					sdto = u.getSupervisorDTO();
				}
				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return sdto;
	}

	/**
	 * @permiso getContacto
	 */
	@Override
	public ContactoDTO getContacto(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException {

		ContactoDTO cdto = new ContactoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getContacto")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				Integer idNivel = ac.getIdNivel();
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				s.beginTransaction();

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimient(
								idAplicacion, idNivel, idActividadTipo,
								idEstablecimiento);
				if (a != null) {
					cdto = a.getContactoDTO();
				}
				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		}
		return cdto;
	}

	/**
	 * @permiso getTotalPreviewAgendamientos
	 */
	@Override
	public Integer getTotalPreviewAgendamientos(Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
