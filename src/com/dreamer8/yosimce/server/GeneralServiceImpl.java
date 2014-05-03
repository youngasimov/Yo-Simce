package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.general.GeneralService;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ComunaDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.RegionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ZonaDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.Comuna;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.Region;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Zona;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;
import com.dreamer8.yosimce.shared.dto.SectorDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
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
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				RegionDAO rdao = new RegionDAO(s);
				List<Region> rs = rdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDUsuarioTipo(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol());
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return sdtos;
	}

	/**
	 * @permiso getComunas
	 */
	@Override
	public ArrayList<SectorDTO> getComunas(SectorDTO sector) {

		ArrayList<SectorDTO> sdtos = new ArrayList<SectorDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ComunaDAO cdao = new ComunaDAO(s);
				List<Comuna> cs = null;

				if (sector == null || sector.getIdSector() == null) {
					cs = cdao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDusuarioTipo(
									idAplicacion, idNivel, idActividadTipo,
									u.getId(), usuarioTipo.getRol());
				} else if (sector.getTipoSector().equals(
						SectorDTO.TIPO_PROVINCIA)) {
					cs = cdao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdProvinciaANDIdUsuarioANDusuarioTipo(
									idAplicacion, idNivel, idActividadTipo,
									sector.getIdSector(), u.getId(),
									usuarioTipo.getRol());
				} else {
					cs = cdao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdRegionANDIdUsuarioANDusuarioTipo(
									idAplicacion, idNivel, idActividadTipo,
									sector.getIdSector(), u.getId(),
									usuarioTipo.getRol());
				}
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
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return sdtos;
	}

	/**
	 * @permiso getCursos
	 */
	@Override
	public ArrayList<CursoDTO> getCursos(String rbdSeach)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<CursoDTO> cdtos = new ArrayList<CursoDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getCursos")) {

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

				// if (rbdSeach == null || !StringUtils.isInt(rbdSeach)) {
				if (rbdSeach == null || rbdSeach.isEmpty()) {
					throw new NullPointerException(
							"No se ha especificado un campo válido de búsqueda.");
				}

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO(s);
				List<Curso> cs = cdao.findByByActividadANDRbd(idAplicacion,
						idNivel, idActividadTipo, u.getId(),
						usuarioTipo.getRol(), rbdSeach);
				if (cs != null && !cs.isEmpty()) {
					for (Curso curso : cs) {
						cdtos.add(curso.getCursoDTO());
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
	 * @permiso getDetalleCurso
	 */
	@Override
	public DetalleCursoDTO getDetalleCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {

		DetalleCursoDTO dcdto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getDetalleCurso")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO(s);
				dcdto = cdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (dcdto == null || dcdto.getId() == null) {
					throw new NullPointerException(
							"No se ha encontrado información para el curso especificado en la actividad indicada.");
				}
				UsuarioDAO udao = new UsuarioDAO(s);
				List<Usuario> examinadores = udao
						.findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				ArrayList<UserDTO> exs = new ArrayList<UserDTO>();
				if (examinadores != null && !examinadores.isEmpty()) {
					for (Usuario ex : examinadores) {
						exs.add(ex.getUserDTO());
					}
				}
				dcdto.setExaminadores(exs);
				Usuario supervisor = udao
						.findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (supervisor != null) {
					dcdto.setSupervisor(supervisor.getUserDTO());
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
		return dcdto;
	}

	/**
	 * @permiso getCurso
	 */
	@Override
	public CursoDTO getCurso(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {

		CursoDTO cdto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getCurso")) {

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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO(s);
				Curso c = cdao.getById(idCurso);
				if (c == null || c.getId() == null) {
					throw new NullPointerException(
							"El curso especificado no existe.");
				}
				cdto = c.getCursoDTO();
				if (cdto.getRbd() != null && !cdto.getRbd().isEmpty()) {
					CoDAO codao = new CoDAO(s);
					Co co = codao
							.findByIdAplicacionANDIdNivelANDIdEstablecimiento(
									idAplicacion, idNivel,
									Integer.valueOf(cdto.getRbd()));
					if (co != null) {
						cdto.setCoAsociado(co.getNombre());
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
		return cdto;
	}

	/**
	 * 
	 * @permiso getCentrosOperacion
	 */
	@Override
	public ArrayList<CentroOperacionDTO> getCentrosOperacion()
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException {

		ArrayList<CentroOperacionDTO> codtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getCentrosOperacion")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CoDAO codao = new CoDAO(s);
				codtos = (ArrayList<CentroOperacionDTO>) codao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDUsuarioTipo(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getRol());

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			ex.printStackTrace();
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
		return codtos;
	}

	/**
	 * 
	 * @permiso getZonas
	 */
	@Override
	public ArrayList<SectorDTO> getZonas(SectorDTO parent)
			throws NoAllowedException, NoLoggedException, DBException,
			ConsistencyException, NullPointerException {

		ArrayList<SectorDTO> sdtos = new ArrayList<SectorDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getZonas")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ZonaDAO zdao = new ZonaDAO(s);
				List<Zona> zs = null;

				if (parent != null && parent.getIdSector() != null) {
					if (SectorDTO.TIPO_COMUNA.equals(parent.getTipoSector())) {
						zs = zdao.findByIdAplicacionANDIdComuna(idAplicacion,
								parent.getIdSector());
					} else if (SectorDTO.TIPO_REGION.equals(parent
							.getTipoSector())) {
						zs = zdao.findByIdAplicacionANDIdRegion(idAplicacion,
								parent.getIdSector());
					}
				}

				if (zs == null) {
					zs = zdao.findByIdAplicacion(idAplicacion);
				}

				if (zs != null && !zs.isEmpty()) {
					for (Zona zona : zs) {
						sdtos.add(zona.getSectodDTO());
					}
				}

				s.getTransaction().commit();
			}
		} catch (HibernateException ex) {
			System.err.println(ex);
			ex.printStackTrace();
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
		return sdtos;
	}

	/**
	 * 
	 * @permiso getDetalleCurso
	 */
	@Override
	public DetalleCursoDTO getDetalleCurso(Integer idCurso,
			Integer idActividadTipo) throws NoAllowedException,
			NoLoggedException, DBException, ConsistencyException,
			NullPointerException {
		DetalleCursoDTO dcdto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getDetalleCurso")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo(s);
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO(s);
				dcdto = cdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (dcdto == null || dcdto.getId() == null) {
					throw new NullPointerException(
							"No se ha encontrado información para el curso especificado en la actividad indicada.");
				}
				UsuarioDAO udao = new UsuarioDAO(s);
				List<Usuario> examinadores = udao
						.findExaminadoresByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				ArrayList<UserDTO> exs = new ArrayList<UserDTO>();
				if (examinadores != null && !examinadores.isEmpty()) {
					for (Usuario ex : examinadores) {
						exs.add(ex.getUserDTO());
					}
				}
				dcdto.setExaminadores(exs);
				Usuario supervisor = udao
						.findSupervisorByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (supervisor != null) {
					dcdto.setSupervisor(supervisor.getUserDTO());
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
		return dcdto;
	}
}
