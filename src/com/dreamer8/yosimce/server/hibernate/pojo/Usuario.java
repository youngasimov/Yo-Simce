package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 12-07-2013 05:32:10 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	private Integer id;
	private CompaniaTelefono companiaTelefono;
	private CarreraEstado carreraEstado;
	private Carrera carrera;
	private Sexo sexo;
	private Comuna comuna;
	private String username;
	private String salt;
	private String password;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String email;
	private String celular;
	private Boolean smartphone;
	private String direccion;
	private BigDecimal direccionLatitud;
	private BigDecimal direccionLongitud;
	private Integer carreraAnoIngreso;
	private Date fechaNacimiento;
	private Boolean aceptaConfidencialidad;
	private Date fechaAceptacion;
	private List<UsuarioXTest> usuarioXTests = new ArrayList<UsuarioXTest>(0);
	private List<UsuarioXFaseXAplicacion> usuarioXFaseXAplicacions = new ArrayList<UsuarioXFaseXAplicacion>(0);
	private List<UsuarioXAplicacionXNivel> usuarioXAplicacionXNivels = new ArrayList<UsuarioXAplicacionXNivel>(0);
	private List<Nivel> nivels = new ArrayList<Nivel>(0);
	private List<UsuarioXCo> usuarioXCos = new ArrayList<UsuarioXCo>(0);
	private List<HistorialCambios> historialCambios = new ArrayList<HistorialCambios>(0);
	private List<UsuarioXRequisito> usuarioXRequisitos = new ArrayList<UsuarioXRequisito>(0);
	private List<Transporte> transportes = new ArrayList<Transporte>(0);
	private List<Actividad> actividads = new ArrayList<Actividad>(0);
	private List<RutaXEstablecimiento> rutaXEstablecimientos = new ArrayList<RutaXEstablecimiento>(0);
	private List<SuplenteXCo> suplenteXCos = new ArrayList<SuplenteXCo>(0);
	private List<JrXCentroRegional> jrXCentroRegionals = new ArrayList<JrXCentroRegional>(0);
	private List<Mensaje> mensajes = new ArrayList<Mensaje>(0);
	private List<Sesion> sesions = new ArrayList<Sesion>(0);
	private List<Comuna> comunas = new ArrayList<Comuna>(0);
	private List<UsuarioXCcCapacitacion> usuarioXCcCapacitacions = new ArrayList<UsuarioXCcCapacitacion>(0);
	private List<JzXZona> jzXZonas = new ArrayList<JzXZona>(0);
	private List<UsuarioXActividad> usuarioXActividads = new ArrayList<UsuarioXActividad>(0);
	private List<Ruta> rutas = new ArrayList<Ruta>(0);
	private List<JoXCo> joXCos = new ArrayList<JoXCo>(0);
	private List<UsuarioXEstablecimiento> usuarioXEstablecimientos = new ArrayList<UsuarioXEstablecimiento>(0);

	public Usuario() {
	}

	public Usuario(Integer id) {
		this.id = id;
	}

	public Usuario(Integer id, CompaniaTelefono companiaTelefono,
			CarreraEstado carreraEstado, Carrera carrera, Sexo sexo,
			Comuna comuna, String username, String salt, String password,
			String nombres, String apellidoPaterno, String apellidoMaterno,
			String email, String celular, Boolean smartphone, String direccion,
			BigDecimal direccionLatitud, BigDecimal direccionLongitud,
			Integer carreraAnoIngreso, Date fechaNacimiento,
			Boolean aceptaConfidencialidad, Date fechaAceptacion,
			List<UsuarioXTest> usuarioXTests, List<UsuarioXFaseXAplicacion> usuarioXFaseXAplicacions,
			List<UsuarioXAplicacionXNivel> usuarioXAplicacionXNivels, List<Nivel> nivels, List<UsuarioXCo> usuarioXCos,
			List<HistorialCambios> historialCambios, List<UsuarioXRequisito> usuarioXRequisitos, List<Transporte> transportes,
			List<Actividad> actividads, List<RutaXEstablecimiento> rutaXEstablecimientos, List<SuplenteXCo> suplenteXCos,
			List<JrXCentroRegional> jrXCentroRegionals, List<Mensaje> mensajes, List<Sesion> sesions, List<Comuna> comunas,
			List<UsuarioXCcCapacitacion> usuarioXCcCapacitacions, List<JzXZona> jzXZonas, List<UsuarioXActividad> usuarioXActividads,
			List<Ruta> rutas, List<JoXCo> joXCos, List<UsuarioXEstablecimiento> usuarioXEstablecimientos) {
		this.id = id;
		this.companiaTelefono = companiaTelefono;
		this.carreraEstado = carreraEstado;
		this.carrera = carrera;
		this.sexo = sexo;
		this.comuna = comuna;
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.celular = celular;
		this.smartphone = smartphone;
		this.direccion = direccion;
		this.direccionLatitud = direccionLatitud;
		this.direccionLongitud = direccionLongitud;
		this.carreraAnoIngreso = carreraAnoIngreso;
		this.fechaNacimiento = fechaNacimiento;
		this.aceptaConfidencialidad = aceptaConfidencialidad;
		this.fechaAceptacion = fechaAceptacion;
		this.usuarioXTests = usuarioXTests;
		this.usuarioXFaseXAplicacions = usuarioXFaseXAplicacions;
		this.usuarioXAplicacionXNivels = usuarioXAplicacionXNivels;
		this.nivels = nivels;
		this.usuarioXCos = usuarioXCos;
		this.historialCambios = historialCambios;
		this.usuarioXRequisitos = usuarioXRequisitos;
		this.transportes = transportes;
		this.actividads = actividads;
		this.rutaXEstablecimientos = rutaXEstablecimientos;
		this.suplenteXCos = suplenteXCos;
		this.jrXCentroRegionals = jrXCentroRegionals;
		this.mensajes = mensajes;
		this.sesions = sesions;
		this.comunas = comunas;
		this.usuarioXCcCapacitacions = usuarioXCcCapacitacions;
		this.jzXZonas = jzXZonas;
		this.usuarioXActividads = usuarioXActividads;
		this.rutas = rutas;
		this.joXCos = joXCos;
		this.usuarioXEstablecimientos = usuarioXEstablecimientos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CompaniaTelefono getCompaniaTelefono() {
		return this.companiaTelefono;
	}

	public void setCompaniaTelefono(CompaniaTelefono companiaTelefono) {
		this.companiaTelefono = companiaTelefono;
	}

	public CarreraEstado getCarreraEstado() {
		return this.carreraEstado;
	}

	public void setCarreraEstado(CarreraEstado carreraEstado) {
		this.carreraEstado = carreraEstado;
	}

	public Carrera getCarrera() {
		return this.carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Sexo getSexo() {
		return this.sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Comuna getComuna() {
		return this.comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Boolean getSmartphone() {
		return this.smartphone;
	}

	public void setSmartphone(Boolean smartphone) {
		this.smartphone = smartphone;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public BigDecimal getDireccionLatitud() {
		return this.direccionLatitud;
	}

	public void setDireccionLatitud(BigDecimal direccionLatitud) {
		this.direccionLatitud = direccionLatitud;
	}

	public BigDecimal getDireccionLongitud() {
		return this.direccionLongitud;
	}

	public void setDireccionLongitud(BigDecimal direccionLongitud) {
		this.direccionLongitud = direccionLongitud;
	}

	public Integer getCarreraAnoIngreso() {
		return this.carreraAnoIngreso;
	}

	public void setCarreraAnoIngreso(Integer carreraAnoIngreso) {
		this.carreraAnoIngreso = carreraAnoIngreso;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getAceptaConfidencialidad() {
		return this.aceptaConfidencialidad;
	}

	public void setAceptaConfidencialidad(Boolean aceptaConfidencialidad) {
		this.aceptaConfidencialidad = aceptaConfidencialidad;
	}

	public Date getFechaAceptacion() {
		return this.fechaAceptacion;
	}

	public void setFechaAceptacion(Date fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}

	public List<UsuarioXTest> getUsuarioXTests() {
		return this.usuarioXTests;
	}

	public void setUsuarioXTests(List<UsuarioXTest> usuarioXTests) {
		this.usuarioXTests = usuarioXTests;
	}

	public List<UsuarioXFaseXAplicacion> getUsuarioXFaseXAplicacions() {
		return this.usuarioXFaseXAplicacions;
	}

	public void setUsuarioXFaseXAplicacions(List<UsuarioXFaseXAplicacion> usuarioXFaseXAplicacions) {
		this.usuarioXFaseXAplicacions = usuarioXFaseXAplicacions;
	}

	public List<UsuarioXAplicacionXNivel> getUsuarioXAplicacionXNivels() {
		return this.usuarioXAplicacionXNivels;
	}

	public void setUsuarioXAplicacionXNivels(List<UsuarioXAplicacionXNivel> usuarioXAplicacionXNivels) {
		this.usuarioXAplicacionXNivels = usuarioXAplicacionXNivels;
	}

	public List<Nivel> getNivels() {
		return this.nivels;
	}

	public void setNivels(List<Nivel> nivels) {
		this.nivels = nivels;
	}

	public List<UsuarioXCo> getUsuarioXCos() {
		return this.usuarioXCos;
	}

	public void setUsuarioXCos(List<UsuarioXCo> usuarioXCos) {
		this.usuarioXCos = usuarioXCos;
	}

	public List<HistorialCambios> getHistorialCambios() {
		return this.historialCambios;
	}

	public void setHistorialCambioses(List<HistorialCambios> historialCambios) {
		this.historialCambios = historialCambios;
	}

	public List<UsuarioXRequisito> getUsuarioXRequisitos() {
		return this.usuarioXRequisitos;
	}

	public void setUsuarioXRequisitos(List<UsuarioXRequisito> usuarioXRequisitos) {
		this.usuarioXRequisitos = usuarioXRequisitos;
	}

	public List<Transporte> getTransportes() {
		return this.transportes;
	}

	public void setTransportes(List<Transporte> transportes) {
		this.transportes = transportes;
	}

	public List<Actividad> getActividads() {
		return this.actividads;
	}

	public void setActividads(List<Actividad> actividads) {
		this.actividads = actividads;
	}

	public List<RutaXEstablecimiento> getRutaXEstablecimientos() {
		return this.rutaXEstablecimientos;
	}

	public void setRutaXEstablecimientos(List<RutaXEstablecimiento> rutaXEstablecimientos) {
		this.rutaXEstablecimientos = rutaXEstablecimientos;
	}

	public List<SuplenteXCo> getSuplenteXCos() {
		return this.suplenteXCos;
	}

	public void setSuplenteXCos(List<SuplenteXCo> suplenteXCos) {
		this.suplenteXCos = suplenteXCos;
	}

	public List<JrXCentroRegional> getJrXCentroRegionals() {
		return this.jrXCentroRegionals;
	}

	public void setJrXCentroRegionals(List<JrXCentroRegional> jrXCentroRegionals) {
		this.jrXCentroRegionals = jrXCentroRegionals;
	}

	public List<Mensaje> getMensajes() {
		return this.mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public List<Sesion> getSesions() {
		return this.sesions;
	}

	public void setSesions(List<Sesion> sesions) {
		this.sesions = sesions;
	}

	public List<Comuna> getComunas() {
		return this.comunas;
	}

	public void setComunas(List<Comuna> comunas) {
		this.comunas = comunas;
	}

	public List<UsuarioXCcCapacitacion> getUsuarioXCcCapacitacions() {
		return this.usuarioXCcCapacitacions;
	}

	public void setUsuarioXCcCapacitacions(List<UsuarioXCcCapacitacion> usuarioXCcCapacitacions) {
		this.usuarioXCcCapacitacions = usuarioXCcCapacitacions;
	}

	public List<JzXZona> getJzXZonas() {
		return this.jzXZonas;
	}

	public void setJzXZonas(List<JzXZona> jzXZonas) {
		this.jzXZonas = jzXZonas;
	}

	public List<UsuarioXActividad> getUsuarioXActividads() {
		return this.usuarioXActividads;
	}

	public void setUsuarioXActividads(List<UsuarioXActividad> usuarioXActividads) {
		this.usuarioXActividads = usuarioXActividads;
	}

	public List<Ruta> getRutas() {
		return this.rutas;
	}

	public void setRutas(List<Ruta> rutas) {
		this.rutas = rutas;
	}

	public List<JoXCo> getJoXCos() {
		return this.joXCos;
	}

	public void setJoXCos(List<JoXCo> joXCos) {
		this.joXCos = joXCos;
	}

	public List<UsuarioXEstablecimiento> getUsuarioXEstablecimientos() {
		return this.usuarioXEstablecimientos;
	}

	public void setUsuarioXEstablecimientos(List<UsuarioXEstablecimiento> usuarioXEstablecimientos) {
		this.usuarioXEstablecimientos = usuarioXEstablecimientos;
	}

}
