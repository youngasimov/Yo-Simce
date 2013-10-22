package com.dreamer8.yosimce.client.general;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.StorageExt;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyFactory;
import com.seanchenxi.gwt.storage.client.StorageQuotaExceededException;

public class LocalCentroControlService {
	
	private StorageExt storage;
	private boolean supportMessage;
	private EventBus eventBus;
	private Logger logger = Logger.getLogger("");
	
	public LocalCentroControlService(EventBus eventBus){
		storage = StorageExt.getLocalStorage();
		supportMessage = false;
		this.eventBus =eventBus;
	}

	
	public HashMap<Date,ArrayList<CentroOperacionDTO>> getHistorial(String token){
		if(storage!=null){
			StorageKey<HashMap<Date,ArrayList<CentroOperacionDTO>>> key = StorageKeyFactory.objectKey("historial-"+token);
			try{
				return (HashMap<Date,ArrayList<CentroOperacionDTO>>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}
	
	public void setHistorial(String token, HashMap<Date,ArrayList<CentroOperacionDTO>> historial){
		if(storage!=null){
			StorageKey<HashMap<Date,ArrayList<CentroOperacionDTO>>> key = StorageKeyFactory.objectKey("historial-"+token);
			try{
				storage.put(key, historial);
			}catch(SerializationException e){
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}catch(StorageQuotaExceededException e){
				storage.clear();
				logger.log(Level.WARNING, e.getLocalizedMessage());
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
	}
	
	public ArrayList<Integer> getMonitoreados(String token){
		if(storage!=null){
			StorageKey<ArrayList<Integer>> key = StorageKeyFactory.objectKey("monitor-"+token);
			try{
				return (ArrayList<Integer>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}
	
	public void setMonitoreados(String token, ArrayList<Integer> monitoreados){
		if(storage!=null){
			StorageKey<ArrayList<Integer>> key = StorageKeyFactory.objectKey("monitor-"+token);
			try{
				storage.put(key, monitoreados);
			}catch(SerializationException e){
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}catch(StorageQuotaExceededException e){
				storage.clear();
				logger.log(Level.WARNING, e.getLocalizedMessage());
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
	}
	
	public Integer getEvento(String token){
		if(storage!=null){
			StorageKey<Integer> key = StorageKeyFactory.objectKey("evento-"+token);
			try{
				return (Integer)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}
	
	public void setEvento(String token,Integer evento){
		if(storage!=null){
			StorageKey<Integer> key = StorageKeyFactory.objectKey("evento-"+token);
			try{
				storage.put(key, evento);
			}catch(SerializationException e){
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}catch(StorageQuotaExceededException e){
				storage.clear();
				logger.log(Level.WARNING, e.getLocalizedMessage());
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
	}
	
}
