package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dreamer8.yosimce.server.hibernate.dao.EstablecimientoTipoDAO;
import com.dreamer8.yosimce.shared.dto.AgendaDTO;
import com.dreamer8.yosimce.shared.dto.AgendaItemDTO;
import com.dreamer8.yosimce.shared.dto.AgendaPreviewDTO;
import com.dreamer8.yosimce.shared.dto.ContactoDTO;

/**
 * Actividad generated by hbm2java
 */
public class Actividad implements java.io.Serializable {

	private Integer id;
	private ActividadEstado actividadEstado;
	private Curso curso;
	private ContactoCargo contactoCargo;
	private AplicacionXNivelXActividadTipo aplicacionXNivelXActividadTipo;
	private Usuario usuario;
	private String comentario;
	private Date fechaInicio;
	private Date fechaTermino;
	private Date fechaInicioPrueba;
	private Date fechaTerminoPrueba;
	private String contactoNombre;
	private String contactoTelefono;
	private String contactoEmail;
	private boolean sinMaterial;
	private Integer notaProceso;
	private Integer dia;
	private Integer totalAlumnosPresentes;
	private Integer totalAlumnosAusentes;
	private List<AlumnoXActividad> alumnoXActividads = new ArrayList<AlumnoXActividad>(
			0);
	private List<UsuarioXActividad> usuarioXActividads = new ArrayList<UsuarioXActividad>(
			0);
	private List<MaterialHistorial> materialHistorials = new ArrayList<MaterialHistorial>(
			0);
	private List<Material> materials = new ArrayList<Material>(0);
	private List<ActividadXDocumentoTipo> actividadXDocumentoTipos = new ArrayList<ActividadXDocumentoTipo>(
			0);
	private List<ActividadHistorial> actividadHistorials = new ArrayList<ActividadHistorial>(
			0);
	private List<TransporteXActividad> transporteXActividads = new ArrayList<TransporteXActividad>(
			0);
	private List<Documento> documentos = new ArrayList<Documento>(0);
	private List<ActividadXIncidencia> actividadXIncidencias = new ArrayList<ActividadXIncidencia>(
			0);

	public Actividad() {
	}

	public Actividad(Integer id, boolean sinMaterial) {
		this.id = id;
		this.sinMaterial = sinMaterial;
	}

	public Actividad(Integer id, ActividadEstado actividadEstado, Curso curso,
			ContactoCargo contactoCargo,
			AplicacionXNivelXActividadTipo aplicacionXNivelXActividadTipo,
			Usuario usuario, String comentario, Date fechaInicio,
			Date fechaTermino, Date fechaInicioPrueba, Date fechaTerminoPrueba,
			String contactoNombre, String contactoTelefono,
			String contactoEmail, boolean sinMaterial,
			List<AlumnoXActividad> alumnoXActividads,
			List<UsuarioXActividad> usuarioXActividads,
			List<MaterialHistorial> materialHistorials,
			List<Material> materials,
			List<ActividadXDocumentoTipo> actividadXDocumentoTipos,
			List<ActividadHistorial> actividadHistorials,
			List<TransporteXActividad> transporteXActividads,
			List<Documento> documentos,
			List<ActividadXIncidencia> actividadXIncidencias) {
		this.id = id;
		this.actividadEstado = actividadEstado;
		this.curso = curso;
		this.contactoCargo = contactoCargo;
		this.aplicacionXNivelXActividadTipo = aplicacionXNivelXActividadTipo;
		this.usuario = usuario;
		this.comentario = comentario;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.fechaInicioPrueba = fechaInicioPrueba;
		this.fechaTerminoPrueba = fechaTerminoPrueba;
		this.contactoNombre = contactoNombre;
		this.contactoTelefono = contactoTelefono;
		this.contactoEmail = contactoEmail;
		this.sinMaterial = sinMaterial;
		this.alumnoXActividads = alumnoXActividads;
		this.usuarioXActividads = usuarioXActividads;
		this.materialHistorials = materialHistorials;
		this.materials = materials;
		this.actividadXDocumentoTipos = actividadXDocumentoTipos;
		this.actividadHistorials = actividadHistorials;
		this.transporteXActividads = transporteXActividads;
		this.documentos = documentos;
		this.actividadXIncidencias = actividadXIncidencias;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ActividadEstado getActividadEstado() {
		return this.actividadEstado;
	}

	public void setActividadEstado(ActividadEstado actividadEstado) {
		this.actividadEstado = actividadEstado;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public ContactoCargo getContactoCargo() {
		return this.contactoCargo;
	}

	public void setContactoCargo(ContactoCargo contactoCargo) {
		this.contactoCargo = contactoCargo;
	}

	public AplicacionXNivelXActividadTipo getAplicacionXNivelXActividadTipo() {
		return this.aplicacionXNivelXActividadTipo;
	}

	public void setAplicacionXNivelXActividadTipo(
			AplicacionXNivelXActividadTipo aplicacionXNivelXActividadTipo) {
		this.aplicacionXNivelXActividadTipo = aplicacionXNivelXActividadTipo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public Date getFechaInicioPrueba() {
		return this.fechaInicioPrueba;
	}

	public void setFechaInicioPrueba(Date fechaInicioPrueba) {
		this.fechaInicioPrueba = fechaInicioPrueba;
	}

	public Date getFechaTerminoPrueba() {
		return this.fechaTerminoPrueba;
	}

	public void setFechaTerminoPrueba(Date fechaTerminoPrueba) {
		this.fechaTerminoPrueba = fechaTerminoPrueba;
	}

	public String getContactoNombre() {
		return this.contactoNombre;
	}

	public void setContactoNombre(String contactoNombre) {
		this.contactoNombre = contactoNombre;
	}

	public String getContactoTelefono() {
		return this.contactoTelefono;
	}

	public void setContactoTelefono(String contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	public String getContactoEmail() {
		return this.contactoEmail;
	}

	public void setContactoEmail(String contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	public boolean isSinMaterial() {
		return this.sinMaterial;
	}

	public void setSinMaterial(boolean sinMaterial) {
		this.sinMaterial = sinMaterial;
	}

	/**
	 * @return the notaProceso
	 */
	public Integer getNotaProceso() {
		return notaProceso;
	}

	/**
	 * @param notaProceso
	 *            the notaProceso to set
	 */
	public void setNotaProceso(Integer notaProceso) {
		this.notaProceso = notaProceso;
	}

	/**
	 * @return the dia
	 */
	public Integer getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            the dia to set
	 */
	public void setDia(Integer dia) {
		this.dia = dia;
	}

	/**
	 * @return the totalAlumnosPresentes
	 */
	public Integer getTotalAlumnosPresentes() {
		return totalAlumnosPresentes;
	}

	/**
	 * @param totalAlumnosPresentes
	 *            the totalAlumnosPresentes to set
	 */
	public void setTotalAlumnosPresentes(Integer totalAlumnosPresentes) {
		this.totalAlumnosPresentes = totalAlumnosPresentes;
	}

	/**
	 * @return the totalAlumnosAusentes
	 */
	public Integer getTotalAlumnosAusentes() {
		return totalAlumnosAusentes;
	}

	/**
	 * @param totalAlumnosAusentes
	 *            the totalAlumnosAusentes to set
	 */
	public void setTotalAlumnosAusentes(Integer totalAlumnosAusentes) {
		this.totalAlumnosAusentes = totalAlumnosAusentes;
	}

	public List<AlumnoXActividad> getAlumnoXActividads() {
		return this.alumnoXActividads;
	}

	public void setAlumnoXActividads(List<AlumnoXActividad> alumnoXActividads) {
		this.alumnoXActividads = alumnoXActividads;
	}

	public List<UsuarioXActividad> getUsuarioXActividads() {
		return this.usuarioXActividads;
	}

	public void setUsuarioXActividads(List<UsuarioXActividad> usuarioXActividads) {
		this.usuarioXActividads = usuarioXActividads;
	}

	public List<MaterialHistorial> getMaterialHistorials() {
		return this.materialHistorials;
	}

	public void setMaterialHistorials(List<MaterialHistorial> materialHistorials) {
		this.materialHistorials = materialHistorials;
	}

	public List<Material> getMaterials() {
		return this.materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public List<ActividadXDocumentoTipo> getActividadXDocumentoTipos() {
		return this.actividadXDocumentoTipos;
	}

	public void setActividadXDocumentoTipos(
			List<ActividadXDocumentoTipo> actividadXDocumentoTipos) {
		this.actividadXDocumentoTipos = actividadXDocumentoTipos;
	}

	public List<ActividadHistorial> getActividadHistorials() {
		return this.actividadHistorials;
	}

	public void setActividadHistorials(
			List<ActividadHistorial> actividadHistorials) {
		this.actividadHistorials = actividadHistorials;
	}

	public List<TransporteXActividad> getTransporteXActividads() {
		return this.transporteXActividads;
	}

	public void setTransporteXActividads(
			List<TransporteXActividad> transporteXActividads) {
		this.transporteXActividads = transporteXActividads;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<ActividadXIncidencia> getActividadXIncidencias() {
		return this.actividadXIncidencias;
	}

	public void setActividadXIncidencias(
			List<ActividadXIncidencia> actividadXIncidencias) {
		this.actividadXIncidencias = actividadXIncidencias;
	}

	public ContactoDTO getContactoDTO() {
		ContactoDTO cdto = new ContactoDTO();
		cdto.setContactoEmail(contactoEmail);
		cdto.setContactoNombre(contactoNombre);
		cdto.setContactoTelefono(contactoTelefono);
		return cdto;
	}

	public AgendaPreviewDTO getAgendaPreviewDTO(Integer idAplicacion) {
		AgendaPreviewDTO apdto = new AgendaPreviewDTO();
		apdto.setCurso(curso.getNombre());
		apdto.setCursoId(curso.getId());
		apdto.setEstablecimientoName(curso.getEstablecimiento().getNombre());
		Integer idEstablecimiento = curso.getEstablecimiento().getId();
		apdto.setRbd(Integer.toString(idEstablecimiento));
		EstablecimientoTipoDAO etdao = new EstablecimientoTipoDAO();
		EstablecimientoTipo et = etdao.findByIdAplicacionANDIdEstablecimiento(
				idAplicacion, idEstablecimiento);
		if (et != null) {
			apdto.setTipoEstablecimiento(et.getTipoEstablecimientoDTO());
		}
		apdto.setComunaName(curso.getEstablecimiento().getComuna().getNombre());
		apdto.setRegionName(curso.getEstablecimiento().getComuna()
				.getProvincia().getRegion().getNombre());
		apdto.setAgendaItemActual(getAgendaItemDTO());
		return apdto;
	}

	public AgendaItemDTO getAgendaItemDTO() {
		AgendaItemDTO aidto = new AgendaItemDTO();
		aidto.setEstado(actividadEstado.getEstadoAgendaDTO());
		aidto.setComentario(comentario);
		aidto.setFecha(fechaInicio);
		if (usuario != null) {
			aidto.setCreador(usuario.getUserDTO());
		}
		return aidto;
	}

	/**
	 * @return
	 */
	public AgendaDTO getAgendaDTO() {
		AgendaDTO adto = new AgendaDTO();
		adto.setCurso(curso.getNombre());
		adto.setEstablecimiento(curso.getEstablecimiento().getNombre());
		adto.setRbd(Integer.toString(curso.getEstablecimiento().getId()));
		ArrayList<AgendaItemDTO> aidtos = new ArrayList<AgendaItemDTO>();
		for (ActividadHistorial ah : actividadHistorials) {
			aidtos.add(ah.getAgendaItemDTO());
		}
		adto.setItems(aidtos);
		return adto;
	}
}
