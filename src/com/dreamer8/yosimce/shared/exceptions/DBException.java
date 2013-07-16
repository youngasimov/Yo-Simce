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
	super("Ha ocurrido un error con la base de datos, por lo que la operación no se pudo realizar.<br />Por favor, intentelo nuevamente.");
    }

    public DBException(String string) {
	super(string);
    }
}
