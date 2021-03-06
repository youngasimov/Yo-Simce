package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;

/**
 * Co generated by hbm2java
 */
public class Co implements java.io.Serializable {

	private Integer id;
	private Zona zona;
	private Comuna comuna;
	private String nombre;
	private String direccion;
	private BigDecimal direccionLatitud;
	private BigDecimal direccionLongitud;
	private String propietarioNombre;
	private String propietarioRut;
	private String propietarioEmail;
	private String propietarioTelefono;
	private String propietarioCelular;
	private Integer modificadorId;
	private List<CoXEstablecimiento> coXEstablecimientos = new ArrayList<CoXEstablecimiento>(
			0);
	private List<AplicacionXNivelXUsuarioTipoXCo> aplicacionXNivelXUsuarioTipoXCos = new ArrayList<AplicacionXNivelXUsuarioTipoXCo>(
			0);
	private List<UsuarioPreseleccion> usuarioPreseleccions = new ArrayList<UsuarioPreseleccion>(
			0);
	private List<Material> materials = new ArrayList<Material>(0);
	private List<SuplenteXCo> suplenteXCos = new ArrayList<SuplenteXCo>(0);
	private List<JoXCo> joXCos = new ArrayList<JoXCo>(0);
	private List<UsuarioXCo> usuarioXCos = new ArrayList<UsuarioXCo>(0);
	private List<MaterialHistorial> materialHistorials = new ArrayList<MaterialHistorial>(
			0);
	private List<Ruta> rutas = new ArrayList<Ruta>(0);

	public Co() {
	}

	public Co(Integer id) {
		this.id = id;
	}

	public Co(
			Integer id,
			Zona zona,
			Comuna comuna,
			String nombre,
			String direccion,
			BigDecimal direccionLatitud,
			BigDecimal direccionLongitud,
			String propietarioNombre,
			String propietarioRut,
			String propietarioEmail,
			String propietarioTelefono,
			String propietarioCelular,
			Integer modificadorId,
			List<CoXEstablecimiento> coXEstablecimientos,
			List<AplicacionXNivelXUsuarioTipoXCo> aplicacionXNivelXUsuarioTipoXCos,
			List<UsuarioPreseleccion> usuarioPreseleccions,
			List<Material> materials, List<SuplenteXCo> suplenteXCos,
			List<JoXCo> joXCos, List<UsuarioXCo> usuarioXCos,
			List<MaterialHistorial> materialHistorials, List<Ruta> rutas) {
		this.id = id;
		this.zona = zona;
		this.comuna = comuna;
		this.nombre = nombre;
		this.direccion = direccion;
		this.direccionLatitud = direccionLatitud;
		this.direccionLongitud = direccionLongitud;
		this.propietarioNombre = propietarioNombre;
		this.propietarioRut = propietarioRut;
		this.propietarioEmail = propietarioEmail;
		this.propietarioTelefono = propietarioTelefono;
		this.propietarioCelular = propietarioCelular;
		this.modificadorId = modificadorId;
		this.coXEstablecimientos = coXEstablecimientos;
		this.aplicacionXNivelXUsuarioTipoXCos = aplicacionXNivelXUsuarioTipoXCos;
		this.usuarioPreseleccions = usuarioPreseleccions;
		this.materials = materials;
		this.suplenteXCos = suplenteXCos;
		this.joXCos = joXCos;
		this.usuarioXCos = usuarioXCos;
		this.materialHistorials = materialHistorials;
		this.rutas = rutas;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Zona getZona() {
		return this.zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Comuna getComuna() {
		return this.comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getPropietarioNombre() {
		return this.propietarioNombre;
	}

	public void setPropietarioNombre(String propietarioNombre) {
		this.propietarioNombre = propietarioNombre;
	}

	public String getPropietarioRut() {
		return this.propietarioRut;
	}

	public void setPropietarioRut(String propietarioRut) {
		this.propietarioRut = propietarioRut;
	}

	public String getPropietarioEmail() {
		return this.propietarioEmail;
	}

	public void setPropietarioEmail(String propietarioEmail) {
		this.propietarioEmail = propietarioEmail;
	}

	public String getPropietarioTelefono() {
		return this.propietarioTelefono;
	}

	public void setPropietarioTelefono(String propietarioTelefono) {
		this.propietarioTelefono = propietarioTelefono;
	}

	public String getPropietarioCelular() {
		return this.propietarioCelular;
	}

	public void setPropietarioCelular(String propietarioCelular) {
		this.propietarioCelular = propietarioCelular;
	}

	public Integer getModificadorId() {
		return this.modificadorId;
	}

	public void setModificadorId(Integer modificadorId) {
		this.modificadorId = modificadorId;
	}

	public List<CoXEstablecimiento> getCoXEstablecimientos() {
		return this.coXEstablecimientos;
	}

	public void setCoXEstablecimientos(
			List<CoXEstablecimiento> coXEstablecimientos) {
		this.coXEstablecimientos = coXEstablecimientos;
	}

	public List<AplicacionXNivelXUsuarioTipoXCo> getAplicacionXNivelXUsuarioTipoXCos() {
		return this.aplicacionXNivelXUsuarioTipoXCos;
	}

	public void setAplicacionXNivelXUsuarioTipoXCos(
			List<AplicacionXNivelXUsuarioTipoXCo> aplicacionXNivelXUsuarioTipoXCos) {
		this.aplicacionXNivelXUsuarioTipoXCos = aplicacionXNivelXUsuarioTipoXCos;
	}

	public List<UsuarioPreseleccion> getUsuarioPreseleccions() {
		return this.usuarioPreseleccions;
	}

	public void setUsuarioPreseleccions(
			List<UsuarioPreseleccion> usuarioPreseleccions) {
		this.usuarioPreseleccions = usuarioPreseleccions;
	}

	public List<Material> getMaterials() {
		return this.materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public List<SuplenteXCo> getSuplenteXCos() {
		return this.suplenteXCos;
	}

	public void setSuplenteXCos(List<SuplenteXCo> suplenteXCos) {
		this.suplenteXCos = suplenteXCos;
	}

	public List<JoXCo> getJoXCos() {
		return this.joXCos;
	}

	public void setJoXCos(List<JoXCo> joXCos) {
		this.joXCos = joXCos;
	}

	public List<UsuarioXCo> getUsuarioXCos() {
		return this.usuarioXCos;
	}

	public void setUsuarioXCos(List<UsuarioXCo> usuarioXCos) {
		this.usuarioXCos = usuarioXCos;
	}

	public List<MaterialHistorial> getMaterialHistorials() {
		return this.materialHistorials;
	}

	public void setMaterialHistorials(List<MaterialHistorial> materialHistorials) {
		this.materialHistorials = materialHistorials;
	}

	public List<Ruta> getRutas() {
		return this.rutas;
	}

	public void setRutas(List<Ruta> rutas) {
		this.rutas = rutas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Co other = (Co) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public EmplazamientoDTO getEmplazamientoDTO() {
		EmplazamientoDTO edto = new EmplazamientoDTO();
		edto.setId(id);
		edto.setNombre(nombre);
		edto.setTipoEmplazamiento(EmplazamientoDTO.CENTRO_OPERACIONAL);
		return edto;
	}

}
