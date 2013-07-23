package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class PlanificacionServiceImpl extends CustomRemoteServiceServlet
		implements PlanificacionService {

	private String className = "PLanAndResultService";

	/**
	 * @permiso getEstablecimientos
	 */
	@Override
	public ArrayList<EstablecimientoDTO> getEstablecimientos(Integer offset,
			Integer lenght, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstablecimientoDTO> edtos = new ArrayList<EstablecimientoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstablecimientos")) {

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

				s.beginTransaction();

				Usuario u = getUsuarioActual();
				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				UsuarioTipo ut = utdao
						.findByIdAplicacionANDIdNivelANDIdUsuario(idAplicacion,
								idNivel, u.getId());

				if (ut == null) {
					throw new NullPointerException(
							"El usuario no está habilitado para obtener establecimientos para la aplicación y nivel especificados.");
				}

				EstablecimientoDAO edao = new EstablecimientoDAO();
				List<Establecimiento> es = edao
						.findEstablecimientosByActividadWithFilters(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), ut.getNombre(), offset, lenght,
								filtros);
				
				if (es != null && !es.isEmpty()) {
					for (Establecimiento establecimiento : es) {
						edtos.add(establecimiento.getEstablecimientoDTO());
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
		return edtos;
	}
}
