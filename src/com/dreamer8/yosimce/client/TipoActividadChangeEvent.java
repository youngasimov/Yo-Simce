package com.dreamer8.yosimce.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

public class TipoActividadChangeEvent extends Event<TipoActividadChangeEvent.TipoActividadChangeHandler> {

	public static final Type<TipoActividadChangeHandler> TYPE = new Type<TipoActividadChangeHandler>();
	
	
	public interface TipoActividadChangeHandler extends EventHandler{
		void onTipoActividadChange(TipoActividadChangeEvent event);
	}
	
	private int idTipo;
	
	

	public TipoActividadChangeEvent(int idTipo) {
		super();
		this.idTipo = idTipo;
	}

	public int getIdTipo() {
		return idTipo;
	}


	@Override
	public com.google.web.bindery.event.shared.Event.Type<TipoActividadChangeHandler> getAssociatedType() {
		return TYPE;
	}


	@Override
	protected void dispatch(TipoActividadChangeHandler handler) {
		handler.onTipoActividadChange(this);
	}
	
}
