package com.dreamer8.yosimce.client;

import java.util.ArrayList;

import com.dreamer8.yosimce.client.ui.TipoUsuarioSelectorView;
import com.dreamer8.yosimce.client.ui.TipoUsuarioSelectorView.TipoUsuarioSelectorPresenter;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.user.client.Command;
import com.google.gwt.view.client.ListDataProvider;

public class TipoUsuarioSelector implements TipoUsuarioSelectorPresenter {

	
	private final TipoUsuarioSelectorView view;
	
	private final ListDataProvider<TipoUsuarioDTO> provider;
	
	private TipoUsuarioDTO selected;
	
	private Command selectedCommand;
	
	public TipoUsuarioSelector(ClientFactory factory, ArrayList<TipoUsuarioDTO> tipos){
		this.view = factory.getTipoUsuarioSelectorView();
		view.setPresenter(this);
		
		provider = new ListDataProvider<TipoUsuarioDTO>(tipos,TipoUsuarioDTO.KEY_PROVIDER);
		provider.addDataDisplay(view.getDataDisplay());
	}
	
	@Override
	public void onTipoUsuarioSelected(TipoUsuarioDTO tudto) {
		selected = tudto;
		if(selectedCommand!=null){
			selectedCommand.execute();
		}
	}
	
	public TipoUsuarioDTO getSelectedTipoUsuario(){
		return selected;
	}
	
	public void setSelectedCommand(Command c){
		this.selectedCommand = c;
	}
	
	public void show(){
		view.show();
	}
	
	public void hide(){
		view.hide();
	}

}
