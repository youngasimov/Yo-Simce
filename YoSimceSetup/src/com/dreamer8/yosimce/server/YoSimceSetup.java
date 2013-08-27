package com.dreamer8.yosimce.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.dao.ActividadDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadEstadoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXNivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXNivelXActividadTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.AplicacionXUsuarioTipoXPermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.ContactoCargoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.CursoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.dao.NivelDAO;
import com.dreamer8.yosimce.server.hibernate.dao.PermisoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioSeleccionDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioTipoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.UsuarioXAplicacionXNivelDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Actividad;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadEstado;
import com.dreamer8.yosimce.server.hibernate.pojo.ActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.Aplicacion;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXNivel;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXNivelXActividadTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.AplicacionXUsuarioTipoXPermiso;
import com.dreamer8.yosimce.server.hibernate.pojo.ContactoCargo;
import com.dreamer8.yosimce.server.hibernate.pojo.Curso;
import com.dreamer8.yosimce.server.hibernate.pojo.Establecimiento;
import com.dreamer8.yosimce.server.hibernate.pojo.Permiso;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioSeleccion;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioTipo;
import com.dreamer8.yosimce.server.hibernate.pojo.UsuarioXAplicacionXNivel;

public class YoSimceSetup {

	public static void main(String[] args) {
		createActividad();
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(2);
		ids.add(4);
		ids.add(6);
		ids.add(8);
		ids.add(10);
		asignarUsuario(16361209, 1, ids, 1);
		asignarUsuario(16370885, 1, ids, 1);
		ids = new ArrayList<Integer>();
		ids.add(10);
		asignarUsuario(16361209, 2, ids, 1);
		asignarUsuario(16370885, 2, ids, 1);
		// initPermisos();
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
							if (axnxat.getActividadTipo().equals(
									ActividadTipo.APLICACION_DIA_1)) {
								a.setDia(1);
							} else if (axnxat.getActividadTipo().equals(
									ActividadTipo.APLICACION_DIA_1)) {
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
					UsuarioTipoDAO utdao = new UsuarioTipoDAO();
					List<UsuarioTipo> uts = utdao.findAll();
					AplicacionDAO adao = new AplicacionDAO();
					List<Aplicacion> as = adao.findAll();
					AplicacionXUsuarioTipo axut = null;
					axuts = new ArrayList<AplicacionXUsuarioTipo>();
					for (Aplicacion a : as) {
						for (UsuarioTipo ut : uts) {
							axut = new AplicacionXUsuarioTipo();
							axut.setAplicacion(a);
							axut.setUsuarioTipo(ut);
							axutdao.save(axut);
							axuts.add(axut);
						}
					}
					s.flush();
				}
				AplicacionXUsuarioTipoXPermisoDAO axutxpdao = new AplicacionXUsuarioTipoXPermisoDAO();
				AplicacionXUsuarioTipoXPermiso axutxp = null;
				for (Permiso p : ps) {
					for (AplicacionXUsuarioTipo axut : axuts) {
						axutxp = new AplicacionXUsuarioTipoXPermiso();
						axutxp.setPermiso(p);
						axutxp.setAplicacionXUsuarioTipo(axut);
						axutxp.setAcceso(false);
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
}
