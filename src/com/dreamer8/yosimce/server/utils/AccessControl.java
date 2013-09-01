/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server.utils;

import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.SesionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

/**
 * 
 * @author jorge
 */
public class AccessControl {

	public static final String TOKEN_COOKIE_NAME = "yosimce";
	public static final String APLICACION_COOKIE_NAME = "a";
	public static final String NIVEL_COOKIE_NAME = "n";
	public static final String ACTIVIDAD_TIPO_COOKIE_NAME = "t";
	private HttpSession session = null;
	private HttpServletRequest request = null;
	private Integer idAplicacion = null;
	private Integer idNivel = null;
	private Integer idActividadTipo = null;
	private UsuarioTipo usuarioTipo = null;

	public AccessControl(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	private HttpSession getSession() {
		return this.session;
	}

	public boolean isLogged() throws NoLoggedException {
		Usuario usuario = (Usuario) this.session.getAttribute("usuario");

		if (usuario != null && usuario.getId() != null) {
			return true;
		}

		Cookie cookie = null;
		for (Cookie c : this.request.getCookies()) {
			if (c.getName().equals(TOKEN_COOKIE_NAME)) {
				cookie = c;
				break;
			}
		}
		if (cookie != null) {
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			SesionDAO sdao = new SesionDAO();
			List<Sesion> ss = sdao.findBySessionId(cookie.getValue());
			if (!ss.isEmpty()) {
				usuario = ss.get(0).getUsuario();
				this.session.setAttribute("usuario", usuario);
			}
			s.getTransaction().commit();
			if (usuario != null) {
				return true;
			}
		}

		throw new NoLoggedException();
	}

	public Integer getIdAplicacion() {
		if (idAplicacion == null) {
			Cookie cookie = null;
			for (Cookie c : this.request.getCookies()) {
				if (c.getName().equals(APLICACION_COOKIE_NAME)) {
					cookie = c;
					break;
				}
			}
			if (cookie != null) {
				try {
					idAplicacion = Integer.parseInt(cookie.getValue());
				} catch (NumberFormatException ex) {
					idAplicacion = null;
				}
			}
		}
		return idAplicacion;
	}

	public Integer getIdNivel() {
		if (idNivel == null) {
			Cookie cookie = null;
			for (Cookie c : this.request.getCookies()) {
				if (c.getName().equals(NIVEL_COOKIE_NAME)) {
					cookie = c;
					break;
				}
			}
			if (cookie != null) {
				try {
					idNivel = Integer.parseInt(cookie.getValue());
				} catch (NumberFormatException ex) {
					idNivel = null;
				}
			}
		}
		return idNivel;
	}

	public Integer getIdActividadTipo() {
		if (idActividadTipo == null) {
			Cookie cookie = null;
			for (Cookie c : this.request.getCookies()) {
				if (c.getName().equals(ACTIVIDAD_TIPO_COOKIE_NAME)) {
					cookie = c;
					break;
				}
			}
			if (cookie != null) {
				try {
					idActividadTipo = Integer.parseInt(cookie.getValue());
				} catch (NumberFormatException ex) {
					idActividadTipo = null;
				}
			}
		}
		return idActividadTipo;
	}

	public UsuarioTipo getUsuarioTipo() {
		if (usuarioTipo == null) {
			Usuario usuario = (Usuario) this.session.getAttribute("usuario");
			UsuarioTipoDAO utdao = new UsuarioTipoDAO();
			UsuarioTipo ut = utdao.findByIdAplicacionANDIdNivelANDIdUsuario(
					idAplicacion, idNivel, usuario.getId());
			if (ut != null) {
				usuarioTipo = ut;
			}
		}
		return usuarioTipo;
	}

	public boolean isAllowed(String className, String methodName)
			throws NoAllowedException {
		Usuario usuario = (Usuario) this.session.getAttribute("usuario");
		/*
		 * if (usuario != null && usuario.getPermiso(className, methodName) ==
		 * 1) { return true; }
		 */
		if (true) {
			return true;
		}
		throw new NoAllowedException(
				"El usuario no está autorizado para ejecutar " + methodName
						+ " en la clase " + className);
	}

	public boolean isAllowed(String methodName) throws NoAllowedException {
		if (true) {
			return true;
		}
		throw new NoAllowedException(
				"El usuario no está autorizado para ejecutar " + methodName);
	}

	public static boolean isLoggedAndAllowed(HttpServletRequest request,
			String className, String methodName) throws NoLoggedException,
			NoAllowedException {
		AccessControl ac = new AccessControl(request);
		try {
			if (ac.isLogged() && ac.isAllowed(className, methodName)) {
				return true;
			}
		} catch (NoLoggedException e) {
			Logger.getLogger(className).log(Level.SEVERE, null, e);
			throw e;
		} catch (NoAllowedException e) {
			Logger.getLogger(className).log(Level.SEVERE, null, e);
			throw e;
		}
		return false;
	}
}
