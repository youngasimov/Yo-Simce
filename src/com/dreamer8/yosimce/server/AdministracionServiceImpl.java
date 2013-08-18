package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.administracion.AdministracionService;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoXPermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CentroRegionalDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ZonaDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipoXPermiso;
import com.dreamer8.yosimce.server.hibernate.pojo.CentroRegional;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Zona;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class AdministracionServiceImpl extends CustomRemoteServiceServlet
		implements AdministracionService {

	private String className = "AdministracionService";

	/**
	 * @permiso getUsuarios
	 */
	@Override
	public ArrayList<UserDTO> getUsuarios(String filtro, Integer offset,
			Integer length) throws NoAllowedException, NoLoggedException,
			DBException {

		ArrayList<UserDTO> udtos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getUsuarios")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioDAO udao = new UsuarioDAO();
				if (usuarioTipo.getNombre().equals(UsuarioTipo.ADMINISTRADOR)) {
					udtos = (ArrayList<UserDTO>) udao
							.findByIdAplicacionANDIdNivelANDFiltro(
									idAplicacion, idNivel, offset, length,
									filtro);
				} else {
					udtos = (ArrayList<UserDTO>) udao
							.findByIdAplicacionANDIdNivelANDIdTipoUsuarioSuperiorANDFiltro(
									idAplicacion, idNivel, usuarioTipo.getId(),
									offset, length, filtro);
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
		return udtos;
	}

	/**
	 * @permiso getTiposUsuario
	 */
	@Override
	public ArrayList<TipoUsuarioDTO> getTiposUsuario()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<TipoUsuarioDTO> tudtos = new ArrayList<TipoUsuarioDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getTiposUsuario")) {

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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				List<UsuarioTipo> uts = null;

				if (usuarioTipo.getNombre().equals(UsuarioTipo.ADMINISTRADOR)) {
					uts = utdao.findByIdAplicacion(idAplicacion);
				} else {
					uts = utdao.findByIdAplicacionANDIdTipoUsuarioSuperior(
							idAplicacion, usuarioTipo.getId());
				}

				if (uts != null && !uts.isEmpty()) {
					for (UsuarioTipo ut : uts) {
						tudtos.add(ut.getTipoUsuarioDTO());
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
		return tudtos;
	}

	/**
	 * @permiso getEmplazamientos
	 */
	@Override
	public ArrayList<EmplazamientoDTO> getEmplazamientos(
			String tipoEmplazamiento) throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<EmplazamientoDTO> edtos = new ArrayList<EmplazamientoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEmplazamientos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				if (tipoEmplazamiento == null || tipoEmplazamiento.equals("")) {
					throw new NullPointerException(
							"No se ha especificado un tipo de emplazamiento");
				}

				s.beginTransaction();

				if (tipoEmplazamiento
						.equals(EmplazamientoDTO.CENTRO_OPERACIONAL)) {
					CoDAO codao = new CoDAO();
					List<Co> cos = codao.findByIdAplicacion(idAplicacion);
					if (cos != null && !cos.isEmpty()) {
						for (Co co : cos) {
							edtos.add(co.getEmplazamientoDTO());
						}
					}
				} else if (tipoEmplazamiento.equals(EmplazamientoDTO.ZONA)) {
					ZonaDAO zdao = new ZonaDAO();
					List<Zona> zs = zdao.findByIdAplicacion(idAplicacion);
					if (zs != null && !zs.isEmpty()) {
						for (Zona z : zs) {
							edtos.add(z.getEmplazamientoDTO());
						}
					}
				} else if (tipoEmplazamiento
						.equals(EmplazamientoDTO.CENTRO_REGIONAL)) {
					CentroRegionalDAO crdao = new CentroRegionalDAO();
					List<CentroRegional> crs = crdao
							.findByIdAplicacion(idAplicacion);
					if (crs != null && !crs.isEmpty()) {
						for (CentroRegional cr : crs) {
							edtos.add(cr.getEmplazamientoDTO());
						}
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
	 * @permiso setPerfilUsuario
	 */
	@Override
	public Boolean setPerfilUsuario(Integer idUsuario, Integer idTipoUsuario,
			EmplazamientoDTO emplazamiento) throws ConsistencyException,
			NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso reiniciarPassword
	 */
	@Override
	public Boolean reiniciarPassword(Integer idUsuario)
			throws NoAllowedException, NoLoggedException, DBException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getPermisos
	 */
	@Override
	public ArrayList<PermisoDTO> getPermisos() throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<PermisoDTO> pdtos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getPermisos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				s.beginTransaction();

				PermisoDAO pdao = new PermisoDAO();
				pdtos = (ArrayList<PermisoDTO>) pdao
						.findByIdAplicacion(idAplicacion);

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
		return pdtos;
	}

	/**
	 * @permiso setPermisos
	 */
	@Override
	public Boolean setPermisos(ArrayList<PermisoDTO> permisos)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean resutl = true;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "setPermisos")) {

				Integer idAplicacion = ac.getIdAplicacion();
				if (idAplicacion == null) {
					throw new NullPointerException(
							"No se ha especificado una aplicación.");
				}

				if (permisos != null && !permisos.isEmpty()) {
					throw new NullPointerException(
							"No se especificó ningún cambio en los permisos");
				}

				s.beginTransaction();
				AplicacionXUsuarioTipoDAO axutdao = new AplicacionXUsuarioTipoDAO();
				List<AplicacionXUsuarioTipo> axuts = axutdao
						.findByIdAplicacionSortedByIdUsuarioTipo(idAplicacion);

				AplicacionXUsuarioTipoXPermisoDAO axutxpdao = new AplicacionXUsuarioTipoXPermisoDAO();
				List<AplicacionXUsuarioTipoXPermiso> axutxps = axutxpdao
						.findByIdAplicacionSortedByIdPermiso(idAplicacion);

				// Recorrer listas y actualizar los pojos correspondientes con
				// los permisos adecuados

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
		return resutl;
	}

}
