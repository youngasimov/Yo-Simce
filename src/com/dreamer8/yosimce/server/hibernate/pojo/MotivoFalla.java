package com.dreamer8.yosimce.server.hibernate.pojo;

// Generated 16-08-2013 05:13:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import com.dreamer8.yosimce.shared.dto.TipoContingenciaDTO;

/**
 * MotivoFalla generated by hbm2java
 */
public class MotivoFalla implements java.io.Serializable {

	private Integer id;
	private IncidenciaTipo incidenciaTipo;
	private String nombre;
	private List<ActividadXIncidencia> actividadXIncidencias = new ArrayList<ActividadXIncidencia>(0);

	public MotivoFalla() {
	}

	public MotivoFalla(Integer id) {
		this.id = id;
	}

	public MotivoFalla(Integer id, IncidenciaTipo incidenciaTipo, String nombre,
			List<ActividadXIncidencia> actividadXIncidencias) {
		this.id = id;
		this.incidenciaTipo = incidenciaTipo;
		this.nombre = nombre;
		this.actividadXIncidencias = actividadXIncidencias;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public IncidenciaTipo getIncidenciaTipo() {
		return this.incidenciaTipo;
	}

	public void setIncidenciaTipo(IncidenciaTipo incidenciaTipo) {
		this.incidenciaTipo = incidenciaTipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ActividadXIncidencia> getActividadXIncidencias() {
		return this.actividadXIncidencias;
	}

	public void setActividadXIncidencias(List<ActividadXIncidencia> actividadXIncidencias) {
		this.actividadXIncidencias = actividadXIncidencias;
	}

	/**
	 * @return
	 */
	public TipoContingenciaDTO getTipoContingenciaDTO() {
		TipoContingenciaDTO tcdto = new TipoContingenciaDTO();
		tcdto.setId(id);
		tcdto.setContingencia(nombre);
		return tcdto;
	}

}
