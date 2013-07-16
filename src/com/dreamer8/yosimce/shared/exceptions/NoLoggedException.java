/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamer8.yosimce.shared.exceptions;

/**
 *
 * @author jorge
 */
public class NoLoggedException extends RuntimeException {

    public NoLoggedException() {
	super("No ha iniciado sesión o ésta ha expirado.");
    }

    public NoLoggedException(String message) {
	super(message);
    }
}
