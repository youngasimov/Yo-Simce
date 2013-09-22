package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MensajeEvent extends GwtEvent<MensajeEvent.MensajeHandler> {
	public static final int MSG_OK = 1; 
	public static final int MSG_WARNING = 2; 
	public static final int MSG_ERROR = 3;
	public static final int MSG_PERMISOS = 4;
	public static final Type<MensajeHandler> TYPE = new Type<MensajeHandler>();
	
	public interface MensajeHandler extends EventHandler{
		void onMensaje(MensajeEvent event);
	}
	
	private String mensaje;
	private int tipo;
	private boolean autoClose;

	public MensajeEvent(String mensaje) {
		this(mensaje,MSG_OK,false);
	}
	
	public MensajeEvent(String mensaje, int tipo) {
		this(mensaje,tipo,false);
	}
	
	public MensajeEvent(String mensaje, int tipo, boolean autoclose) {
		super();
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.autoClose = autoclose;
	}

	public String getMensaje() {
		return mensaje;
	}

	public int getTipo() {
		return tipo;
	}
	
	public boolean isAutoClose(){
		return autoClose;
	}

	@Override
	public Type<MensajeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MensajeHandler handler) {
		handler.onMensaje(this);
	}
}
