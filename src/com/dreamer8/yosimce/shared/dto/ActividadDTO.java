package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ActividadDTO implements Serializable {

	
	private String nombreEstablecimiento;
	private String rbd;
	private String curso;
	private String tipoEstablecimiento;
	private String region;
	private String comuna;
	private EstadoAgendaDTO estadoAplicacion;
	private ArrayList<ContingenciaDTO> contingencias;
	private Date inicioActividad;
	private Date inicioPrueba;
	private Date terminoPrueba;
	private Integer alumnosTotal;
	private Integer alumnosAusentes;
	private Integer alumnosDs;
	private Integer totalCuestionarios;
	private Integer cuestionariosEntregados;	
	private Integer cuestionariosRecibidos;
	private Boolean materialContingencia;
	private String detalleUsoMaterialContingencia;
	private Integer evaluacionProcedimientos;
	
	public ActividadDTO() {
		// TODO Auto-generated constructor stub
	}

}
