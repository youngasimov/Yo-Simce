package com.dreamer8.yosimce.server;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.LoginService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.NivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.SesionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Aplicacion;
import com.dreamer8.yosimce.server.hibernate.pojo.Nivel;
import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;
import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.MantencionUtils;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class LoginServiceImpl extends CustomRemoteServiceServlet implements
		LoginService {

	private String className = "LoginService";

	/**
	 * @permiso getUser
	 */
	public UserDTO getUser(String token) {
		UserDTO udto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {

			if (token == null) {
				Cookie cookie = null;
				for (Cookie c : this.getThreadLocalRequest().getCookies()) {
					if (c.getName().equals(AccessControl.TOKEN_COOKIE_NAME)) {
						cookie = c;
						break;
					}
				}
				token = cookie.getValue();
			}
			if (token == null) {
				throw new NullPointerException(
						"No se ha especificado un usuario.");
			}


			s.beginTransaction();
			SesionDAO sdao = new SesionDAO();
			List<Sesion> ss = sdao.findBySessionId(token);
			if (ss == null || ss.isEmpty()) {
				throw new NullPointerException(
						"No se ha encontrado el usuario especificado");
			}

			Usuario u = ss.get(0).getUsuario();

			if (u == null) {
				throw new NullPointerException(
						"No se ha encontrado el usuario especificado");
			}

			this.getThreadLocalRequest().getSession()
					.setAttribute("usuario", u);

			udto = u.getUserDTO();

			s.getTransaction().commit();
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
		return udto;
	}

	/**
	 * @permiso getAplicaciones
	 */
	@Override
	public ArrayList<AplicacionDTO> getAplicaciones()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<AplicacionDTO> adtos = new ArrayList<AplicacionDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()) {

				s.beginTransaction();

				Usuario u = getUsuarioActual();
				AplicacionDAO adao = new AplicacionDAO();
				List<Aplicacion> as = adao.findByIdUsuario(u.getId());
				if (as != null && !as.isEmpty()) {
					for (Aplicacion a : as) {
						adtos.add(a.getAplicacionDTO());
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
		return adtos;
	}

	/**
	 * @permiso getNiveles
	 */
	@Override
	public ArrayList<NivelDTO> getNiveles() throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<NivelDTO> ndtos = new ArrayList<NivelDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()) {
				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				s.beginTransaction();
				Usuario u = getUsuarioActual();
				NivelDAO ndao = new NivelDAO();
				List<Nivel> ns = ndao.findByIdAplicacionANDIdUsuario(
						idAplicacion, u.getId());
				if (ns != null && !ns.isEmpty()) {
					for (Nivel nivel : ns) {
						ndtos.add(nivel.getNivelDTO());
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
		return ndtos;
	}

	/**
	 * @permiso getActividadTipos
	 */
	@Override
	public ArrayList<ActividadTipoDTO> getActividadTipos()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<ActividadTipoDTO> atdtos = new ArrayList<ActividadTipoDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getActividadTipos")) {

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

				Usuario u = getUsuarioActual();
				ActividadTipoDAO atdao = new ActividadTipoDAO();
				List<ActividadTipo> ats = atdao
						.findByIdAplicacionANDIdNivelANDIdUsuario(idAplicacion,
								idNivel, u.getId());
				if (ats != null && !ats.isEmpty()) {
					for (ActividadTipo actividadTipo : ats) {
						atdtos.add(actividadTipo.getActividadTipoDTO());
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
		return atdtos;
	}

	/**
	 * @permiso getUsuarioPermisos
	 */
	@Override
	public HashMap<String, ArrayList<String>> getUsuarioPermisos()
			throws NoAllowedException, NoLoggedException, DBException {

		HashMap<String, ArrayList<String>> permisos = new HashMap<String, ArrayList<String>>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()) {

				Integer idAplicacion = ac.getIdAplicacion();
				Integer idNivel = ac.getIdNivel();

				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}
				s.beginTransaction();
				PermisoDAO pdao = new PermisoDAO();
				List<Permiso> ps = pdao
						.findByIdAplicacionANDIdNivelANDIdUsuario(idAplicacion,
								idNivel, getUsuarioActual().getId());
				ArrayList<String> metodos;
				if (ps != null && !ps.isEmpty()) {
					List<String> clases = pdao.getClases();
					for (String clase : clases) {
						permisos.put(clase, new ArrayList<String>());
					}
					for (Permiso p : ps) {
						metodos = permisos.get(p.getClase());
						metodos.add(p.getMetodo());
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
		return permisos;
	}

	@Override
	public String getUserToken(String username) throws DBException {

		String result = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			s.beginTransaction();
			UsuarioDAO udao = new UsuarioDAO();
			Usuario u = null;
			SesionDAO sdao = new SesionDAO();
			List<Sesion> ss = sdao.findBySessionId(username);

			Sesion sesion = null;
			if (ss != null && !ss.isEmpty()) {
				sesion = ss.get(0);
			}
			if (sesion == null) {
				u = udao.findbyUsername(StringUtils.formatRut(username));
				if (u == null) {
					throw new NoLoggedException();
				}
				sesion = new Sesion();
				sesion.setSessionId(username);
				sesion.setSessionValue(username);
				sesion.setUsuario(u);
				sdao.saveOrUpdate(sesion);
				result = username;
			} else {
				result = sesion.getSessionId();
				u = sesion.getUsuario();
			}

			s.getTransaction().commit();

			this.getThreadLocalRequest().getSession()
					.setAttribute("usuario", u);
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

	@Override
	public Boolean logout() {
		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {

			SesionDAO sdao = new SesionDAO();
			for (Cookie c : this.getThreadLocalRequest().getCookies()) {
				if (c.getName().equals(AccessControl.TOKEN_COOKIE_NAME)) {
					s.beginTransaction().begin();
					if (c.getValue() != null) {
						sdao.deleteById(c.getValue());
					}
					s.getTransaction().commit();
					c.setMaxAge(0);
					this.getThreadLocalResponse().addCookie(c);
					break;
				}
			}
			HttpSession session = this.getThreadLocalRequest().getSession();
			session.invalidate();
		} catch (Exception e) {
			result = false;
			HibernateUtil.rollbackActiveOnly(s);
			System.err.println(e);
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return result;
	}

	@Override
	public Integer keepAlive() throws NoLoggedException {

		AccessControl ac = getAccessControl();
		Integer result = SESION_INACTIVA;
		if (ac.isLogged()) {
			result = (MantencionUtils.isMantencionAgendada()) ? PRONTA_ACTUALIZACION
					: SESION_ACTIVA;
		}
		return result;
	}

	/**
	 * @permiso getUsuarioTipos
	 */
	@Override
	public ArrayList<TipoUsuarioDTO> getUsuarioTipos()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<TipoUsuarioDTO> tudtos = new ArrayList<TipoUsuarioDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getUsuarioTipos")) {

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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				List<UsuarioTipo> uts = utdao
						.findByIdAplicacionANDIdNivelANDIdUsuario(idAplicacion,
								idNivel, u.getId());
				if (uts != null && !uts.isEmpty()) {
					for (UsuarioTipo usuarioTipo : uts) {
						tudtos.add(usuarioTipo.getTipoUsuarioDTO());
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
	 * @permiso getActualizacionDate
	 */
	@Override
	public String getActualizacionDate() {
		return MantencionUtils.getFechaMantencionString();
	}

	/**
	 * @permiso setActualizacionDate
	 */
	@Override
	public Boolean setActualizacionDate(String date) {
		MantencionUtils.setFechaMantencion(StringUtils.getDate(date));
		return true;
	}

	@Override
	public UserDTO getYoSimceUser() throws NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getTrackingUser(String user, String password)
			throws NoLoggedException, DBException, NullPointerException,
			ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

}
