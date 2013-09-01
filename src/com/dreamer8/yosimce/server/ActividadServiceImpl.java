package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.actividad.ActividadService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoXActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoXActividadXDocumentoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.DocumentoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.DocumentoEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.IncidenciaTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MotivoFallaDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioXActividadDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.Alumno;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividad;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividadXDocumento;
import com.dreamer8.yosimce.server.hibernate.pojo.Documento;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.IncidenciaTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.MotivoFalla;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXActividad;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.ActividadDTO;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDefectuosoDTO;
import com.dreamer8.yosimce.shared.dto.SincAlumnoDTO;
import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class ActividadServiceImpl extends CustomRemoteServiceServlet implements
		ActividadService {

	private String className = "ActividadService";

	/**
	 * @permiso getPreviewActividades
	 */
	@Override
	public ArrayList<ActividadPreviewDTO> getPreviewActividades(Integer offset,
			Integer length, HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<ActividadPreviewDTO> apdtos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getPreviewActividades")) {

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

				ActividadDAO adao = new ActividadDAO();
				apdtos = (ArrayList<ActividadPreviewDTO>) adao
						.findActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getNombre(), offset,
								length, filtros);

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
		return apdtos;
	}

	/**
	 * @permiso getTotalPreviewActividades
	 */
	@Override
	public Integer getTotalPreviewActividades(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getTotalPreviewActividades")) {

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

				ActividadDAO adao = new ActividadDAO();
				result = adao
						.countActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getNombre(), filtros);

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
		return result;
	}

	/**
	 * @permiso getSincronizacionesCurso
	 */
	@Override
	public ArrayList<SincAlumnoDTO> getSincronizacionesCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<SincAlumnoDTO> sadtos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getSincronizacionesCurso")) {

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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				AlumnoXActividadXDocumentoDAO axaxddao = new AlumnoXActividadXDocumentoDAO();
				sadtos = (ArrayList<SincAlumnoDTO>) axaxddao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

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
		return sadtos;
	}

	/**
	 * @permiso updateSincronizacionAlumno
	 */
	@Override
	public Boolean updateSincronizacionAlumno(Integer idCurso,
			SincAlumnoDTO sinc) throws NoAllowedException, NoLoggedException,
			DBException, ConsistencyException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "updateSincronizacionAlumno")) {

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

				if (sinc == null) {
					throw new NullPointerException(
							"No se han especificado los datos para la sincronización.");
				}

				if (sinc.getRut() == null || !StringUtils.isRut(sinc.getRut())) {
					throw new NullPointerException(
							"No se ha especificado un alumno. Verifique que ha ingresado un rut válido.");
				}

				if (sinc.getIdPendrive() == null) {
					throw new NullPointerException(
							"No se ha especificado un Pendrive.");
				}

				if (sinc.getEstado() == null
						|| sinc.getEstado().getIdEstadoSincronizacion() == null) {
					throw new NullPointerException(
							"No se ha especificado un estado.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				/**
				 * Obtener encuesta.
				 * 
				 * si sincroniza y no era titular, cambiar a titular (preguntar)
				 */

				AlumnoXActividadDAO axadao = new AlumnoXActividadDAO();
				AlumnoXActividad axa = axadao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCursoANDRutAlumno(
								idAplicacion, idNivel, idActividadTipo,
								idCurso, sinc.getRut());

				if (axa == null) {
					throw new NullPointerException(
							"No se ha encontrado al alumno especificado.");
				}

				AlumnoEstadoDAO aedao = new AlumnoEstadoDAO();
				AlumnoEstado ae = aedao.findByNombre(AlumnoEstado.PRESENTE);

				axa.setAlumnoEstado(ae);
				axa.setPruebaComentario(sinc.getComentario());
				axadao.update(axa);

				AlumnoXActividadXDocumentoDAO axaxddao = new AlumnoXActividadXDocumentoDAO();
				AlumnoXActividadXDocumento axaxdPndrive = axaxddao
						.findByIdAlumnoXActividadANDCodigoDocumentoANDTipoDocumento(
								axa.getId(), sinc.getIdPendrive(),
								DocumentoTipo.PRUEBA);

				if (axaxdPndrive == null) {
					DocumentoDAO ddao = new DocumentoDAO();
					Documento pendrive = ddao.findByCodigoANDTipoDocumento(
							sinc.getIdPendrive(), DocumentoTipo.PRUEBA);
					if (pendrive == null) {
						throw new NullPointerException(
								"No se ha encontrado un pendrive con el código ingresado.");
					}
					axaxdPndrive = new AlumnoXActividadXDocumento();
					axaxdPndrive.setAlumnoXActividad(axa);
					axaxdPndrive.setDocumento(pendrive);
				}

				DocumentoEstadoDAO dedao = new DocumentoEstadoDAO();
				DocumentoEstado de = dedao.getById(sinc.getEstado()
						.getIdEstadoSincronizacion());

				axaxdPndrive.setDocumentoEstado(de);
				axaxdPndrive.setComentario(sinc.getComentario());
				axaxdPndrive.setEntregado(true);
				axaxdPndrive
						.setRecibido(!de.equals(DocumentoEstado.EXTRAVIADO));

				axaxdPndrive.setUpdatedAt(new Date());
				axaxdPndrive.setModificadorId(u.getId());

				axaxddao.saveOrUpdate(axaxdPndrive);

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
		return result;
	}

	/**
	 * @permiso getEvaluacionSupervisores
	 */
	@Override
	public ArrayList<EvaluacionUsuarioDTO> getEvaluacionSupervisores()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EvaluacionUsuarioDTO> eudtos = new ArrayList<EvaluacionUsuarioDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getEvaluacionSupervisores")) {

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

				UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO();
				List<UsuarioXActividad> uxas = uxadao
						.findSupervisoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getNombre());

				if (uxas != null && !uxas.isEmpty()) {
					for (UsuarioXActividad uxa : uxas) {
						eudtos.add(uxa.getEvaluacionUsuarioDTO());
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
		return eudtos;
	}

	/**
	 * @permiso updateEvaluacionSupervisor
	 */
	@Override
	public Boolean updateEvaluacionSupervisor(EvaluacionUsuarioDTO evaluacion)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getExaminadores
	 */
	@Override
	public ArrayList<UserDTO> getExaminadores(String search)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getTiposContingencia
	 */
	@Override
	public ArrayList<TipoContingenciaDTO> getTiposContingencia(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<TipoContingenciaDTO> tcdto = new ArrayList<TipoContingenciaDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getTiposContingencia")) {

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

				IncidenciaTipoDAO itdao = new IncidenciaTipoDAO();
				IncidenciaTipo it = itdao
						.findByNombre(IncidenciaTipo.CONTINGENCIA);
				if (it != null) {
					MotivoFallaDAO mfdao = new MotivoFallaDAO();
					List<MotivoFalla> mfs = mfdao.findByIdIncidenciaTipo(it
							.getId());

					if (mfs != null && !mfs.isEmpty()) {
						for (MotivoFalla motivoFalla : mfs) {
							tcdto.add(motivoFalla.getTipoContingenciaDTO());
						}
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
		return tcdto;
	}

	/**
	 * @permiso getActividad
	 */
	@Override
	public ActividadDTO getActividad(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {

		ActividadDTO adto = new ActividadDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getActividad")) {

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
							"No existe una actividad para el curso especificado.");
				}
				adto = a.getActividadDTO(idAplicacion);
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
	 * @permiso actualizarActividad
	 */
	@Override
	public Boolean actualizarActividad(ActividadDTO actividad)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getEstadosActividad
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosActividad()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstadosActividad")) {

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
				List<ActividadEstado> aes = aedao.findAllByActividad();
				if (aes != null && !aes.isEmpty()) {
					for (ActividadEstado ae : aes) {
						eadtos.add(ae.getEstadoAgendaDTO());
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
		return eadtos;
	}

	/**
	 * @permiso getEvaluacionExaminadores
	 */
	@Override
	public ArrayList<EvaluacionUsuarioDTO> getEvaluacionExaminadores(
			Integer idCurso) throws NoAllowedException, NoLoggedException,
			DBException {

		ArrayList<EvaluacionUsuarioDTO> eudtos = new ArrayList<EvaluacionUsuarioDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getEvaluacionExaminadores")) {

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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO();
				List<UsuarioXActividad> uxas = uxadao
						.findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (uxas != null && !uxas.isEmpty()) {
					for (UsuarioXActividad uxa : uxas) {
						eudtos.add(uxa.getEvaluacionUsuarioDTO());
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
		return eudtos;
	}

	/**
	 * @permiso updateEvaluacionExaminadores
	 */
	@Override
	public Boolean updateEvaluacionExaminadores(Integer idCurso,
			ArrayList<EvaluacionUsuarioDTO> evaluaciones)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getEstadosSincronizacionFallida
	 */
	@Override
	public ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacionFallida()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoSincronizacionDTO> esdtos = new ArrayList<EstadoSincronizacionDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className,
							"getEstadosSincronizacionFallida")) {

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

				DocumentoEstadoDAO dedao = new DocumentoEstadoDAO();
				List<DocumentoEstado> des = dedao
						.findForSincronizacionFallida();
				if (des != null && !des.isEmpty()) {
					for (DocumentoEstado documentoEstado : des) {
						esdtos.add(documentoEstado.getEstadoSincrinizacion());
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
		return esdtos;
	}

	/**
	 * @permiso getMaterialDefectuoso
	 */
	@Override
	public ArrayList<MaterialDefectuosoDTO> getMaterialDefectuoso(
			Integer idCurso) throws NoAllowedException, NoLoggedException,
			DBException {

		ArrayList<MaterialDefectuosoDTO> mddtos = new ArrayList<MaterialDefectuosoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getMaterialDefectuoso")) {

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

				/**********
				 * 
				 * 
				 * 
				 * BORRAR DESPUÉS
				 * 
				 * 
				 * 
				 * 
				 * 
				 */

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
		return mddtos;
	}

	/**
	 * @permiso addOrUpdateMaterialDefectuoso
	 */
	@Override
	public Boolean addOrUpdateMaterialDefectuoso(Integer idCurso,
			MaterialDefectuosoDTO material) throws NoAllowedException,
			NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso removeMaterialDefectuoso
	 */
	@Override
	public Boolean removeMaterialDefectuoso(Integer idCurso, String idMaterial)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getDocumentoPreviewActividades
	 */
	@Override
	public DocumentoDTO getDocumentoPreviewActividades(
			HashMap<String, String> filtros) throws NoAllowedException,
			NoLoggedException, DBException {

		DocumentoDTO ddto = new DocumentoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getDocumentoPreviewActividades")) {

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

				/**********
				 * 
				 * 
				 * 
				 * BORRAR DESPUÉS
				 * 
				 * 
				 * 
				 * 
				 * 
				 */

				if (true) {
					throw new NullPointerException(
							"No se ha podido generar el archivo");
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
		return ddto;
	}

	/**
	 * @permiso getDocumentoAlumnos
	 */
	@Override
	public DocumentoDTO getDocumentoAlumnos(HashMap<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		DocumentoDTO ddto = new DocumentoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getDocumentoAlumnos")) {

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

				/**********
				 * 
				 * 
				 * 
				 * BORRAR DESPUÉS
				 * 
				 * 
				 * 
				 * 
				 * 
				 */

				if (true) {
					throw new NullPointerException(
							"No se han encontrado alumnos");
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
		return ddto;
	}

	/**
	 * @permiso getEstadosSincronizacion
	 */
	@Override
	public ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacion()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoSincronizacionDTO> esdtos = new ArrayList<EstadoSincronizacionDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getEstadosSincronizacion")) {

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

				DocumentoEstadoDAO dedao = new DocumentoEstadoDAO();
				List<DocumentoEstado> des = dedao.findForSincronizacion();
				if (des != null && !des.isEmpty()) {
					for (DocumentoEstado documentoEstado : des) {
						esdtos.add(documentoEstado.getEstadoSincrinizacion());
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
		return esdtos;
	}

}
