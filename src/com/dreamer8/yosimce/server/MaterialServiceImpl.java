package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.material.MaterialService;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.GuiaDespachoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.LugarDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialHistorialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.GuiaDespacho;
import com.dreamer8.yosimce.server.hibernate.pojo.Lugar;
import com.dreamer8.yosimce.server.hibernate.pojo.Material;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.DetallesMaterialDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class MaterialServiceImpl extends CustomRemoteServiceServlet implements
		MaterialService {

	private String className = "MaterialService";

	/**
	 * @permiso getCentrosOperacionAsociados
	 */
	@Override
	public ArrayList<EmplazamientoDTO> getCentrosOperacionAsociados()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<EmplazamientoDTO> edtos = new ArrayList<EmplazamientoDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getCentrosOperacionAsociados")) {

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

				CoDAO cdao = new CoDAO();
				List<Co> cos = cdao
						.findByIdAplicacionANDIdUsuarioANDUsuarioTipo(
								idAplicacion, u.getId(),
								usuarioTipo.getNombre());

				if (cos != null && !cos.isEmpty()) {
					for (Co co : cos) {
						edtos.add(co.getEmplazamientoDTO());
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
		return edtos;
	}

	/**
	 * @permiso getEtapas
	 */
	@Override
	public ArrayList<EtapaDTO> getEtapas() throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {

		ArrayList<EtapaDTO> edtos = new ArrayList<EtapaDTO>();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEtapas")) {

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

				LugarDAO ldao = new LugarDAO();
				List<Lugar> ls = ldao.findByIdAplicacion(idAplicacion);
				if (ls != null && !ls.isEmpty()) {
					for (Lugar lugar : ls) {
						edtos.add(lugar.getEtapaDTO());
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
		return edtos;
	}

	/**
	 * @permiso getUser
	 */
	@Override
	public UserDTO getUser(String rut) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {

		UserDTO udto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getUser")) {

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

				if (rut == null || !StringUtils.isRut(rut)) {
					throw new NullPointerException(
							"No se ha especificado un rut válido.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioDAO udao = new UsuarioDAO();
				Usuario user = udao.findbyUsername(StringUtils.formatRut(rut));
				if (user == null) {
					throw new NullPointerException(
							"No se ha encontrado el usuario especificado.");
				}

				udto = user.getUserDTO();

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
		return udto;
	}

	/**
	 * @permiso getMateriales
	 */
	@Override
	public ArrayList<MaterialDTO> getMateriales(Integer idCo)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<MaterialDTO> mdtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getMateriales")) {

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

				if (idCo == null) {
					throw new NullPointerException(
							"No se ha especificado el centro de operación.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				MaterialDAO mdao = new MaterialDAO();
				mdtos = (ArrayList<MaterialDTO>) mdao
						.findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCo(
								idAplicacion, idNivel, idActividadTipo, idCo);

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
		return mdtos;
	}

	/**
	 * @permiso getMaterial
	 */
	@Override
	public MaterialDTO getMaterial(String codigo) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {

		MaterialDTO mdto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getMaterial")) {

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

				if (codigo == null) {
					throw new NullPointerException(
							"No se ha especificado el código del material.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				MaterialDAO mdao = new MaterialDAO();
				mdto = mdao
						.findDTOByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigo(
								idAplicacion, idNivel, idActividadTipo, codigo);
				if (mdto == null) {
					throw new NullPointerException(
							"No se encontró un material con el código especificado.");
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
		return mdto;
	}

	/**
	 * @permiso exportar
	 */
	@Override
	public DocumentoDTO exportar(ArrayList<Integer> idsMaterial)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso getCentrosOperacion
	 */
	@Override
	public ArrayList<EmplazamientoDTO> getCentrosOperacion()
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		ArrayList<EmplazamientoDTO> edtos = new ArrayList<EmplazamientoDTO>();
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

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CoDAO cdao = new CoDAO();
				List<Co> cos = cdao.findByIdAplicacion(idAplicacion);
				if (cos != null && !cos.isEmpty()) {
					for (Co co : cos) {
						edtos.add(co.getEmplazamientoDTO());
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
		return edtos;
	}

	/**
	 * @permiso getMaterialesByCodigos
	 */
	@Override
	public ArrayList<MaterialDTO> getMaterialesByCodigos(
			ArrayList<String> codigos) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {

		ArrayList<MaterialDTO> mdtos = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getMaterialesByCodigos")) {

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

				if (mdtos == null || mdtos.isEmpty()) {
					throw new NullPointerException(
							"No se han especificado códigos de materiales.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				MaterialDAO mdao = new MaterialDAO();
				mdtos = (ArrayList<MaterialDTO>) mdao
						.findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
								idAplicacion, idNivel, idActividadTipo, codigos);

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
		return mdtos;
	}

	/**
	 * @permiso getDetallesMaterial
	 */
	@Override
	public DetallesMaterialDTO getDetallesMaterial(Integer idMaterial)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		DetallesMaterialDTO dmdto = new DetallesMaterialDTO();
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getDetallesMaterial")) {

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

				if (idMaterial == null) {
					throw new NullPointerException(
							"No se ha especificado un material.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				MaterialDAO mdao = new MaterialDAO();
				Material m = mdao.getById(idMaterial);
				if (m == null) {
					throw new NullPointerException(
							"El material especificado no existe.");
				}
				dmdto.setNombreCentroOperacion(m.getCo().getNombre());

				MaterialHistorialDAO mhdao = new MaterialHistorialDAO();
				List<MaterialHistorial> mhs = mhdao
						.findByIdMaterial(idMaterial);
				ArrayList<HistorialMaterialItemDTO> hmidto = new ArrayList<HistorialMaterialItemDTO>();

				if (mhs != null && !mhs.isEmpty()) {
					for (MaterialHistorial mh : mhs) {
						hmidto.add(mh.getHistorialMaterialItemDTO());
					}
				}

				dmdto.setHistorial(hmidto);

				GuiaDespachoDAO gddao = new GuiaDespachoDAO();
				List<GuiaDespacho> gds = gddao.findByIdMaterial(idMaterial);
				HashMap<String, DocumentoDTO> docs = new HashMap<String, DocumentoDTO>();
				DocumentoDTO ddto = null;
				if (gds != null && !gds.isEmpty()) {
					for (GuiaDespacho gd : gds) {
						ddto = gd.getDocumentoDTO(getBaseURL());
						docs.put(gd.getCodigo(), ddto);
					}
				}

				dmdto.setDocumentos(docs);

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
		return dmdto;
	}

	/**
	 * @permiso ingresarMateriales
	 */
	@Override
	public Boolean ingresarMateriales(Integer idCo, ArrayList<String> codigos,
			String folio, String file) throws NoAllowedException,
			NoLoggedException, DBException, NullPointerException,
			ConsistencyException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "ingresarMateriales")) {

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

				if (idCo == null) {
					throw new NullPointerException(
							"No se ha especificado el centro de operaciones.");
				}

				if (codigos == null || codigos.isEmpty()) {
					throw new NullPointerException(
							"No se han especificado los códigos.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CoDAO cdao = new CoDAO();
				Co co = cdao.getById(idCo);
				if (co == null) {
					throw new NullPointerException(
							"No se ha encontrado el centro especificado.");
				}

				MaterialDAO mdao = new MaterialDAO();
				List<Material> ms = mdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
								idAplicacion, idNivel, idActividadTipo, codigos);

				if (ms == null || ms.isEmpty()) {
					throw new NullPointerException(
							"No se encontró ningún material con los códigos especificados.");
				}

				LugarDAO ldao = new LugarDAO();
				Lugar centro = ldao.findByNombre(Lugar.CENTRO_DE_OPERACIONES);
				Lugar imprenta = ldao.findByNombre(Lugar.IMPRENTA);

				MaterialEstadoDAO medao = new MaterialEstadoDAO();
				MaterialEstado me = medao
						.findByNombre(MaterialEstado.EN_EL_LUGAR);
				
				MaterialHistorialDAO mhdao = new MaterialHistorialDAO();

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
		return result;
	}

	/**
	 * @permiso crearOEditarLote
	 */
	@Override
	public Integer crearOEditarLote(Integer idCo,
			ArrayList<Integer> materiales, LoteDTO lote)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso despacharMateriales
	 */
	@Override
	public Boolean despacharMateriales(Integer idCo, EtapaDTO etapa,
			String rut, ArrayList<String> codigos, String folio, String file)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso despacharMateriales
	 */
	@Override
	public Boolean despacharMateriales(Integer idCo, Integer idCoDestino,
			String rut, ArrayList<String> codigos, String folio, String file)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @permiso eliminarLote
	 */
	@Override
	public Boolean eliminarLote(Integer idCo, Integer loteId)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {
		// TODO Auto-generated method stub
		return null;
	}

}
