/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server.utils;

import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.SesionDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
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

    public static final String TOKEN_COOKIE_NAME = "token";
    private HttpSession session = null;
    private HttpServletRequest request = null;

    public AccessControl(HttpServletRequest request) {
	this.request = request;
	this.session = request.getSession();
    }

    private HttpSession getSession() {
	return this.session;
    }

    public boolean isLogged() throws NoLoggedException {
	Usuario usuario = (Usuario) this.session.getAttribute("usuario");

	if (usuario != null) {
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
	    List<Sesion> ss = sdao.findBySessionValue(cookie.getValue());
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
    	return null;
    }
    
    public Integer getIdNivel() {
    	return null;
    }
    
    public Integer getIdActividadTipo() {
    	return null;
    }

    public boolean isAllowed(String className, String methodName) throws NoAllowedException {
	Usuario usuario = (Usuario) this.session.getAttribute("usuario");
	/*
	 * if (usuario != null && usuario.getPermiso(className, methodName) ==
	 * 1) { return true; }
	 *
	 */
	if (true) {
	    return true;
	}
	throw new NoAllowedException("El usuario no está autorizado para ejecutar "
	    + methodName + " en la clase " + className);
    }

    public boolean isAllowed(String methodName) throws NoAllowedException {
	if (true) {
	    return true;
	}
	throw new NoAllowedException("El usuario no está autorizado para ejecutar "
	    + methodName);
    }

    public static boolean isLoggedAndAllowed(HttpServletRequest request, String className, String methodName) throws NoLoggedException, NoAllowedException {
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
