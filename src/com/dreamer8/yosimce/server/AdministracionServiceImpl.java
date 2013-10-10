package com.dreamer8.yosimce.server;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.administracion.AdministracionService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoXPermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CentroRegionalDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ZonaDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipoXPermiso;
import com.dreamer8.yosimce.server.hibernate.pojo.CentroRegional;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Zona;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class AdministracionServiceImpl extends CustomRemoteServiceServlet
		implements AdministracionService {

	private String className = "AdministracionService";

	/**
	 * @permiso getUsuarios
	 */
	@Override
	public ArrayList<UserDTO> getUsuarios(String filtro, Integer offset,
			Integer length) throws NoAllowedException, NoLoggedException,
			DBException {

		ArrayList<UserDTO> udtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getUsuarios")) {

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

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioDAO udao = new UsuarioDAO();
				if (usuarioTipo.getRol().equals(UsuarioTipo.ADMINISTRADOR)) {
					udtos = (ArrayList<UserDTO>) udao
							.findByIdAplicacionANDIdNivelANDFiltro(
									idAplicacion, idNivel, offset, length,
									filtro);
				} else {
					udtos = (ArrayList<UserDTO>) udao
							.findByIdAplicacionANDIdNivelANDIdTipoUsuarioSuperiorANDFiltro(
									idAplicacion, idNivel, usuarioTipo.getId(),
									offset, length, filtro);
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
		return udtos;
	}

	/**
	 * @permiso getTiposUsuario
	 */
	@Override
	public ArrayList<TipoUsuarioDTO> getTiposUsuario()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<TipoUsuarioDTO> tudtos = new ArrayList<TipoUsuarioDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getTiposUsuario")) {

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

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				List<UsuarioTipo> uts = null;

				if (usuarioTipo.getRol().equals(UsuarioTipo.ADMINISTRADOR)) {
					uts = utdao.findByIdAplicacion(idAplicacion);
				} else {
					uts = utdao.findByIdAplicacionANDIdTipoUsuarioSuperior(
							idAplicacion, usuarioTipo.getId());
				}

				if (uts != null && !uts.isEmpty()) {
					for (UsuarioTipo ut : uts) {
						tudtos.add(ut.getTipoUsuarioDTO());
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
		return tudtos;
	}

	/**
	 * @permiso getEmplazamientos
	 */
	@Override
	public ArrayList<EmplazamientoDTO> getEmplazamientos(
			String tipoEmplazamiento) throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<EmplazamientoDTO> edtos = new ArrayList<EmplazamientoDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEmplazamientos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				if (tipoEmplazamiento == null || tipoEmplazamiento.equals("")) {
					throw new NullPointerException(
							"No se ha especificado un tipo de emplazamiento");
				}

				s.beginTransaction();

				if (tipoEmplazamiento
						.equals(EmplazamientoDTO.CENTRO_OPERACIONAL)) {
					CoDAO codao = new CoDAO();
					List<Co> cos = codao.findByIdAplicacion(idAplicacion);
					if (cos != null && !cos.isEmpty()) {
						for (Co co : cos) {
							edtos.add(co.getEmplazamientoDTO());
						}
					}
				} else if (tipoEmplazamiento.equals(EmplazamientoDTO.ZONA)) {
					ZonaDAO zdao = new ZonaDAO();
					List<Zona> zs = zdao.findByIdAplicacion(idAplicacion);
					if (zs != null && !zs.isEmpty()) {
						for (Zona z : zs) {
							edtos.add(z.getEmplazamientoDTO());
						}
					}
				} else if (tipoEmplazamiento
						.equals(EmplazamientoDTO.CENTRO_REGIONAL)) {
					CentroRegionalDAO crdao = new CentroRegionalDAO();
					List<CentroRegional> crs = crdao
							.findByIdAplicacion(idAplicacion);
					if (crs != null && !crs.isEmpty()) {
						for (CentroRegional cr : crs) {
							edtos.add(cr.getEmplazamientoDTO());
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
		return edtos;
	}

	/**
	 * @permiso setPerfilUsuario
	 */
	@Override
	public Boolean setPerfilUsuario(Integer idUsuario, Integer idTipoUsuario,
			EmplazamientoDTO emplazamiento) throws ConsistencyException,
			NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso reiniciarPassword
	 */
	@Override
	public Boolean reiniciarPassword(Integer idUsuario)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getPermisos
	 */
	@Override
	public ArrayList<PermisoDTO> getPermisos() throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<PermisoDTO> pdtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getPermisos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				s.beginTransaction();

				PermisoDAO pdao = new PermisoDAO();
				pdtos = (ArrayList<PermisoDTO>) pdao
						.findByIdAplicacion(idAplicacion);

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
		return pdtos;
	}

	/**
	 * @permiso setPermisos
	 */
	@Override
	public Boolean setPermisos(ArrayList<PermisoDTO> permisos)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean resutl = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "setPermisos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				if (permisos == null || permisos.isEmpty()) {
					throw new NullPointerException(
							"No se especificó ningún cambio en los permisos");
				}

				s.beginTransaction();

				AplicacionXUsuarioTipoDAO axutdao = new AplicacionXUsuarioTipoDAO();
				AplicacionXUsuarioTipo axut = null;

				AplicacionXUsuarioTipoXPermisoDAO axutxpdao = new AplicacionXUsuarioTipoXPermisoDAO();
				AplicacionXUsuarioTipoXPermiso axutxp = null;

				PermisoDAO pdao = new PermisoDAO();
				Permiso p = null;
				Integer idUsuarioTipo = null;
				for (PermisoDTO pdto : permisos) {
					if (pdto.getIdPermiso() != null) {
						p = pdao.getById(pdto.getIdPermiso());
						for (AplicacionXUsuarioTipoXPermiso aplicacionXUsuarioTipoXPermiso : axutxpdao
								.findByIdAplicacionANDIdPermiso(idAplicacion,
										p.getId())) {
							idUsuarioTipo = aplicacionXUsuarioTipoXPermiso
									.getAplicacionXUsuarioTipo()
									.getUsuarioTipo().getId();

							if (pdto.getIdTiposUsuariosPermitidos().contains(
									idUsuarioTipo)) {

								pdto.getIdTiposUsuariosPermitidos().remove(
										idUsuarioTipo);

								if (!aplicacionXUsuarioTipoXPermiso.getAcceso()) {
									aplicacionXUsuarioTipoXPermiso
											.setAcceso(true);
									axutxpdao
											.update(aplicacionXUsuarioTipoXPermiso);
								}
							} else if (aplicacionXUsuarioTipoXPermiso
									.getAcceso()) {
								aplicacionXUsuarioTipoXPermiso.setAcceso(false);
								axutxpdao
										.update(aplicacionXUsuarioTipoXPermiso);
							}
						}
						if (!pdto.getIdTiposUsuariosPermitidos().isEmpty()) {
							for (Integer idUT : pdto
									.getIdTiposUsuariosPermitidos()) {
								axut = axutdao
										.findByIdAplicacionANDIdUsuarioTipo(
												idAplicacion, idUT);
								if (axut != null && axut.getId() != null) {
									axutxp = new AplicacionXUsuarioTipoXPermiso();
									axutxp.setPermiso(p);
									axutxp.setAcceso(true);
									axutxp.setAplicacionXUsuarioTipo(axut);
									axutxpdao.save(axutxp);
								}
							}
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
		return resutl;
	}

	@Override
	public DocumentoDTO getReporte(Integer tipo, Integer region,
			Integer comuna, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso enviarCorreosSimceTic
	 */
	@Override
	public Boolean enviarCorreosSimceTic() throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException {

		Boolean b = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "enviarCorreosSimceTic")) {

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
				List<Actividad> as = adao
						.findAgendadasParaMañanaByIdAplicacionANDIdNivelANDIdActividadTipo(
								2, 10, 1);

				URL url;
				url = this.getServletContext().getResource(
						"/Orientaciones_al_Director.pdf");
				String path = url.getFile().replaceAll("/localhost",
						"/usr/share/tomcat6/webapps");

				// as = as.subList(0, 3);
				int totalHebras = 4;
				List<Integer> ids = new ArrayList<Integer>();
				if (as != null && !as.isEmpty()) {
					for (Actividad a : as) {
						ids.add(a.getId());
					}
					if (ids.size() >= totalHebras) {
						int inc = (ids.size() / totalHebras);
						EnviarCorreoDirTICThread[] hebras = new EnviarCorreoDirTICThread[totalHebras];
						for (int i = 0; i < totalHebras; i++) {
							hebras[i] = new EnviarCorreoDirTICThread(path,
									ids.subList(inc * i, inc * (i + 1)));
							hebras[i].start();
						}
						if (ids.size() > (inc * totalHebras)) {
							System.err.println("Aún quedaban correos");
							EnviarCorreoDirTICThread hebra = new EnviarCorreoDirTICThread(
									path, ids.subList(inc * totalHebras,
											ids.size()));
							hebra.start();
						}
					} else {
						System.err.println("Son menos de " + totalHebras
								+ " correos");
						EnviarCorreoDirTICThread hebra = new EnviarCorreoDirTICThread(
								path, ids);
						hebra.start();
					}
				}
				s.getTransaction().commit();

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			HibernateUtil.rollback(s);
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
		return b;
	}

	private class EnviarCorreoDirTICThread extends Thread {
		private String path;
		private String[] destinatarios;
		private String fechaVisitaPrevia;
		private String horaInicioVisitaPrevia;
		private String fechaAplicacion;
		private String horaInicioAplicacion;
		private String horaTerminoAplicacion;
		private String examinador;
		private String supervisor;
		private Integer idAplicacion = 2;
		private Integer idNivel = 10;
		private List<String> fallos = new ArrayList<String>();
		private List<String> exitos = new ArrayList<String>();

		private List<Integer> actividades;

		/**
		 * @param path
		 * @param actividades
		 */
		public EnviarCorreoDirTICThread(String path, List<Integer> actividades) {
			super();
			this.path = path;
			this.actividades = actividades;
		}

		public void run() {

			String mensaje;
			Calendar calendar = Calendar.getInstance();
			Actividad aplicacion = null;
			int year;
			int month;
			int day;
			int hour;
			int min;
			String dirMail = null;
			String contMail = null;
			ActividadDAO adao = new ActividadDAO();
			Establecimiento e = null;
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Integer region;
			Usuario u = null;
			List<Usuario> us = null;
			UsuarioDAO udao = new UsuarioDAO();
			Boolean mandarMail;
			String motivo;
			Actividad actividad;
			List<String> correos;
			Session s = HibernateUtil.getSessionFactory().openSession();
			ManagedSessionContext.bind(s);

			try {
				if (actividades != null && !actividades.isEmpty()) {
					s.beginTransaction();
					for (Integer idActividad : actividades) {
						actividad = adao.getById(idActividad);
						motivo = null;
						mandarMail = true;
						examinador = null;
						supervisor = null;
						calendar.setTime(actividad.getFechaInicio());
						year = calendar.get(Calendar.YEAR);
						month = calendar.get(Calendar.MONTH);
						day = calendar.get(Calendar.DAY_OF_MONTH);
						hour = calendar.get(Calendar.HOUR_OF_DAY);
						min = calendar.get(Calendar.MINUTE);

						fechaVisitaPrevia = day + " de "
								+ StringUtils.getMes(month);
						horaInicioVisitaPrevia = StringUtils
								.forceTwoDigits(hour)
								+ ":"
								+ StringUtils.forceTwoDigits(min);

						aplicacion = adao
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
										idAplicacion, idNivel, 2, actividad
												.getCurso().getId());

						if (aplicacion != null
								&& aplicacion.getFechaInicio() != null) {
							calendar.setTime(aplicacion.getFechaInicio());
							year = calendar.get(Calendar.YEAR);
							month = calendar.get(Calendar.MONTH);
							day = calendar.get(Calendar.DAY_OF_MONTH);
							hour = calendar.get(Calendar.HOUR_OF_DAY);
							min = calendar.get(Calendar.MINUTE);

							fechaAplicacion = day + " de "
									+ StringUtils.getMes(month);
							horaInicioAplicacion = StringUtils
									.forceTwoDigits(hour)
									+ ":"
									+ StringUtils.forceTwoDigits(min);

							horaTerminoAplicacion = StringUtils
									.forceTwoDigits(hour + 2)
									+ ":"
									+ StringUtils.forceTwoDigits(min);
						} else {
							mandarMail = false;
							motivo = "no tiene aplicación o no está agendada";
						}
						us = udao
								.findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
										idAplicacion, idNivel, 1, actividad
												.getCurso().getId());
						if (us != null && !us.isEmpty()) {
							u = us.get(0);
							examinador = StringUtils.nombreInicialSegundo(u
									.getNombres())
									+ " "
									+ u.getApellidoPaterno();
						} else {
							mandarMail = false;
							motivo = "no tiene examinador";
						}
						u = udao.findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								2, 10, 1, actividad.getCurso().getId());
						if (u != null) {
							supervisor = StringUtils.nombreInicialSegundo(u
									.getNombres())
									+ " "
									+ u.getApellidoPaterno();
						} else {
							mandarMail = false;
							motivo = "no tiene supervisor";
						}

						dirMail = null;
						contMail = actividad.getContactoEmail();
						e = edao.findByIdActividad(actividad.getId());
						region = e.getComuna().getProvincia().getRegion()
								.getId();
						if (actividad.getContactoCargo() != null
								&& actividad.getContactoCargo().getNombre()
										.equals(ContactoCargo.DIRECTOR)) {
							dirMail = contMail;
							contMail = null;
						} else {
							dirMail = e.getEmail();
						}

						// dirMail =
						// "jflores@dreamer8.com; también puede ser 1313 y bestjef@gmail.com";
						// contMail =
						// "el contacto es camilo cvera@dreamer8.com y camilo.vera@live.com";
						correos = null;
						if (dirMail != null || contMail != null) {
							if (dirMail != null) {
								correos = StringUtils.extractMails(dirMail);
								if (contMail != null
										&& !dirMail.equals(contMail)) {
									List<String> tmp = StringUtils
											.extractMails(contMail);
									if (tmp != null && !tmp.isEmpty()) {
										correos.addAll(tmp);
									}
								}
							} else {
								correos = StringUtils.extractMails(contMail);
							}
							if (correos != null && !correos.isEmpty()) {

								destinatarios = new String[correos.size() + 2];
								for (int i = 0; i < correos.size(); i++) {
									destinatarios[i] = correos.get(i)
											.toLowerCase();
								}
								destinatarios[correos.size()] = "simce@usm.cl";
								destinatarios[correos.size() + 1] = "encargado.tic"
										+ region + "@usm.cl";
							} else {
								mandarMail = false;
								motivo = "no tiene correo del director ni de contacto.";
							}

						} else {
							mandarMail = false;
							motivo = "no tiene correo del director.";
						}

						// destinatarios = new String[2];
						// destinatarios[0] = "jflores@dreamer8.com";
						// destinatarios[1] = "cvera@dreamer8.com";

						if (mandarMail) {
							mensaje = "Estimado Director(a) o Jefe de UTP:\n\n"

									+ "Junto con saludarlo(a), y según lo conversado telefónicamente le recuerdo que la visita previa se realizará\n"
									+ "el día "
									+ fechaVisitaPrevia
									+ " a las "
									+ horaInicioVisitaPrevia
									+ ".\n"
									+ "En este proceso participará "
									+ examinador
									+ ", Examinador de SIMCE TIC, el Encargado técnico\n"
									+ " y quien le escribe "
									+ supervisor
									+ ", Supervisor de la aplicación.\n\n"

									+ "La visita previa tiene como objetivo:\n"
									+ "* Seleccionar la sala o laboratorio de computación del establecimiento que se utilizará para la aplicación\n"
									+ "del SIMCE TIC.\n"
									+ "* Informar a los estudiantes de segundo medio seleccionados para rendir la prueba.\n"
									+ "* Verificar con los libros de clases de segundo año medio el registro de los estudiantes seleccionados\n"
									+ "para rendir la prueba.\n"
									+ "* Realizar reemplazos de estudiantes en caso de ser necesario.\n"
									+ "* Realizar una charla informativa a los estudiantes, el Director y el Jefe de UTP.\n"
									+ "* Entregar el Cuestionario para Padres y/o Apoderados.\n"
									+ "* Identificar vías de escape ante eventuales emergencias.\n\n"

									+ "Durante este proceso se requiere de su colaboración para brindar el apoyo necesario que permita agilizar\n"
									+ "los procedimientos establecidos para la aplicación de la prueba SIMCE TIC.\n\n"

									+ "Antes de la visita previa le solicitamos revisar el documento adjunto “Orientaciones al Director”, el cual\n"
									+ "sintetiza los procesos y actividades a realizar.\n\n"

									+ "Finalmente, le informamos que la aplicación de SIMCE TIC se realizará el día\n"
									+ fechaAplicacion
									+ " desde las "
									+ horaInicioAplicacion
									+ " hasta las "
									+ horaTerminoAplicacion
									+ " aproximadamente.\n\n"

									+ "Para consultas comuníquese al teléfono: 2-2353 1247\n"
									+ "Mail: simce@usm.cl\n\n"
									+ "Se despide cordialmente\n"
									+ supervisor
									+ "\nSupervisor";
							sendMailWithAttachment(mensaje,
									"Notificación de actividades SIMCE TIC",
									destinatarios,
									"Orientaciones al Director.pdf", path,
									"application/pdf");

							String receptores = "";
							for (String email : destinatarios) {
								receptores += email + "; ";
							}
							exitos.add("Actividad id:" + actividad.getId()
									+ " enviado a " + receptores + "\n");
						} else {
							fallos.add("Actividad id:" + actividad.getId()
									+ " " + motivo + "\n");
						}
					}
					s.getTransaction().commit();
				}

				String[] dest = { "jflores@dreamer8.com", "cvera@dreamer8.com" };

				if (fallos != null && !fallos.isEmpty()) {
					mensaje = "";
					for (String fallo : fallos) {
						mensaje += fallo;
					}
					sendMail(mensaje, "Correos que fallaron SIMCE TIC", dest);
				}
				if (exitos != null && !exitos.isEmpty()) {
					mensaje = "";
					for (String exito : exitos) {
						mensaje += exito;
					}
					sendMail(mensaje, "Correos que se mandaron SIMCE TIC", dest);
				}

			} catch (UnsupportedEncodingException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} catch (MessagingException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} catch (HibernateException ex) {
				System.err.println(ex);
				HibernateUtil.rollback(s);
				throw new DBException();
			} finally {
				ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
				if (s.isOpen()) {
					s.clear();
					s.close();
				}
			}

		}
	}

	/**
	 * @permiso enviarCorreosSimce
	 */
	@Override
	public Boolean enviarCorreosSimce() throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}
}
