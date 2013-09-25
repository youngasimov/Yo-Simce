package com.dreamer8.yosimce.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadHistorialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ArchivoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ContactoCargoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class PlanificacionServiceImpl extends CustomRemoteServiceServlet
		implements PlanificacionService {

	private String className = "PlanificacionService";

	/**
	 * @permiso getPreviewAgendamientos
	 */
	@Override
	public ArrayList<AgendaPreviewDTO> getPreviewAgendamientos(Integer offset,
			Integer length, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<AgendaPreviewDTO> apdtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getPreviewAgendamientos")) {

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
				apdtos = (ArrayList<AgendaPreviewDTO>) adao
						.findAgendasByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
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
	 * @permiso getAgendaCurso
	 */
	@Override
	public AgendaDTO getAgendaCurso(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {
		AgendaDTO adto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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
	 * @permiso AgendarVisita
	 */
	@Override
	public AgendaItemDTO AgendarVisita(Integer idCurso, AgendaItemDTO itemAgenda)
			throws NoAllowedException, NoLoggedException, DBException {

		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				Boolean enviarMail = false;

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

				ActividadHistorialDAO ahdao = new ActividadHistorialDAO();
				ActividadHistorial ah = ahdao.findFirstByIdActividad(a.getId());
				ActividadEstadoDAO aedao = new ActividadEstadoDAO();
				ActividadEstado ae = aedao.getById(itemAgenda.getEstado()
						.getId());

				if (ae == null) {
					throw new NullPointerException(
							"El estado especificado no existe");
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(itemAgenda.getFecha());
				int yearNew = calendar.get(Calendar.YEAR);
				int monthNew = calendar.get(Calendar.MONTH);
				SimpleDateFormat sdf = new SimpleDateFormat("MMMM",
						Locale.forLanguageTag("es"));
				String month = sdf.format(itemAgenda.getFecha());
				int dayNew = calendar.get(Calendar.DAY_OF_MONTH);
				int hourNew = calendar.get(Calendar.HOUR_OF_DAY);
				int minNew = calendar.get(Calendar.MINUTE);

				if (ae.getNombre().equals(ActividadEstado.CONFIRMADO)) {
					enviarMail = true;
					if (ah != null && ah.getFechaInicio() != null) {

						calendar.setTime(ah.getFechaInicio());
						int yearHist = calendar.get(Calendar.YEAR);
						int monthHist = calendar.get(Calendar.MONTH);
						int dayHist = calendar.get(Calendar.DAY_OF_MONTH);
						int hourHist = calendar.get(Calendar.HOUR_OF_DAY);
						int minHist = calendar.get(Calendar.MINUTE);

						if (yearHist != yearNew || monthHist != monthNew
								|| dayHist != dayNew || hourHist != hourNew
								|| minHist != minNew) {
							ae = aedao
									.findByNombre(ActividadEstado.CONFIRMADO_CON_CAMBIOS);
						}
					}
				}

				// if (idAplicacion == 2
				// && a.getAplicacionXNivelXActividadTipo()
				// .getActividadTipo().getNombre()
				// .equals(ActividadTipo.APLICACION_DIA_1)) {
				// if (itemAgenda.getFecha() != null) {
				// Actividad visitaPrevia = adao
				// .findByIdAplicacionANDIdNivelANDIdCursoANDTipoActividad(
				// idAplicacion, idNivel, idCurso,
				// ActividadTipo.VISITA_PREVIA);
				// if (visitaPrevia == null
				// || !ActividadEstado.SIN_INFORMACION
				// .equals(visitaPrevia
				// .getActividadEstado()
				// .getNombre())
				// || visitaPrevia.getFechaInicio() == null) {
				// throw new ConsistencyException(
				// "Primero debe agendarse la visita previa.");
				// }
				//
				// Long diff = itemAgenda.getFecha().getTime()
				// - visitaPrevia.getFechaInicio().getTime();
				// Double dias = diff.doubleValue()
				// / (1000 * 60 * 60 * 24);
				//
				// if (dias < 7) {
				// throw new ConsistencyException(
				// "La aplicación debe realizarse al menos 7 días después de la visita previa.");
				// }
				// }
				// }

				a.setFechaInicio(itemAgenda.getFecha());
				a.setUsuario(u);
				a.setComentario(itemAgenda.getComentario());
				a.setActividadEstado(ae);
				adao.update(a);

				UsuarioDAO udao = new UsuarioDAO();
				u = udao.getById(u.getId());
				itemAgenda.setCreador(u.getUserDTO());

				String dirMail = null;
				String contMail = a.getContactoEmail();

				Usuario firm = null;
				String firmante = null;
				if (enviarMail) {
					if (a.getContactoCargo() != null
							&& a.getContactoCargo().getNombre()
									.equals(ContactoCargo.DIRECTOR)) {
						dirMail = contMail;
						contMail = null;
					} else {
						EstablecimientoDAO edao = new EstablecimientoDAO();
						Establecimiento e = edao.findByIdActividad(a.getId());
						if (e != null) {
							dirMail = e.getEmail();
						}
					}

					firm = udao.findJOByIdAplicacionANDIdNivelANDIdActividad(
							idAplicacion, idNivel, a.getId());

					firmante = (firm != null) ? StringUtils
							.nombreInicialSegundo(firm.getNombres())
							+ " "
							+ firm.getApellidoPaterno() : "";

				}
				s.getTransaction().commit();
				
				if (enviarMail
						&& ((dirMail != null && !dirMail.isEmpty()) || (contMail != null && !contMail
								.isEmpty()))) {
					if (dirMail == null || dirMail.isEmpty()) {
						dirMail = contMail;
						contMail = null;
					}
					if (idAplicacion == 1) {
						sendEmailDirector(dirMail, contMail, firmante, dayNew
								+ " de " + month,
								StringUtils.forceTwoDigits(hourNew) + ":"
										+ StringUtils.forceTwoDigits(minNew),
								StringUtils.forceTwoDigits(hourNew + 2) + ":"
										+ StringUtils.forceTwoDigits(minNew));
					} else {
						// sendEmailDirectorTIC();
					}
				}
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
		return itemAgenda;
	}

	/**
	 * @permiso getEstadosAgenda
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosAgenda()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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
	 * @permiso getContacto
	 */
	@Override
	public ContactoDTO getContacto(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {

		ContactoDTO cdto = new ContactoDTO();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				if (idCurso == null) {
					throw new NullPointerException(
							"No se ha especificado un curso.");
				}

				s.beginTransaction();

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return cdto;
	}

	/**
	 * @permiso getTotalPreviewAgendamientos
	 */
	@Override
	public Integer getTotalPreviewAgendamientos(Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		Integer result = 0;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getTotalPreviewAgendamientos")) {

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
						.countAgendasByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
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
	 * @permiso editarContacto
	 */
	@Override
	public Boolean editarContacto(Integer idCurso, ContactoDTO contacto)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean resutl = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "editarContacto")) {

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

				if (contacto == null || contacto.getContactoNombre().equals("")
						|| contacto.getContactoTelefono().equals("")) {
					throw new NullPointerException(
							"No se han especificado los datos del contacto.");
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
							"No se ha encontrado una actividad para el curso especificado.");
				}
				a.setContactoNombre(contacto.getContactoNombre());
				a.setContactoTelefono(contacto.getContactoTelefono());
				a.setContactoEmail(contacto.getContactoEmail());
				if (contacto.getCargo() != null
						&& contacto.getCargo().getId() != null) {
					ContactoCargoDAO ccdao = new ContactoCargoDAO();
					ContactoCargo cc = ccdao.getById(contacto.getCargo()
							.getId());
					a.setContactoCargo(cc);
				}
				adao.update(a);

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
		return resutl;
	}

	/**
	 * @permiso getCargos
	 */
	@Override
	public ArrayList<CargoDTO> getCargos() throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<CargoDTO> cdtos = new ArrayList<CargoDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getCargos")) {

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

				ContactoCargoDAO ccdao = new ContactoCargoDAO();
				List<ContactoCargo> ccs = ccdao.findAll();
				if (ccs != null && !ccs.isEmpty()) {
					for (ContactoCargo contactoCargo : ccs) {
						cdtos.add(contactoCargo.getCargoDTO());
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
		return cdtos;
	}

	/**
	 * @permiso getDocumentoPreviewAgendamientos
	 */
	@Override
	public DocumentoDTO getDocumentoPreviewAgendamientos(
			Map<String, String> filtros) throws NoAllowedException,
			NoLoggedException, DBException {

		DocumentoDTO ddto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className,
							"getDocumentoPreviewAgendamientos")) {

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
				Integer total = adao
						.countAgendasCsvByIdAplicacionANDIdNivelANDFiltros(
								idAplicacion, idNivel, u.getId(),
								usuarioTipo.getNombre(), filtros);
				if (total == null || total == 0) {
					throw new NullPointerException(
							"No se han obtenido resultados con el filtro especificado.");
				}

				Integer offset = 0;
				Integer lenght = 1000;
				List<String> filas = null;
				DateFormat dateFormat = new SimpleDateFormat(
						"dd-MM-yyyy HH.mm.ss");
				String name = "Agendamiento " + dateFormat.format(new Date());
				File file = File.createTempFile(
						StringUtils.getDatePathSafe(name), ".csv",
						getUploadDir());
				// FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "ISO-8859-1"));

				while (total > 0) {
					filas = adao
							.findAgendasCsvByIdAplicacionANDIdNivelANDFiltros(
									idAplicacion, idNivel, u.getId(),
									usuarioTipo.getNombre(), offset, lenght,
									filtros);
					total -= lenght;

					if (filas != null && !filas.isEmpty()) {
						for (String contenido : filas) {
							bw.write(contenido + "\r");
						}
					}
				}
				bw.close();

				ArchivoDAO ardao = new ArchivoDAO();
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
		} catch (IOException ex) {
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
	 * @permiso getDirector
	 */
	@Override
	public ContactoDTO getDirector(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {

		ContactoDTO cdto = new ContactoDTO();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getDirector")) {

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

				CursoDAO cdao = new CursoDAO();
				Curso c = cdao.getById(idCurso);
				if (c == null) {
					throw new NullPointerException(
							"El curso especificado no existe.");
				}
				Establecimiento e = c.getEstablecimiento();
				ContactoCargoDAO ccdao = new ContactoCargoDAO();
				ContactoCargo cc = ccdao.findByName(ContactoCargo.DIRECTOR);
				cdto.setCargo(cc.getCargoDTO());
				cdto.setContactoEmail(e.getEmail());
				cdto.setContactoNombre(e.getDirectorNombre());
				cdto.setContactoTelefono(e.getTelefono());

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
		return cdto;
	}

	/**
	 * @permiso editarDirector
	 */
	@Override
	public Boolean editarDirector(Integer idCurso, ContactoDTO director)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "editarDirector")) {

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

				if (director == null) {
					throw new NullPointerException(
							"No se han especificado los datos del director.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO();
				Curso c = cdao.getById(idCurso);
				if (c == null) {
					throw new NullPointerException(
							"El curso especificado no existe.");
				}
				Establecimiento e = c.getEstablecimiento();
				if (director.getContactoNombre() != null) {
					e.setDirectorNombre(director.getContactoNombre());
				}
				if (director.getContactoTelefono() != null) {
					e.setTelefono(director.getContactoTelefono());
				}
				if (director.getContactoEmail() != null) {
					e.setEmail(director.getContactoEmail());
				}
				EstablecimientoDAO edao = new EstablecimientoDAO();
				edao.update(e);

				ActividadDAO adao = new ActividadDAO();
				List<Actividad> as = c.getActividads();
				if (as != null && !as.isEmpty()) {
					for (Actividad actividad : as) {
						if (actividad.getContactoCargo() != null
								&& actividad.getContactoCargo().getNombre()
										.equals(ContactoCargo.DIRECTOR)) {
							if (director.getContactoNombre() != null) {
								actividad.setContactoNombre(director
										.getContactoNombre());
							}
							if (director.getContactoTelefono() != null) {
								actividad.setContactoTelefono(director
										.getContactoTelefono());
							}
							if (director.getContactoEmail() != null) {
								actividad.setContactoEmail(director
										.getContactoEmail());
							}
							adao.update(actividad);
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
		return result;
	}

	/**
	 * @permiso getEstadosAgendaFiltro
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosAgendaFiltro()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getEstadosAgendaFiltro")) {

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
				List<ActividadEstado> aes = aedao.findAll();
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

	public void sendEmailDirectorTIC() {

		URL url;
		try {
			url = this.getServletContext().getResource(
					"/Orientaciones_al_Director.pdf");

			EnviarCorreoDirTICThread t = new EnviarCorreoDirTICThread(
					url.getPath());
			t.start();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class EnviarCorreoDirTICThread extends Thread {
		private String path;

		/**
		 * @param path
		 */
		public EnviarCorreoDirTICThread(String path) {
			super();
			this.path = path;
		}

		public void run() {

			enviarArchivo("jflores@dreamer8.com",
					"Orientaciones al Director.pdf", path, "application/pdf");
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		};

	}

	public void sendEmailDirector(String dirMail, String contactoMail,
			String firmante, String fecha, String horaInicio, String horaTermino) {

		String[] destinatarios = new String[(dirMail.equals(contactoMail) || contactoMail == null) ? 2
				: 3];
		destinatarios[0] = dirMail.toLowerCase();
		destinatarios[1] = "simce@usm.cl";
		if (!dirMail.equals(contactoMail) && contactoMail != null
				&& !contactoMail.isEmpty()) {
			destinatarios[2] = contactoMail.toLowerCase();
		}
		EnviarCorreoDirThread t = new EnviarCorreoDirThread(destinatarios,
				fecha, horaInicio, horaTermino, firmante);
		t.start();

	}

	private class EnviarCorreoDirThread extends Thread {
		private String[] destinatarios;
		private String fecha;
		private String horaInicio;
		private String horaTermino;
		private String firmante;

		/**
		 * @param path
		 * @param destinatarios
		 * @param fecha
		 * @param horaInicio
		 * @param horaTermino
		 * @param firmante
		 */
		public EnviarCorreoDirThread(String[] destinatarios, String fecha,
				String horaInicio, String horaTermino, String firmante) {
			super();
			this.destinatarios = destinatarios;
			this.fecha = fecha;
			this.horaInicio = horaInicio;
			this.horaTermino = horaTermino;
			this.firmante = firmante;
		}

		public void run() {

			String mensaje = "Estimado Director(a) o Jefe de UTP:\n"
					+ "Junto con saludarlo(a), y según lo conversado telefónicamente le recuerdo que la visita previa se realizará\n"
					+ "el día "
					+ fecha
					+ " entre las "
					+ horaInicio
					+ " y las "
					+ horaTermino
					+ ".\n"
					+ "En este proceso participará un examinador y un supervisor.\n\n"
					+ "Algunas de las actividades de la visita previa son:\n"
					+ "* Verificar la información de la lista del curso.\n"
					+ "* Informarle el cronograma de aplicación.\n"
					+ "* Entregar los cuestionarios, si corresponde.\n"
					+ "* Visitar la sala donde se realizara la aplicación.\n\n"
					+ "Durante este proceso se requiere de su colaboración para brindar el apoyo necesario que permita agilizar\n"
					+ "los procedimientos establecidos para la aplicación de la prueba SIMCE.\n\n"
					+ "Para consultas comuníquese al teléfono:2-2353 1247\n"
					+ "Mail: yosimce@usm.cl\n\n" + "Se despide cordialmente\n"
					+ firmante;

			try {
				sendMail(mensaje, "Notificación de actividades Simce",
						destinatarios);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public String[] getDestinatarios() {
			return destinatarios;
		}

		public void setDestinatarios(String[] destinatarios) {
			this.destinatarios = destinatarios;
		}

		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getHoraInicio() {
			return horaInicio;
		}

		public void setHoraInicio(String horaInicio) {
			this.horaInicio = horaInicio;
		}

		public String getHoraTermino() {
			return horaTermino;
		}

		public void setHoraTermino(String horaTermino) {
			this.horaTermino = horaTermino;
		}

		public String getFirmante() {
			return firmante;
		}

		public void setFirmante(String firmante) {
			this.firmante = firmante;
		};

	}
}
