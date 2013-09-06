package com.dreamer8.yosimce.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadTipoDAO;
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
import com.dreamer8.yosimce.server.hibernate.dao.ContactoCargoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.NivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.SexoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioSeleccionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioXAplicacionXNivelDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
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
import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.EstablecimientoTipo;
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

		cargarAlumnosTic("titulares.csv", EstablecimientoTipo.SELECCIONADO);
		cargarAlumnosTic("reemplazos1.csv", EstablecimientoTipo.REEMPLAZO_1);
		cargarAlumnosTic("reemplazos2.csv", EstablecimientoTipo.REEMPLAZO_2);

		System.out.println("fin :P");
	}

	public static void createActividad() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			s.beginTransaction();

			AplicacionXNivelXActividadTipoDAO axnxatdao = new AplicacionXNivelXActividadTipoDAO();
			List<AplicacionXNivelXActividadTipo> axnxats = axnxatdao.findAll();
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
					cs = axnxat.getAplicacionXNivel().getCursos();
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
						}
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

	public static void cargarAlumnosTic(String doc, String tipoEstablecimiento) {
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
			ActividadDAO actividadDAO = new ActividadDAO();
			Alumno alumno = null;
			AlumnoDAO alumnoDAO = new AlumnoDAO();
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
			while ((line = br.readLine()) != null) {
				row = line.split(";");
				if (!row[0].equals(rbd)) {
					rbd = row[0];
					establecimiento = edao.getById(Integer.getInteger(rbd));
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
						throw new NullPointerException("El establecimiento "
								+ rbd + " no tiene curso");
					}
					c = cs.get(0);
					as = c.getActividads();
					if (as == null || as.isEmpty()) {
						throw new NullPointerException("El establecimiento "
								+ rbd + " no tiene actividades");
					}
					for (Actividad actividad : as) {
						actividad.setTotalAlumnos(Integer.getInteger(row[6]));
						actividadDAO.update(actividad);
					}
				}
				alumno = new Alumno();
				alumno.setId(Integer.getInteger(row[7]));
				alumno.setRut(StringUtils.formatRut(row[7] + row[8]));
				alumno.setNombres(row[9]);
				alumno.setApellidoPaterno(row[10]);
				alumno.setApellidoMaterno(row[11]);
				alumno.setSexo((row[12].equals("M")) ? masculino : femenino);
				fechaNacimiento = row[13].split("-");
				calendar.set(Integer.getInteger(fechaNacimiento[2]),
						Integer.getInteger(fechaNacimiento[1]),
						Integer.getInteger(fechaNacimiento[0]));
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
}
