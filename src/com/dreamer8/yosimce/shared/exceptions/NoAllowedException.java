/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.shared.exceptions;

/**
 *
 * @author jorge
 */
public class NoAllowedException extends RuntimeException {

    public NoAllowedException() {
	super("El usuario no está autorizado para realizar esta acción.");
    }

    public NoAllowedException(String message) {
	super(message);
    }
}
