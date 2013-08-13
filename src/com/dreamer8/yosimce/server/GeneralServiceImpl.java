package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.general.GeneralService;
import com.dreamer8.yosimce.server.hibernate.dao.ComunaDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.RegionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Comuna;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Region;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialCambioItemDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class GeneralServiceImpl extends CustomRemoteServiceServlet implements
		GeneralService {

	private String className = "GeneralService";

	/**
	 * @permiso getRegiones
	 */
	@Override
	public ArrayList<SectorDTO> getRegiones() {

		ArrayList<SectorDTO> sdtos = new ArrayList<SectorDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getRegiones")) {

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

				RegionDAO rdao = new RegionDAO();
				List<Region> rs = rdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipo(
								idAplicacion, idNivel, idActividadTipo);

				if (rs != null && !rs.isEmpty()) {
					for (Region r : rs) {
						sdtos.add(r.getSectorDTO());
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
		return sdtos;
	}

	/**
	 * @permiso getComunas
	 */
	@Override
	public ArrayList<SectorDTO> getComunas(Integer sectorId) {

		ArrayList<SectorDTO> sdtos = new ArrayList<SectorDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getComunas")) {

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

				ComunaDAO cdao = new ComunaDAO();
				List<Comuna> cs = cdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipo(
								idAplicacion, idNivel, idActividadTipo);
				if (cs != null && !cs.isEmpty()) {
					for (Comuna c : cs) {
						sdtos.add(c.getSectorDTO());
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
		return sdtos;
	}

	/**
	 * @permiso getEstablecimientos
	 */
	@Override
	public ArrayList<EstablecimientoDTO> getEstablecimientos(String rbdSeach)
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

				if (rbdSeach == null || rbdSeach.equals("")) {
					throw new NullPointerException(
							"No se ha especificado un RBD.");
				}

				s.beginTransaction();

				Integer idUsuarioTipo = ac.getIdUsuarioTipo();
				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				UsuarioTipo ut = utdao.getById(idUsuarioTipo);
				if (ut == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				Usuario u = getUsuarioActual();

				EstablecimientoDAO edao = new EstablecimientoDAO();
				List<Establecimiento> es = edao
						.findEstablecimientosByActividadANDRbd(idAplicacion,
								idNivel, idActividadTipo, u.getId(),
								ut.getNombre(), rbdSeach);

				if (es != null && !es.isEmpty()) {
					for (Establecimiento e : es) {
						edtos.add(e.getEstablecimientoDTO());
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

	/**
	 * @permiso getEstablecimiento
	 */
	@Override
	public EstablecimientoDTO getEstablecimiento(Integer idEstablecimiento)
			throws NoAllowedException, NoLoggedException, DBException {

		EstablecimientoDTO edto = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstablecimiento")) {

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

				if (idEstablecimiento == null) {
					throw new NullPointerException(
							"No se ha especificado el establecimiento.");
				}

				s.beginTransaction();

				EstablecimientoDAO edao = new EstablecimientoDAO();
				Establecimiento e = edao.getById(idEstablecimiento);
				if (e == null) {
					throw new NullPointerException(
							"El establecimiento especificado no existe.");
				}

				edto = e.getEstablecimientoDTO();

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
		return edto;
	}

	/**
	 * @permiso getCambios
	 */
	@Override
	public ArrayList<HistorialCambioItemDTO> getCambios(
			Integer idEstablecimiento) throws NoAllowedException,
			NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getCursos
	 */
	@Override
	public ArrayList<CursoDTO> getCursos(String rbdSeach)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
