package com.dreamer8.yosimce.client;

@SuppressWarnings("serial")
public class TimeoutException extends Throwable {

	public TimeoutException(){
		super("El tiempo máximo de espera se ha excedido, se cierra la conexión para no bloquear el sistema");
	}
}
