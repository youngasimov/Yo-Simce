package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;



import com.dreamer8.yosimce.client.activity.planandresult.PlanAndResultService;
import com.dreamer8.yosimce.server.hibernate.dao.AbstractHibernateDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class PlanAndResultServiceImpl extends CustomRemoteServiceServlet
		implements PlanAndResultService {

	private String className = "PLanAndResultService";

	/**
	 * @permiso getEstablecimientos
	 */
	@Override
	public ArrayList<EstablecimientoDTO> getEstablecimientos(
			Integer idAplicacion, Integer idNivel, Integer idActividadTipo,
			Integer offset, Integer lenght, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {
		

		ArrayList<EstablecimientoDTO> edt0s = new ArrayList<EstablecimientoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstablecimientos")) {

				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}
				if (idNivel == null) {
					throw new NullPointerException(
							"No se ha especificado un nivel.");
				}
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				s.beginTransaction();
				EstablecimientoDAO edao = new EstablecimientoDAO();
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
		return edt0s;
	}


	
}
