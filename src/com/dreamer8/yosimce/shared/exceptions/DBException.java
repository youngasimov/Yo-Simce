/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.shared.exceptions;

/**
 * 
 * @author jorge
 */
public class DBException extends RuntimeException {

	public DBException() {
		super("Ha ocurrido un error con la base de datos,"
				+ " por lo que la operación no se pudo realizar.<br />"
				+ "Por favor, inténtelo nuevamente.<br />"
				+ "Si el problema persiste envíe un correo a pisa@ennea.cl"
				+ " indicando la fecha y hora en que ocurrió, junto con la dirección"
				+ " url de la página actual y un comentario indicando qué acción estaba realizando.");
	}

	public DBException(String string) {
		super(string);
	}
}
