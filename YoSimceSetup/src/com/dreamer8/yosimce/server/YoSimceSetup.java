package com.dreamer8.yosimce.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadHistorialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadXDocumentoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AlumnoXActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXEstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXNivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXNivelXActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoXPermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ContactoCargoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.DocumentoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.MaterialXActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.NivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.SexoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioSeleccionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioXAplicacionXNivelDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorial;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadHistorialId;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadXDocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Alumno;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AlumnoXActividad;
import com.dreamer8.yosimce.server.hibernate.pojo.Aplicacion;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXEstablecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXNivel;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXNivelXActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipoXPermiso;
import com.dreamer8.yosimce.server.hibernate.pojo.CarreraEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.Co;
import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.DocumentoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.EstablecimientoTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Lote;
import com.dreamer8.yosimce.server.hibernate.pojo.Material;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXActividad;
import com.dreamer8.yosimce.server.hibernate.pojo.MaterialXActividadId;
import com.dreamer8.yosimce.server.hibernate.pojo.Nivel;
import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;
import com.dreamer8.yosimce.server.hibernate.pojo.Sexo;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioSeleccion;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXAplicacionXNivel;
import com.dreamer8.yosimce.server.utils.StringUtils;

public class YoSimceSetup {

	public static void main(String[] args) {
		// createActividad();
		// List<Integer> ids = new ArrayList<Integer>();
		// ids.add(2);
		// ids.add(4);
		// ids.add(6);
		// ids.add(8);
		// ids.add(10);
		// asignarUsuario(16361209, 1, ids, 1);
		// asignarUsuario(16370885, 1, ids, 1);
		// ids = new ArrayList<Integer>();
		// ids.add(10);
		// asignarUsuario(16361209, 2, ids, 1);
		// asignarUsuario(16370885, 2, ids, 1);
		// initPermisos();

		// cargarAlumnosTic("titulares.csv", EstablecimientoTipo.SELECCIONADO);
		// cargarAlumnosTic("reemplazos1.csv", EstablecimientoTipo.REEMPLAZO_1);
		// cargarAlumnosTic("reemplazos2.csv", EstablecimientoTipo.REEMPLAZO_2);

		// loadLaWeaDeCapacitacion();

		// actualizarAgendamientoTIC();
		// cargarMateriales("4caja.csv", 1, 4, ActividadTipo.APLICACION_DIA_1);
		// cargarMateriales("4sobre.csv", 1, 4, ActividadTipo.VISITA_PREVIA);

		// asignarEstablecimientoTipo("titulares.csv",
		// EstablecimientoTipo.SELECCIONADO);
		// asignarEstablecimientoTipo("reemplazos1.csv",
		// EstablecimientoTipo.REEMPLAZO_1);
		// asignarEstablecimientoTipo("reemplazos2.csv",
		// EstablecimientoTipo.REEMPLAZO_2);

		// cargarEmailDirector("email_est.csv");

		// actualizarAgendamientoSimce("agendamiento_4to_8va.csv");
		// actualizarAgendamientoSimce("agendamiento_4to_rm.csv");

		// elimiarRepetidos();
		// cambiarDeCentroMalCargado();
		// actualizarAgendamientoTICConfirmando("agendamiento_tic.csv");

		// arreglarAlumnosTicRMyBiobio("titularesRMyBiobio.csv");
		// moverCajasADia2();

		// cargarMaterialesDeContingencia(
		// "fixed_sobre_complementario_4basico_Contingencia_adicionales.csv",
		// 1, 4, ActividadTipo.VISITA_PREVIA);
		// cargarMaterialesDeContingencia(
		// "fixed_cajas_curso_dia_4basico_contingencia_adicional.csv", 1,
		// 4, ActividadTipo.APLICACION_DIA_1);
		// asignarCodigoACurso("cursos_SIMCE_2013.csv");

		// cargarMaterialesUsandoCodigos("cajas_curso_dia_6basico_BIOBIO_RM.csv",
		// 1, 6, ActividadTipo.APLICACION_DIA_1);
		// cargarMaterialesUsandoCodigos(
		// "sobre_complementario_6basico_BIOBIO.csv", 1, 6,
		// ActividadTipo.VISITA_PREVIA);
		// cargarMaterialesUsandoCodigos("sobre_complementario_6basico_RM.csv",
		// 1,
		// 6, ActividadTipo.VISITA_PREVIA);

		// cargarMaterialesUsandoCodigos("cajas_pendientes_6to.csv", 1, 6,
		// ActividadTipo.APLICACION_DIA_1);
		// cargarMaterialesUsandoCodigos("sobres_pendientes_6to.csv", 1, 6,
		// ActividadTipo.VISITA_PREVIA);
		// moverCajasADia2("mover_cajas_experimentales_a_dia2.csv");
		// cargarEstablecimientoHospitalario("Rbd Hospitalarios.csv");

		// cargarMaterialesUsandoCodigos("cajas_curso_dia_2basico.csv", 1, 2,
		// ActividadTipo.APLICACION_DIA_1);

		// cargarMaterialesUsandoCodigos("2do.1.csv", 1, 2,
		// ActividadTipo.VISITA_PREVIA);
		// cargarMaterialesUsandoCodigos("2do.2.csv", 1, 2,
		// ActividadTipo.VISITA_PREVIA);
		// cargarMaterialesUsandoCodigos("2do.3.csv", 1, 2,
		// ActividadTipo.VISITA_PREVIA);

		// cargarMaterialesUsandoCodigos("sobres_2do_hospitalarios.csv", 1, 2,
		// ActividadTipo.VISITA_PREVIA);

		// createActividad();

		// cargarAlumnosTic("rbd_581_reemplazo1.csv",
		// EstablecimientoTipo.REEMPLAZO_1);

		// moverAlumnos("mover_alumnos.csv", 2, 10);
		// cargarAlumnosTic("alumnos_a_cargar.csv",
		// EstablecimientoTipo.SELECCIONADO, false);

		// cargarMateriales("cajas_NEE_6basico.csv", 1, 6,
		// ActividadTipo.APLICACION_DIA_1);

		// cargarMateriales("cajas_pendientes_6to_nee.csv", 1, 6,
		// ActividadTipo.APLICACION_DIA_1);
		//
		// cargarMateriales("caja_co_1812.csv", 1, 6,
		// ActividadTipo.APLICACION_DIA_1);
		//
		// cargarMateriales("sobre_co_1812.csv", 1, 6,
		// ActividadTipo.VISITA_PREVIA);

		// cargarCuestionariosEntregadosTic("CUESTIONARIOS_PADRES_APODERADOS.csv");

		moverCajasAOtroDia("361119167", 6, 3, 1);

		moverCajasAOtroDia("161213983", 6, 1, 3);

		System.out.println("fin :P");
	}

	public static void createActividad() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			s.beginTransaction();

			AplicacionXNivelXActividadTipoDAO axnxatdao = new AplicacionXNivelXActividadTipoDAO();
			List<AplicacionXNivelXActividadTipo> axnxats = axnxatdao.findAll();
			Integer total = 0;
			if (axnxats != null && !axnxats.isEmpty()) {
				CursoDAO cdao = new CursoDAO();
				List<Curso> cs = null;
				ActividadDAO adao = new ActividadDAO();
				Actividad a = null;
				Actividad actividadBase = null;
				ActividadEstadoDAO aedao = new ActividadEstadoDAO();
				ActividadTipoDAO atdao = new ActividadTipoDAO();
				ActividadTipo dia1 = atdao
						.finByNombre(ActividadTipo.APLICACION_DIA_1);
				ActividadEstado ae = aedao
						.findByNombre(ActividadEstado.SIN_INFORMACION);
				Establecimiento e = null;
				ContactoCargoDAO ccdao = new ContactoCargoDAO();
				ContactoCargo cc = ccdao.findByName(ContactoCargo.DIRECTOR);
				for (AplicacionXNivelXActividadTipo axnxat : axnxats) {
					cs = cdao.findSinActividadByIdAplicacionXNivel(axnxat
							.getAplicacionXNivel().getId());
					if (cs != null && !cs.isEmpty()) {
						for (Curso curso : cs) {
							a = new Actividad();
							a.setCurso(curso);
							a.setAplicacionXNivelXActividadTipo(axnxat);
							a.setActividadEstado(ae);
							e = curso.getEstablecimiento();
							a.setContactoNombre(e.getDirectorNombre());
							a.setContactoEmail(e.getEmail());
							a.setContactoTelefono(e.getTelefono());
							a.setContactoCargo(cc);
							a.setFechaInicio(axnxat.getFechaInicio());
							a.setFechaTermino(axnxat.getFechaTermino());
							a.setTotalAlumnos(curso.getCantidadAlumnos());
							if (axnxat.getActividadTipo().getNombre()
									.equals(ActividadTipo.APLICACION_DIA_1)) {
								a.setDia(1);
							} else if (axnxat.getActividadTipo().getNombre()
									.equals(ActividadTipo.APLICACION_DIA_2)) {
								actividadBase = adao
										.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
												axnxat.getAplicacionXNivel()
														.getAplicacion()
														.getId(), axnxat
														.getAplicacionXNivel()
														.getNivel().getId(),
												dia1.getId(), curso.getId());
								a.setDia(2);
								a.setActividadBase(actividadBase);
							}
							adao.save(a);
							total++;

						}
					}
					s.flush();
				}
			}
			System.err.println("Total " + total);
			s.getTransaction().commit();
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);

		}
	}

	public static void asignarUsuario(Integer idUsuario, Integer idAplicacion,
			List<Integer> idNiveles, Integer idTipoUsuario) {

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			s.beginTransaction();
			AplicacionXNivelDAO axndao = new AplicacionXNivelDAO();
			AplicacionXNivel axn = null;
			UsuarioXAplicacionXNivelDAO uxaxndao = new UsuarioXAplicacionXNivelDAO();
			UsuarioXAplicacionXNivel uxaxn = null;
			UsuarioDAO udao = new UsuarioDAO();
			Usuario u = udao.getById(idUsuario);
			UsuarioTipoDAO utdao = new UsuarioTipoDAO();
			UsuarioTipo ut = utdao.getById(idTipoUsuario);
			UsuarioSeleccionDAO usdao = new UsuarioSeleccionDAO();
			UsuarioSeleccion us = null;
			for (Integer idNivel : idNiveles) {
				uxaxn = uxaxndao.findByIdUsuarioANDIdAplicacionANDIdNivel(
						idUsuario, idAplicacion, idNivel);
				if (uxaxn == null) {
					uxaxn = new UsuarioXAplicacionXNivel();
					uxaxn.setUsuario(u);
					axn = axndao.findByIdAplicacionANDIdNivel(idAplicacion,
							idNivel);
					uxaxn.setAplicacionXNivel(axn);
					uxaxndao.save(uxaxn);
				}
				us = usdao.findBYIdUsuarioXAplicacionXNivel(uxaxn.getId());
				if (us == null) {
					us = new UsuarioSeleccion();
					us.setUsuarioXAplicacionXNivel(uxaxn);
				}
				us.setUsuarioTipo(ut);
				usdao.saveOrUpdate(us);
			}
			s.getTransaction().commit();
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);

		}
	}

	public static void initPermisos() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			s.beginTransaction();
			PermisoDAO pdao = new PermisoDAO();
			List<Permiso> ps = pdao.findAll();
			if (ps != null && !ps.isEmpty()) {
				AplicacionXUsuarioTipoDAO axutdao = new AplicacionXUsuarioTipoDAO();
				List<AplicacionXUsuarioTipo> axuts = axutdao.findAll();
				if (axuts == null || axuts.isEmpty()) {
					axuts = new ArrayList<AplicacionXUsuarioTipo>();
				}
				UsuarioTipoDAO utdao = new UsuarioTipoDAO();
				List<UsuarioTipo> uts = utdao.findAll();
				AplicacionDAO adao = new AplicacionDAO();
				List<Aplicacion> as = adao.findAll();
				AplicacionXUsuarioTipo axut = null;
				for (Aplicacion a : as) {
					for (UsuarioTipo ut : uts) {
						axut = axutdao.findByIdAplicacionANDIdUsuarioTipo(
								a.getId(), ut.getId());
						if (axut == null || axut.getId() == null) {
							axut = new AplicacionXUsuarioTipo();
							axut.setAplicacion(a);
							axut.setUsuarioTipo(ut);
							axutdao.save(axut);
							axuts.add(axut);
						}
					}
				}
				s.flush();
				AplicacionXUsuarioTipoXPermisoDAO axutxpdao = new AplicacionXUsuarioTipoXPermisoDAO();
				AplicacionXUsuarioTipoXPermiso axutxp = null;
				for (Permiso p : ps) {
					for (AplicacionXUsuarioTipo aplicacionXUsuarioTipo : axuts) {
						axutxp = new AplicacionXUsuarioTipoXPermiso();
						axutxp.setPermiso(p);
						axutxp.setAplicacionXUsuarioTipo(aplicacionXUsuarioTipo);
						axutxp.setAcceso(aplicacionXUsuarioTipo
								.getUsuarioTipo().getNombre()
								.equals(UsuarioTipo.ADMINISTRADOR));
						axutxpdao.save(axutxp);
					}
					s.flush();
				}
			}
			s.getTransaction().commit();
		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);

		}
	}

	public static void cargarAlumnosTic(String doc, String tipoEstablecimiento,
			Boolean actualizarActividad) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			String rbd = null;
			Curso c = null;
			CursoDAO cdao = new CursoDAO();
			AplicacionDAO aplicacionDAO = new AplicacionDAO();
			Aplicacion aplicacion = aplicacionDAO.getById(2);
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento establecimiento = null;
			AplicacionXEstablecimientoDAO axedao = new AplicacionXEstablecimientoDAO();
			AplicacionXEstablecimiento axe = null;
			EstablecimientoTipoDAO etdao = new EstablecimientoTipoDAO();
			EstablecimientoTipo et = etdao.findByNombre(tipoEstablecimiento);
			List<Curso> cs = null;
			List<Actividad> as = null;
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			Alumno alumno = null;
			AlumnoDAO alumnoDAO = new AlumnoDAO();
			ActividadEstadoDAO actividadEstadoDAO = new ActividadEstadoDAO();
			ActividadEstado actividadEstado = actividadEstadoDAO
					.findByNombre(ActividadEstado.SIN_INFORMACION);
			ContactoCargoDAO ccdao = new ContactoCargoDAO();
			ContactoCargo cc = ccdao.findByName(ContactoCargo.DIRECTOR);
			AlumnoEstadoDAO aedao = new AlumnoEstadoDAO();
			AlumnoEstado ae = aedao.findByNombre(AlumnoEstado.SIN_INFORMACION);
			AlumnoTipoDAO atdao = new AlumnoTipoDAO();
			AlumnoTipo titular = atdao.findByNombre(AlumnoTipo.TITULAR);
			AlumnoTipo reemplazo = atdao.findByNombre(AlumnoTipo.SUPLENTE);
			SexoDAO sdao = new SexoDAO();
			Sexo masculino = sdao.findByNombre(Sexo.MASCULINO);
			Sexo femenino = sdao.findByNombre(Sexo.FEMENINO);
			Calendar calendar = Calendar.getInstance();
			AlumnoXActividadDAO axadao = new AlumnoXActividadDAO();
			AlumnoXActividad axa = null;
			String[] fechaNacimiento = null;
			AplicacionXNivelDAO axndao = new AplicacionXNivelDAO();
			AplicacionXNivel axn = axndao.getById(6);
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					if (!row[0].equals(rbd)) {
						rbd = row[0];
						establecimiento = edao.getById(Integer.valueOf(rbd));
						if (establecimiento == null) {
							throw new NullPointerException(
									"No se encontró el establecimiento " + rbd);
						}
						axe = axedao.findByIdAplicacionANDIdEstablecimiento(2,
								establecimiento.getId());
						if (axe == null) {
							axe = new AplicacionXEstablecimiento();
							axe.setEstablecimiento(establecimiento);
							axe.setAplicacion(aplicacion);
						}
						axe.setEstablecimientoTipo(et);
						axedao.saveOrUpdate(axe);
						cs = establecimiento.getCursos();
						if (cs == null || cs.isEmpty()) {
							c = new Curso();
							c.setEstablecimiento(establecimiento);
							c.setAplicacionXNivel(axn);
							c.setNombre("2TIC");
							c.setCantidadAlumnos(Integer.valueOf(row[6]));
							cdao.save(c);
						} else {
							c = cdao.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
									2, 10, establecimiento.getId(), "2TIC");
						}
						as = c.getActividads();
						if (as == null || as.isEmpty()) {
							for (AplicacionXNivelXActividadTipo axnxat : axn
									.getAplicacionXNivelXActividadTipos()) {
								a = new Actividad();
								a.setCurso(c);
								a.setAplicacionXNivelXActividadTipo(axnxat);
								a.setActividadEstado(actividadEstado);
								a.setContactoNombre(establecimiento
										.getDirectorNombre());
								a.setContactoEmail(establecimiento.getEmail());
								a.setContactoTelefono(establecimiento
										.getTelefono());
								a.setContactoCargo(cc);
								a.setFechaInicio(axnxat.getFechaInicio());
								a.setFechaTermino(axnxat.getFechaTermino());
								a.setDia(1);
								actividadDAO.save(a);
								as.add(a);
							}
						} else {
							if (actualizarActividad) {
								for (Actividad actividad : as) {
									actividad.setTotalAlumnos(Integer
											.valueOf(row[6]));
									actividadDAO.update(actividad);
								}
							}
						}
					}
					alumno = new Alumno();
					alumno.setId(Integer.valueOf(row[7]));
					alumno.setRut(StringUtils.formatRut(row[7] + row[8]));
					alumno.setRutStripped(StringUtils.formatRut(row[7] + "-"
							+ row[8]));
					alumno.setNombres(row[9]);
					alumno.setApellidoPaterno(row[10]);
					alumno.setApellidoMaterno(row[11]);
					alumno.setSexo((row[12].equals("M")) ? masculino : femenino);
					fechaNacimiento = row[13].split("-");
					calendar.set(Integer.valueOf(fechaNacimiento[2]),
							Integer.valueOf(fechaNacimiento[1]) - 1,
							Integer.valueOf(fechaNacimiento[0]));
					alumno.setFechaNacimiento(calendar.getTime());
					alumnoDAO.save(alumno);
					for (Actividad actividad : as) {
						axa = new AlumnoXActividad();
						axa.setActividad(actividad);
						axa.setAlumno(alumno);
						axa.setAlumnoEstado(ae);
						axa.setAlumnoTipo((row[19].equals("T") ? titular
								: reemplazo));
						axadao.save(axa);
					}
				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter + " alumnos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void arreglarAlumnosTicRMyBiobio(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String line = null;
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			String rbd = null;
			Curso c = null;
			CursoDAO cdao = new CursoDAO();
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento establecimiento = null;
			List<Actividad> as = null;
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			Alumno alumno = null;
			AlumnoDAO alumnoDAO = new AlumnoDAO();
			AlumnoEstadoDAO aedao = new AlumnoEstadoDAO();
			AlumnoEstado ae = aedao.findByNombre(AlumnoEstado.SIN_INFORMACION);
			AlumnoTipoDAO atdao = new AlumnoTipoDAO();
			AlumnoTipo titular = atdao.findByNombre(AlumnoTipo.TITULAR);
			AlumnoTipo reemplazo = atdao.findByNombre(AlumnoTipo.SUPLENTE);
			AlumnoXActividadDAO axadao = new AlumnoXActividadDAO();
			AlumnoXActividad axa = null;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					if (!row[0].equals(rbd)) {
						rbd = row[0];
						establecimiento = edao.getById(Integer.valueOf(rbd));
						if (establecimiento == null) {
							throw new NullPointerException(
									"No se encontró el establecimiento " + rbd);
						}
						c = cdao.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
								2, 10, establecimiento.getId(), "2TIC");
						if (c == null) {
							throw new NullPointerException(
									"No se encontró el curso " + line);
						}
						as = c.getActividads();
						for (Actividad actividad : as) {
							System.err.println(actividad
									.getAplicacionXNivelXActividadTipo()
									.getAplicacionXNivel().getAplicacion()
									.getNombre());
							actividad.setTotalAlumnos(Integer.valueOf(row[6]));
							actividadDAO.update(actividad);
						}
					}

					alumno = alumnoDAO.getById(Integer.valueOf(row[7]));
					if (alumno == null) {
						throw new NullPointerException(
								"No se encontró el alumno " + line);
					}

					for (Actividad actividad : as) {
						axa = axadao.findByIdAlumnoANDActividad(alumno.getId(),
								actividad.getId());
						if (axa != null) {
							axadao.delete(axa);
							System.err.println("Existía");
						}
						axa = new AlumnoXActividad();
						axa.setActividad(actividad);
						axa.setAlumno(alumno);
						axa.setAlumnoEstado(ae);
						axa.setAlumnoTipo((row[19].equals("T") ? titular
								: reemplazo));
						axadao.save(axa);
					}
				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter + " alumnos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			System.err.println(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void cargarMateriales(String doc, Integer idAplicacion,
			Integer idNivel, String tipoActividad) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String line = "";
		try {
			Integer dia = (tipoActividad.equals(ActividadTipo.VISITA_PREVIA)) ? null
					: 1;
			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo at1 = atdao.finByNombre(tipoActividad);
			ActividadTipo at2 = atdao
					.finByNombre(ActividadTipo.APLICACION_DIA_2);
			ActividadTipo at = null;
			MaterialDAO mdao = new MaterialDAO();
			Material m = null;
			MaterialTipoDAO mtdao = new MaterialTipoDAO();
			MaterialTipo caja = mtdao.findByNombre(MaterialTipo.CAJA_CURSO_DIA);
			MaterialTipo complementario = mtdao
					.findByNombre(MaterialTipo.COMPLEMENTARIO);
			MaterialXActividadDAO mxadao = new MaterialXActividadDAO();
			MaterialXActividad mxa = null;
			MaterialXActividadId mxaid = null;
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento eCont = edao
					.getById(Establecimiento.CONTINGENCIA_ID);
			Establecimiento e = null;
			CursoDAO cdao = new CursoDAO();
			Curso cCont = null;
			if (eCont != null) {
				cCont = cdao
						.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
								idAplicacion, idNivel, eCont.getId(), "CONT");
			}
			Curso curso = null;
			Co co = null;
			CoDAO codao = new CoDAO();
			AplicacionXNivelDAO axndao = new AplicacionXNivelDAO();
			AplicacionXNivel axn = axndao.findByIdAplicacionANDIdNivel(
					idAplicacion, idNivel);
			ActividadEstadoDAO actividadEstadoDAO = new ActividadEstadoDAO();
			ActividadEstado actividadEstado = actividadEstadoDAO
					.findByNombre(ActividadEstado.SIN_INFORMACION);
			ContactoCargoDAO ccdao = new ContactoCargoDAO();
			ContactoCargo cc = ccdao.findByName(ContactoCargo.DIRECTOR);
			int totalCursosNuevos = 0;
			int totalMaterialesNuevos = 0;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					a = null;
					if (dia != null) {
						dia = (row[0].matches("[0-9]{3}1[0-9]{5}")) ? 1 : 2;
					}
					at = (dia != null && dia == 2) ? at2 : at1;
					if (row[2] != null && !row[2].equals("")) {
						a = actividadDAO
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
										idAplicacion,
										idNivel,
										at.getId(),
										Integer.valueOf(row[2]),
										row[3].replaceAll(" ", "")
												+ row[4].replaceAll(" ", ""),
										dia);
					}
					if (a == null) {
						if ((row[1].toUpperCase().matches(".*CONTINGENCIA.*"))) {
							if (eCont == null) {
								eCont = new Establecimiento();
								eCont.setId(Establecimiento.CONTINGENCIA_ID);
								eCont.setNombre("CONTINGENCIA");
								edao.save(eCont);
							}
							if (cCont == null) {
								cCont = new Curso();
								cCont.setAplicacionXNivel(axn);
								cCont.setNombre("CONT");
								cCont.setEstablecimiento(eCont);
								cdao.save(cCont);

								for (AplicacionXNivelXActividadTipo axnxat : axn
										.getAplicacionXNivelXActividadTipos()) {
									a = new Actividad();
									a.setCurso(cCont);
									a.setAplicacionXNivelXActividadTipo(axnxat);
									a.setActividadEstado(actividadEstado);
									a.setFechaInicio(axnxat.getFechaInicio());
									a.setFechaTermino(axnxat.getFechaTermino());
									a.setDia(dia);
									actividadDAO.save(a);
								}

							}
							a = actividadDAO
									.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
											idAplicacion, idNivel, at.getId(),
											eCont.getId(), cCont.getNombre(),
											dia);
						} else {
							// throw new NullPointerException("El curso " +
							// row[5]
							// + row[6] + " del " + row[3]
							// + " no tiene actividad");
							System.err.println("El curso " + row[3] + row[4]
									+ " del " + row[2] + " no tiene actividad "
									+ line);

							e = edao.getById(Integer.valueOf(row[2]));
							if (e == null) {
								System.err
										.println("No existe el establecimiento de rbd "
												+ row[2] + " " + line);
								continue;
								// throw new NullPointerException(
								// "No existe el establecimiento de rbd "
								// + row[2] + " " + line);
							}
							curso = cdao
									.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
											idAplicacion,
											idNivel,
											e.getId(),
											row[3].replaceAll(" ", "")
													+ row[4].replaceAll(" ", ""));
							if (curso == null) {
								curso = new Curso();
								curso.setAplicacionXNivel(axn);
								curso.setNombre(row[3].replaceAll(" ", "")
										+ row[4].replaceAll(" ", ""));
								curso.setEstablecimiento(e);
								cdao.save(curso);
								totalCursosNuevos++;
							}
							if (curso.getActividads() == null
									|| curso.getActividads().isEmpty()) {
								for (AplicacionXNivelXActividadTipo axnxat : axn
										.getAplicacionXNivelXActividadTipos()) {
									a = new Actividad();
									a.setCurso(curso);
									a.setAplicacionXNivelXActividadTipo(axnxat);
									a.setActividadEstado(actividadEstado);
									a.setContactoNombre(e.getDirectorNombre());
									a.setContactoEmail(e.getEmail());
									a.setContactoTelefono(e.getTelefono());
									a.setContactoCargo(cc);
									a.setFechaInicio(axnxat.getFechaInicio());
									a.setFechaTermino(axnxat.getFechaTermino());
									a.setDia(dia);
									actividadDAO.save(a);
								}
							}
							a = actividadDAO
									.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
											idAplicacion, idNivel, at.getId(),
											e.getId(), curso.getNombre(), dia);
						}
					}
					m = new Material();
					m.setCodigo(row[0]);
					m.setMaterialTipo((at.getNombre()
							.equals(ActividadTipo.VISITA_PREVIA)) ? complementario
							: caja);
					m.setContingencia((row[1].toUpperCase()
							.matches(".*CONTINGENCIA.*")));
					// co = codao
					// .findByIdAplicacionANDIdNivelANDIdEstablecimiento(
					// idAplicacion, idNivel,
					// Integer.valueOf(row[3]));
					if (m.getContingencia()) {
						// co = codao
						// .findByIdAplicacionANDIdNivelANDComunaANDRegion(
						// idAplicacion, idNivel, row[4], row[5]);
					} else {
						co = codao
								.findByIdAplicacionANDIdNivelANDIdEstablecimiento(
										idAplicacion, idNivel,
										Integer.valueOf(row[2]));
					}
					m.setCo(co);
					mdao.save(m);
					mxaid = new MaterialXActividadId();
					mxaid.setActividadId(a.getId());
					mxaid.setMaterialId(m.getId());
					mxa = new MaterialXActividad();
					mxa.setId(mxaid);
					mxadao.save(mxa);
					totalMaterialesNuevos++;
				}
				rowCounter++;
			}
			System.err.println("Total cursos nuevos " + totalCursosNuevos);
			System.out.println("Ingresados " + totalMaterialesNuevos
					+ " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			System.err.println(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollback(s);
			System.err.println(line);
		}
	}

	/**
	 * Serie Material tipo de material Id Curso Rbd Comuna Establecimiento
	 * Region Establecimiento
	 * 
	 * @param doc
	 * @param idAplicacion
	 * @param idNivel
	 * @param tipoActividad
	 */
	public static void cargarMaterialesUsandoCodigos(String doc,
			Integer idAplicacion, Integer idNivel, String tipoActividad) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String line = "";
		try {
			Integer dia = (tipoActividad.equals(ActividadTipo.VISITA_PREVIA)) ? null
					: 1;
			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo at1 = atdao.finByNombre(tipoActividad);
			ActividadTipo at2 = atdao
					.finByNombre(ActividadTipo.APLICACION_DIA_2);
			ActividadTipo at = null;
			MaterialDAO mdao = new MaterialDAO();
			Material m = null;
			MaterialTipoDAO mtdao = new MaterialTipoDAO();
			MaterialTipo caja = mtdao.findByNombre(MaterialTipo.CAJA_CURSO_DIA);
			MaterialTipo complementario = mtdao
					.findByNombre(MaterialTipo.COMPLEMENTARIO);
			MaterialXActividadDAO mxadao = new MaterialXActividadDAO();
			MaterialXActividad mxa = null;
			MaterialXActividadId mxaid = null;
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento eCont = edao
					.getById(Establecimiento.CONTINGENCIA_ID);
			Establecimiento e = null;
			CursoDAO cdao = new CursoDAO();
			Curso cCont = null;
			if (eCont != null) {
				cCont = cdao
						.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
								idAplicacion, idNivel, eCont.getId(), "CONT");
			}
			Curso curso = null;
			Co co = null;
			CoDAO codao = new CoDAO();
			AplicacionXNivelDAO axndao = new AplicacionXNivelDAO();
			AplicacionXNivel axn = axndao.findByIdAplicacionANDIdNivel(
					idAplicacion, idNivel);
			ActividadEstadoDAO actividadEstadoDAO = new ActividadEstadoDAO();
			ActividadEstado actividadEstado = actividadEstadoDAO
					.findByNombre(ActividadEstado.SIN_INFORMACION);
			ContactoCargoDAO ccdao = new ContactoCargoDAO();
			ContactoCargo cc = ccdao.findByName(ContactoCargo.DIRECTOR);
			Integer materialNuevo = 0;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					a = null;
					if (dia != null) {
						dia = (row[0].matches("[0-9]{3}1[0-9]{5}")) ? 1 : 2;
					}
					at = (dia != null && dia == 2) ? at2 : at1;
					if (row[2] != null && !row[2].equals("")) {
						a = actividadDAO
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDCodigoCursoANDDia(
										idAplicacion, idNivel, at.getId(),
										Integer.valueOf(row[3]),
										row[2].replaceAll(" ", ""), dia);
					}
					if (a == null) {
						if ((row[1].toUpperCase().matches(".*CONTINGENCIA.*"))) {
							if (eCont == null) {
								eCont = new Establecimiento();
								eCont.setId(Establecimiento.CONTINGENCIA_ID);
								eCont.setNombre("CONTINGENCIA");
								edao.save(eCont);
							}
							if (cCont == null) {
								cCont = new Curso();
								cCont.setAplicacionXNivel(axn);
								cCont.setNombre("CONT");
								cCont.setEstablecimiento(eCont);
								cdao.save(cCont);
								Integer d = null;

								for (AplicacionXNivelXActividadTipo axnxat : axn
										.getAplicacionXNivelXActividadTipos()) {
									a = new Actividad();
									a.setCurso(cCont);
									a.setAplicacionXNivelXActividadTipo(axnxat);
									a.setActividadEstado(actividadEstado);
									a.setFechaInicio(axnxat.getFechaInicio());
									a.setFechaTermino(axnxat.getFechaTermino());
									String actT = axnxat.getActividadTipo()
											.getNombre();
									d = (ActividadTipo.APLICACION_DIA_2
											.equals(actT)) ? 2 : null;
									if (d == null) {
										d = (ActividadTipo.APLICACION_DIA_1
												.equals(actT)) ? 1 : null;
									}
									a.setDia(d);
									actividadDAO.save(a);
								}

							}
							a = actividadDAO
									.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
											idAplicacion, idNivel, at.getId(),
											eCont.getId(), cCont.getNombre(),
											dia);
						} else {
							// throw new NullPointerException("El curso " +
							// row[5]
							// + row[6] + " del " + row[3]
							// + " no tiene actividad");
							System.err.println("El curso " + row[2] + " del "
									+ row[3] + " no tiene actividad " + line);

							e = edao.getById(Integer.valueOf(row[3]));
							if (e == null) {
								throw new NullPointerException(
										"No existe el establecimiento de rbd "
												+ row[3] + " " + line);
							}
							curso = cdao
									.findByIdAplicacionANDIdNivelANDIdEstablecimientoANDCodigo(
											idAplicacion, idNivel, e.getId(),
											row[2].replaceAll(" ", ""));
							if (curso == null) {
								// throw new NullPointerException(
								// "No existe el curso id " + row[2] + " "
								// + line);
								System.err.println("No existe el curso id "
										+ row[2] + " " + line);
								continue;
							}
							a = actividadDAO
									.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
											idAplicacion, idNivel, at.getId(),
											e.getId(), curso.getNombre(), dia);
						}
					}
					m = new Material();
					m.setCodigo(row[0].replaceAll(" ", ""));
					m.setMaterialTipo((at.getNombre()
							.equals(ActividadTipo.VISITA_PREVIA)) ? complementario
							: caja);
					m.setContingencia((row[1].toUpperCase()
							.matches(".*CONTINGENCIA.*")));
					// co = codao
					// .findByIdAplicacionANDIdNivelANDIdEstablecimiento(
					// idAplicacion, idNivel,
					// Integer.valueOf(row[3]));
					if (m.getContingencia()) {
						co = codao
								.findByIdAplicacionANDIdNivelANDComunaANDRegion(
										idAplicacion, idNivel, row[4], row[5]);
					} else {
						co = codao
								.findByIdAplicacionANDIdNivelANDIdEstablecimiento(
										idAplicacion, idNivel,
										Integer.valueOf(row[3]));
					}
					if (co == null) {
						System.err.println(line + " no se encontró centro");
					}
					m.setCo(co);
					mdao.save(m);
					mxaid = new MaterialXActividadId();
					mxaid.setActividadId(a.getId());
					mxaid.setMaterialId(m.getId());
					mxa = new MaterialXActividad();
					mxa.setId(mxaid);
					mxadao.save(mxa);
					materialNuevo++;
				}
				rowCounter++;
				if (rowCounter % 50 == 0) {
					s.flush();
				}
			}
			System.out.println("Ingresados " + materialNuevo + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
			System.err.println(line);
			e.printStackTrace();
		}
	}

	public static void actualizarAgendamientoTIC() {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream("Agendamiento_1_Todo.csv");
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo visitaPrevia = atdao
					.finByNombre(ActividadTipo.VISITA_PREVIA);
			ActividadTipo aplicacionD1 = atdao
					.finByNombre(ActividadTipo.APLICACION_DIA_1);
			ActividadEstadoDAO aedao = new ActividadEstadoDAO();
			ActividadEstado ae = aedao
					.findByNombre(ActividadEstado.POR_CONFIRMAR);
			Calendar calendar = Calendar.getInstance();
			String[] fecha = null;
			ActividadHistorial ah = null;
			ActividadHistorialId ahid = null;
			ActividadHistorialDAO ahdao = new ActividadHistorialDAO();
			Date fechaMod = new Date();
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					a = actividadDAO
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
									2, 10, visitaPrevia.getId(),
									Integer.valueOf(row[0]));
					if (a == null) {
						throw new NullPointerException(
								"No se encontró visita previa para " + row[0]);
					}
					fecha = row[32].split("-");
					calendar.set(Integer.valueOf(fecha[0]),
							Integer.valueOf(fecha[1]) - 1,
							Integer.valueOf(fecha[2]), 10, 0, 0);
					a.setFechaInicio(calendar.getTime());
					a.setActividadEstado(ae);
					actividadDAO.update(a);
					ahid = new ActividadHistorialId();
					ahid.setActividadId(a.getId());
					ahid.setFecha(fechaMod);
					ah = new ActividadHistorial();
					ah.setId(ahid);
					ah.setActividadEstado(a.getActividadEstado());
					ah.setFechaInicio(a.getFechaInicio());
					ah.setFechaTermino(a.getFechaTermino());
					ahdao.save(ah);
					a = actividadDAO
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
									2, 10, aplicacionD1.getId(),
									Integer.valueOf(row[0]));
					if (a == null) {
						throw new NullPointerException(
								"No se encontró aplicación para " + row[0]);
					}
					fecha = row[30].split("-");
					calendar.set(Integer.valueOf(fecha[0]),
							Integer.valueOf(fecha[1]) - 1,
							Integer.valueOf(fecha[2]), 10, 0, 0);
					a.setFechaInicio(calendar.getTime());
					a.setActividadEstado(ae);
					actividadDAO.update(a);
					ahid = new ActividadHistorialId();
					ahid.setActividadId(a.getId());
					ahid.setFecha(fechaMod);
					ah = new ActividadHistorial();
					ah.setId(ahid);
					ah.setActividadEstado(a.getActividadEstado());
					ah.setFechaInicio(a.getFechaInicio());
					ah.setFechaTermino(a.getFechaTermino());
					ahdao.save(ah);
				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void actualizarAgendamientoSimce(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String line = null;
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo visitaPrevia = atdao
					.finByNombre(ActividadTipo.VISITA_PREVIA);
			ActividadEstadoDAO aedao = new ActividadEstadoDAO();
			ActividadEstado ae = aedao
					.findByNombre(ActividadEstado.POR_CONFIRMAR);
			Calendar calendar = Calendar.getInstance();
			String[] fecha = null;
			String[] hora = null;
			ActividadHistorial ah = null;
			ActividadHistorialId ahid = null;
			ActividadHistorialDAO ahdao = new ActividadHistorialDAO();
			Date fechaMod = new Date();
			String[] cursos = null;
			Integer nivel = null;
			CursoDAO cdao = new CursoDAO();
			List<Curso> cs = null;
			Integer rbd = null;
			Boolean todosLosCursos = false;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					rbd = Integer.valueOf(row[1]);
					nivel = Integer.valueOf(row[2]);
					if (row.length > 3 && row[3] != null && !row[3].isEmpty()) {
						cursos = row[3].replaceAll(" ", "").split("-");
						todosLosCursos = false;
					} else {
						todosLosCursos = true;
						cs = cdao
								.findByIdAplicacionANDIdNivelANIdEstablecimiento(
										1, nivel, rbd);
						if (cs != null && !cs.isEmpty()) {
							cursos = new String[cs.size()];
							for (int i = 0; i < cs.size(); i++) {
								cursos[i] = cs.get(i).getNombre();
							}
						} else {
							cursos = new String[0];
						}
					}
					for (String curso : cursos) {
						a = actividadDAO
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
										1,
										nivel,
										visitaPrevia.getId(),
										rbd,
										(todosLosCursos) ? curso
												: (nivel + curso.toUpperCase()),
										null);
						if (a == null) {
							System.err
									.println("No se encontró visita previa para "
											+ line);
						} else {
							if (row.length > 4 && row[4] != null
									&& !row[4].isEmpty()) {
								fecha = row[4].split("-");
								if (row.length < 6 || row[5] == null
										|| row[5].replaceAll(" ", "").isEmpty()) {
									hora = new String[2];
									hora[0] = "10";
									hora[1] = "0";
								} else {
									hora = row[5].split(":");
								}
								calendar.set(Integer.valueOf(fecha[2]),
										Integer.valueOf(fecha[1]) - 1,
										Integer.valueOf(fecha[0]),
										Integer.valueOf(hora[0]),
										Integer.valueOf(hora[1]), 0);
								a.setFechaInicio(calendar.getTime());
								a.setActividadEstado(ae);
								actividadDAO.update(a);
								ahid = new ActividadHistorialId();
								ahid.setActividadId(a.getId());
								ahid.setFecha(fechaMod);
								ah = new ActividadHistorial();
								ah.setId(ahid);
								ah.setActividadEstado(a.getActividadEstado());
								ah.setFechaInicio(a.getFechaInicio());
								ah.setFechaTermino(a.getFechaTermino());
								ahdao.save(ah);
							}
						}
					}
				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
			System.err.println(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
			System.err.println(line);
		}
	}

	public static void loadLaWeaDeCapacitacion() {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream("Muestra_TSES-61_2013.csv");
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			File file = File.createTempFile(
					StringUtils.getDatePathSafe("TSES"), ".csv", new File(
							"/tmp"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "ISO-8859-1"));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			UsuarioDAO udao = new UsuarioDAO();
			Usuario u = null;
			String lineaOut = "RUN;PATERNO;MATERNO;NOMBRES;SEXO;EDAD;REGION;CARRERA;PROFESION";
			for (int i = 1; i < 62; i++) {
				lineaOut += ";P" + StringUtils.forceTwoDigits(i);
			}
			// System.out.println(lineaOut);
			bw.write(lineaOut + "\r");

			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					u = udao.getById(Integer.valueOf(row[2]));
					if (u == null) {
						// System.out.println("No se encontró el examinador "
						// + row[2]);
					} else {
						lineaOut = row[2] + ";" + u.getApellidoPaterno() + ";"
								+ u.getApellidoMaterno() + ";" + u.getNombres()
								+ ";";

						if (u.getSexo() != null) {
							lineaOut += (u.getSexo().getNombre()
									.equals(Sexo.MASCULINO) ? "H" : "M");
						}
						lineaOut += ";"
								+ edad(u.getFechaNacimiento())
								+ ";"
								+ u.getComuna().getProvincia().getRegion()
										.getId();
						if (u.getCarreraEstado().getNombre()
								.equals(CarreraEstado.TITULADO)) {
							lineaOut += ";;" + u.getCarrera().getNombre();
						} else {
							lineaOut += ";" + u.getCarrera().getNombre() + ";";
						}
						for (int i = 0; i < 61; i++) {
							lineaOut += ";" + row[4 + i];
						}
						// System.out.println(lineaOut);
						bw.write(lineaOut + "\r");
					}
				}
				rowCounter++;
			}
			bw.close();
			br.close();

			System.out.println("Ingresados " + rowCounter + " alumnos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static Integer edad(Date dateOfBirth) {
		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOfBirth);
		Calendar today = Calendar.getInstance();
		Integer age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
			age--;
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				&& today.get(Calendar.DAY_OF_MONTH) < dob
						.get(Calendar.DAY_OF_MONTH)) {
			age--;
		}
		return age;
	}

	public static void asignarEstablecimientoTipo(String doc,
			String tipoEstablecimiento) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			String rbd = null;
			AplicacionDAO aplicacionDAO = new AplicacionDAO();
			Aplicacion aplicacion = aplicacionDAO.getById(2);
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento establecimiento = null;
			AplicacionXEstablecimientoDAO axedao = new AplicacionXEstablecimientoDAO();
			AplicacionXEstablecimiento axe = null;
			EstablecimientoTipoDAO etdao = new EstablecimientoTipoDAO();
			EstablecimientoTipo et = etdao.findByNombre(tipoEstablecimiento);

			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					if (!row[0].equals(rbd)) {
						rbd = row[0];
						establecimiento = edao.getById(Integer.valueOf(rbd));
						if (establecimiento == null) {
							throw new NullPointerException(
									"No se encontró el establecimiento " + rbd);
						}
						axe = axedao.findByIdAplicacionANDIdEstablecimiento(2,
								establecimiento.getId());
						if (axe == null) {
							axe = new AplicacionXEstablecimiento();
							axe.setEstablecimiento(establecimiento);
							axe.setAplicacion(aplicacion);
						}
						axe.setEstablecimientoTipo(et);
						axedao.saveOrUpdate(axe);
					}

				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter
					+ " establecimientos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void cargarEmailDirector(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			String rbd = null;
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento establecimiento = null;

			ActividadDAO adao = new ActividadDAO();
			List<Curso> cs = null;
			List<Actividad> as = null;

			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					if (!row[0].equals(rbd)) {
						rbd = row[0];
						establecimiento = edao.getById(Integer.valueOf(rbd));
						if (establecimiento == null) {
							throw new NullPointerException(
									"No se encontró el establecimiento " + rbd);
						}
						establecimiento.setEmail(row[15]);
						edao.update(establecimiento);
						cs = establecimiento.getCursos();
						if (cs != null && !cs.isEmpty()) {
							for (Curso curso : cs) {
								as = curso.getActividads();
								if (as != null && !as.isEmpty()) {
									for (Actividad actividad : as) {
										actividad.setContactoEmail(row[15]);
										adao.update(actividad);
									}
								}
							}
						}
					}

				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter
					+ " establecimientos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void elimiarRepetidos() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			s.beginTransaction();
			Integer total = 0;
			MaterialDAO mdao = new MaterialDAO();
			Map<String, List<Integer>> repetidos = mdao.findRepetidos();
			List<Integer> ids = null;
			if (repetidos != null && !repetidos.isEmpty()) {
				System.err.println("total de repetidos: " + repetidos.size());
				for (String codigo : repetidos.keySet()) {
					ids = repetidos.get(codigo);
					if (ids != null && !ids.isEmpty() && ids.size() == 2) {
						mdao.deleteById(ids.get(1));
						total++;
					}
				}
			}

			s.getTransaction().commit();

			System.err.println("Se borraron " + total);

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void cambiarDeCentroMalCargado() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			s.beginTransaction();
			Integer total = 0;
			MaterialDAO mdao = new MaterialDAO();
			Material m = null;
			Map<Integer, Integer> cambiados = mdao.findEnCentroEquivocado();
			CoDAO cdao = new CoDAO();
			Co co = null;
			Integer prevCo = null;
			if (cambiados != null && !cambiados.isEmpty()) {
				System.err.println("total cambiados: " + cambiados.size());
				for (Integer idMat : cambiados.keySet()) {
					m = mdao.getById(idMat);
					if (m != null) {
						if (!cambiados.get(idMat).equals(prevCo)) {
							prevCo = cambiados.get(idMat);
							co = cdao.getById(prevCo);
						}
						if (co != null) {
							m.setCo(co);
							mdao.update(m);
							total++;
						}
					}
				}
			}

			s.getTransaction().commit();

			System.err.println("Se cambiaron " + total);

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void actualizarAgendamientoTICConfirmando(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo visitaPrevia = atdao
					.finByNombre(ActividadTipo.VISITA_PREVIA);
			ActividadTipo aplicacionD1 = atdao
					.finByNombre(ActividadTipo.APLICACION_DIA_1);
			ActividadEstadoDAO aedao = new ActividadEstadoDAO();
			ActividadEstado confirmado = aedao
					.findByNombre(ActividadEstado.CONFIRMADO);
			ActividadEstado confirmadoConCambios = aedao
					.findByNombre(ActividadEstado.CONFIRMADO_CON_CAMBIOS);
			ActividadEstado porConfirmar = aedao
					.findByNombre(ActividadEstado.POR_CONFIRMAR);
			Calendar calendar = Calendar.getInstance();
			String[] fecha = null;
			ActividadHistorial ah = null;
			ActividadHistorialId ahid = null;
			ActividadHistorialDAO ahdao = new ActividadHistorialDAO();
			Date fechaMod = new Date();
			String[] time;
			String[] date;
			String[] dateParts;
			int yearHist;
			int monthHist;
			int dayHist;
			int hourHist;
			int minHist;
			int yearNew;
			int monthNew;
			int dayNew;
			int hourNew;
			int minNew;
			Date fechaOriginal;
			Boolean actualizado = false;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					a = actividadDAO
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
									2, 10, visitaPrevia.getId(),
									Integer.valueOf(row[0]));
					if (a == null) {
						throw new NullPointerException(
								"No se encontró visita previa para " + row[0]);
					}
					if (a.getActividadEstado().equals(porConfirmar)) {
						actualizado = false;
						fechaOriginal = a.getFechaInicio();
						calendar.setTime(a.getFechaInicio());
						yearHist = calendar.get(Calendar.YEAR);
						monthHist = calendar.get(Calendar.MONTH);
						dayHist = calendar.get(Calendar.DAY_OF_MONTH);
						hourHist = calendar.get(Calendar.HOUR_OF_DAY);
						minHist = calendar.get(Calendar.MINUTE);
						dateParts = row[1].replaceAll("\\.[0-9]{1,3}", "")
								.split(" ");
						date = dateParts[0].split("-");
						time = dateParts[1].split(":");
						calendar.set(Integer.valueOf(date[0]),
								Integer.valueOf(date[1]) - 1,
								Integer.valueOf(date[2]),
								Integer.valueOf(time[0]),
								Integer.valueOf(time[1]),
								Integer.valueOf(time[2]));
						yearNew = calendar.get(Calendar.YEAR);
						monthNew = calendar.get(Calendar.MONTH);
						dayNew = calendar.get(Calendar.DAY_OF_MONTH);
						hourNew = calendar.get(Calendar.HOUR_OF_DAY);
						minNew = calendar.get(Calendar.MINUTE);
						a.setFechaInicio(calendar.getTime());
						if (row[2].equals("1")) {
							actualizado = true;
							if (yearHist != yearNew || monthHist != monthNew
									|| dayHist != dayNew || hourHist != hourNew
									|| minHist != minNew) {
								a.setActividadEstado(confirmadoConCambios);
								System.err.println(line
										+ " vp confirmado con cambios "
										+ fechaOriginal);
							} else {
								a.setActividadEstado(confirmado);
								System.err.println(line + " confirmado");
							}
						} else {
							if (yearHist != yearNew || monthHist != monthNew
									|| dayHist != dayNew || hourHist != hourNew
									|| minHist != minNew) {
								actualizado = true;
							}
						}
						if (row.length > 5 && !row[5].equals("0")
								&& !row[5].equals("")) {
							a.setComentario(row[5]);
							actualizado = true;
						}
						if (actualizado) {
							actividadDAO.update(a);
							ahid = new ActividadHistorialId();
							ahid.setActividadId(a.getId());
							ahid.setFecha(fechaMod);
							ah = new ActividadHistorial();
							ah.setId(ahid);
							ah.setActividadEstado(a.getActividadEstado());
							ah.setFechaInicio(a.getFechaInicio());
							ah.setFechaTermino(a.getFechaTermino());
							ahdao.save(ah);
						}
					}
					a = actividadDAO
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
									2, 10, aplicacionD1.getId(),
									Integer.valueOf(row[0]));
					if (a == null) {
						throw new NullPointerException(
								"No se encontró aplicación para " + row[0]);
					}
					if (a.getActividadEstado().equals(porConfirmar)) {
						actualizado = false;
						fechaOriginal = a.getFechaInicio();
						calendar.setTime(a.getFechaInicio());
						yearHist = calendar.get(Calendar.YEAR);
						monthHist = calendar.get(Calendar.MONTH);
						dayHist = calendar.get(Calendar.DAY_OF_MONTH);
						hourHist = calendar.get(Calendar.HOUR_OF_DAY);
						minHist = calendar.get(Calendar.MINUTE);
						dateParts = row[3].replaceAll("\\.[0-9]{1,3}", "")
								.split(" ");
						date = dateParts[0].split("-");
						time = dateParts[1].split(":");
						calendar.set(Integer.valueOf(date[0]),
								Integer.valueOf(date[1]) - 1,
								Integer.valueOf(date[2]),
								Integer.valueOf(time[0]),
								Integer.valueOf(time[1]),
								Integer.valueOf(time[2]));
						yearNew = calendar.get(Calendar.YEAR);
						monthNew = calendar.get(Calendar.MONTH);
						dayNew = calendar.get(Calendar.DAY_OF_MONTH);
						hourNew = calendar.get(Calendar.HOUR_OF_DAY);
						minNew = calendar.get(Calendar.MINUTE);
						a.setFechaInicio(calendar.getTime());
						if (row[4].equals("1")) {
							actualizado = true;
							if (yearHist != yearNew || monthHist != monthNew
									|| dayHist != dayNew || hourHist != hourNew
									|| minHist != minNew) {
								a.setActividadEstado(confirmadoConCambios);
								System.err.println(line
										+ " a confirmado con cambios "
										+ fechaOriginal);
							} else {
								a.setActividadEstado(confirmado);
								System.err.println(line + " confirmado");
							}
						} else {
							if (yearHist != yearNew || monthHist != monthNew
									|| dayHist != dayNew || hourHist != hourNew
									|| minHist != minNew) {
								actualizado = true;
							}
						}
						if (row.length > 5 && !row[5].equals("0")
								&& !row[5].equals("")) {
							a.setComentario(row[5]);
							actualizado = true;
						}
						if (actualizado) {
							actividadDAO.update(a);
							ahid = new ActividadHistorialId();
							ahid.setActividadId(a.getId());
							ahid.setFecha(fechaMod);
							ah = new ActividadHistorial();
							ah.setId(ahid);
							ah.setActividadEstado(a.getActividadEstado());
							ah.setFechaInicio(a.getFechaInicio());
							ah.setFechaTermino(a.getFechaTermino());
							ahdao.save(ah);
						}
					}
				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void moverCajasADia2() {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		int rowCounter = 0;
		try {
			s.beginTransaction();
			MaterialDAO mdao = new MaterialDAO();
			List<Material> ms = mdao.findByDia(2);
			MaterialXActividadDAO mxadao = new MaterialXActividadDAO();
			MaterialXActividad mxa = null;
			MaterialXActividadId mxaid = null;
			List<MaterialXActividad> mxas = null;
			Actividad a1 = null;
			Actividad a2 = null;
			ActividadDAO adao = new ActividadDAO();

			if (ms != null && !ms.isEmpty()) {
				for (Material material : ms) {
					mxas = mxadao.findByIdMaterial(material.getId());
					if (mxas != null && !mxas.isEmpty()) {
						if (mxas.size() > 1) {
							throw new NullPointerException("El material "
									+ material.getCodigo()
									+ " está en más de una actividad");
						}
						a1 = adao.getById(mxas.get(0).getId().getActividadId());
						if (a1 == null) {
							throw new NullPointerException("El material "
									+ material.getCodigo()
									+ " está en una actividad que no existe");
						}
						a2 = adao
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
										1, 4, 3, a1.getCurso().getId());
						if (a2 == null || a2.getDia() != 2) {
							throw new NullPointerException(
									"Para el material "
											+ material.getCodigo()
											+ " no se encontró una actividad del día 2, curso "
											+ a1.getCurso().getId());
						}
						mxadao.delete(mxas.get(0));
						mxaid = new MaterialXActividadId();
						mxaid.setActividadId(a2.getId());
						mxaid.setMaterialId(material.getId());
						mxa = new MaterialXActividad();
						mxa.setId(mxaid);
						mxadao.save(mxa);
						rowCounter++;
					} else {
						throw new NullPointerException("El material "
								+ material.getCodigo()
								+ " no está en una actividad");
					}
				}
			}

			System.out.println("Movidos " + rowCounter + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void moverCajasADia2(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		String line = "";
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String[] row;
		try {
			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;

			s.beginTransaction();
			MaterialDAO mdao = new MaterialDAO();
			List<Material> ms = null;
			MaterialXActividadDAO mxadao = new MaterialXActividadDAO();
			MaterialXActividad mxa = null;
			MaterialXActividadId mxaid = null;
			List<MaterialXActividad> mxas = null;
			Actividad a1 = null;
			Actividad a2 = null;
			ActividadDAO adao = new ActividadDAO();
			Integer materialesMovidos = 0;
			List<String> codigos = new ArrayList<String>();
			Integer idAplicacion = 1;
			Integer idNivel = 4;
			Integer idActividadTipo = 2;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					codigos.add(row[0]);
				}
				rowCounter++;
			}
			ms = mdao.findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
					idAplicacion, idNivel, idActividadTipo, codigos);
			if (ms != null && !ms.isEmpty()) {
				for (Material material : ms) {
					mxas = mxadao.findByIdMaterial(material.getId());
					if (mxas != null && !mxas.isEmpty()) {
						if (mxas.size() > 1) {
							throw new NullPointerException("El material "
									+ material.getCodigo()
									+ " está en más de una actividad");
						}
						a1 = adao.getById(mxas.get(0).getId().getActividadId());
						if (a1 == null) {
							throw new NullPointerException("El material "
									+ material.getCodigo()
									+ " está en una actividad que no existe");
						}
						a2 = adao
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
										1, 4, 3, a1.getCurso().getId());
						if (a2 == null || a2.getDia() != 2) {
							throw new NullPointerException(
									"Para el material "
											+ material.getCodigo()
											+ " no se encontró una actividad del día 2, curso "
											+ a1.getCurso().getId());
						}
						mxadao.delete(mxas.get(0));
						mxaid = new MaterialXActividadId();
						mxaid.setActividadId(a2.getId());
						mxaid.setMaterialId(material.getId());
						mxa = new MaterialXActividad();
						mxa.setId(mxaid);
						mxadao.save(mxa);
						if (material.getLotes() != null
								&& !material.getLotes().isEmpty()) {
							for (Lote lote : material.getLotes()) {
								System.err.println(material.getCodigo()
										+ " en lote " + lote.getNombre()
										+ " centro "
										+ material.getCo().getNombre());
							}

						}
						materialesMovidos++;
					} else {
						throw new NullPointerException("El material "
								+ material.getCodigo()
								+ " no está en una actividad");
					}
				}
			}

			System.out.println("Movidos " + materialesMovidos + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		}
	}

	public static void moverCajasAOtroDia(String codigo, Integer idNivel,
			Integer idTipoOrigen, Integer idTipoDestino) {
		DataInputStream in;
		FileInputStream fstream;
		String line = "";
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		try {

			s.beginTransaction();
			MaterialDAO mdao = new MaterialDAO();
			List<Material> ms = null;
			MaterialXActividadDAO mxadao = new MaterialXActividadDAO();
			MaterialXActividad mxa = null;
			MaterialXActividadId mxaid = null;
			List<MaterialXActividad> mxas = null;
			Actividad a1 = null;
			Actividad a2 = null;
			ActividadDAO adao = new ActividadDAO();
			Integer materialesMovidos = 0;
			List<String> codigos = new ArrayList<String>();
			Integer idAplicacion = 1;
			codigos.add(codigo);
			ms = mdao.findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigos(
					idAplicacion, idNivel, idTipoOrigen, codigos);

			if (ms != null && !ms.isEmpty()) {
				for (Material material : ms) {
					mxas = mxadao.findByIdMaterial(material.getId());
					if (mxas != null && !mxas.isEmpty()) {
						if (mxas.size() > 1) {
							throw new NullPointerException("El material "
									+ material.getCodigo()
									+ " está en más de una actividad");
						}
						a1 = adao.getById(mxas.get(0).getId().getActividadId());
						if (a1 == null) {
							throw new NullPointerException("El material "
									+ material.getCodigo()
									+ " está en una actividad que no existe");
						}
						a2 = adao
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdCurso(
										idAplicacion, idNivel, idTipoDestino,
										a1.getCurso().getId());
						if (a2 == null) {
							throw new NullPointerException("Para el material "
									+ material.getCodigo()
									+ " no se encontró una actividad del tipo "
									+ idTipoDestino + ", curso "
									+ a1.getCurso().getId());
						}
						mxadao.delete(mxas.get(0));
						mxaid = new MaterialXActividadId();
						mxaid.setActividadId(a2.getId());
						mxaid.setMaterialId(material.getId());
						mxa = new MaterialXActividad();
						mxa.setId(mxaid);
						mxadao.save(mxa);
						if (material.getLotes() != null
								&& !material.getLotes().isEmpty()) {
							for (Lote lote : material.getLotes()) {
								System.err.println(material.getCodigo()
										+ " en lote " + lote.getNombre()
										+ " centro "
										+ material.getCo().getNombre());
							}

						}
						materialesMovidos++;
					} else {
						throw new NullPointerException("El material "
								+ material.getCodigo()
								+ " no está en una actividad");
					}
				}
			}

			System.out.println("Movidos " + materialesMovidos + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollback(s);
		}
	}

	public static void cargarMaterialesDeContingencia(String doc,
			Integer idAplicacion, Integer idNivel, String tipoActividad) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String line = "";
		try {
			Integer dia = (tipoActividad.equals(ActividadTipo.VISITA_PREVIA)) ? null
					: 1;
			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo at1 = atdao.finByNombre(tipoActividad);
			ActividadTipo at2 = atdao
					.finByNombre(ActividadTipo.APLICACION_DIA_2);
			ActividadTipo at = null;
			MaterialDAO mdao = new MaterialDAO();
			Material m = null;
			MaterialTipoDAO mtdao = new MaterialTipoDAO();
			MaterialTipo caja = mtdao.findByNombre(MaterialTipo.CAJA_CURSO_DIA);
			MaterialTipo complementario = mtdao
					.findByNombre(MaterialTipo.COMPLEMENTARIO);
			MaterialXActividadDAO mxadao = new MaterialXActividadDAO();
			MaterialXActividad mxa = null;
			MaterialXActividadId mxaid = null;
			EstablecimientoDAO edao = new EstablecimientoDAO();
			Establecimiento eCont = edao
					.getById(Establecimiento.CONTINGENCIA_ID);
			CursoDAO cdao = new CursoDAO();
			Curso cCont = null;
			int totalMateriales = 0;

			Co co = null;
			CoDAO codao = new CoDAO();
			AplicacionXNivelDAO axndao = new AplicacionXNivelDAO();
			AplicacionXNivel axn = axndao.findByIdAplicacionANDIdNivel(
					idAplicacion, idNivel);

			if (eCont != null) {
				cCont = cdao
						.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
								idAplicacion, idNivel, eCont.getId(), "CONT");
			}
			if (eCont == null) {
				eCont = new Establecimiento();
				eCont.setId(Establecimiento.CONTINGENCIA_ID);
				eCont.setNombre("CONTINGENCIA");
				edao.save(eCont);
			}
			if (cCont == null) {
				cCont = new Curso();
				cCont.setAplicacionXNivel(axn);
				cCont.setNombre("CONT");
				cCont.setEstablecimiento(eCont);
				cdao.save(cCont);
			}
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					a = null;
					if (dia != null) {
						dia = (row[0].matches("[0-9]{3}1[0-9]{5}")) ? 1 : 2;
						// if (dia != Integer.valueOf(row[3])) {
						// throw new NullPointerException("No coincide día "
						// + line);
						// }
					}
					at = (dia != null && dia == 2) ? at2 : at1;

					if ((row[1].toUpperCase().matches(".*CONTINGENCIA.*"))) {

						a = actividadDAO
								.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDNombreCursoANDDia(
										idAplicacion, idNivel, at.getId(),
										eCont.getId(), cCont.getNombre(), dia);
					} else {
						throw new NullPointerException("No es de contingencia "
								+ line);
					}
					if (a == null) {
						throw new NullPointerException(
								"No se encontró actividad " + line);
					}
					m = mdao.findByIdAplicacionANDIdNivelANDIdActividadTipoANDCodigo(
							idAplicacion, idNivel, at.getId(),
							row[0].replaceAll(" ", ""));
					if (m != null) {
						System.err.println("El material ya existe " + line);
					} else {
						m = new Material();
						m.setCodigo(row[0].replaceAll(" ", ""));
						m.setMaterialTipo((at.getNombre()
								.equals(ActividadTipo.VISITA_PREVIA)) ? complementario
								: caja);
						m.setContingencia((row[1].toUpperCase()
								.matches(".*CONTINGENCIA.*")));
						// co = codao
						// .findByIdAplicacionANDIdNivelANDIdEstablecimiento(
						// idAplicacion, idNivel,
						// Integer.valueOf(row[3]));
						co = codao.findByIdAplicacionANDNombre(idAplicacion,
								row[2].replaceAll(" ", ""));
						m.setCo(co);
						mdao.save(m);
						mxaid = new MaterialXActividadId();
						mxaid.setActividadId(a.getId());
						mxaid.setMaterialId(m.getId());
						mxa = new MaterialXActividad();
						mxa.setId(mxaid);
						mxadao.save(mxa);
						totalMateriales++;
					}
				}

				rowCounter++;
			}
			System.out
					.println("Ingresados " + totalMateriales + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			System.err.println(line);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
			System.err.println(line);
		}
	}

	public static void asignarCodigoACurso(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String line = "";
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Integer idAplicacion = 1;
			Integer idNivel = null;
			NivelDAO ndao = new NivelDAO();
			CursoDAO cdao = new CursoDAO();
			Curso c = null;
			Integer totalCursos = 0;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					if (!row[3].toLowerCase().matches(".*medio*")) {
						idNivel = getNumero(row[3]);
						c = cdao.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
								idAplicacion,
								idNivel,
								Integer.valueOf(row[0]),
								idNivel
										+ String.valueOf(row[4].subSequence(0,
												1)));

						if (c != null) {
							c.setCodigo(row[1]);
							cdao.update(c);
							totalCursos++;
						}
					}
				}

				rowCounter++;
			}
			System.out
					.println("Se han actualizado " + totalCursos + " cursos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			System.err.println(line);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			HibernateUtil.rollback(s);
			System.err.println(line);
		}
	}

	public static Integer getNumero(String str) {
		Pattern p = Pattern.compile("^[0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return Integer.valueOf(m.group());
		}
		return null;
	}

	public static void cargarEstablecimientoHospitalario(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String line = "";
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Integer idAplicacion = 1;
			Integer idNivel = null;
			NivelDAO ndao = new NivelDAO();
			CursoDAO cdao = new CursoDAO();
			Curso c = null;
			ActividadDAO adao = new ActividadDAO();
			ActividadEstadoDAO aedao = new ActividadEstadoDAO();
			ActividadEstado ae = aedao.findByNombre(ActividadEstado.NO_APLICA);
			Integer totalCursos = 0;
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					if (!row[13].toLowerCase().matches(".*medio*")) {
						idNivel = getNumero(row[13]);
					} else {
						idNivel = 10;
					}
					c = cdao.findByIdAplicacionANDIdNivelANIdEstablecimientoDANDNombreCurso(
							idAplicacion,
							idNivel,
							Integer.valueOf(row[0]),
							((idNivel != 10) ? idNivel : 2)
									+ String.valueOf(row[14].subSequence(0, 1)));

					if (c != null) {
						for (Actividad actividad : c.getActividads()) {
							actividad.setActividadEstado(ae);
							adao.update(actividad);
						}
						totalCursos++;
					}
				}

				rowCounter++;
			}
			System.out
					.println("Se han actualizado " + totalCursos + " cursos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			System.err.println(line);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			HibernateUtil.rollback(s);
			System.err.println(line);
		}
	}

	/**
	 * Rut RDB donde rindió RDB en nómina Enlaces
	 * 
	 * @param doc
	 * @param idAplicacion
	 * @param idNivel
	 */
	public static void moverAlumnos(String doc, Integer idAplicacion,
			Integer idNivel) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();

		String line = "";
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			ActividadDAO adao = new ActividadDAO();
			Actividad a = null;
			Integer totalAlumnos = 0;
			AlumnoXActividadDAO axadao = new AlumnoXActividadDAO();
			AlumnoXActividad axa = null;
			Integer idActividadTipo = null;
			AlumnoTipoDAO atdao = new AlumnoTipoDAO();
			AlumnoTipo at = atdao.findByNombre(AlumnoTipo.SUPLENTE);
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					idActividadTipo = 1;
					axa = axadao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDRutAlumno(
									idAplicacion, idNivel, idActividadTipo,
									Integer.valueOf(row[2]), row[0]);
					if (axa == null) {
						throw new NullPointerException(
								"No se encontró visita previa para " + line);
					}
					a = adao.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
							idAplicacion, idNivel, idActividadTipo,
							Integer.valueOf(row[1]));
					if (a == null) {
						throw new NullPointerException(
								"No se encotró visita previa para destino "
										+ line);
					}
					axa.setActividad(a);
					axa.setAlumnoTipo(at);
					axadao.update(axa);

					idActividadTipo = 2;
					axa = axadao
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimientoANDRutAlumno(
									idAplicacion, idNivel, idActividadTipo,
									Integer.valueOf(row[2]), row[0]);
					if (axa == null) {
						throw new NullPointerException(
								"No se encontró aplicación para " + line);
					}
					a = adao.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
							idAplicacion, idNivel, idActividadTipo,
							Integer.valueOf(row[1]));
					if (a == null) {
						throw new NullPointerException(
								"No se encotró aplicación para destino " + line);
					}
					axa.setActividad(a);
					axa.setAlumnoTipo(at);
					axadao.update(axa);
					totalAlumnos++;
				}

				rowCounter++;
			}
			System.out.println("Se han actualizado " + totalAlumnos
					+ " alumnos.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			System.err.println(ex);
			System.err.println(line);
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			HibernateUtil.rollback(s);
			System.err.println(line);
		}
	}

	public static void cargarCuestionariosEntregadosTic(String doc) {
		DataInputStream in;
		FileInputStream fstream;
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			fstream = new FileInputStream(doc);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int rowCounter = 0;
			String[] row;
			s.beginTransaction();
			Actividad a = null;
			ActividadDAO actividadDAO = new ActividadDAO();
			ActividadTipoDAO atdao = new ActividadTipoDAO();
			ActividadTipo visitaPrevia = atdao
					.finByNombre(ActividadTipo.VISITA_PREVIA);

			ActividadXDocumentoTipoDAO axdtdao = new ActividadXDocumentoTipoDAO();
			ActividadXDocumentoTipo axdt = null;
			DocumentoTipoDAO dtdao = new DocumentoTipoDAO();
			DocumentoTipo dt = dtdao
					.findByNombre(DocumentoTipo.CUESTIONARIO_PADRE);

			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (rowCounter != 0) {
					a = actividadDAO
							.findByIdAplicacionANDIdNivelANDIdActividadTipoANDIdEstablecimiento(
									2, 10, visitaPrevia.getId(),
									Integer.valueOf(row[0]));
					if (a == null) {
						throw new NullPointerException(
								"No se encontró visita previa para " + row[0]);
					}
					axdt = axdtdao.findByIdActividadANDDocumentoTipo(a.getId(),
							DocumentoTipo.CUESTIONARIO_PADRE);

					if (axdt == null) {
						axdt = new ActividadXDocumentoTipo();
						axdt.setActividad(a);
						axdt.setDocumentoTipo(dt);
					}

					axdt.setTotalEntregados(Integer.valueOf(row[1]));

					axdtdao.saveOrUpdate(axdt);
				}
				rowCounter++;
			}

			System.out.println("Ingresados " + rowCounter + " materiales.");
			s.getTransaction().commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			HibernateUtil.rollback(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			HibernateUtil.rollback(s);
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollback(s);
		}
	}
}
