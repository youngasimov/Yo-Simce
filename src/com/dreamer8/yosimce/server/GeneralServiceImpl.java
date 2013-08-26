package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.general.GeneralService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ComunaDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.RegionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.Comuna;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Region;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.CursoDTO;
import com.dreamer8.yosimce.shared.dto.DetalleCursoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.HistorialCambioItemDTO;
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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				RegionDAO rdao = new RegionDAO();
				List<Region> rs = rdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDUsuarioTipo(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getNombre());
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
	public ArrayList<SectorDTO> getComunas(SectorDTO sector) {

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

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ComunaDAO cdao = new ComunaDAO();
				List<Comuna> cs = null;

				if (sector == null || sector.getIdSector() == null) {
					cs = cdao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdUsuarioANDusuarioTipo(
									idAplicacion, idNivel, idActividadTipo,
									u.getId(), usuarioTipo.getNombre());
				} else if (sector.getTipoSector().equals(
						SectorDTO.TIPO_PROVINCIA)) {
					cs = cdao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdProvinciaANDIdUsuarioANDusuarioTipo(
									idAplicacion, idNivel, idActividadTipo,
									sector.getIdSector(), u.getId(),
									usuarioTipo.getNombre());
				} else {
					cs = cdao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdRegionANDIdUsuarioANDusuarioTipo(
									idAplicacion, idNivel, idActividadTipo,
									sector.getIdSector(), u.getId(),
									usuarioTipo.getNombre());
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
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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

				if (rbdSeach == null || !StringUtils.isInt(rbdSeach)) {
					throw new NullPointerException(
							"No se ha especificado un RBD válido.");
				}

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO();
				List<Curso> cs = cdao.findByByActividadANDRbd(idAplicacion,
						idNivel, idActividadTipo, u.getId(),
						usuarioTipo.getNombre(), rbdSeach);
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
		}
		return cdtos;
	}

	@Override
	public DetalleCursoDTO getDetalleCurso(Integer idCurso)
			throws NoAllowedException, NoLoggedException, DBException {

		DetalleCursoDTO dcdto = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO();
				dcdto = cdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);

				if (dcdto == null || dcdto.getId() == null) {
					throw new NullPointerException(
							"No se ha encontrado información para el curso especificado en la actividad indicada.");
				}
				UsuarioDAO udao = new UsuarioDAO();
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
		}
		return dcdto;
	}

	@Override
	public CursoDTO getCurso(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
