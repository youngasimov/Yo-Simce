package com.dreamer8.yosimce.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.client.planificacion.PlanificacionService;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ArchivoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ContactoCargoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.CargoDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EstablecimientoDTO;
import com.dreamer8.yosimce.shared.dto.EstadoAgendaDTO;
import com.dreamer8.yosimce.shared.dto.ExaminadorDTO;
import com.dreamer8.yosimce.shared.dto.SupervisorDTO;
import com.dreamer8.yosimce.shared.exceptions.ConsistencyException;
import com.dreamer8.yosimce.shared.exceptions.DBException;
import com.dreamer8.yosimce.shared.exceptions.NoAllowedException;
import com.dreamer8.yosimce.shared.exceptions.NoLoggedException;

public class PlanificacionServiceImpl extends CustomRemoteServiceServlet
		implements PlanificacionService {

	private String className = "PlanificacionService";

	/**
	 * @permiso getPreviewAgendamientos
	 */
	@Override
	public ArrayList<AgendaPreviewDTO> getPreviewAgendamientos(Integer offset,
			Integer length, Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<AgendaPreviewDTO> apdtos = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getPreviewAgendamientos")) {

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

				ActividadDAO adao = new ActividadDAO();
				apdtos = (ArrayList<AgendaPreviewDTO>) adao
						.findAgendasByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getNombre(), offset,
								length, filtros);

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
		return apdtos;
	}

	/**
	 * @permiso getAgendaCurso
	 */
	@Override
	public AgendaDTO getAgendaCurso(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {
		AgendaDTO adto = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getAgendaCurso")) {

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

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a == null || a.getId() == null) {
					throw new NullPointerException(
							"La actividad especificada no existe.");
				}

				adto = a.getAgendaDTO();

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
		return adto;
	}

	/**
	 * @permiso AgendarVisita
	 */
	@Override
	public AgendaItemDTO AgendarVisita(Integer idCurso, AgendaItemDTO itemAgenda)
			throws NoAllowedException, NoLoggedException, DBException {

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "AgendarVisita")) {

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
							"No se ha especificado un curso");
				}

				if (itemAgenda == null || itemAgenda.getFecha() == null
						|| itemAgenda.getEstado() == null
						|| itemAgenda.getEstado().getId() == null) {
					throw new NullPointerException(
							"Faltan datos para realizar el agendamiento");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a == null) {
					throw new NullPointerException(
							"No existe una actividad para este curso en el nivel seleccionado");
				}
				ActividadEstadoDAO aedao = new ActividadEstadoDAO();
				ActividadEstado ae = aedao.getById(itemAgenda.getEstado()
						.getId());
				if (ae == null) {
					throw new NullPointerException(
							"El estado especificado no existe");
				}
				a.setFechaInicio(itemAgenda.getFecha());
				a.setUsuario(u);
				a.setComentario(itemAgenda.getComentario());
				a.setActividadEstado(ae);
				adao.update(a);

				itemAgenda.setCreador(u.getUserDTO());

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
		return itemAgenda;
	}

	/**
	 * @permiso getEstadosAgenda
	 */
	@Override
	public ArrayList<EstadoAgendaDTO> getEstadosAgenda()
			throws NoAllowedException, NoLoggedException, DBException {

		ArrayList<EstadoAgendaDTO> eadtos = new ArrayList<EstadoAgendaDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getEstadosAgenda")) {

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

				ActividadEstadoDAO aedao = new ActividadEstadoDAO();
				List<ActividadEstado> aes = aedao.findAllByAgendamiento();
				for (ActividadEstado actividadEstado : aes) {
					eadtos.add(actividadEstado.getEstadoAgendaDTO());
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
		return eadtos;
	}

	/**
	 * @permiso getContacto
	 */
	@Override
	public ContactoDTO getContacto(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {

		ContactoDTO cdto = new ContactoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getContacto")) {

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

				s.beginTransaction();

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a != null) {
					cdto = a.getContactoDTO();
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
		return cdto;
	}

	/**
	 * @permiso getTotalPreviewAgendamientos
	 */
	@Override
	public Integer getTotalPreviewAgendamientos(Map<String, String> filtros)
			throws NoAllowedException, NoLoggedException, DBException {

		Integer result = 0;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className, "getTotalPreviewAgendamientos")) {

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

				ActividadDAO adao = new ActividadDAO();
				result = adao
						.countAgendasByIdAplicacionANDIdNivelANDIdActividadTipoANDFiltros(
								idAplicacion, idNivel, idActividadTipo,
								u.getId(), usuarioTipo.getNombre(), filtros);

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
		return result;
	}

	/**
	 * @permiso editarContacto
	 */
	@Override
	public Boolean editarContacto(Integer idCurso, ContactoDTO contacto)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean resutl = true;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "editarContacto")) {

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

				if (contacto == null || contacto.getContactoNombre().equals("")
						|| contacto.getContactoTelefono().equals("")) {
					throw new NullPointerException(
							"No se han especificado los datos del contacto.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				ActividadDAO adao = new ActividadDAO();
				Actividad a = adao
						.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
								idAplicacion, idNivel, idActividadTipo, idCurso);
				if (a == null) {
					throw new NullPointerException(
							"No se ha encontrado una actividad para el curso especificado.");
				}
				a.setContactoNombre(contacto.getContactoNombre());
				a.setContactoTelefono(contacto.getContactoTelefono());
				a.setContactoEmail(contacto.getContactoEmail());
				if (contacto.getCargo() != null
						&& contacto.getCargo().getId() != null) {
					ContactoCargoDAO ccdao = new ContactoCargoDAO();
					ContactoCargo cc = ccdao.getById(contacto.getCargo()
							.getId());
					a.setContactoCargo(cc);
				}
				adao.update(a);

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

	/**
	 * @permiso getCargos
	 */
	@Override
	public ArrayList<CargoDTO> getCargos() throws NoAllowedException,
			NoLoggedException, DBException {

		ArrayList<CargoDTO> cdtos = new ArrayList<CargoDTO>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getCargos")) {

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

				ContactoCargoDAO ccdao = new ContactoCargoDAO();
				List<ContactoCargo> ccs = ccdao.findAll();
				if (ccs != null && !ccs.isEmpty()) {
					for (ContactoCargo contactoCargo : ccs) {
						cdtos.add(contactoCargo.getCargoDTO());
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
	public DocumentoDTO getDocumentoPreviewAgendamientos(
			Map<String, String> filtros) throws NoAllowedException,
			NoLoggedException, DBException {

		DocumentoDTO ddto = null;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged()
					&& ac.isAllowed(className,
							"getDocumentoPreviewAgendamientos")) {

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

				ActividadDAO adao = new ActividadDAO();
				Integer total = adao
						.countAgendasCsvByIdAplicacionANDIdNivelANDFiltros(
								idAplicacion, idNivel, u.getId(),
								usuarioTipo.getNombre(), filtros);
				if (total == null || total == 0) {
					throw new NullPointerException(
							"No se han obtenido resultados con el filtro especificado.");
				}

				Integer offset = 0;
				Integer lenght = 1000;
				List<String> filas = null;
				DateFormat dateFormat = new SimpleDateFormat(
						"dd-MM-yyyy HH:mm:ss");
				String name = dateFormat.format(new Date());
				File file = File.createTempFile(name.replaceAll(" ", "_").replaceAll(":", ""), ".csv", getUploadDir());
//				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ISO-8859-1"));

				while (total > 0) {
					filas = adao
							.findAgendasCsvByIdAplicacionANDIdNivelANDFiltros(
									idAplicacion, idNivel, u.getId(),
									usuarioTipo.getNombre(), offset, lenght,
									filtros);
					total -= lenght;

					if (filas != null && !filas.isEmpty()) {
						for (String contenido : filas) {
							bw.write(contenido + "\n\r");
						}
					}
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
		}
		return ddto;
	}

	/**
	 * @permiso getDirector
	 */
	@Override
	public ContactoDTO getDirector(Integer idCurso) throws NoAllowedException,
			NoLoggedException, DBException {

		ContactoDTO cdto = new ContactoDTO();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "getDirector")) {

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
				Curso c = cdao.getById(idCurso);
				if (c == null) {
					throw new NullPointerException(
							"El curso especificado no existe.");
				}
				Establecimiento e = c.getEstablecimiento();
				ContactoCargoDAO ccdao = new ContactoCargoDAO();
				ContactoCargo cc = ccdao.findByName(ContactoCargo.DIRECTOR);
				cdto.setCargo(cc.getCargoDTO());
				cdto.setContactoEmail(e.getEmail());
				cdto.setContactoNombre(e.getDirectorNombre());
				cdto.setContactoTelefono(e.getTelefono());

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
		return cdto;
	}

	/**
	 * @permiso editarDirector
	 */
	@Override
	public Boolean editarDirector(Integer idCurso, ContactoDTO director)
			throws NoAllowedException, NoLoggedException, DBException {

		Boolean result = true;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			AccessControl ac = getAccessControl();
			if (ac.isLogged() && ac.isAllowed(className, "editarDirector")) {

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

				if (director == null) {
					throw new NullPointerException(
							"No se han especificado los datos del director.");
				}

				Usuario u = getUsuarioActual();

				s.beginTransaction();

				UsuarioTipo usuarioTipo = ac.getUsuarioTipo();
				if (usuarioTipo == null) {
					throw new NullPointerException(
							"No se ha especificado el tipo de usuario.");
				}

				CursoDAO cdao = new CursoDAO();
				Curso c = cdao.getById(idCurso);
				if (c == null) {
					throw new NullPointerException(
							"El curso especificado no existe.");
				}
				Establecimiento e = c.getEstablecimiento();
				if (director.getContactoNombre() != null) {
					e.setDirectorNombre(director.getContactoNombre());
				}
				if (director.getContactoTelefono() != null) {
					e.setTelefono(director.getContactoTelefono());
				}
				if (director.getContactoEmail() != null) {
					e.setEmail(director.getContactoEmail());
				}
				EstablecimientoDAO edao = new EstablecimientoDAO();
				edao.update(e);

				ActividadDAO adao = new ActividadDAO();
				List<Actividad> as = c.getActividads();
				if (as != null && !as.isEmpty()) {
					for (Actividad actividad : as) {
						if (actividad.getContactoCargo() != null
								&& actividad.getContactoCargo().getNombre()
										.equals(ContactoCargo.DIRECTOR)) {
							if (director.getContactoNombre() != null) {
								actividad.setContactoNombre(director
										.getContactoNombre());
							}
							if (director.getContactoTelefono() != null) {
								actividad.setContactoTelefono(director
										.getContactoTelefono());
							}
							if (director.getContactoEmail() != null) {
								actividad.setContactoEmail(director
										.getContactoEmail());
							}
							adao.update(actividad);
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
		return result;
	}
}
