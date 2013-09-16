package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dreamer8.yosimce.client.MensajeEvent;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.StorageExt;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyFactory;
import com.seanchenxi.gwt.storage.client.StorageQuotaExceededException;

public class LocalMaterialService {

	private StorageExt storage;
	private boolean supportMessage;
	private EventBus eventBus;
	private Logger logger = Logger.getLogger("");
	
	public LocalMaterialService(EventBus eventBus){
		storage = StorageExt.getLocalStorage();
		supportMessage = false;
		this.eventBus =eventBus;
	}
	
	public void setCentrosOperacionAsociados(String token,ArrayList<EmplazamientoDTO> value){
		if(storage!=null){
			StorageKey<ArrayList<EmplazamientoDTO>> key = StorageKeyFactory.objectKey("centrosasociados-"+token);
			try{
				storage.put(key, value);
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

	public ArrayList<EmplazamientoDTO> getCentrosOperacionAsociados(String token){
		if(storage!=null){
			StorageKey<ArrayList<EmplazamientoDTO>> key = StorageKeyFactory.objectKey("centrosasociados-"+token);
			try{
			return (ArrayList<EmplazamientoDTO>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}

	public void setCentrosOperacion(String token,ArrayList<EmplazamientoDTO> value){
		if(storage!=null){
			StorageKey<ArrayList<EmplazamientoDTO>> key = StorageKeyFactory.objectKey("centros-"+token);
			try{
				storage.put(key, value);
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
	
	public ArrayList<EmplazamientoDTO> getCentrosOperacion(String token){
		if(storage!=null){
			StorageKey<ArrayList<EmplazamientoDTO>> key = StorageKeyFactory.objectKey("centros-"+token);
			try{
			return (ArrayList<EmplazamientoDTO>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}

	public void setEtapas(String token, ArrayList<EtapaDTO> etapas){
		if(storage!=null){
			StorageKey<ArrayList<EtapaDTO>> key = StorageKeyFactory.objectKey("etapas-"+token);
			try{
				storage.put(key, etapas);
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
	
	public ArrayList<EtapaDTO> getEtapas(String token){
		if(storage!=null){
			StorageKey<ArrayList<EtapaDTO>> key = StorageKeyFactory.objectKey("etapas-"+token);
			try{
			return (ArrayList<EtapaDTO>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}

	public void setMateriales(String token, ArrayList<MaterialDTO> value){
		if(storage!=null){
			StorageKey<ArrayList<MaterialDTO>> key = StorageKeyFactory.objectKey("materiales-"+token);
			try{
				storage.put(key, value);
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
	
	public ArrayList<MaterialDTO> getMateriales(String token){
		if(storage!=null){
			StorageKey<ArrayList<MaterialDTO>> key = StorageKeyFactory.objectKey("materiales-"+token);
			try{
			return (ArrayList<MaterialDTO>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}
	
	public void setIngreso(String token, ArrayList<String> codigos){
		if(storage!=null){
			StorageKey<ArrayList<String>> key = StorageKeyFactory.objectKey("ingreso-"+token);
			try{
				storage.put(key, codigos);
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

	public ArrayList<String> getLastIngreso(String token){
		if(storage!=null){
			StorageKey<ArrayList<String>> key = StorageKeyFactory.objectKey("ingreso-"+token);
			try{
			return (ArrayList<String>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}

	public void removeLastIngreso(String token){
		if(storage!=null){
			StorageKey<ArrayList<String>> key = StorageKeyFactory.objectKey("ingreso-"+token);
			storage.remove(key);
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
	}
	
	public void setDespacho(String token, ArrayList<String> codigos){
		if(storage!=null){
			StorageKey<ArrayList<String>> key = StorageKeyFactory.objectKey("despacho-"+token);
			try{
				storage.put(key, codigos);
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

	public ArrayList<String> getLastDespacho(String token){
		if(storage!=null){
			StorageKey<ArrayList<String>> key = StorageKeyFactory.objectKey("despacho-"+token);
			try{
				return (ArrayList<String>)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}

	public void removeLastDespacho(String token){
		if(storage!=null){
			StorageKey<ArrayList<String>> key = StorageKeyFactory.objectKey("despacho-"+token);
			storage.remove(key);
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
	}

	public void setRetirante(String token, UserDTO user){
		if(storage!=null){
			StorageKey<UserDTO> key = StorageKeyFactory.objectKey("retirante-"+token);
			try{
				storage.put(key, user);
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
	
	public UserDTO getRetirante(String token){
		if(storage!=null){
			StorageKey<UserDTO> key = StorageKeyFactory.objectKey("retirante-"+token);
			try{
				return (UserDTO)storage.get(key);
			}catch(Throwable t){
				return null;
			}
		}else if(!supportMessage){
			supportMessage = true;
			eventBus.fireEvent(new MensajeEvent("Su navegador no soporta la función de almacenamiento local",MensajeEvent.MSG_WARNING,true));
		}
		return null;
	}
}

