package com.dreamer8.yosimce.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.dreamer8.yosimce.client.material.MaterialService;
import com.dreamer8.yosimce.server.hibernate.dao.ArchivoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.GuiaDespachoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.LoteDAO;
import com.dreamer8.yosimce.server.hibernate.dao.LugarDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialHistorialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialXGuiaDespachoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialXLoteDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.GuiaDespacho;
import com.dreamer8.yosimce.server.hibernate.pojo.Lote;
import com.dreamer8.yosimce.server.hibernate.pojo.Lugar;
import com.dreamer8.yosimce.server.hibernate.pojo.Material;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialHistorialId;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXGuiaDespacho;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXLote;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXLoteId;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.server.utils.StringUtils;
import com.dreamer8.yosimce.shared.dto.ActividadPreviewDTO;
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

		DocumentoDTO ddto = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "exportar")) {

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

				if (idsMaterial == null || idsMaterial.isEmpty()) {
					throw new NullPointerException(
							"No se han especificado materiales.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				DateFormat dateFormat = new SimpleDateFormat(
						"dd-MM-yyyy HH.mm.ss");
				String name = "Materiales " + dateFormat.format(new Date());
				File file = File.createTempFile(
						StringUtils.getDatePathSafe(name), ".csv",
						getUploadDir());
				// FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "ISO-8859-1"));

				MaterialDAO mdao = new MaterialDAO();
				List<MaterialDTO> mdtos = mdao
						.findDTOSByIdAplicacionANDIdNivelANDIdActividadTipoANDIdMateriales(
								idAplicacion, idNivel, idActividadTipo,
								idsMaterial);

				if (mdtos == null || mdtos.isEmpty()) {
					throw new NullPointerException(
							"No se encontraron los materiales especificados.");
				}
				String contenido;
				bw.write("Código;Tipo Material;RBD;Establecimiento;Nivel;Curso;Destino;Lote\r");

				for (MaterialDTO mdto : mdtos) {
					contenido = mdto.getCodigo() + ";";
					contenido += mdto.getTipo() + ";";
					contenido += mdto.getRbd() + ";";
					contenido += mdto.getEstablecimiento() + ";";
					contenido += mdto.getNivel() + ";";
					contenido += mdto.getCurso() + ";";
					contenido += mdto.getEtapa() + ";";
					contenido += (mdto.getLote() != null) ? mdto.getLote()
							.getNombre() : "" + ";";
					bw.write(contenido + "\r");

				}

				bw.close();

				ArchivoDAO ardao = new ArchivoDAO();
				Archivo archivo = new Archivo();
				archivo.setTitulo(name);
				archivo.setRutaArchivo(file.getAbsolutePath());
				archivo.setMimeType("text/plain");
				archivo.setIpServer("200.1.30.52");
				ardao.save(archivo);

				ddto = archivo.getDocumentoDTO(getBaseURL());

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
		} catch (IOException ex) {
			HibernateUtil.rollbackActiveOnly(s);
			throw new NullPointerException("No se pudo crear el archivo.");
		} finally {
			ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
			if (s.isOpen()) {
				s.clear();
				s.close();
			}
		}
		return ddto;
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

				if (codigos == null || codigos.isEmpty()) {
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

				if (mdtos == null || mdtos.isEmpty()) {
					throw new NullPointerException(
							"Los códigos especificados no han entregado ningún resultado.");
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

				for (String string : codigos) {
					if (string == null || string.isEmpty()) {
						throw new NullPointerException(
								"Se ha ingresado un código inválido.");
					}
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
				List<Lugar> lugares = ldao.findAll();

				MaterialEstadoDAO medao = new MaterialEstadoDAO();
				MaterialEstado me = medao
						.findByNombre(MaterialEstado.EN_EL_LUGAR);

				GuiaDespachoDAO gddao = new GuiaDespachoDAO();
				GuiaDespacho gd = null;
				Date fecha = new Date();
				if (folio != null && !folio.isEmpty()) {
					gd = new GuiaDespacho();
					gd.setFecha(new Date());
					gd.setFecha(fecha);
					gd.setCodigo(folio);
					if (file != null && !file.isEmpty()) {
						ArchivoDAO adao = new ArchivoDAO();
						Archivo archivo = guardarArchivo(file);
						archivo.setTitulo(folio);
						adao.save(archivo);
						gd.setArchivo(archivo);
					}
					gddao.save(gd);
				}

				MaterialHistorialDAO mhdao = new MaterialHistorialDAO();
				MaterialHistorial mh = null;
				MaterialHistorialId mhid = null;
				HistorialMaterialItemDTO mhLast = null;
				MaterialXGuiaDespachoDAO mxgddao = new MaterialXGuiaDespachoDAO();
				MaterialXGuiaDespacho mxgd = null;
				List<HistorialMaterialItemDTO> mhLasts = mhdao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
								idAplicacion, idNivel, idActividadTipo, codigos);
				for (Material material : ms) {
					mhLast = null;
					// mhLast = mhdao.findLastByIdMaterial(material.getId());
					if (mhLasts != null && !mhLasts.isEmpty()) {
						for (HistorialMaterialItemDTO materialHistorial : mhLasts) {
							if (material.getId().equals(
									materialHistorial.getId())) {
								mhLast = materialHistorial;
								mhLasts.remove(materialHistorial);
								break;
							}
						}
					}
					mhid = new MaterialHistorialId();
					mhid.setMaterialId(material.getId());
					mhid.setFecha(fecha);
					mh = new MaterialHistorial();
					mh.setId(mhid);
					mh.setUsuario(u);
					mh.setModificadorId(u.getId());
					mh.setLugarByDestinoId(centro);
					if (mhLast == null || mhLast.getIdOrigen() == null) {
						mh.setLugarByOrigenId(imprenta);
					} else {
						for (Lugar lugar : lugares) {
							if (lugar.getId().equals(mhLast.getIdOrigen())) {
								mh.setLugarByOrigenId(lugar);
								break;
							}
						}
					}
					// mh.setLugarByOrigenId((mhLast == null) ? imprenta :
					// mhLast
					// .getLugarByDestinoId());
					mh.setMaterialEstado(me);
					mh.setCo(co);
					mhdao.save(mh);
					if (gd != null) {
						mxgd = new MaterialXGuiaDespacho();
						mxgd.setMaterial(material);
						mxgd.setGuiaDespacho(gd);
						mxgd.setFecha(fecha);
						mxgddao.save(mxgd);
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

		Integer result = null;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "crearOEditarLote")) {

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

				if (lote == null || lote.getNombre() == null
						|| lote.getNombre().isEmpty()) {
					throw new NullPointerException(
							"No se ha especificado un nombre para el lote.");
				}

				if (materiales == null || materiales.isEmpty()) {
					throw new NullPointerException(
							"No se han especificado materiales.");
				}

				if (idCo == null) {
					throw new NullPointerException(
							"No se ha especificado el centro de operaciones.");
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

				LoteDAO ldao = new LoteDAO();
				Lote l = null;

				if (lote.getId() != -1) {
					l = ldao.getById(lote.getId());
				}
				if (l == null || lote.getId() == -1) {
					l = new Lote();
					l.setNombre(lote.getNombre());
					l.setFechaCreacion(new Date());
					ldao.save(l);
				}

				MaterialXLoteDAO mxldao = new MaterialXLoteDAO();
				List<Integer> idMs = mxldao.findIdMaterialesByIdLote(l.getId());
				MaterialXLoteId mxlid = null;
				MaterialXLote mxl = null;
				if (idMs != null && !idMs.isEmpty()) {
					for (Integer idM : idMs) {
						if (!materiales.contains(idM)) {
							mxlid = new MaterialXLoteId();
							mxlid.setMaterialId(idM);
							mxlid.setLoteId(l.getId());
							mxldao.deleteById(mxlid);
						} else {
							materiales.remove(idM);
						}
					}
				}

				MaterialDAO mdao = new MaterialDAO();
				Material m = null;

				if (materiales != null && !materiales.isEmpty()) {
					for (Integer idM : materiales) {
						m = mdao.getById(idM);
						if (m == null) {
							throw new NullPointerException(
									"No se ha encontrado uno de los materiales especificados.");
						}
						if (!co.equals(m.getCo())) {
							throw new ConsistencyException("El material "
									+ m.getCodigo()
									+ " no corresponde al centro especificado");
						}
						mxlid = new MaterialXLoteId();
						mxlid.setMaterialId(m.getId());
						mxlid.setLoteId(l.getId());
						mxl = new MaterialXLote();
						mxl.setId(mxlid);
						mxldao.save(mxl);
					}
				}

				result = l.getId();

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
	 * @permiso despacharMateriales
	 */
	@Override
	public Boolean despacharMateriales(Integer idCo, EtapaDTO etapa,
			String rut, ArrayList<String> codigos, String folio, String file)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "despacharMateriales")) {

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

				if (etapa == null || etapa.getId() == null) {
					throw new NullPointerException(
							"No se ha especificado el destino.");
				}

				if (rut == null || rut.isEmpty()) {
					throw new NullPointerException(
							"No se ha especificado el receptor de los materiales.");
				}

				if (codigos == null || codigos.isEmpty()) {
					throw new NullPointerException(
							"No se han especificado los códigos de los materiales a despachar.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioDAO udao = new UsuarioDAO();
				Usuario receptor = udao.findbyUsername(StringUtils
						.formatRut(rut));
				if (receptor == null) {
					throw new NullPointerException(
							"No se ha encontrado a un usuario con el rut especificado.");
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
				Lugar destino = ldao.getById(etapa.getId());

				MaterialEstadoDAO medao = new MaterialEstadoDAO();
				MaterialEstado me = medao
						.findByNombre(MaterialEstado.EN_EL_LUGAR);

				GuiaDespachoDAO gddao = new GuiaDespachoDAO();
				GuiaDespacho gd = null;
				Date fecha = new Date();
				if (folio != null && !folio.isEmpty()) {
					gd = new GuiaDespacho();
					gd.setFecha(new Date());
					gd.setFecha(fecha);
					gd.setCodigo(folio);
					if (file != null && !file.isEmpty()) {
						ArchivoDAO adao = new ArchivoDAO();
						Archivo archivo = guardarArchivo(file);
						archivo.setTitulo(folio);
						adao.save(archivo);
						gd.setArchivo(archivo);
					}
					gddao.save(gd);
				}

				MaterialHistorialDAO mhdao = new MaterialHistorialDAO();
				MaterialHistorial mh = null;
				MaterialHistorialId mhid = null;
				MaterialXGuiaDespachoDAO mxgddao = new MaterialXGuiaDespachoDAO();
				MaterialXGuiaDespacho mxgd = null;
				MaterialXLoteDAO mxldao = new MaterialXLoteDAO();
				for (Material material : ms) {
					mhid = new MaterialHistorialId();
					mhid.setMaterialId(material.getId());
					mhid.setFecha(fecha);
					mh = new MaterialHistorial();
					mh.setId(mhid);
					mh.setUsuario(receptor);
					mh.setModificadorId(u.getId());
					mh.setLugarByDestinoId(destino);
					mh.setLugarByOrigenId(centro);
					mh.setMaterialEstado(me);
					mh.setCo(co);
					mhdao.save(mh);
					if (gd != null) {
						mxgd = new MaterialXGuiaDespacho();
						mxgd.setMaterial(material);
						mxgd.setGuiaDespacho(gd);
						mxgd.setFecha(fecha);
						mxgddao.save(mxgd);
					}
					mxldao.deleteByIdMaterial(material.getId());
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
		return result;
	}

	/**
	 * @permiso despacharMateriales
	 */
	@Override
	public Boolean despacharMateriales(Integer idCo, Integer idCoDestino,
			String rut, ArrayList<String> codigos, String folio, String file)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "despacharMateriales")) {

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

				if (idCoDestino == null) {
					throw new NullPointerException(
							"No se ha especificado el centro de destino.");
				}

				if (rut == null || rut.isEmpty()) {
					throw new NullPointerException(
							"No se ha especificado el receptor de los materiales.");
				}

				if (codigos == null || codigos.isEmpty()) {
					throw new NullPointerException(
							"No se han especificado los códigos de los materiales a despachar.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				UsuarioDAO udao = new UsuarioDAO();
				Usuario receptor = udao.findbyUsername(StringUtils
						.formatRut(rut));
				if (receptor == null) {
					throw new NullPointerException(
							"No se ha encontrado a un usuario con el rut especificado.");
				}

				CoDAO cdao = new CoDAO();
				Co co = cdao.getById(idCo);
				if (co == null) {
					throw new NullPointerException(
							"No se ha encontrado el centro especificado.");
				}

				Co destino = cdao.getById(idCo);
				if (destino == null) {
					throw new NullPointerException(
							"No se ha encontrado el centro de destino especificado.");
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

				MaterialEstadoDAO medao = new MaterialEstadoDAO();
				MaterialEstado me = medao
						.findByNombre(MaterialEstado.EN_CAMINO);

				GuiaDespachoDAO gddao = new GuiaDespachoDAO();
				GuiaDespacho gd = null;
				Date fecha = new Date();
				if (folio != null && !folio.isEmpty()) {
					gd = new GuiaDespacho();
					gd.setFecha(new Date());
					gd.setFecha(fecha);
					gd.setCodigo(folio);
					if (file != null && !file.isEmpty()) {
						ArchivoDAO adao = new ArchivoDAO();
						Archivo archivo = guardarArchivo(file);
						archivo.setTitulo(folio);
						adao.save(archivo);
						gd.setArchivo(archivo);
					}
					gddao.save(gd);
				}

				MaterialHistorialDAO mhdao = new MaterialHistorialDAO();
				MaterialHistorial mh = null;
				MaterialHistorialId mhid = null;
				MaterialXGuiaDespachoDAO mxgddao = new MaterialXGuiaDespachoDAO();
				MaterialXGuiaDespacho mxgd = null;
				MaterialXLoteDAO mxldao = new MaterialXLoteDAO();
				for (Material material : ms) {
					mhid = new MaterialHistorialId();
					mhid.setMaterialId(material.getId());
					mhid.setFecha(fecha);
					mh = new MaterialHistorial();
					mh.setId(mhid);
					mh.setUsuario(receptor);
					mh.setModificadorId(u.getId());
					mh.setLugarByDestinoId(centro);
					mh.setLugarByOrigenId(centro);
					mh.setMaterialEstado(me);
					mh.setCo(co);
					mhdao.save(mh);
					material.setCo(destino);
					mdao.update(material);
					if (gd != null) {
						mxgd = new MaterialXGuiaDespacho();
						mxgd.setMaterial(material);
						mxgd.setGuiaDespacho(gd);
						mxgd.setFecha(fecha);
						mxgddao.save(mxgd);
					}
					mxldao.deleteByIdMaterial(material.getId());
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
		return result;
	}

	/**
	 * @permiso eliminarLote
	 */
	@Override
	public Boolean eliminarLote(Integer idCo, Integer loteId)
			throws NoAllowedException, NoLoggedException, DBException,
			NullPointerException, ConsistencyException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().openSession();
		ManagedSessionContext.bind(s);
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "eliminarLote")) {

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

				if (loteId == null || loteId == -1) {
					throw new NullPointerException(
							"No se ha especificado el lote.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CoDAO cdao = new CoDAO();
				Co destino = cdao.getById(idCo);
				if (destino == null) {
					throw new NullPointerException(
							"No se ha encontrado el centro de destino especificado.");
				}

				LoteDAO ldao = new LoteDAO();
				Lote l = ldao.getById(loteId);

				if (l == null) {
					throw new NullPointerException(
							"No se ha encontrado el lote especificado.");
				}

				ldao.delete(l);

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

}
