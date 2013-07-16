/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.shared.exceptions;

/**
 *
 * @author jorge
 */
public class ConsistencyException extends RuntimeException {

    public ConsistencyException(String string) {
	super(string);
    }

    public ConsistencyException() {
	super("No se ha podido realizar esta operación, debido a que hay documentos que dependen del elemento especificado.");
    }
}
