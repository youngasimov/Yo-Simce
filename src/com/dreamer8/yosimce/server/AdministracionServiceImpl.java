package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.administracion.AdministracionService;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
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

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				s.beginTransaction();

				Integer idTipoUsuario = ac.getIdUsuarioTipo();

				UsuarioDAO udao = new UsuarioDAO();
				udtos = (ArrayList<UserDTO>) udao
						.findByIdAplicacionANDIdNivelANDIdTipoUsuarioSuperiorANDFiltro(
								idAplicacion, idNivel, idTipoUsuario, offset,
								length, filtro);
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

				Integer idActividadTipo = ac.getIdActividadTipo();
				if (idActividadTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de la actividad.");
				}

				s.beginTransaction();
				Integer idUsuarioTipo = ac.getIdUsuarioTipo();
				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				List<UsuarioTipo> uts = utdao
						.findByIdAplicacionANDIdTipoUsuarioSuperior(
								idAplicacion, idUsuarioTipo);

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

				if (tipoEmplazamiento == null || tipoEmplazamiento.equals("")) {
					throw new NullPointerException(
							"No se ha especificado un tipo de emplazamiento");
				}

				s.beginTransaction();
				
				if(tipoEmplazamiento.equals(EmplazamientoDTO.CENTRO_OPERACIONAL)){
					CoDAO codao = new CoDAO();
					List<Co> cos = codao.findByIdAplicacion(idAplicacion);
					if (cos != null && !cos.isEmpty()) {
						for (Co co : cos) {
							edtos.add(co.getEmplazamientoDTO());
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

}
