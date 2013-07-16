package com.dreamer8.yosimce.server;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.LoginService;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.SesionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Sesion;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;

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
			if (ac.isLogged() && ac.isAllowed(className, "getUser")) {

				if (token == null) {
					throw new NullPointerException(
							"No se ha especificado un usuario.");
				}

				s.beginTransaction();
				SesionDAO sdao = new SesionDAO();
				List<Sesion> ss = sdao.findBySessionValue(token);
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

}
