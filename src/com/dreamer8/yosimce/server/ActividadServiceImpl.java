package com.dreamer8.yosimce.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.actividad.ActividadService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadHistorialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadXDocumentoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadXDocumentoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadXIncidenciaDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoXActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoXActividadXDocumentoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ArchivoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.DocumentoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.DocumentoEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.DocumentoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.IncidenciaTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MotivoFallaDAO;
import com.dreamer8.yosimce.server.hibernate.dao.SuplenteXCoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioSeleccionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioXActividadDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorialId;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumento;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumentoId;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXIncidencia;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividad;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividadXDocumento;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.Documento;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.IncidenciaTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.MotivoFalla;
import com.dreamer8.yosimce.server.hibernate.pojo.SuplenteXCo;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioSeleccion;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXActividad;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.ActividadDTO;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
import com.dreamer8.yosimce.shared.dto.ContingenciaDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.EstadoSincronizacionDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionSupervisorDTO;
import com.dreamer8.yosimce.shared.dto.EvaluacionSuplenteDTO;
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
		Session s = HibernateUtil.getSessionFactorySlave().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				apdtos = (ArrayList<ActividadPreviewDTO>) adao
						.findActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol(), offset,
								length, filtros, getBaseURL());

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				result = adao
						.countActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol(), filtros);

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				AlumnoXActividadXDocumentoDAO axaxddao = new AlumnoXActividadXDocumentoDAO(
						s);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				if (idActividadTipo != 2) {
					throw new ConsistencyException(
							"La sincronización debe hacerse en el día 1.");
				}

				if (sinc == null) {
					throw new NullPointerException(
							"No se han especificado los datos para la sincronización.");
				}

				if (sinc.getRut() == null || !StringUtils.isRut(sinc.getRut())) {
					throw new NullPointerException(
							"No se ha especificado un alumno. Verifique que ha ingresado un rut válido.");
				}

				// if (sinc.getIdPendrive() == null) {
				// throw new NullPointerException(
				// "No se ha especificado un Pendrive.");
				// }

				if (sinc.getEstado() == null
						|| sinc.getEstado().getIdEstadoSincronizacion() == null) {
					throw new NullPointerException(
							"No se ha especificado un estado.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				AlumnoXActividadDAO axadao = new AlumnoXActividadDAO(s);
				AlumnoXActividad axa = axadao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCursoANDRutAlumno(
								idAplicacion, idNivel, idActividadTipo,
								idCurso, sinc.getRut());

				if (axa == null) {
					throw new NullPointerException(
							"No se ha encontrado al alumno especificado.");
				}

				AlumnoEstadoDAO aedao = new AlumnoEstadoDAO(s);
				AlumnoEstado ae = aedao
						.findByNombre((DocumentoEstado.SIN_INFORMACION
								.equals(sinc.getEstado().getNombreEstado())) ? AlumnoEstado.SIN_INFORMACION
								: AlumnoEstado.PRESENTE);

				axa.setAlumnoEstado(ae);
				axa.setPruebaComentario(sinc.getComentario());
				axadao.update(axa);

				DocumentoDAO ddao = new DocumentoDAO(s);
				AlumnoXActividadXDocumentoDAO axaxddao = new AlumnoXActividadXDocumentoDAO(
						s);

				AlumnoXActividadXDocumento axaxdPndrive = axaxddao
						.findByIdAlumnoXActividadANDTipoDocumento(axa.getId(),
								DocumentoTipo.PRUEBA);

				if (sinc.getIdPendrive() != null
						&& !sinc.getIdPendrive().isEmpty()) {
					// AlumnoXActividadXDocumento axaxdPndrive = axaxddao
					// .findByIdAlumnoXActividadANDCodigoDocumentoANDTipoDocumento(
					// axa.getId(), sinc.getIdPendrive(),
					// DocumentoTipo.PRUEBA);

					if (axaxdPndrive == null) {

						axaxdPndrive = new AlumnoXActividadXDocumento();
						axaxdPndrive.setAlumnoXActividad(axa);

					}
					Documento pendrive = ddao.findByCodigoANDTipoDocumento(
							sinc.getIdPendrive(), DocumentoTipo.PRUEBA);
					if (pendrive == null) {
						throw new NullPointerException(
								"No se ha encontrado un pendrive con el código ingresado.");
					}
					axaxdPndrive.setDocumento(pendrive);

					DocumentoEstadoDAO dedao = new DocumentoEstadoDAO(s);
					DocumentoEstado de = dedao.getById(sinc.getEstado()
							.getIdEstadoSincronizacion());

					axaxdPndrive.setDocumentoEstado(de);
					axaxdPndrive.setComentario(sinc.getComentario());
					axaxdPndrive.setEntregado(true);
					axaxdPndrive.setRecibido(!de
							.equals(DocumentoEstado.EXTRAVIADO));

					axaxdPndrive.setUpdatedAt(new Date());
					axaxdPndrive.setModificadorId(u.getId());

					axaxddao.saveOrUpdate(axaxdPndrive);
				} else {
					if (axaxdPndrive != null) {
						axaxddao.delete(axaxdPndrive);
					}
				}

				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a != null) {
					a.setTotalAlumnosPresentes((a.getTotalAlumnosPresentes() != null) ? a
							.getTotalAlumnosPresentes() + 1 : 0);
				}

				if (sinc.getEntregoFormulario()) {
					AlumnoXActividadXDocumento axaxdCuestionario = axaxddao
							.findByIdAlumnoXActividadANDTipoDocumento(
									axa.getId(),
									DocumentoTipo.CUESTIONARIO_PADRE);

					if (axaxdCuestionario == null) {
						DocumentoTipoDAO dtdao = new DocumentoTipoDAO(s);
						DocumentoTipo dt = dtdao
								.findByNombre(DocumentoTipo.CUESTIONARIO_PADRE);
						Documento cuestionario = new Documento();
						cuestionario.setDocumentoTipo(dt);
						ddao.save(cuestionario);
						axaxdCuestionario = new AlumnoXActividadXDocumento();
						axaxdCuestionario.setAlumnoXActividad(axa);
						axaxdCuestionario.setDocumento(cuestionario);
					}

					axaxdCuestionario.setEntregado(true);
					axaxdCuestionario.setRecibido(sinc.getEntregoFormulario());

					axaxdCuestionario.setUpdatedAt(new Date());
					axaxdCuestionario.setModificadorId(u.getId());

					axaxddao.saveOrUpdate(axaxdCuestionario);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	// /**
	// * @permiso getEvaluacionSupervisores
	// */
	// @Override
	// public ArrayList<EvaluacionUsuarioDTO> getEvaluacionSupervisores()
	// throws NoAllowedException, NoLoggedException, DBException {
	//
	// ArrayList<EvaluacionUsuarioDTO> eudtos = new
	// ArrayList<EvaluacionUsuarioDTO>();
	// Session s = HibernateUtil.getSessionFactory().openSession();
	// ManagedSessionContext.bind(s);
	// try {
	// AccessControl ac = getAccessControl();
	// if (ac.isLogged()
	// && ac.isAllowed(className, "getEvaluacionSupervisores")) {
	//
	// Integer idAplicacion = ac.getIdAplicacion();
	// if (idAplicacion == null) {
	// throw new NullPointerException(
	// "No se ha especificado una aplicación.");
	// }
	//
	// Integer idNivel = ac.getIdNivel();
	// if (idNivel == null) {
	// throw new NullPointerException(
	// "No se ha especificado un nivel.");
	// }
	//
	// Integer idActividadTipo = ac.getIdActividadTipo();
	// if (idActividadTipo == null) {
	// throw new NullPointerException(
	// "No se ha especificado el tipo de la actividad.");
	// }
	//
	// Usuario u = getUsuarioActual();
	//
	// s.beginTransaction();
	//
	// UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
	// if (usuarioTipo == null) {
	// throw new NullPointerException(
	// "No se ha especificado el tipo de usuario.");
	// }
	//
	// UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO(s);
	// List<UsuarioXActividad> uxas = uxadao
	// .findSupervisoresByIdAplicacionANDIdNivelANDIdActividadTipo(
	// idAplicacion, idNivel, idActividadTipo,
	// u.getId(), usuarioTipo.getRol());
	//
	// if (uxas != null && !uxas.isEmpty()) {
	// for (UsuarioXActividad uxa : uxas) {
	// eudtos.add(uxa.getEvaluacionUsuarioDTO());
	// }
	// }
	//
	// s.getTransaction().commit();
	// }
	// } catch (HibernateException ex) {
	// System.err.println(ex);
	// HibernateUtil.rollback(s);
	// throw new DBException();
	// } catch (ConsistencyException ex) {
	// HibernateUtil.rollbackActiveOnly(s);
	// throw ex;
	// } catch (NullPointerException ex) {
	// HibernateUtil.rollbackActiveOnly(s);
	// throw ex;
	// } finally {
	// ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
	// if (s.isOpen()) {
	// s.clear();
	// s.close();
	// }
	// }
	// return eudtos;
	// }

	// /**
	// * @permiso updateEvaluacionSupervisor
	// */
	// @Override
	// public Boolean updateEvaluacionSupervisor(EvaluacionUsuarioDTO
	// evaluacion)
	// throws NoAllowedException, NoLoggedException, DBException {
	//
	// Boolean result = true;
	// Session s = HibernateUtil.getSessionFactory().openSession();
	// ManagedSessionContext.bind(s);
	// try {
	// AccessControl ac = getAccessControl();
	// if (ac.isLogged()
	// && ac.isAllowed(className, "updateEvaluacionSupervisor")) {
	//
	// Integer idAplicacion = ac.getIdAplicacion();
	// if (idAplicacion == null) {
	// throw new NullPointerException(
	// "No se ha especificado una aplicación.");
	// }
	//
	// Integer idNivel = ac.getIdNivel();
	// if (idNivel == null) {
	// throw new NullPointerException(
	// "No se ha especificado un nivel.");
	// }
	//
	// Integer idActividadTipo = ac.getIdActividadTipo();
	// if (idActividadTipo == null) {
	// throw new NullPointerException(
	// "No se ha especificado el tipo de la actividad.");
	// }
	//
	// if (evaluacion == null) {
	// throw new NullPointerException(
	// "No se ha especificado la evaluación para el supervisor.");
	// }
	//
	// UserDTO udto = evaluacion.getUsuario();
	// if (udto == null || udto.getId() == null) {
	// throw new NullPointerException(
	// "No se ha especificado el supervisor.");
	// }
	//
	// Usuario u = getUsuarioActual();
	//
	// s.beginTransaction();
	//
	// UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
	// if (usuarioTipo == null) {
	// throw new NullPointerException(
	// "No se ha especificado el tipo de usuario.");
	// }
	//
	// UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO(s);
	// List<UsuarioXActividad> uxas = uxadao
	// .findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuario(
	// idAplicacion, idNivel, idActividadTipo,
	// udto.getId());
	//
	// if (uxas == null || uxas.isEmpty()) {
	// throw new ConsistencyException("El examinador ("
	// + udto.getRut() + ") " + udto.getNombres() + " "
	// + udto.getApellidoPaterno()
	// + " no está asociado a esta actividad.");
	// }
	// for (UsuarioXActividad uxa : uxas) {
	// uxa.setNotaPuntualidad(evaluacion.getPuntualidad());
	// uxa.setNotaLlenadoFormularios(evaluacion.getFormulario());
	// uxa.setNotaPresentacionPersonal(evaluacion
	// .getPresentacionPersonal());
	// uxa.setNotaDespempeno(evaluacion.getGeneral());
	// uxadao.update(uxa);
	// }
	//
	// s.getTransaction().commit();
	// }
	// } catch (HibernateException ex) {
	// System.err.println(ex);
	// HibernateUtil.rollback(s);
	// throw new DBException();
	// } catch (ConsistencyException ex) {
	// HibernateUtil.rollbackActiveOnly(s);
	// throw ex;
	// } catch (NullPointerException ex) {
	// HibernateUtil.rollbackActiveOnly(s);
	// throw ex;
	// } finally {
	// ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
	// if (s.isOpen()) {
	// s.clear();
	// s.close();
	// }
	// }
	// return result;
	// }

	/**
	 * @permiso getExaminadores
	 */
	@Override
	public ArrayList<UserDTO> getExaminadores(String search)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<UserDTO> udtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getExaminadores")) {

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

				if (search == null || search.equals("")) {
					throw new NullPointerException(
							"No se ha especificado un criterio de búsqueda.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioDAO udao = new UsuarioDAO(s);
				udtos = (ArrayList<UserDTO>) udao
						.findExaminadoresByIdAplicacionANDIdNivelANDFiltro(
								idAplicacion, idNivel, 0, 10, search);

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return udtos;
	}

	/**
	 * @permiso getTiposContingencia
	 */
	@Override
	public ArrayList<TipoContingenciaDTO> getTiposContingencia(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<TipoContingenciaDTO> tcdto = new ArrayList<TipoContingenciaDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				IncidenciaTipoDAO itdao = new IncidenciaTipoDAO(s);
				IncidenciaTipo it = itdao
						.findByNombre(IncidenciaTipo.CONTINGENCIA);
				if (it != null) {
					MotivoFallaDAO mfdao = new MotivoFallaDAO(s);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a == null) {
					throw new NullPointerException(
							"No existe una actividad para el curso especificado.");
				}
				// if (a.getFechaInicio() == null) {
				// throw new ConsistencyException(
				// "Esta actividad aún no ha sido agendada.");
				// }
				adto = a.getActividadDTO(s, idAplicacion, getBaseURL());
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return adto;
	}

	/**
	 * @permiso actualizarActividad
	 */
	@Override
	public Boolean actualizarActividad(ActividadDTO actividad)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "actualizarActividad")) {

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

				if (actividad == null) {
					throw new NullPointerException(
							"No se ha especificado una actividad..");
				}

				if (actividad.getIdCurso() == null) {
					throw new NullPointerException(
							"No se ha especificado un curso..");
				}

				if (actividad.getFechaActividad() == null) {
					throw new NullPointerException(
							"No se ha especificado la fecha de la actividad.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo,
								actividad.getIdCurso());

				if (a == null) {
					throw new NullPointerException(
							"No se ha encontrado la actividad especificada.");
				}

				a.setTotalAlumnosAusentes(actividad.getAlumnosAusentes());
				a.setTotalAlumnos(actividad.getAlumnosTotal());
				if (idAplicacion == 1) {
					a.setTotalAlumnosPresentes(actividad.getAlumnosTotal()
							- actividad.getAlumnosAusentes());
				}
				a.setTotalAlumnosNee(actividad.getAlumnosDs());

				Calendar c = Calendar.getInstance();

				// c.setTime(a.getFechaInicio());

				c.setTime(StringUtils.getDate(actividad.getFechaActividad()));

				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DATE);
				int hour;
				int minute;

				if (actividad.getInicioActividad() != null) {
					c.setTime(StringUtils.getDate(actividad
							.getInicioActividad()));
					hour = c.get(Calendar.HOUR_OF_DAY);
					minute = c.get(Calendar.MINUTE);
					c.set(year, month, day, hour, minute);
					a.setFechaInicio(c.getTime());
				}
				if (actividad.getInicioPrueba() != null) {
					c.setTime(StringUtils.getDate(actividad.getInicioPrueba()));
					hour = c.get(Calendar.HOUR_OF_DAY);
					minute = c.get(Calendar.MINUTE);
					c.set(year, month, day, hour, minute);
					a.setFechaInicioPrueba(c.getTime());
				}
				if (actividad.getTerminoPrueba() != null) {
					c.setTime(StringUtils.getDate(actividad.getTerminoPrueba()));
					hour = c.get(Calendar.HOUR_OF_DAY);
					minute = c.get(Calendar.MINUTE);
					c.set(year, month, day, hour, minute);
					a.setFechaTerminoPrueba(c.getTime());
				}

				a.setNotaProceso(actividad.getEvaluacionProcedimientos());

				if (actividad.getEstadoAplicacion() != null
						&& actividad.getEstadoAplicacion().getId() != null) {
					ActividadEstadoDAO aedao = new ActividadEstadoDAO(s);
					ActividadEstado ae = aedao.getById(actividad
							.getEstadoAplicacion().getId());
					a.setActividadEstado(ae);
				}

				ActividadXDocumentoTipoDAO axdtdao = new ActividadXDocumentoTipoDAO(
						s);
				ActividadXDocumentoTipo axdt = axdtdao
						.findByIdActividadANDDocumentoTipo(a.getId(),
								DocumentoTipo.CUESTIONARIO_PADRE);

				if (axdt == null) {
					axdt = new ActividadXDocumentoTipo();
					axdt.setActividad(a);
					DocumentoTipoDAO dtdao = new DocumentoTipoDAO(s);
					DocumentoTipo dt = dtdao
							.findByNombre(DocumentoTipo.CUESTIONARIO_PADRE);
					axdt.setDocumentoTipo(dt);
				}

				axdt.setTotal(actividad.getTotalCuestionarios());
				axdt.setTotalEntregados(actividad.getCuestionariosEntregados());
				axdt.setTotalRecibidos(actividad.getCuestionariosRecibidos());
				axdtdao.saveOrUpdate(axdt);

				// Visita Previa
				if (idActividadTipo == 1) {
					Actividad aplicacion = adao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
									idAplicacion, idNivel, 2, a.getCurso()
											.getId());
					if (aplicacion != null) {
						axdt = axdtdao.findByIdActividadANDDocumentoTipo(
								aplicacion.getId(),
								DocumentoTipo.CUESTIONARIO_PADRE);

						if (axdt == null) {
							axdt = new ActividadXDocumentoTipo();
							axdt.setActividad(aplicacion);
							DocumentoTipoDAO dtdao = new DocumentoTipoDAO(s);
							DocumentoTipo dt = dtdao
									.findByNombre(DocumentoTipo.CUESTIONARIO_PADRE);
							axdt.setDocumentoTipo(dt);
						}

						axdt.setTotal(actividad.getTotalCuestionarios());
						axdt.setTotalEntregados(actividad
								.getCuestionariosEntregados());
						axdtdao.saveOrUpdate(axdt);

						if (idAplicacion == 1) {
							aplicacion = adao
									.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
											idAplicacion, idNivel, 3, a
													.getCurso().getId());
							if (aplicacion != null) {
								axdt = axdtdao
										.findByIdActividadANDDocumentoTipo(
												aplicacion.getId(),
												DocumentoTipo.CUESTIONARIO_PADRE);

								if (axdt == null) {
									axdt = new ActividadXDocumentoTipo();
									axdt.setActividad(aplicacion);
									DocumentoTipoDAO dtdao = new DocumentoTipoDAO(
											s);
									DocumentoTipo dt = dtdao
											.findByNombre(DocumentoTipo.CUESTIONARIO_PADRE);
									axdt.setDocumentoTipo(dt);
								}

								axdt.setTotal(actividad.getTotalCuestionarios());
								axdt.setTotalEntregados(actividad
										.getCuestionariosEntregados());
								axdtdao.saveOrUpdate(axdt);
							}
						}
					}
				}

				a.setDetalleUsoMaterialContingencia(actividad
						.getDetalleUsoMaterialContingencia());
				a.setMaterialContingencia((actividad.getMaterialContingencia() != null && actividad
						.getMaterialContingencia()));

				Documento d = null;
				if (actividad.getDocumento() != null
						&& !StringUtils.isEmpty(actividad.getDocumento()
								.getName())) {
					DocumentoDAO ddao = new DocumentoDAO(s);
					if (actividad.getDocumento().getId() != null) {
						d = ddao.findByIdArchivo(actividad.getDocumento()
								.getId());
					}
					if (d == null) {
						d = new Documento();
						DocumentoTipoDAO dtdao = new DocumentoTipoDAO(s);
						DocumentoTipo dt = dtdao
								.findByNombre(DocumentoTipo.FORMULARIO_CONTROL_DE_APLICACION);
						d.setDocumentoTipo(dt);
					}
					if (d.getArchivo() == null
							|| !d.getArchivo().getId()
									.equals(actividad.getDocumento().getId())) {
						ArchivoDAO archivoDAO = new ArchivoDAO(s);
						Archivo archivo = null;
						if (actividad.getDocumento().getId() != null) {
							archivo = archivoDAO.getById(actividad
									.getDocumento().getId());
						}
						if (archivo == null) {
							archivo = guardarArchivo(actividad.getDocumento()
									.getName());
							DateFormat dateFormat = new SimpleDateFormat(
									"dd-MM-yyyy HH.mm.ss");
							archivo.setTitulo(dateFormat.format(new Date()));
							archivoDAO.save(archivo);
						}
						d.setArchivo(archivo);
						ddao.saveOrUpdate(d);
					}

					ActividadXDocumentoDAO axddao = new ActividadXDocumentoDAO(
							s);
					ActividadXDocumento axd = axddao
							.findByIdActividadANDIdDocumento(a.getId(),
									d.getId());
					if (axd == null) {
						axd = new ActividadXDocumento();
						ActividadXDocumentoId axdi = new ActividadXDocumentoId();
						axdi.setIdActividad(a.getId());
						axdi.setIdDocumento(d.getId());
						axd.setId(axdi);
						axddao.save(axd);
					}
				}

				ActividadXIncidenciaDAO axidao = new ActividadXIncidenciaDAO(s);
				List<ActividadXIncidencia> axis = axidao
						.findByIdActividadANDIncidenciaTipo(a.getId(),
								IncidenciaTipo.CONTINGENCIA);
				MotivoFallaDAO mfdao = new MotivoFallaDAO(s);
				MotivoFalla mf = null;

				if (axis != null && !axis.isEmpty()) {
					Boolean encontrado = false;
					for (ActividadXIncidencia axi : axis) {
						encontrado = false;
						if (actividad.getContingencias() != null
								&& !actividad.getContingencias().isEmpty()) {
							for (ContingenciaDTO cdto : actividad
									.getContingencias()) {
								if (axi.getMotivoFalla()
										.getId()
										.equals(cdto.getTipoContingencia()
												.getId())) {
									encontrado = true;
								}
							}
						}
						if (!encontrado) {
							axidao.delete(axi);
						}
					}
				}

				if (actividad.getContingencias() != null
						&& !actividad.getContingencias().isEmpty()) {
					ActividadXIncidencia axi = null;
					for (ContingenciaDTO cdto : actividad.getContingencias()) {
						if (cdto.getTipoContingencia() != null
								&& cdto.getTipoContingencia().getId() != null) {
							axi = axidao.findByIdActividadANDIdMotivoFalla(a
									.getId(), cdto.getTipoContingencia()
									.getId());
							if (axi == null) {
								axi = new ActividadXIncidencia();
								axi.setActividad(a);
								mf = mfdao.getById(cdto.getTipoContingencia()
										.getId());
								axi.setMotivoFalla(mf);
							}
							axi.setInhabilitaAplicacion(cdto.getInabilitante());
							axi.setMotivoDescripcion(cdto
									.getDetalleContingencia());
							axidao.saveOrUpdate(axi);
						}
					}
				}

				a.setUsuario(u);
				adao.update(a);

				ActividadHistorialDAO ahdao = new ActividadHistorialDAO(s);
				ActividadHistorial ah = new ActividadHistorial();
				ActividadHistorialId ahid = new ActividadHistorialId();
				ahid.setActividadId(a.getId());
				ahid.setFecha(new Date());
				ah.setId(ahid);
				ah.setFechaInicio(a.getFechaInicio());
				ah.setActividadEstado(a.getActividadEstado());
				ah.setComentario(a.getComentario());
				ah.setFechaTermino(a.getFechaTermino());
				ah.setModificadorId(u.getId());
				ahdao.save(ah);

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			ex.printStackTrace();
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			ex.printStackTrace();
			throw ex;
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	/**
	 * @permiso getEstadosActividad
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosActividad()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadEstadoDAO aedao = new ActividadEstadoDAO(s);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO(s);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "updateEvaluacionExaminadores")) {

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
							"No se ha especificado un curso.");
				}

				// if (evaluaciones == null || evaluaciones.isEmpty()) {
				// throw new NullPointerException(
				// "No se han especificado las evaluaciones.");
				// }

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO(s);
				UsuarioXActividad uxa = null;
				UserDTO udto = null;
				SuplenteXCoDAO sxcDAO = new SuplenteXCoDAO(s);
				SuplenteXCo sxc = null;
				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				UsuarioSeleccionDAO usdao = new UsuarioSeleccionDAO(s);
				UsuarioSeleccion us = null;
				List<Integer> idReemplazados = new ArrayList<Integer>();
				List<Actividad> actividades = null;
				UsuarioTipoDAO utdao = new UsuarioTipoDAO(s);
				UsuarioTipo examinadorTipo = utdao
						.findByNombre(UsuarioTipo.EXAMINADOR);
				UsuarioXActividad uxaNext = null;
				UsuarioDAO udao = new UsuarioDAO(s);
				List<Usuario> usuariosAusentes = null;

				if (evaluaciones != null && !evaluaciones.isEmpty()) {
					for (EvaluacionUsuarioDTO eudto : evaluaciones) {
						if (eudto.getEstado().equals(
								EvaluacionUsuarioDTO.ESTADO_REMPLAZADO)) {
							idReemplazados.add(eudto.getUsuario().getId());
						}
					}

					usuariosAusentes = udao
							.findExaminadoresAusentesByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
									idAplicacion, idNivel, idActividadTipo,
									idCurso);

					if (usuariosAusentes != null && !usuariosAusentes.isEmpty()) {
						for (Usuario usuarioAusente : usuariosAusentes) {
							idReemplazados.add(usuarioAusente.getId());
						}
					}

					if (idReemplazados != null && !idReemplazados.isEmpty()) {
						actividades = adao
								.findByIdAplicacionANDIdNivelANDIdCursoANDBiggerThanFechaInicio(
										idAplicacion, idNivel, idCurso,
										a.getFechaInicio());
					}

					for (EvaluacionUsuarioDTO eudto : evaluaciones) {
						udto = eudto.getUsuario();
						if (udto != null && udto.getId() != null) {
							uxa = uxadao
									.findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCursoANDIdUsuario(
											idAplicacion, idNivel,
											idActividadTipo, idCurso,
											udto.getId());
							if (uxa == null) {
								if (eudto
										.getEstado()
										.equals(EvaluacionUsuarioDTO.ESTADO_REMPLAZANTE)) {
									sxc = sxcDAO
											.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuario(
													idAplicacion, idNivel,
													idActividadTipo,
													udto.getId());
									if (sxc != null) {
										sxc.setReemplazando(true);
										sxc.setAsistencia(true);
										sxcDAO.update(sxc);

										us = sxc.getUsuarioSeleccion();
									} else {
										us = usdao
												.findByIdAplicacionANDIdNivelANDIdUsuario(
														idAplicacion, idNivel,
														udto.getId());
										if (us == null) {
											throw new ConsistencyException(
													"El usuario ("
															+ udto.getRut()
															+ ") "
															+ udto.getNombres()
															+ " "
															+ udto.getApellidoPaterno()
															// +
															// " no ha sido asignado como examinador para este nivel.<br />Si cree que es un error envíe un correo a server.simce@usm.cl con este mensaje y los detalles de esta actividad.");
															+ " no ha sido seleccionado como examinador para este nivel.<br />El "
															+ ((idAplicacion == 1) ? "Jefe de Centro de Operaciones"
																	: "Encargado de Logística")
															+ " debe realizar la selección para el nivel en la intranet en YoSimce.");
										}
									}
									uxa = uxadao
											.findExaminadorNoAsignadoByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
													idAplicacion, idNivel,
													idActividadTipo, idCurso);
									if (uxa == null) {
										uxa = new UsuarioXActividad();
										uxa.setActividad(a);
										uxa.setUsuarioTipo(us.getUsuarioTipo());
									}
									uxa.setUsuarioSeleccion(us);

									if (actividades != null
											&& !actividades.isEmpty()) {
										if (idReemplazados != null
												&& !idReemplazados.isEmpty()) {
											for (Actividad actividad : actividades) {
												uxaNext = uxadao
														.findByIdActividadANDIdUsuario(
																actividad
																		.getId(),
																idReemplazados
																		.get(0));
												if (uxaNext != null) {
													uxaNext.setUsuarioSeleccion(us);
													uxaNext.setUsuario(u);
													uxadao.update(uxaNext);
												}
											}
											idReemplazados.remove(0);
										}
									}

									// else {
									// throw new ConsistencyException(
									// "El usuario ("
									// + udto.getRut()
									// + ") "
									// + udto.getNombres()
									// + " "
									// + udto.getApellidoPaterno()
									// +
									// " no ha sido asignado como suplente.<br />Si cree que es un error envíe un correo a server.simce@usm.cl con este mensaje y los detalles de esta actividad.");
									// }

								} else {
									throw new ConsistencyException(
											"El examinador ("
													+ udto.getRut()
													+ ") "
													+ udto.getNombres()
													+ " "
													+ udto.getApellidoPaterno()
													// +
													// " no está asociado a esta actividad.<br />Si cree que es un error envíe un correo a server.simce@usm.cl con este mensaje y los detalles de esta actividad.");
													+ " no ha sido seleccionado como examinador para este nivel.<br />El "
													+ ((idAplicacion == 1) ? "Jefe de Centro de Operaciones"
															: "Encargado de Logística")
													+ " debe realizar la selección para el nivel en la intranet en YoSimce.");
								}
							}
							if (eudto.getEstado().equals(
									EvaluacionUsuarioDTO.ESTADO_REMPLAZADO)) {
								uxa.setAsistencia(false);
							} else {
								uxa.setNotaPuntualidad(eudto.getPuntualidad());
								uxa.setNotaLlenadoFormularios(eudto
										.getFormulario());
								uxa.setNotaPresentacionPersonal(eudto
										.getPresentacionPersonal());
								uxa.setNotaDespempeno(eudto.getGeneral());
								uxa.setAsistencia(true);
							}
							uxa.setUsuario(u);
							uxadao.saveOrUpdate(uxa);
						}
					}
				}

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			ex.printStackTrace();
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	/**
	 * @permiso getEstadosSincronizacionFallida
	 */
	@Override
	public ArrayList<EstadoSincronizacionDTO> getEstadosSincronizacionFallida()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoSincronizacionDTO> esdtos = new ArrayList<EstadoSincronizacionDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				DocumentoEstadoDAO dedao = new DocumentoEstadoDAO(s);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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

		ArrayList<MaterialDefectuosoDTO> mddtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (a == null) {
					throw new NullPointerException(
							"El curso especificado no tiene una actividad asociada.");
				}

				AlumnoXActividadXDocumentoDAO axaxddao = new AlumnoXActividadXDocumentoDAO(
						s);
				mddtos = (ArrayList<MaterialDefectuosoDTO>) axaxddao
						.findDefectuososByIdctividadANDTipoDocumento(a.getId(),
								DocumentoTipo.PRUEBA);

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "addOrUpdateMaterialDefectuoso")) {

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

				if (material == null || material.getIdMaterial() == null
						|| material.getIdMaterial().equals("")) {
					throw new NullPointerException(
							"No se ha especificado el pendrive.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (a == null) {
					throw new NullPointerException(
							"El curso especificado no tiene una actividad asociada.");
				}

				DocumentoDAO ddao = new DocumentoDAO(s);
				Documento d = ddao.findByCodigoANDTipoDocumento(
						material.getIdMaterial(), DocumentoTipo.PRUEBA);

				if (d == null) {
					throw new NullPointerException(
							"No se ha encontrado el pendrive especificado. Verifique que ha ingresado el código correctamente.");
				}

				AlumnoXActividadDAO axadao = new AlumnoXActividadDAO(s);
				AlumnoXActividad axa = axadao.findSinAlumnoByIdActividad(a
						.getId());

				if (axa == null) {
					axa = new AlumnoXActividad();
					axa.setActividad(a);
					axadao.save(axa);
				}

				AlumnoXActividadXDocumentoDAO axaxddao = new AlumnoXActividadXDocumentoDAO(
						s);
				AlumnoXActividadXDocumento axaxd = axaxddao
						.findByIdAlumnoXActividadANDIdDocumento(axa.getId(),
								d.getId());

				if (axaxd == null) {
					axaxd = new AlumnoXActividadXDocumento();
					axaxd.setAlumnoXActividad(axa);
					axaxd.setDocumento(d);
				}

				if (material.getEstado() != null) {
					DocumentoEstadoDAO dedao = new DocumentoEstadoDAO(s);
					DocumentoEstado de = dedao.getById(material.getEstado()
							.getIdEstadoSincronizacion());
					axaxd.setDocumentoEstado(de);
				}

				axaxddao.saveOrUpdate(axaxd);

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	/**
	 * @permiso removeMaterialDefectuoso
	 */
	@Override
	public Boolean removeMaterialDefectuoso(Integer idCurso, String idMaterial)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "removeMaterialDefectuoso")) {

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

				if (idMaterial == null || idMaterial.equals("")) {
					throw new NullPointerException(
							"No se ha especificado el pendrive.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (a == null) {
					throw new NullPointerException(
							"El curso especificado no tiene una actividad asociada.");
				}

				DocumentoDAO ddao = new DocumentoDAO(s);
				Documento d = ddao.findByCodigoANDTipoDocumento(idMaterial,
						DocumentoTipo.PRUEBA);

				if (d == null) {
					throw new NullPointerException(
							"No se ha encontrado el pendrive especificado. Verifique que ha ingresado el código correctamente.");
				}

				AlumnoXActividadDAO axadao = new AlumnoXActividadDAO(s);
				AlumnoXActividad axa = axadao.findSinAlumnoByIdActividad(a
						.getId());

				if (axa != null) {
					axadao.delete(axa);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	/**
	 * @permiso getDocumentoPreviewActividades
	 */
	@Override
	public DocumentoDTO getDocumentoPreviewActividades(
			HashMap<String, String> filtros) throws NoAllowedException,
			NoLoggedException, DBException {

		DocumentoDTO ddto = new DocumentoDTO();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO(s);
				Integer total = adao
						.countActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol(), filtros);
				if (total == null || total == 0) {
					throw new NullPointerException(
							"No se han obtenido resultados con el filtro especificado.");
				}

				Integer offset = 0;
				Integer lenght = 10000;
				List<ActividadPreviewDTO> apdtos = null;

				ActividadTipoDAO atdao = new ActividadTipoDAO(s);
				ActividadTipo at = atdao.getById(idActividadTipo);

				DateFormat dateFormat = new SimpleDateFormat(
						"dd-MM-yyyy HH.mm.ss");
				String name = "Actividades " + at.getNombre() + " "
						+ dateFormat.format(new Date());
				File file = File.createTempFile(
						StringUtils.getDatePathSafe(name), ".csv",
						getUploadDirForTmpFiles());
				// FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "ISO-8859-1"));

				String contenido;
				if (total != 0) {
					String header = "RBD;Establecimiento;Curso;Tipo Establecimiento;Estado Actividad;Alumnos Total";
					if (!(idAplicacion == 2 && idActividadTipo == 1)) {
						header += ";Alumnos Evaluados;Alumnos Sincronizados";
					}
					header += ";Cuestionarios Entregados";
					if (!(idAplicacion == 2 && idActividadTipo == 1)) {
						header += ";Cuestionarios Recibidos";
					}
					if (idAplicacion == 2 && idActividadTipo == 2) {
						header += ";Cuestionarios Aplicados";
					}
					header += ";Ocurrió Contingencia;Contingencia Inhabilitante";
					if (idAplicacion == 1) {
						header += ";Usa Material de Contingencia;Detalle Uso Material de Contingencia";
					}
					header += ";Región;Comuna;Examinador;Supervisor;Nombre Contacto;Teléfono Contacto;Email Contacto\r";
					bw.write(header);
				}
				while (total > 0) {
					apdtos = adao
							.findActividadesByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
									idAplicacion, idNivel, idActividadTipo,
									u.getId(), usuarioTipo.getRol(), offset,
									lenght, filtros, getBaseURL());

					total -= lenght;
					offset += lenght;

					if (apdtos != null && !apdtos.isEmpty()) {
						for (ActividadPreviewDTO apdto : apdtos) {
							contenido = apdto.getRbd() + ";";
							contenido += apdto.getNombreEstablecimiento() + ";";
							contenido += apdto.getCurso() + ";";
							contenido += apdto.getTipoEstablecimiento() + ";";
							contenido += apdto.getEstadoAgenda() + ";";
							contenido += apdto.getAlumnosTotales() + ";";
							if (!(idAplicacion == 2 && idActividadTipo == 1)) {
								contenido += apdto.getAlumnosEvaluados() + ";";
								contenido += apdto.getAlumnosSincronizados()
										+ ";";
							}
							contenido += apdto
									.getCuestionariosPadresApoderadosEntregados()
									+ ";";
							if (!(idAplicacion == 2 && idActividadTipo == 1)) {
								contenido += apdto
										.getCuestionariosPadresApoderadosRecibidos()
										+ ";";
							}
							if (idAplicacion == 2 && idActividadTipo == 2) {
								contenido += apdto
										.getCuestionariosPadresApoderadosRecibidosAplicados()
										+ ";";
							}
							contenido += apdto.getContingencia() + ";";
							contenido += apdto.getContingenciaLimitante() + ";";
							if (idAplicacion == 1) {
								contenido += apdto.getUsoMaterialContingencia()
										+ ";";
								contenido += apdto
										.getDetalleUsoMaterialContingecia()
										+ ";";
							}
							contenido += apdto.getRegion() + ";";
							contenido += apdto.getComuna() + ";";
							contenido += apdto.getNombreExaminador() + ";";
							contenido += apdto.getNombreSupervisor() + ";";
							contenido += apdto.getNombreContacto() + ";";
							contenido += apdto.getTelefonoContacto() + ";";
							contenido += apdto.getMailContacto();
							bw.write(contenido + "\r");
						}
					}
				}
				bw.close();

				ArchivoDAO ardao = new ArchivoDAO(s);
				Archivo archivo = new Archivo();
				archivo.setTitulo(name);
				archivo.setRutaArchivo(file.getAbsolutePath());
				archivo.setMimeType("text/plain");
				archivo.setIpServer("200.1.30.52");
				ardao.save(archivo);

				ddto = archivo.getDocumentoDTO(getBaseURL());

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
		} catch (IOException e) {
			HibernateUtil.rollbackActiveOnly(s);
			throw new NullPointerException("No se pudo crear el archivo.");
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				AlumnoDAO adao = new AlumnoDAO(s);
				Integer total = adao
						.countAlumnosCsvByIdAplicacionANDIdNivelANDIdTipoActividadANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol(), filtros);
				if (total == null || total == 0) {
					throw new NullPointerException(
							"No se han obtenido resultados con el filtro especificado.");
				}
				ActividadTipoDAO atdao = new ActividadTipoDAO(s);
				ActividadTipo at = atdao.getById(idActividadTipo);

				Integer offset = 0;
				Integer lenght = 10000;
				List<String> filas = null;
				DateFormat dateFormat = new SimpleDateFormat(
						"dd-MM-yyyy HH.mm.ss");
				String name = "Alumnos " + at.getNombre() + " "
						+ dateFormat.format(new Date());
				File file = File.createTempFile(
						StringUtils.getDatePathSafe(name), ".csv",
						getUploadDirForTmpFiles());
				// FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "ISO-8859-1"));

				bw.write("rbd;establecimiento_nombre;establecimiento_tipo;región;comuna;tipo_actividad;total_alumnos;"
						+ "rut;apellido_paterno;apellido_materno;nombres;tipo_alumno;estado_alumno;pendrive;estado_pendrive;cuestionario_entregado\r");
				while (total > 0) {
					filas = adao
							.findAlumnosCsvByIdAplicacionANDIdNivelANDIdTipoActividadANDFiltros(
									idAplicacion, idNivel, idActividadTipo,
									u.getId(), usuarioTipo.getRol(), offset,
									lenght, filtros);
					total -= lenght;
					offset += lenght;

					if (filas != null && !filas.isEmpty()) {
						for (String contenido : filas) {
							bw.write(contenido + "\r");
						}
					}
				}
				bw.close();

				ArchivoDAO ardao = new ArchivoDAO(s);
				Archivo archivo = new Archivo();
				archivo.setTitulo(name);
				archivo.setRutaArchivo(file.getAbsolutePath());
				archivo.setMimeType("text/plain");
				archivo.setIpServer("200.1.30.52");
				ardao.save(archivo);

				ddto = archivo.getDocumentoDTO(getBaseURL());

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
		} catch (IOException e) {
			HibernateUtil.rollbackActiveOnly(s);
			throw new NullPointerException("No se pudo crear el archivo.");
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				DocumentoEstadoDAO dedao = new DocumentoEstadoDAO(s);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return esdtos;
	}

	/**
	 * @permiso getEstadosActividadFiltro
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosActividadFiltro()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getEstadosActividadFiltro")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadEstadoDAO aedao = new ActividadEstadoDAO(s);
				List<ActividadEstado> aes = aedao.findAll2();

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return eadtos;
	}

	/**
	 * @permiso getEvaluacionSupervisores
	 */
	@Override
	public ArrayList<EvaluacionSupervisorDTO> getEvaluacionSupervisores()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<EvaluacionSupervisorDTO> eudtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO(s);
				eudtos = (ArrayList<EvaluacionSupervisorDTO>) uxadao
						.findEvaluacionSupervisoresByIdAplicacionANDIdNivelANDIdActividadTipo(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol());

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return eudtos;
	}

	/**
	 * @permiso updateEvaluacionSupervisor
	 */
	@Override
	public Boolean updateEvaluacionSupervisor(
			EvaluacionSupervisorDTO evaluaciones) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {
		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "updateEvaluacionSupervisor")) {

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

				if (evaluaciones == null) {
					throw new NullPointerException(
							"No se ha especificado la evaluación para el supervisor.");
				}

				if (evaluaciones.getCurso() == null) {
					throw new NullPointerException(
							"No se ha especificado el curso.");
				}
				if (evaluaciones.getEstablecimiento() == null) {
					throw new NullPointerException(
							"No se ha especificado el establecimiento.");
				}

				UserDTO udto = evaluaciones.getSupervisor();
				if (udto == null || udto.getId() == null) {
					throw new NullPointerException(
							"No se ha especificado el supervisor.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioXActividadDAO uxadao = new UsuarioXActividadDAO(s);
				UsuarioXActividad uxa = uxadao
						.findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDIdEstablecimientoANDNombreCurso(
								idAplicacion, idNivel, idActividadTipo,
								udto.getId(),
								Integer.valueOf(evaluaciones.getRbd()),
								evaluaciones.getCurso());

				if (uxa == null) {
					throw new ConsistencyException("El supervisor ("
							+ udto.getRut() + ") " + udto.getNombres() + " "
							+ udto.getApellidoPaterno()
							+ " no está asociado a la actividad del curso ["
							+ evaluaciones.getEstablecimiento() + "] "
							+ evaluaciones.getCurso() + ".");
				}
				uxa.setNotaPuntualidad(evaluaciones.getPuntualidad());
				uxa.setNotaPresentacionPersonal(evaluaciones
						.getPresentacionPersonal());
				uxa.setNotaDespempeno(evaluaciones.getGeneral());
				// uxa.setAsistencia(evaluaciones.getGeneral() != null
				// && evaluaciones.getGeneral() > 0);
				uxa.setAsistencia(evaluaciones.getPresente());
				uxadao.update(uxa);

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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	/**
	 * @permiso getEvaluacionSuplentes
	 */
	@Override
	public ArrayList<EvaluacionSuplenteDTO> getEvaluacionSuplentes()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<EvaluacionSuplenteDTO> esdtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getEvaluacionSuplentes")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				SuplenteXCoDAO sxcdao = new SuplenteXCoDAO(s);
				esdtos = (ArrayList<EvaluacionSuplenteDTO>) sxcdao
						.findEvaluacionesByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDUsuarioTipo(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol());

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			ex.printStackTrace();
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (Exception ex) {
			HibernateUtil.rollbackActiveOnly(s);
			System.err.println(ex);
			throw new NullPointerException("Ocurrió un error inesperado");
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return esdtos;
	}

	/**
	 * @permiso updateEvaluacionSuplente
	 */
	@Override
	public Boolean updateEvaluacionSuplente(EvaluacionSuplenteDTO evaluaciones)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "updateEvaluacionSuplente")) {

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

				if (evaluaciones == null) {
					throw new NullPointerException(
							"No se ha especificado la evaluación para el supervisor.");
				}

				UserDTO udto = evaluaciones.getSuplente();
				if (udto == null || udto.getId() == null) {
					throw new NullPointerException(
							"No se ha especificado el suplente.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				SuplenteXCoDAO sxcdao = new SuplenteXCoDAO(s);
				SuplenteXCo sxc = sxcdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuario(
								idAplicacion, idNivel, idActividadTipo,
								udto.getId());

				if (sxc == null) {
					throw new NullPointerException(
							"El usuario especificado no figura como suplente en el nivel y tipo de actividad indicados.");
				}

				if (evaluaciones.getGeneral() != null) {
					sxc.setEvaluacion(evaluaciones.getGeneral());
				}
				sxc.setAsistencia((evaluaciones.getPresente() != null) ? evaluaciones
						.getPresente() : false);
				sxc.setUsuario(u);

				sxcdao.update(sxc);

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			ex.printStackTrace();
			HibernateUtil.rollback(s);
			throw new DBException();
		} catch (ConsistencyException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (NullPointerException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw ex;
		} catch (Exception ex) {
			HibernateUtil.rollbackActiveOnly(s);
			System.err.println(ex);
			throw new NullPointerException("Ocurrió un error inesperado");
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}
}
