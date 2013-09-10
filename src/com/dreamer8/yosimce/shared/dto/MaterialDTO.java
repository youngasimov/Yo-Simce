package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

@SuppressWarnings("serial")
public class MaterialDTO implements Serializable {

	public static final ProvidesKey<MaterialDTO> KEY_PROVIDER = new ProvidesKey<MaterialDTO>() {

		@Override
		public Object getKey(MaterialDTO item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	
	private Integer id;
	private String codigo;
	private String tipo;
	private String rbd;
	private String establecimiento;
	private String nivel;
	private String curso;
	private String etapa;
	private LoteDTO lote;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getRbd() {
		return rbd;
	}


	public void setRbd(String rbd) {
		this.rbd = rbd;
	}


	public String getEstablecimiento() {
		return establecimiento;
	}


	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}


	public String getNivel() {
		return nivel;
	}


	public void setNivel(String nivel) {
		this.nivel = nivel;
	}


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}


	public String getEtapa() {
		return etapa;
	}


	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public LoteDTO getLote() {
		return lote;
	}


	public void setLote(LoteDTO lote) {
		this.lote = lote;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result
				+ ((establecimiento == null) ? 0 : establecimiento.hashCode());
		result = prime * result + ((etapa == null) ? 0 : etapa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
		result = prime * result + ((rbd == null) ? 0 : rbd.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		MaterialDTO other = (MaterialDTO) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (establecimiento == null) {
			if (other.establecimiento != null)
				return false;
		} else if (!establecimiento.equals(other.establecimiento))
			return false;
		if (etapa == null) {
			if (other.etapa != null)
				return false;
		} else if (!etapa.equals(other.etapa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nivel == null) {
			if (other.nivel != null)
				return false;
		} else if (!nivel.equals(other.nivel))
			return false;
		if (rbd == null) {
			if (other.rbd != null)
				return false;
		} else if (!rbd.equals(other.rbd))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
}
