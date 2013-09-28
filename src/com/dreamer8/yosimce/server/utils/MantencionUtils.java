/**
 * 
 */
package com.dreamer8.yosimce.server.utils;

import java.util.Date;

/**
 * @author jorge
 * 
 */
public class MantencionUtils {
	private static Boolean mantencionAgendada;
	private static Date fechaMantencion;
	private static String fechaMantencionString;

	static {
		mantencionAgendada = false;
		fechaMantencion = null;
		fechaMantencionString = "";
	}

	public static Boolean getMantencionAgendada() {
		return mantencionAgendada;
	}

	public static Boolean isMantencionAgendada() {
		return mantencionAgendada;
	}

	public static void setMantencionAgendada(Boolean mantencionAgendada) {
		MantencionUtils.mantencionAgendada = mantencionAgendada;
	}

	public static Date getFechaMantencion() {
		return fechaMantencion;
	}

	public static void setFechaMantencion(Date fechaMantencion) {
		MantencionUtils.fechaMantencion = fechaMantencion;
		mantencionAgendada = (fechaMantencion != null);
		fechaMantencionString = StringUtils.getDateString(fechaMantencion);
	}

	public static String getFechaMantencionString() {
		return fechaMantencionString;
	}

}
