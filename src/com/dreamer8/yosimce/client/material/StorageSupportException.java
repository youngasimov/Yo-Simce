package com.dreamer8.yosimce.client.material;

@SuppressWarnings("serial")
public class StorageSupportException extends Throwable {

	public StorageSupportException(){
		super("Su navegador no soporta el almacenamiento local de la información");
	}
}
