package com.dreamer8.yosimce.client;

@SuppressWarnings("serial")
public class TimeoutException extends Throwable {

	
	public TimeoutException(){
		super("El tiempo de conexión, en espera de respuesta del servidor, se ha excedido");
	}
}
