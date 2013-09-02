package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.LoginService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.NivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.SesionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Aplicacion;
import com.dreamer8.yosimce.server.hibernate.pojo.Nivel;
import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;
import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.ActividadTipoDTO;
import com.dreamer8.yosimce.shared.dto.AplicacionDTO;
import com.dreamer8.yosimce.shared.dto.NivelDTO;
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
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()) {

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

				udto = u.getUserDTO();

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
		return udto;
	}

	/**
	 * @permiso getAplicaciones
	 */
	@Override
	public ArrayList<AplicacionDTO> getAplicaciones()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<AplicacionDTO> adtos = new ArrayList<AplicacionDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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
		}
		return permisos;
	}

	@Override
	public String getUserToken(String username) throws DBException {

		String result = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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

			this.getThreadLocalRequest().getSession()
					.setAttribute("usuario", u);

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
		}
		return result;
	}

}
