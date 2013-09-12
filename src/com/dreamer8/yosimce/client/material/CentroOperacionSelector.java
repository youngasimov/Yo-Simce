package com.dreamer8.yosimce.client.material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.dreamer8.yosimce.client.ClientFactory;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionSelectorView;
import com.dreamer8.yosimce.client.material.ui.CentroOperacionSelectorView.CentroOperacionSelectorPresenter;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.google.gwt.user.client.Command;
import com.google.gwt.view.client.ListDataProvider;

public class CentroOperacionSelector implements
		CentroOperacionSelectorPresenter {

	private final CentroOperacionSelectorView view; 
	
	private final ListDataProvider<EmplazamientoDTO> provider;
	
	private EmplazamientoDTO selected;
	
	private Command selectedCommand;
	private Command autoHideCommand;
	
	public CentroOperacionSelector(ClientFactory factory, ArrayList<EmplazamientoDTO> emplazamientos) {
		this.view = factory.getCentroOperacionSelectorView();
		view.setPresenter(this);
		
		Collections.sort(emplazamientos, new Comparator<EmplazamientoDTO>() {

			@Override
			public int compare(EmplazamientoDTO o1, EmplazamientoDTO o2) {
				return o1.getNombre().compareToIgnoreCase(o2.getNombre());
			}
		});
		provider = new ListDataProvider<EmplazamientoDTO>(emplazamientos,EmplazamientoDTO.KEY_PROVIDER);
		provider.addDataDisplay(view.getDataDisplay());
	}

	public void show(){
		view.show();
	}
	
	public void hide(){
		view.hide();
	}
	
	public EmplazamientoDTO getSelectedEmplazamiento(){
		return selected;
	}
	
	public void setSelectedCommand(Command c){
		this.selectedCommand = c;
	}
	
	public void setAutoHideCommand(Command c){
		this.autoHideCommand = c;
	}

	@Override
	public void centroOperacionSelected(EmplazamientoDTO co) {
		selected = co;
		if(selectedCommand!=null){
			selectedCommand.execute();
		}
	}
	
	@Override
	public void onAutoHide() {
		if(autoHideCommand!=null){
			autoHideCommand.execute();
		}
	}

}
